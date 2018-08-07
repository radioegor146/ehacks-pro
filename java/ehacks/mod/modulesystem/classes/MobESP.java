/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.multiplayer.WorldClient
 *  net.minecraft.client.renderer.entity.RenderManager
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.monster.EntityMob
 *  net.minecraft.entity.passive.EntityAnimal
 *  org.lwjgl.opengl.GL11
 */
package ehacks.mod.modulesystem.classes;

import java.util.List;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import org.lwjgl.opengl.GL11;
import ehacks.api.module.Mod;
import ehacks.mod.external.axis.AltAxisAlignedBB;
import ehacks.mod.util.GLUtils;
import ehacks.mod.wrapper.ModuleCategories;
import ehacks.mod.wrapper.Wrapper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraftforge.client.event.RenderWorldLastEvent;

public class MobESP
extends Mod {
    public MobESP() {
        super(ModuleCategories.RENDER);
    }

    @Override
    public String getName() {
        return "MobESP";
    }

    @Override
    public void onWorldRender(RenderWorldLastEvent event) {            
        GL11.glPushMatrix();
        GL11.glClear((int)256);
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2884);
        GL11.glEnable((int)3042); 
        RenderHelper.enableStandardItemLighting();
        for (Object o : Wrapper.INSTANCE.world().loadedEntityList) {
            Entity ent = (Entity)o;
            if (ent instanceof EntityMob || ent instanceof EntityCreature || ent instanceof EntityAmbientCreature)
            {
                double d0;
                double d1;
                double d2;
                if (!ent.isInRangeToRender3d(d0 = Wrapper.INSTANCE.mc().renderViewEntity.getPosition(event.partialTicks).xCoord, d1 = Wrapper.INSTANCE.mc().renderViewEntity.getPosition(event.partialTicks).yCoord, d2 = Wrapper.INSTANCE.mc().renderViewEntity.getPosition(event.partialTicks).zCoord) || ent == Wrapper.INSTANCE.mc().renderViewEntity && Wrapper.INSTANCE.mc().gameSettings.thirdPersonView == 0 && !Wrapper.INSTANCE.mc().renderViewEntity.isPlayerSleeping()) continue;
                RenderManager.instance.renderEntitySimple(ent, event.partialTicks);
            }
        }                 
        RenderHelper.disableStandardItemLighting();
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2884);
        GL11.glDisable((int)3042);  
        GL11.glPopMatrix();
    }
}

