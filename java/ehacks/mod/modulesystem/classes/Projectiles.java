package ehacks.mod.modulesystem.classes;

import ehacks.api.module.Module;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemSnowball;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Cylinder;

public class Projectiles
        extends Module {

    public Projectiles() {
        super(ModuleCategory.RENDER);
    }

    @Override
    public String getName() {
        return "Projectiles";
    }

    @Override
    public String getDescription() {
        return "Allows you to see all projectiles";
    }

    @Override
    public void onWorldRender(RenderWorldLastEvent event) {
        boolean bow = false;
        EntityClientPlayerMP player = Wrapper.INSTANCE.player();
        if (player.getCurrentEquippedItem() != null) {
            Item item = player.getCurrentEquippedItem().getItem();
            if (!(item instanceof ItemBow || item instanceof ItemSnowball || item instanceof ItemEnderPearl || item instanceof ItemEgg)) {
                return;
            }
            if (item instanceof ItemBow) {
                bow = true;
            }
        } else {
            return;
        }
        double posX = RenderManager.renderPosX - (double) (MathHelper.cos((float) (player.rotationYaw / 180.0f * 3.141593f)) * 0.16f);
        double posY = RenderManager.renderPosY + (double) player.getEyeHeight() - 0.1000000014901161;
        double posZ = RenderManager.renderPosZ - (double) (MathHelper.sin((float) (player.rotationYaw / 180.0f * 3.141593f)) * 0.16f);
        double motionX = (double) ((-MathHelper.sin((float) (player.rotationYaw / 180.0f * 3.141593f))) * MathHelper.cos((float) (player.rotationPitch / 180.0f * 3.141593f))) * (bow ? 1.0 : 0.4);
        double motionY = (double) (-MathHelper.sin((float) (player.rotationPitch / 180.0f * 3.141593f))) * (bow ? 1.0 : 0.4);
        double motionZ = (double) (MathHelper.cos((float) (player.rotationYaw / 180.0f * 3.141593f)) * MathHelper.cos((float) (player.rotationPitch / 180.0f * 3.141593f))) * (bow ? 1.0 : 0.4);
        if (player.getItemInUseCount() <= 0 && bow) {
            return;
        }
        int var6 = 72000 - player.getItemInUseCount();
        float power = (float) var6 / 20.0f;
        if ((double) (power = (power * power + power * 2.0f) / 3.0f) < 0.1) {
            return;
        }
        if (power > 1.0f) {
            power = 1.0f;
        }
        GL11.glColor3f((float) (1.0f - power), (float) power, (float) 0.0f);
        float distance = MathHelper.sqrt_double((double) (motionX * motionX + motionY * motionY + motionZ * motionZ));
        motionX /= (double) distance;
        motionY /= (double) distance;
        motionZ /= (double) distance;
        motionX *= (double) (bow ? power * 2.0f : 1.0f) * 1.5;
        motionY *= (double) (bow ? power * 2.0f : 1.0f) * 1.5;
        motionZ *= (double) (bow ? power * 2.0f : 1.0f) * 1.5;
        GL11.glLineWidth((float) 2.0f);
        GL11.glBegin((int) 3);
        boolean hasLanded = false;
        boolean isEntity = false;
        MovingObjectPosition landingPosition = null;
        float size = (float) (bow ? 0.3 : 0.25);
        while (!hasLanded) {
            AxisAlignedBB boundingBox;
            Vec3 present = Vec3.createVectorHelper((double) posX, (double) posY, (double) posZ);
            Vec3 future = Vec3.createVectorHelper((double) (posX + motionX), (double) (posY + motionY), (double) (posZ + motionZ));
            MovingObjectPosition possibleLandingStrip = Wrapper.INSTANCE.world().func_147447_a(present, future, false, true, true);
            present = Vec3.createVectorHelper((double) posX, (double) posY, (double) posZ);
            future = Vec3.createVectorHelper((double) (posX + motionX), (double) (posY + motionY), (double) (posZ + motionZ));
            if (possibleLandingStrip != null) {
                hasLanded = true;
                landingPosition = possibleLandingStrip;
            }
            Object hitEntity = null;
            AxisAlignedBB arrowBox = AxisAlignedBB.getBoundingBox((double) (posX - (double) size), (double) (posY - (double) size), (double) (posZ - (double) size), (double) (posX + (double) size), (double) (posY + (double) size), (double) (posZ + (double) size));
            List entities = this.getEntitiesWithinAABB(arrowBox.addCoord(motionX, motionY, motionZ).expand(1.0, 1.0, 1.0));
            for (int index = 0; index < entities.size(); ++index) {
                float var11;
                MovingObjectPosition possibleEntityLanding;
                AxisAlignedBB var12;
                Entity entity = (Entity) entities.get(index);
                if (!entity.canBeCollidedWith() || entity == player || (possibleEntityLanding = (var12 = entity.boundingBox.expand((double) (var11 = 0.3f), (double) var11, (double) var11)).calculateIntercept(present, future)) == null) {
                    continue;
                }
                hasLanded = true;
                isEntity = true;
                landingPosition = possibleEntityLanding;
            }
            float motionAdjustment = 0.99f;
            if (this.isInMaterial(boundingBox = AxisAlignedBB.getBoundingBox((double) (posX - (double) size), (double) (posY - (double) size), (double) (posZ - (double) size), (double) ((posX += motionX) + (double) size), (double) ((posY += motionY) + (double) size), (double) ((posZ += motionZ) + (double) size)), Material.water)) {
                motionAdjustment = 0.8f;
            }
            motionX *= (double) motionAdjustment;
            motionY *= (double) motionAdjustment;
            motionZ *= (double) motionAdjustment;
            motionY -= bow ? 0.05 : 0.03;
            GL11.glVertex3d((double) (posX - RenderManager.renderPosX), (double) (posY - RenderManager.renderPosY), (double) (posZ - RenderManager.renderPosZ));
        }
        GL11.glEnd();
        GL11.glPushMatrix();
        GL11.glTranslated((double) (posX - RenderManager.renderPosX), (double) (posY - RenderManager.renderPosY), (double) (posZ - RenderManager.renderPosZ));
        if (landingPosition != null) {
            switch (landingPosition.sideHit) {
                case 2: {
                    GL11.glRotatef((float) 90.0f, (float) 1.0f, (float) 0.0f, (float) 0.0f);
                    break;
                }
                case 3: {
                    GL11.glRotatef((float) 90.0f, (float) 1.0f, (float) 0.0f, (float) 0.0f);
                    break;
                }
                case 4: {
                    GL11.glRotatef((float) 90.0f, (float) 0.0f, (float) 0.0f, (float) 1.0f);
                    break;
                }
                case 5: {
                    GL11.glRotatef((float) 90.0f, (float) 0.0f, (float) 0.0f, (float) 1.0f);
                }
            }
            if (isEntity) {
                GL11.glColor3f((float) 1.0f, (float) 0.0f, (float) 0.0f);
            }
        }
        this.renderPoint();
        GL11.glPopMatrix();
    }

    private void renderPoint() {
        GL11.glBegin((int) 1);
        GL11.glVertex3d((double) -0.5, (double) 0.0, (double) 0.0);
        GL11.glVertex3d((double) 0.0, (double) 0.0, (double) 0.0);
        GL11.glVertex3d((double) 0.0, (double) 0.0, (double) -0.5);
        GL11.glVertex3d((double) 0.0, (double) 0.0, (double) 0.0);
        GL11.glVertex3d((double) 0.5, (double) 0.0, (double) 0.0);
        GL11.glVertex3d((double) 0.0, (double) 0.0, (double) 0.0);
        GL11.glVertex3d((double) 0.0, (double) 0.0, (double) 0.5);
        GL11.glVertex3d((double) 0.0, (double) 0.0, (double) 0.0);
        GL11.glEnd();
        Cylinder c = new Cylinder();
        GL11.glRotatef((float) -90.0f, (float) 1.0f, (float) 0.0f, (float) 0.0f);
        c.setDrawStyle(100011);
        c.draw(0.5f, 0.5f, 0.1f, 24, 1);
    }

    private boolean isInMaterial(AxisAlignedBB axisalignedBB, Material material) {
        int chunkMinX = MathHelper.floor_double((double) axisalignedBB.minX);
        int chunkMaxX = MathHelper.floor_double((double) (axisalignedBB.maxX + 1.0));
        int chunkMinY = MathHelper.floor_double((double) axisalignedBB.minY);
        int chunkMaxY = MathHelper.floor_double((double) (axisalignedBB.maxY + 1.0));
        int chunkMinZ = MathHelper.floor_double((double) axisalignedBB.minZ);
        int chunkMaxZ = MathHelper.floor_double((double) (axisalignedBB.maxZ + 1.0));
        if (!Wrapper.INSTANCE.world().checkChunksExist(chunkMinX, chunkMinY, chunkMinZ, chunkMaxX, chunkMaxY, chunkMaxZ)) {
            return false;
        }
        boolean isWithin = false;
        Vec3 vector = Vec3.createVectorHelper((double) 0.0, (double) 0.0, (double) 0.0);
        for (int x = chunkMinX; x < chunkMaxX; ++x) {
            for (int y = chunkMinY; y < chunkMaxY; ++y) {
                for (int z = chunkMinZ; z < chunkMaxZ; ++z) {
                    double liquidHeight;
                    Block block = Block.getBlockById((int) Wrapper.INSTANCE.world().getBlockMetadata(x, y, z));
                    if (block == null || block.getMaterial() != material || (double) chunkMaxY < (liquidHeight = (double) ((float) (y + 1) - BlockLiquid.getLiquidHeightPercent((int) Wrapper.INSTANCE.world().getBlockMetadata(x, y, z))))) {
                        continue;
                    }
                    isWithin = true;
                }
            }
        }
        return isWithin;
    }

    private List getEntitiesWithinAABB(AxisAlignedBB axisalignedBB) {
        ArrayList list = new ArrayList();
        int chunkMinX = MathHelper.floor_double((double) ((axisalignedBB.minX - 2.0) / 16.0));
        int chunkMaxX = MathHelper.floor_double((double) ((axisalignedBB.maxX + 2.0) / 16.0));
        int chunkMinZ = MathHelper.floor_double((double) ((axisalignedBB.minZ - 2.0) / 16.0));
        int chunkMaxZ = MathHelper.floor_double((double) ((axisalignedBB.maxZ + 2.0) / 16.0));
        for (int x = chunkMinX; x <= chunkMaxX; ++x) {
            for (int z = chunkMinZ; z <= chunkMaxZ; ++z) {
                if (!Wrapper.INSTANCE.world().getChunkProvider().chunkExists(x, z)) {
                    continue;
                }
                Wrapper.INSTANCE.world().getChunkFromChunkCoords(x, z).getEntitiesWithinAABBForEntity((Entity) Wrapper.INSTANCE.player(), axisalignedBB, list, (IEntitySelector) null);
            }
        }
        return list;
    }
}
