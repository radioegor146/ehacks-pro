/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.entity.EntityClientPlayerMP
 *  net.minecraft.client.network.NetHandlerPlayClient
 *  net.minecraft.client.settings.GameSettings
 *  net.minecraft.client.settings.KeyBinding
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.C03PacketPlayer
 *  net.minecraft.network.play.client.C03PacketPlayer$C04PacketPlayerPosition
 *  net.minecraft.util.AxisAlignedBB
 */
package ehacks.mod.modulesystem.classes;

import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;
import ehacks.api.module.Mod;
import ehacks.mod.wrapper.ModuleCategories;
import ehacks.mod.wrapper.Wrapper;

public class NCPFly
extends Mod {
    public NCPFly() {
        super(ModuleCategories.NOCHEATPLUS);
    }

    @Override
    public String getName() {
        return "Fly";
    }

    @Override
    public void onEnableMod() {
        for (int i = 0; i < 4; ++i) {
            Wrapper.INSTANCE.player().sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(Wrapper.INSTANCE.player().posX, Wrapper.INSTANCE.player().boundingBox.minY + 1.01, Wrapper.INSTANCE.player().posY + 1.01, Wrapper.INSTANCE.player().posZ, false));
            Wrapper.INSTANCE.player().sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(Wrapper.INSTANCE.player().posX, Wrapper.INSTANCE.player().boundingBox.minY, Wrapper.INSTANCE.player().posY, Wrapper.INSTANCE.player().posZ, false));
        }
        Wrapper.INSTANCE.player().setPosition(Wrapper.INSTANCE.player().posX, Wrapper.INSTANCE.player().posY + 0.8, Wrapper.INSTANCE.player().posZ);
    }

    @Override
    public void onTicks() {
        Wrapper.INSTANCE.player().motionY = -0.04;
        if (Wrapper.INSTANCE.mcSettings().keyBindJump.getIsKeyPressed()) {
            Wrapper.INSTANCE.player().motionY = 0.3;
        }
        if (Wrapper.INSTANCE.mcSettings().keyBindSneak.getIsKeyPressed()) {
            Wrapper.INSTANCE.player().motionY = -0.3;
        }
    }
}

