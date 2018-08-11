/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityClientPlayerMP
 *  net.minecraft.client.multiplayer.WorldClient
 *  net.minecraft.client.network.NetHandlerPlayClient
 *  net.minecraft.entity.player.InventoryPlayer
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemAppleGold
 *  net.minecraft.item.ItemFood
 *  net.minecraft.item.ItemPotion
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.C03PacketPlayer
 *  net.minecraft.network.play.client.C07PacketPlayerDigging
 *  net.minecraft.network.play.client.C09PacketHeldItemChange
 *  net.minecraft.util.FoodStats
 *  net.minecraft.util.MovingObjectPosition
 *  org.lwjgl.input.Mouse
 */
package ehacks.mod.modulesystem.classes;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.FoodStats;
import net.minecraft.util.MovingObjectPosition;
import org.lwjgl.input.Mouse;
import ehacks.api.module.Mod;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;

public class FastEat
extends Mod {
    private int mode = 0;

    public FastEat() {
        super(ModuleCategory.PLAYER);
    }

    @Override
    public String getName() {
        return "FastEat";
    }

    @Override
    public void onTicks() {
        if (this.isActive() && Mouse.getEventButton() == 1 && Mouse.isButtonDown((int)1)) {
            if (this.mode == 1) {
                int[] ignoredBlockIds;
                for (int id : ignoredBlockIds = new int[]{23, 25, 26, 54, 58, 61, 62, 64, 69, 71, 77, 84, 92, 96, 107, 116, 117, 118, 120, 130, 137, 143, 145, 146, 149, 150, 154, 158}) {
                    if (Block.getIdFromBlock((Block)Wrapper.INSTANCE.world().getBlock(Wrapper.INSTANCE.mc().objectMouseOver.blockX, Wrapper.INSTANCE.mc().objectMouseOver.blockY, Wrapper.INSTANCE.mc().objectMouseOver.blockZ)) != id) continue;
                    return;
                }
            }
            if (Wrapper.INSTANCE.player().inventory.getCurrentItem() == null) {
                return;
            }
            Item item = Wrapper.INSTANCE.player().inventory.getCurrentItem().getItem();
            if (Wrapper.INSTANCE.player().onGround && (item instanceof ItemFood || item instanceof ItemPotion || item instanceof ItemAppleGold) && (Wrapper.INSTANCE.player().getFoodStats().needFood() || item instanceof ItemPotion || item instanceof ItemAppleGold)) {
                Wrapper.INSTANCE.player().sendQueue.addToSendQueue((Packet)new C09PacketHeldItemChange(Wrapper.INSTANCE.player().inventory.currentItem));
                for (int i = 0; i < 1000; ++i) {
                    Wrapper.INSTANCE.player().sendQueue.addToSendQueue((Packet)new C03PacketPlayer(false));
                }
                Wrapper.INSTANCE.player().sendQueue.addToSendQueue((Packet)new C07PacketPlayerDigging(5, 0, 0, 0, 255));
            }
        }
    }
}

