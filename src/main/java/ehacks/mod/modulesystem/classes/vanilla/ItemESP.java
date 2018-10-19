package ehacks.mod.modulesystem.classes.vanilla;

import ehacks.mod.api.Module;
import ehacks.mod.util.GLUtils;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import java.util.List;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import static net.minecraft.client.renderer.entity.RenderManager.renderPosX;
import static net.minecraft.client.renderer.entity.RenderManager.renderPosY;
import static net.minecraft.client.renderer.entity.RenderManager.renderPosZ;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;

public class ItemESP
        extends Module {

    public ItemESP() {
        super(ModuleCategory.RENDER);
    }

    @Override
    public String getName() {
        return "ItemESP";
    }

    @Override
    public String getDescription() {
        return "Allows you to see all items around you";
    }

    @Override
    public void onWorldRender(RenderWorldLastEvent event) {
        if (!GLUtils.hasClearedDepth) {
            GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
            GLUtils.hasClearedDepth = true;
        }
        @SuppressWarnings("unchecked")
        List<Entity> entities = Wrapper.INSTANCE.world().loadedEntityList;
        entities.stream().filter((ent) -> !(!(ent instanceof EntityItem))).filter((ent) -> !(ent == Wrapper.INSTANCE.mc().renderViewEntity && Wrapper.INSTANCE.mcSettings().thirdPersonView == 0 && !Wrapper.INSTANCE.mc().renderViewEntity.isPlayerSleeping())).forEachOrdered((ent) -> {
            double xPos = ent.lastTickPosX + (ent.posX - ent.lastTickPosX) * event.partialTicks;
            double yPos = ent.lastTickPosY + (ent.posY - ent.lastTickPosY) * event.partialTicks;
            double zPos = ent.lastTickPosZ + (ent.posZ - ent.lastTickPosZ) * event.partialTicks;
            float f1 = ent.prevRotationYaw + (ent.rotationYaw - ent.prevRotationYaw) * event.partialTicks;
            RenderHelper.enableStandardItemLighting();
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240 / 1.0F, 240 / 1.0F);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            RenderManager.instance.func_147939_a(ent, xPos - renderPosX, yPos - renderPosY, zPos - renderPosZ, f1, event.partialTicks, false);
            this.renderDebugBoundingBox(ent, xPos - renderPosX, yPos - renderPosY, zPos - renderPosZ, f1, event.partialTicks);
        });
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240f, 240f);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        GL11.glDisable(GL11.GL_LIGHTING);
        Wrapper.INSTANCE.world().loadedEntityList.stream().map((o) -> (Entity) o).filter((entObj) -> !(!(entObj instanceof EntityItem))).map((entObj) -> (EntityItem) entObj).map((ent) -> {
            float labelScale = 0.04F;
            GL11.glPushMatrix();
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            GL11.glDepthMask(true);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            Entity entity = (Entity)ent;
            double xPos = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * event.partialTicks;
            double yPos = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * event.partialTicks;
            double zPos = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * event.partialTicks;
            double xEnd = xPos - RenderManager.renderPosX;
            double yEnd = yPos + entity.height / 2 - RenderManager.renderPosY;
            double zEnd = zPos - RenderManager.renderPosZ;
            GL11.glTranslatef((float) xEnd, (float) yEnd + entity.height / 2 + .3f, (float) zEnd);
            GL11.glRotatef(-RenderManager.instance.playerViewY, 0, 1, 0);
            GL11.glRotatef(RenderManager.instance.playerViewX, 1, 0, 0);
            GL11.glScalef(-labelScale, -labelScale, labelScale);
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240f, 240f);
            return ent;
        }).map((ent) -> {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            return ent;
        }).map((ent) -> {
            GL11.glScalef(.7f, .7f, 1f);
            return ent;
        }).map((ent) -> {
            EntityItem entity = (EntityItem)ent;
            Wrapper.INSTANCE.fontRenderer().drawStringWithShadow(entity.getEntityItem().stackSize + "x[" + entity.getEntityItem().getRarity().rarityColor + entity.getEntityItem().getDisplayName() + "\u00a7f]", -Wrapper.INSTANCE.fontRenderer().getStringWidth(entity.getEntityItem().stackSize + "x[" + entity.getEntityItem().getDisplayName() + "]") / 2, 0, GLUtils.getColor(255, 255, 255));
            return ent;
        }).forEachOrdered((_item) -> {
            GL11.glPopMatrix();
        });
    }

    private void renderDebugBoundingBox(Entity p_85094_1_, double p_85094_2_, double p_85094_4_, double p_85094_6_, float p_85094_8_, float p_85094_9_) {
        GL11.glDepthMask(false);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_BLEND);
        float f2 = p_85094_1_.width / 2.0F;
        AxisAlignedBB axisalignedbb = AxisAlignedBB.getBoundingBox(p_85094_2_ - f2, p_85094_4_, p_85094_6_ - f2, p_85094_2_ + f2, p_85094_4_ + p_85094_1_.height, p_85094_6_ + f2);
        GL11.glLineWidth(1f);
        RenderGlobal.drawOutlinedBoundingBox(axisalignedbb, 16777215);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDepthMask(true);
    }
}
