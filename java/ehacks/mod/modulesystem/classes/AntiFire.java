/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.entity.EntityClientPlayerMP
 *  net.minecraft.client.network.NetHandlerPlayClient
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.C03PacketPlayer
 *  net.minecraft.util.AxisAlignedBB
 */
package ehacks.mod.modulesystem.classes;

import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;
import ehacks.api.module.Mod;
import ehacks.mod.modulesystem.classes.WaterWalk;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;

public class AntiFire
extends Mod {
    public AntiFire() {
        super(ModuleCategory.PLAYER);
    }

    @Override
    public String getName() {
        return "AntiFire";
    }
    
    @Override
    public String getDescription() {
        return "Removes fire on you";
    }

    @Override
    public void onTicks() {
        if (!WaterWalk.isOnLiquid(Wrapper.INSTANCE.player().boundingBox) && Wrapper.INSTANCE.player().isBurning() && Wrapper.INSTANCE.player().onGround) {
            for (int i = 0; i < 10; ++i) {
                Wrapper.INSTANCE.player().sendQueue.addToSendQueue((Packet)new C03PacketPlayer(false));
            }
        }
    }
}

