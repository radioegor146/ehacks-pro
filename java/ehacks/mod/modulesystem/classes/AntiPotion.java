package ehacks.mod.modulesystem.classes;

import ehacks.api.module.Module;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;

public class AntiPotion
        extends Module {

    private final Potion[] badEffects = new Potion[]{Potion.moveSlowdown, Potion.digSlowdown, Potion.harm, Potion.confusion, Potion.blindness, Potion.hunger, Potion.weakness, Potion.poison, Potion.wither};
    
    public AntiPotion() {
        super(ModuleCategory.PLAYER);
    }

    @Override
    public String getName() {
        return "AntiPotion";
    }

    @Override
    public String getDescription() {
        return "Removes potion effects on you";
    }

    @Override
    public void onTicks() {
        if (Wrapper.INSTANCE.player().isPotionActive(Potion.blindness)) {
            Wrapper.INSTANCE.player().removePotionEffect(Potion.blindness.id);
        }
        if (Wrapper.INSTANCE.player().isPotionActive(Potion.confusion)) {
            Wrapper.INSTANCE.player().removePotionEffect(Potion.confusion.id);
        }
        if (Wrapper.INSTANCE.player().isPotionActive(Potion.digSlowdown)) {
            Wrapper.INSTANCE.player().removePotionEffect(Potion.digSlowdown.id);
        }
        if (Wrapper.INSTANCE.player().onGround) {
            for (Potion effect : this.badEffects) {
                if (!Wrapper.INSTANCE.player().isPotionActive(effect)) {
                    continue;
                }
                for (int a2 = 0; a2 <= 20; ++a2) {
                    Wrapper.INSTANCE.player().sendQueue.addToSendQueue((Packet) new C03PacketPlayer.C05PacketPlayerLook(Wrapper.INSTANCE.player().rotationYaw, Wrapper.INSTANCE.player().rotationPitch, Wrapper.INSTANCE.player().onGround));
                }
            }
            if (Wrapper.INSTANCE.player().getHealth() <= 15.0f && Wrapper.INSTANCE.player().isPotionActive(Potion.regeneration)) {
                for (int a3 = 0; a3 <= 10; ++a3) {
                    Wrapper.INSTANCE.player().sendQueue.addToSendQueue((Packet) new C03PacketPlayer.C05PacketPlayerLook(Wrapper.INSTANCE.player().rotationYaw, Wrapper.INSTANCE.player().rotationPitch, Wrapper.INSTANCE.player().onGround));
                }
            }
        }
    }
}
