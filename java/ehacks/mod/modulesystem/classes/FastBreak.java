/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.material.Material
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityClientPlayerMP
 *  net.minecraft.client.multiplayer.WorldClient
 *  net.minecraft.init.Blocks
 *  net.minecraft.util.MovingObjectPosition
 */
package ehacks.mod.modulesystem.classes;

import ehacks.api.module.Module;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.init.Blocks;
import net.minecraft.util.MovingObjectPosition;

public class FastBreak
extends Module {
    public FastBreak() {
        super(ModuleCategory.PLAYER);
    }

    @Override
    public String getName() {
        return "Fast Break";
    }

    @Override
    public String getDescription() {
        return "Applies Haste III for help the player to break blocks faster. Works in MP";
    }

    @Override
    public void onTicks() {
        Minecraft o = Minecraft.getMinecraft();
        MovingObjectPosition position = Wrapper.INSTANCE.mc().objectMouseOver;
        Block block = Wrapper.INSTANCE.world().getBlock(position.blockX, position.blockY, position.blockZ);
        if (block.getMaterial() != Material.air && block != Blocks.bedrock) {
            int blockX = (int)Wrapper.INSTANCE.player().lastTickPosX - (int)(Wrapper.INSTANCE.player().posX - Wrapper.INSTANCE.player().lastTickPosX);
            int blockY = (int)Wrapper.INSTANCE.player().lastTickPosY - (int)(Wrapper.INSTANCE.player().posY - Wrapper.INSTANCE.player().lastTickPosY);
            int blockZ = (int)Wrapper.INSTANCE.player().lastTickPosZ - (int)(Wrapper.INSTANCE.player().posZ - Wrapper.INSTANCE.player().lastTickPosZ);
            block.setHardness(0.0f);
        }
    }
}

