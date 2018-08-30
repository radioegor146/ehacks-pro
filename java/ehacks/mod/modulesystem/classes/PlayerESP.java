package ehacks.mod.modulesystem.classes;

import ehacks.api.module.Module;
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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;

public class PlayerESP
        extends Module {

    public PlayerESP() {
        super(ModuleCategory.RENDER);
    }

    @Override
    public String getName() {
        return "PlayerESP";
    }

    @Override
    public String getDescription() {
        return "Allows you to see all players around you";
    }

    @Override
    public void onWorldRender(RenderWorldLastEvent event) {
        if (!GLUtils.hasClearedDepth) {
            GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
            GLUtils.hasClearedDepth = true;
        }
        List<Entity> entities = Wrapper.INSTANCE.world().loadedEntityList;
        for (Entity ent : entities) {
            if (!(ent instanceof EntityPlayer))
                continue;
            if (ent == Wrapper.INSTANCE.player())
                continue;
            if (!ent.isInRangeToRender3d(Wrapper.INSTANCE.mc().renderViewEntity.getPosition(event.partialTicks).xCoord, Wrapper.INSTANCE.mc().renderViewEntity.getPosition(event.partialTicks).yCoord, Wrapper.INSTANCE.mc().renderViewEntity.getPosition(event.partialTicks).zCoord) || ent == Wrapper.INSTANCE.mc().renderViewEntity && Wrapper.INSTANCE.mcSettings().thirdPersonView == 0 && !Wrapper.INSTANCE.mc().renderViewEntity.isPlayerSleeping())
                continue;
            double xPos = ent.lastTickPosX + (ent.posX - ent.lastTickPosX) * (double)event.partialTicks;
            double yPos = ent.lastTickPosY + (ent.posY - ent.lastTickPosY) * (double)event.partialTicks;
            double zPos = ent.lastTickPosZ + (ent.posZ - ent.lastTickPosZ) * (double)event.partialTicks;
            float f1 = ent.prevRotationYaw + (ent.rotationYaw - ent.prevRotationYaw) * event.partialTicks;
            RenderHelper.enableStandardItemLighting();
            //RenderManager.instance.renderEntitySimple(ent, event.partialTicks);
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)240 / 1.0F, (float)240 / 1.0F);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            RenderManager.instance.func_147939_a(ent, xPos - renderPosX, yPos - renderPosY, zPos - renderPosZ, f1, event.partialTicks, false);
        }
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240f, 240f);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        GL11.glDisable(GL11.GL_LIGHTING);
        for (Object o : Wrapper.INSTANCE.world().loadedEntityList) {
            Entity entObj = (Entity) o;
            if (!(entObj instanceof EntityPlayer))
                continue;
            if (entObj == Wrapper.INSTANCE.player())
                continue;
            EntityLivingBase ent = (EntityLivingBase)entObj;
            float labelScale = 0.04F;
            GL11.glPushMatrix();
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            GL11.glDepthMask(true);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            //GL11.glDisable(GL11.GL_DEPTH_TEST);
            //GL11.glDepthMask(false);

            double xPos = ent.lastTickPosX + (ent.posX - ent.lastTickPosX) * (double)event.partialTicks;
            double yPos = ent.lastTickPosY + (ent.posY - ent.lastTickPosY) * (double)event.partialTicks;
            double zPos = ent.lastTickPosZ + (ent.posZ - ent.lastTickPosZ) * (double)event.partialTicks;

            
            double xEnd = xPos - RenderManager.renderPosX;
            double yEnd = yPos + ent.height / 2 - RenderManager.renderPosY;
            double zEnd = zPos - RenderManager.renderPosZ;

            GL11.glTranslatef((float) xEnd, (float) yEnd + ent.height / 2 + .8f, (float) zEnd);
            GL11.glRotatef(-RenderManager.instance.playerViewY, 0, 1, 0);
            GL11.glRotatef(RenderManager.instance.playerViewX, 1, 0, 0);
            GL11.glScalef(-labelScale, -labelScale, labelScale);
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240f, 240f);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GLUtils.drawRect(-26f, -1f, 26f, 12f, GLUtils.getColor(255, 64, 64, 64));
            GL11.glTranslatef(0f, 0f, -0.1f);
            GLUtils.drawRect(-25f, 6f, -25f + (50f * ent.getHealth() / ent.getMaxHealth()), 11f, GLUtils.getColor(255, 0, 255, 0));
            GLUtils.drawRect(-25f + (50f * ent.getHealth() / ent.getMaxHealth()), 6f, 25f, 11f, GLUtils.getColor(255, 255, 0, 0));
            GL11.glScalef(Wrapper.INSTANCE.fontRenderer().FONT_HEIGHT / 17f, Wrapper.INSTANCE.fontRenderer().FONT_HEIGHT / 17f, 1f);
            GL11.glTranslatef(0f, 0f, -0.1f);
            Wrapper.INSTANCE.fontRenderer().drawString(String.format("%.2f", Math.round(ent.getHealth() * 100) / 100f), -Wrapper.INSTANCE.fontRenderer().getStringWidth(String.format("%.2f", Math.round(ent.getHealth() * 100) / 100f)) / 2, 12, GLUtils.getColor(255, 255, 255, 255), true);
            Wrapper.INSTANCE.fontRenderer().drawString(String.valueOf(ent.getCommandSenderName()), -47, 1, GLUtils.getColor(255, 255, 255, 255), true);
            GL11.glPopMatrix();
        }
    }
}
