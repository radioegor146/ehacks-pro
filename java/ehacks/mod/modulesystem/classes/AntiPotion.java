/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.entity.EntityClientPlayerMP
 *  net.minecraft.client.network.NetHandlerPlayClient
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.C03PacketPlayer
 *  net.minecraft.network.play.client.C03PacketPlayer$C05PacketPlayerLook
 *  net.minecraft.potion.Potion
 */
package ehacks.mod.modulesystem.classes;

import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;
import ehacks.api.module.Mod;
import ehacks.mod.wrapper.ModuleCategories;
import ehacks.mod.wrapper.Wrapper;

public class AntiPotion
extends Mod {
    private Potion[] badEffects = new Potion[]{Potion.moveSlowdown, Potion.digSlowdown, Potion.harm, Potion.confusion, Potion.blindness, Potion.hunger, Potion.weakness, Potion.poison, Potion.wither};
    private Potion[] goodEffects = new Potion[]{Potion.moveSpeed, Potion.digSpeed, Potion.damageBoost, Potion.heal, Potion.jump, Potion.regeneration, Potion.resistance, Potion.fireResistance, Potion.waterBreathing, Potion.invisibility, Potion.nightVision, Potion.field_76434_w, Potion.field_76444_x, Potion.field_76443_y};

    public AntiPotion() {
        super(ModuleCategories.PLAYER);
    }

    @Override
    public String getName() {
        return "AntiPotion";
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
                if (!Wrapper.INSTANCE.player().isPotionActive(effect)) continue;
                for (int a2 = 0; a2 <= 20; ++a2) {
                    Wrapper.INSTANCE.player().sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C05PacketPlayerLook(Wrapper.INSTANCE.player().rotationYaw, Wrapper.INSTANCE.player().rotationPitch, Wrapper.INSTANCE.player().onGround));
                }
            }
            if (Wrapper.INSTANCE.player().getHealth() <= 15.0f && Wrapper.INSTANCE.player().isPotionActive(Potion.regeneration)) {
                for (int a3 = 0; a3 <= 10; ++a3) {
                    Wrapper.INSTANCE.player().sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C05PacketPlayerLook(Wrapper.INSTANCE.player().rotationYaw, Wrapper.INSTANCE.player().rotationPitch, Wrapper.INSTANCE.player().onGround));
                }
            }
        }
    }
}

