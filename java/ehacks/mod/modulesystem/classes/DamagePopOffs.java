package ehacks.mod.modulesystem.classes;

import ehacks.api.module.Module;
import ehacks.mod.util.damageindicator.Particle;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import java.util.HashMap;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraftforge.event.entity.living.LivingEvent;

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
        int currentHealth = MathHelper.ceiling_float_int((float) el.getHealth());
        if (healths.containsKey(el.getEntityId()) && (lastHealth = healths.get(el.getEntityId()).intValue()) != 0 && lastHealth != currentHealth) {
            int damage = lastHealth - currentHealth;
            Particle customParticle = new Particle(Wrapper.INSTANCE.world(), el.posX, el.posY + (double) el.height, el.posZ, 0.001, 0.05f * 1.5f, 0.001, damage);
            customParticle.shouldOnTop = true;
            if (el != Wrapper.INSTANCE.player() || Wrapper.INSTANCE.mcSettings().thirdPersonView != 0) {
                Wrapper.INSTANCE.mc().effectRenderer.addEffect((EntityFX) customParticle);
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
