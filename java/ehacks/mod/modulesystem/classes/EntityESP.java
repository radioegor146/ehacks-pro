/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.WorldClient
 *  net.minecraft.client.renderer.entity.RenderManager
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.util.Session
 *  org.lwjgl.opengl.GL11
 */
package ehacks.mod.modulesystem.classes;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Session;
import org.lwjgl.opengl.GL11;
import ehacks.api.module.Mod;
import ehacks.mod.external.axis.AltAxisAlignedBB;
import ehacks.mod.util.GLUtils;
import ehacks.mod.wrapper.ModuleCategories;
import ehacks.mod.wrapper.Wrapper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraftforge.client.event.RenderWorldLastEvent;

public class EntityESP
extends Mod {
    public EntityESP() {
        super(ModuleCategories.RENDER);
    }

    @Override
    public String getName() {
        return "Entity ESP";
    }

    @Override
    public void onWorldRender(RenderWorldLastEvent event) {
        GL11.glPushMatrix();
        //GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
        //GL11.glEnable(GL11.GL_BLEND);
        RenderHelper.enableStandardItemLighting();
        List<Entity> entities = Wrapper.INSTANCE.mc().theWorld.loadedEntityList;
        for (Entity ent : entities) {
            double d1;
            double d0;
            double d2;
            if (!ent.isInRangeToRender3d(d0 = Wrapper.INSTANCE.mc().renderViewEntity.getPosition(event.partialTicks).xCoord, d1 = Wrapper.INSTANCE.mc().renderViewEntity.getPosition(event.partialTicks).yCoord, d2 = Wrapper.INSTANCE.mc().renderViewEntity.getPosition(event.partialTicks).zCoord) || ent == Wrapper.INSTANCE.mc().renderViewEntity && Wrapper.INSTANCE.mc().gameSettings.thirdPersonView == 0 && !Wrapper.INSTANCE.mc().renderViewEntity.isPlayerSleeping()) continue;
            RenderManager.instance.renderEntitySimple(ent, event.partialTicks);
        }
        RenderHelper.disableStandardItemLighting();
        //GL11.glDisable(GL11.GL_BLEND); 
        GL11.glPopMatrix();
        
        for (Object o : Wrapper.INSTANCE.world().loadedEntityList) {
            Entity ent = (Entity)o;
            String text = ent.getClass().getName();
            float labelScale = 0.08F;
            GL11.glPushMatrix();
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            GL11.glDepthMask(false);

            double xEnd = ent.posX - RenderManager.instance.renderPosX;
            double yEnd = ent.posY + ent.height / 2 - RenderManager.instance.renderPosY;
            double zEnd = ent.posZ - RenderManager.instance.renderPosZ;
            
            GL11.glTranslatef((float)xEnd, (float)yEnd + ent.height + 0.5F, (float)zEnd);
            GL11.glRotatef(-RenderManager.instance.playerViewY, 0, 1, 0);
            GL11.glRotatef(RenderManager.instance.playerViewX, 1, 0, 0);
            GL11.glScalef(-labelScale, -labelScale, labelScale);
            Wrapper.INSTANCE.fontRenderer().drawString(ent.getClass().getSimpleName(), -Wrapper.INSTANCE.fontRenderer().getStringWidth(ent.getClass().getSimpleName()) / 2, 0, 0xFFFFFFFF, true);

            GL11.glDepthMask(true);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glPopMatrix();
        } 
    }
}

