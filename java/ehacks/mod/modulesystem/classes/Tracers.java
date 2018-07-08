/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityClientPlayerMP
 *  net.minecraft.client.multiplayer.WorldClient
 *  net.minecraft.client.renderer.entity.RenderManager
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  org.lwjgl.opengl.GL11
 */
package ehacks.mod.modulesystem.classes;

import com.mojang.authlib.GameProfile;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;
import ehacks.api.module.Mod;
import ehacks.mod.relationsystem.Friend;
import ehacks.mod.wrapper.ModuleCategories;
import ehacks.mod.wrapper.Wrapper;

public class Tracers
extends Mod {
    public Tracers() {
        super(ModuleCategories.RENDER);
    }

    @Override
    public String getName() {
        return "Tracers";
    }

    @Override
    public String getDescription() {
        return "Traces a line to the players in MP";
    }

    @Override
    public void onWorldRender() {
        try {
            GL11.glPushMatrix();
            GL11.glEnable((int)2848);
            GL11.glDisable((int)2929);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            GL11.glDisable((int)3553);
            GL11.glDepthMask((boolean)false);
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glEnable((int)3042);
            GL11.glLineWidth((float)1.5f);
            for (Object entities : Wrapper.INSTANCE.mc().theWorld.loadedEntityList) {
                if (entities == Wrapper.INSTANCE.mc().thePlayer || entities == null || !(entities instanceof EntityPlayer) || ((EntityPlayer)entities).isDead || ((EntityPlayer)entities).isInvisible()) continue;
                EntityPlayer entity = (EntityPlayer)entities;
                float distance = Wrapper.INSTANCE.mc().renderViewEntity.getDistanceToEntity((Entity)entity);
                double posX = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) - RenderManager.renderPosX;
                double posY = entity.lastTickPosY + 1.4 + (entity.posY - entity.lastTickPosY) - RenderManager.renderPosY;
                double posZ = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) - RenderManager.renderPosZ;
                String playerName = Wrapper.INSTANCE.player().getGameProfile().getName();
                if (Friend.instance().readFriend(playerName)) {
                    GL11.glColor3f((float)0.0f, (float)1.0f, (float)0.0f);
                } else if (distance <= 6.0f) {
                    GL11.glColor3f((float)1.0f, (float)0.0f, (float)0.0f);
                } else if (distance <= 96.0f) {
                    GL11.glColor3f((float)1.0f, (float)(distance / 100.0f), (float)0.0f);
                } else if (distance > 96.0f) {
                    GL11.glColor3f((float)0.1f, (float)0.6f, (float)255.0f);
                }
                GL11.glBegin((int)2);
                GL11.glVertex3d((double)0.0, (double)0.0, (double)0.0);
                GL11.glVertex3d((double)posX, (double)posY, (double)posZ);
                GL11.glEnd();
            }
            GL11.glDisable((int)3042);
            GL11.glDepthMask((boolean)true);
            GL11.glEnable((int)3553);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            GL11.glEnable((int)2929);
            GL11.glDisable((int)2848);
            GL11.glPopMatrix();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

