/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.multiplayer.WorldClient
 *  net.minecraft.client.renderer.entity.RenderManager
 *  net.minecraft.entity.item.EntityFallingBlock
 *  org.lwjgl.opengl.GL11
 */
package ehacks.mod.modulesystem.classes;

import java.util.List;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityFallingBlock;
import org.lwjgl.opengl.GL11;
import ehacks.api.module.Mod;
import ehacks.mod.external.axis.AltAxisAlignedBB;
import ehacks.mod.util.GLUtils;
import ehacks.mod.wrapper.ModuleCategories;
import ehacks.mod.wrapper.Wrapper;

public class ProphuntESP
extends Mod {
    public ProphuntESP() {
        super(ModuleCategories.MINIGAMES);
    }

    @Override
    public String getName() {
        return "ProphuntESP";
    }

    @Override
    public void onWorldRender() {
        if (this.isActive()) {
            for (Object o : Wrapper.INSTANCE.world().loadedEntityList) {
                if (!(o instanceof EntityFallingBlock)) continue;
                EntityFallingBlock e = (EntityFallingBlock)o;
                float halfWidth = e.width / 2.0f;
                AltAxisAlignedBB aaabb = AltAxisAlignedBB.getBoundingBox(e.width - halfWidth, e.height, e.width - halfWidth, e.width + halfWidth, e.height + e.height, e.width + halfWidth);
                double renderX = e.lastTickPosX + (e.posX - e.lastTickPosX) - RenderManager.renderPosX - (double)e.width;
                double renderY = e.lastTickPosY + (e.posY - e.lastTickPosY) - RenderManager.renderPosY - (double)e.height;
                double renderZ = e.lastTickPosZ + (e.posZ - e.lastTickPosZ) - RenderManager.renderPosZ - (double)e.width;
                GL11.glPushMatrix();
                GL11.glTranslated((double)renderX, (double)renderY, (double)renderZ);
                GL11.glColor4f((float)0.27f, (float)0.7f, (float)0.92f, (float)1.0f);
                GL11.glColor4f((float)0.92f, (float)0.2f, (float)0.2f, (float)1.0f);
                GLUtils.startDrawingESPs(aaabb, 0.27f, 0.7f, 0.5f);
                GL11.glPopMatrix();
            }
        }
    }
}

