package ehacks.mod.modulesystem.classes.vanilla;

import ehacks.mod.api.Module;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.renderer.entity.RenderManager;
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
        double posX = RenderManager.renderPosX - (MathHelper.cos((player.rotationYaw / 180.0f * 3.141593f)) * 0.16f);
        double posY = RenderManager.renderPosY + player.getEyeHeight() - 0.1000000014901161;
        double posZ = RenderManager.renderPosZ - (MathHelper.sin((player.rotationYaw / 180.0f * 3.141593f)) * 0.16f);
        double motionX = ((-MathHelper.sin((player.rotationYaw / 180.0f * 3.141593f))) * MathHelper.cos((player.rotationPitch / 180.0f * 3.141593f))) * (bow ? 1.0 : 0.4);
        double motionY = (-MathHelper.sin((player.rotationPitch / 180.0f * 3.141593f))) * (bow ? 1.0 : 0.4);
        double motionZ = (MathHelper.cos((player.rotationYaw / 180.0f * 3.141593f)) * MathHelper.cos((player.rotationPitch / 180.0f * 3.141593f))) * (bow ? 1.0 : 0.4);
        if (player.getItemInUseCount() <= 0 && bow) {
            return;
        }
        int var6 = 72000 - player.getItemInUseCount();
        float power = var6 / 20.0f;
        if ((power = (power * power + power * 2.0f) / 3.0f) < 0.1) {
            return;
        }
        if (power > 1.0f) {
            power = 1.0f;
        }
        GL11.glColor3f((1.0f - power), power, 0.0f);
        float distance = MathHelper.sqrt_double((motionX * motionX + motionY * motionY + motionZ * motionZ));
        motionX /= distance;
        motionY /= distance;
        motionZ /= distance;
        motionX *= (bow ? power * 2.0f : 1.0f) * 1.5;
        motionY *= (bow ? power * 2.0f : 1.0f) * 1.5;
        motionZ *= (bow ? power * 2.0f : 1.0f) * 1.5;
        GL11.glLineWidth(2.0f);
        GL11.glBegin(3);
        boolean hasLanded = false;
        boolean isEntity = false;
        MovingObjectPosition landingPosition = null;
        float size = (float) (bow ? 0.3 : 0.25);
        while (!hasLanded) {
            Vec3 present = Vec3.createVectorHelper(posX, posY, posZ);
            Vec3 future = Vec3.createVectorHelper((posX + motionX), (posY + motionY), (posZ + motionZ));
            MovingObjectPosition possibleLandingStrip = Wrapper.INSTANCE.world().func_147447_a(present, future, false, true, true);
            present = Vec3.createVectorHelper(posX, posY, posZ);
            future = Vec3.createVectorHelper((posX + motionX), (posY + motionY), (posZ + motionZ));
            if (possibleLandingStrip != null) {
                hasLanded = true;
                landingPosition = possibleLandingStrip;
            }
            AxisAlignedBB arrowBox = AxisAlignedBB.getBoundingBox((posX - size), (posY - size), (posZ - size), (posX + size), (posY + size), (posZ + size));
            @SuppressWarnings("unchecked")
            List<Entity> entities = this.getEntitiesWithinAABB(arrowBox.addCoord(motionX, motionY, motionZ).expand(1.0, 1.0, 1.0));
            for (Entity entity1 : entities) {
                float var11;
                MovingObjectPosition possibleEntityLanding;
                if (!entity1.canBeCollidedWith() || entity1 == player || (possibleEntityLanding = (entity1.boundingBox.expand((var11 = 0.3f), var11, var11)).calculateIntercept(present, future)) == null) {
                    continue;
                }
                hasLanded = true;
                isEntity = true;
                landingPosition = possibleEntityLanding;
            }
            float motionAdjustment = 0.99f;
            if (this.isInMaterial(AxisAlignedBB.getBoundingBox((posX - size), (posY - size), (posZ - size), ((posX += motionX) + size), ((posY += motionY) + size), ((posZ += motionZ) + size)), Material.water)) {
                motionAdjustment = 0.8f;
            }
            motionX *= motionAdjustment;
            motionY *= motionAdjustment;
            motionZ *= motionAdjustment;
            motionY -= bow ? 0.05 : 0.03;
            GL11.glVertex3d((posX - RenderManager.renderPosX), (posY - RenderManager.renderPosY), (posZ - RenderManager.renderPosZ));
        }
        GL11.glEnd();
        GL11.glPushMatrix();
        GL11.glTranslated((posX - RenderManager.renderPosX), (posY - RenderManager.renderPosY), (posZ - RenderManager.renderPosZ));
        switch (landingPosition.sideHit) {
            case 2: {
                GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
                break;
            }
            case 3: {
                GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
                break;
            }
            case 4: {
                GL11.glRotatef(90.0f, 0.0f, 0.0f, 1.0f);
                break;
            }
            case 5: {
                GL11.glRotatef(90.0f, 0.0f, 0.0f, 1.0f);
            }
        }
        if (isEntity) {
            GL11.glColor3f(1.0f, 0.0f, 0.0f);
        }
        this.renderPoint();
        GL11.glPopMatrix();
    }

    private void renderPoint() {
        GL11.glBegin(1);
        GL11.glVertex3d(-0.5, 0.0, 0.0);
        GL11.glVertex3d(0.0, 0.0, 0.0);
        GL11.glVertex3d(0.0, 0.0, -0.5);
        GL11.glVertex3d(0.0, 0.0, 0.0);
        GL11.glVertex3d(0.5, 0.0, 0.0);
        GL11.glVertex3d(0.0, 0.0, 0.0);
        GL11.glVertex3d(0.0, 0.0, 0.5);
        GL11.glVertex3d(0.0, 0.0, 0.0);
        GL11.glEnd();
        Cylinder c = new Cylinder();
        GL11.glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);
        c.setDrawStyle(100011);
        c.draw(0.5f, 0.5f, 0.1f, 24, 1);
    }

    private boolean isInMaterial(AxisAlignedBB axisalignedBB, Material material) {
        int chunkMinX = MathHelper.floor_double(axisalignedBB.minX);
        int chunkMaxX = MathHelper.floor_double((axisalignedBB.maxX + 1.0));
        int chunkMinY = MathHelper.floor_double(axisalignedBB.minY);
        int chunkMaxY = MathHelper.floor_double((axisalignedBB.maxY + 1.0));
        int chunkMinZ = MathHelper.floor_double(axisalignedBB.minZ);
        int chunkMaxZ = MathHelper.floor_double((axisalignedBB.maxZ + 1.0));
        if (!Wrapper.INSTANCE.world().checkChunksExist(chunkMinX, chunkMinY, chunkMinZ, chunkMaxX, chunkMaxY, chunkMaxZ)) {
            return false;
        }
        boolean isWithin = false;
        for (int x = chunkMinX; x < chunkMaxX; ++x) {
            for (int y = chunkMinY; y < chunkMaxY; ++y) {
                for (int z = chunkMinZ; z < chunkMaxZ; ++z) {
                    Block block = Block.getBlockById(Wrapper.INSTANCE.world().getBlockMetadata(x, y, z));
                    if (block == null || block.getMaterial() != material || chunkMaxY < ((y + 1) - BlockLiquid.getLiquidHeightPercent(Wrapper.INSTANCE.world().getBlockMetadata(x, y, z)))) {
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
        int chunkMinX = MathHelper.floor_double(((axisalignedBB.minX - 2.0) / 16.0));
        int chunkMaxX = MathHelper.floor_double(((axisalignedBB.maxX + 2.0) / 16.0));
        int chunkMinZ = MathHelper.floor_double(((axisalignedBB.minZ - 2.0) / 16.0));
        int chunkMaxZ = MathHelper.floor_double(((axisalignedBB.maxZ + 2.0) / 16.0));
        for (int x = chunkMinX; x <= chunkMaxX; ++x) {
            for (int z = chunkMinZ; z <= chunkMaxZ; ++z) {
                if (!Wrapper.INSTANCE.world().getChunkProvider().chunkExists(x, z)) {
                    continue;
                }
                Wrapper.INSTANCE.world().getChunkFromChunkCoords(x, z).getEntitiesWithinAABBForEntity(Wrapper.INSTANCE.player(), axisalignedBB, list, null);
            }
        }
        return list;
    }
}
