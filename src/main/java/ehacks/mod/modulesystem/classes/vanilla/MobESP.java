package ehacks.mod.modulesystem.classes.vanilla;

import ehacks.mod.api.Module;
import ehacks.mod.util.GLUtils;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import java.util.List;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import static net.minecraft.client.renderer.entity.RenderManager.renderPosX;
import static net.minecraft.client.renderer.entity.RenderManager.renderPosY;
import static net.minecraft.client.renderer.entity.RenderManager.renderPosZ;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;

public class MobESP
        extends Module {

    public MobESP() {
        super(ModuleCategory.RENDER);
    }

    @Override
    public String getName() {
        return "MobESP";
    }

    @Override
    public String getDescription() {
        return "Allows you to see all mobs around you";
    }

    @Override
    public void onWorldRender(RenderWorldLastEvent event) {
        if (!GLUtils.hasClearedDepth) {
            GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
            GLUtils.hasClearedDepth = true;
        }
        @SuppressWarnings("unchecked")
        List<Entity> entities = Wrapper.INSTANCE.world().loadedEntityList;
        entities.stream().filter((ent) -> !(!(ent instanceof EntityLivingBase))).filter((ent) -> !(ent == Wrapper.INSTANCE.player())).filter((ent) -> !(!ent.isInRangeToRender3d(Wrapper.INSTANCE.mc().renderViewEntity.getPosition(event.partialTicks).xCoord, Wrapper.INSTANCE.mc().renderViewEntity.getPosition(event.partialTicks).yCoord, Wrapper.INSTANCE.mc().renderViewEntity.getPosition(event.partialTicks).zCoord) || ent == Wrapper.INSTANCE.mc().renderViewEntity && Wrapper.INSTANCE.mcSettings().thirdPersonView == 0 && !Wrapper.INSTANCE.mc().renderViewEntity.isPlayerSleeping())).forEachOrdered((ent) -> {
            double xPos = ent.lastTickPosX + (ent.posX - ent.lastTickPosX) * event.partialTicks;
            double yPos = ent.lastTickPosY + (ent.posY - ent.lastTickPosY) * event.partialTicks;
            double zPos = ent.lastTickPosZ + (ent.posZ - ent.lastTickPosZ) * event.partialTicks;
            float f1 = ent.prevRotationYaw + (ent.rotationYaw - ent.prevRotationYaw) * event.partialTicks;
            RenderHelper.enableStandardItemLighting();
            //RenderManager.instance.renderEntitySimple(ent, event.partialTicks);
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240 / 1.0F, 240 / 1.0F);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            RenderManager.instance.func_147939_a(ent, xPos - renderPosX, yPos - renderPosY, zPos - renderPosZ, f1, event.partialTicks, false);
        });
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240f, 240f);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        GL11.glDisable(GL11.GL_LIGHTING);
        Wrapper.INSTANCE.world().loadedEntityList.stream().map((o) -> (Entity) o).filter((entObj) -> !(!(entObj instanceof EntityLivingBase))).map((entObj) -> (EntityLivingBase) entObj).map((ent) -> {
            float labelScale = 0.04F;
            GL11.glPushMatrix();
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            GL11.glDepthMask(true);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            //GL11.glDisable(GL11.GL_DEPTH_TEST);
            //GL11.glDepthMask(false);
            Entity entity = (Entity) ent;
            double xPos = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * event.partialTicks;
            double yPos = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * event.partialTicks;
            double zPos = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * event.partialTicks;
            double xEnd = xPos - RenderManager.renderPosX;
            double yEnd = yPos + entity.height / 2 - RenderManager.renderPosY;
            double zEnd = zPos - RenderManager.renderPosZ;
            GL11.glTranslatef((float) xEnd, (float) yEnd + entity.height / 2 + .8f, (float) zEnd);
            GL11.glRotatef(-RenderManager.instance.playerViewY, 0, 1, 0);
            GL11.glRotatef(RenderManager.instance.playerViewX, 1, 0, 0);
            GL11.glScalef(-labelScale, -labelScale, labelScale);
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240f, 240f);
            return ent;
        }).map((ent) -> {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            return ent;
        }).map((ent) -> {
            GLUtils.drawRect(-26f, -1f, 26f, 12f, GLUtils.getColor(255, 64, 64, 64));
            return ent;
        }).map((ent) -> {
            GL11.glTranslatef(0f, 0f, -0.1f);
            return ent;
        }).map((ent) -> {
            EntityLivingBase entity = (EntityLivingBase) ent;
            GLUtils.drawRect(-25f, 6f, -25f + (50f * entity.getHealth() / entity.getMaxHealth()), 11f, GLUtils.getColor(255, 0, 255, 0));
            return ent;
        }).map((ent) -> {
            EntityLivingBase entity = (EntityLivingBase) ent;
            GLUtils.drawRect(-25f + (50f * entity.getHealth() / entity.getMaxHealth()), 6f, 25f, 11f, GLUtils.getColor(255, 255, 0, 0));
            return ent;
        }).map((ent) -> {
            GL11.glScalef(Wrapper.INSTANCE.fontRenderer().FONT_HEIGHT / 17f, Wrapper.INSTANCE.fontRenderer().FONT_HEIGHT / 17f, 1f);
            return ent;
        }).map((ent) -> {
            GL11.glTranslatef(0f, 0f, -0.1f);
            return ent;
        }).map((ent) -> {
            EntityLivingBase entity = (EntityLivingBase) ent;
            Wrapper.INSTANCE.fontRenderer().drawString(String.format("%.2f", Math.round(entity.getHealth() * 100) / 100f), -Wrapper.INSTANCE.fontRenderer().getStringWidth(String.format("%.2f", Math.round(entity.getHealth() * 100) / 100f)) / 2, 12, GLUtils.getColor(255, 255, 255, 255), true);
            return ent;
        }).map((ent) -> {
            Entity entity = (Entity) ent;
            Wrapper.INSTANCE.fontRenderer().drawString(String.valueOf(entity.getCommandSenderName()), -47, 1, GLUtils.getColor(255, 255, 255, 255), true);
            return ent;
        }).forEachOrdered((_item) -> {
            GL11.glPopMatrix();
        });
    }
}
