/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehacks.mod.modulesystem.classes;

import ehacks.api.module.Module;
import ehacks.api.module.ModController;
import ehacks.mod.util.damageindicator.Particle;
import static ehacks.mod.wrapper.Events.cheatEnabled;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import java.util.HashMap;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraftforge.event.entity.living.LivingEvent;

/**
 *
 * @author radioegor146
 */
public class DamagePopOffs extends Module {
    
    public DamagePopOffs() {
        super(ModuleCategory.RENDER);
    }    
    
    public HashMap<Integer, Integer> healths = new HashMap();
    
    @Override
    public void onLiving(LivingEvent.LivingUpdateEvent event) {
        updateHealth(event.entityLiving);
    }
    
    private void updateHealth(EntityLivingBase el) {
        int lastHealth;
        int currentHealth = MathHelper.ceiling_float_int((float)el.getHealth());
        if (healths.containsKey(el.getEntityId()) && (lastHealth = healths.get(el.getEntityId()).intValue()) != 0 && lastHealth != currentHealth) {
            int damage = lastHealth - currentHealth;
            Particle customParticle = new Particle(Wrapper.INSTANCE.world(), el.posX, el.posY + (double)el.height, el.posZ, 0.001, 0.05f * 1.5f, 0.001, damage);
            customParticle.shouldOnTop = true;
            if (el != Wrapper.INSTANCE.player() || Wrapper.INSTANCE.mcSettings().thirdPersonView != 0) {
                Wrapper.INSTANCE.mc().effectRenderer.addEffect((EntityFX)customParticle);
            }
        }
        healths.put(el.getEntityId(), currentHealth);
    }
    
    @Override
    public String getName() {
        return "DamagePopOffs";
    }
    
    @Override
    public String getDescription() {
        return "Shows damage like DamageIndicators";
    }
}
