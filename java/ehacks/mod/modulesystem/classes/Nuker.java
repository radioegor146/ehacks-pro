/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.material.Material
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityClientPlayerMP
 *  net.minecraft.client.multiplayer.PlayerControllerMP
 *  net.minecraft.client.multiplayer.WorldClient
 *  net.minecraft.client.network.NetHandlerPlayClient
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.C07PacketPlayerDigging
 */
package ehacks.mod.modulesystem.classes;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import ehacks.api.module.Mod;
import ehacks.mod.wrapper.Events;
import ehacks.mod.wrapper.ModuleCategories;
import ehacks.mod.wrapper.Wrapper;

public class Nuker
extends Mod {
    public static boolean isActive = false;
    private static int radius = 5;

    public Nuker() {
        super(ModuleCategories.PLAYER);
    }

    @Override
    public String getName() {
        return "Nuker";
    }

    @Override
    public void onEnableMod() {
        isActive = true;
    }

    @Override
    public void onDisableMod() {
        isActive = false;
        Events.selectedBlock = null;
    }

    @Override
    public void onTicks() {
        block6 : {
            block7 : {
                if (Events.selectedBlock == null) break block6;
                if (!Wrapper.INSTANCE.mc().playerController.isInCreativeMode()) break block7;
                for (int i = Nuker.radius; i >= - radius; --i) {
                    for (int k = Nuker.radius; k >= - radius; --k) {
                        for (int j = - Nuker.radius; j <= radius; ++j) {
                            int x = (int)(Wrapper.INSTANCE.player().posX + (double)i);
                            int y = (int)(Wrapper.INSTANCE.player().posY + (double)j);
                            int z = (int)(Wrapper.INSTANCE.player().posZ + (double)k);
                            Block blockID = Wrapper.INSTANCE.world().getBlock(x, y, z);
                            if (blockID.getMaterial() == Material.air || Events.selectedBlock != blockID) continue;
                            Wrapper.INSTANCE.player().sendQueue.addToSendQueue((Packet)new C07PacketPlayerDigging(0, x, y, z, 0));
                        }
                    }
                }
                break block6;
            }
            if (!Wrapper.INSTANCE.mc().playerController.isNotCreative()) break block6;
            for (int i = Nuker.radius; i >= - radius; --i) {
                for (int k = Nuker.radius; k >= - radius; --k) {
                    for (int j = - Nuker.radius; j <= radius; ++j) {
                        int x = (int)(Wrapper.INSTANCE.player().posX + (double)i);
                        int y = (int)(Wrapper.INSTANCE.player().posY + (double)j);
                        int z = (int)(Wrapper.INSTANCE.player().posZ + (double)k);
                        Block block = Wrapper.INSTANCE.world().getBlock(x, y, z);
                        if (block.getMaterial() == Material.air || Events.selectedBlock != block) continue;
                        Wrapper.INSTANCE.player().sendQueue.addToSendQueue((Packet)new C07PacketPlayerDigging(0, x, y, z, 0));
                        Wrapper.INSTANCE.player().sendQueue.addToSendQueue((Packet)new C07PacketPlayerDigging(2, x, y, z, 0));
                    }
                }
            }
        }
    }
}

