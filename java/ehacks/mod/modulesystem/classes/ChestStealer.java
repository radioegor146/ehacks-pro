/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityClientPlayerMP
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.inventory.GuiChest
 *  net.minecraft.client.multiplayer.PlayerControllerMP
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.inventory.Container
 *  net.minecraft.inventory.Slot
 *  net.minecraft.item.ItemStack
 */
package ehacks.mod.modulesystem.classes;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import ehacks.api.module.Module;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;

public class ChestStealer
extends Module {
    private int delay = 0;

    public ChestStealer() {
        super(ModuleCategory.PLAYER);
    }

    @Override
    public String getName() {
        return "ChestStealer";
    }
    
    @Override
    public String getDescription() {
        return "Steals all stuff from vanilla chest";
    }
    
    @Override
    public void onTicks() {
        if (!Wrapper.INSTANCE.mc().inGameHasFocus && Wrapper.INSTANCE.mc().currentScreen instanceof GuiChest) {
            if (!this.isContainerEmpty(Wrapper.INSTANCE.player().openContainer)) {
                int slotId = this.getNextSlotInContainer(Wrapper.INSTANCE.player().openContainer);
                if (this.delay >= 5) {
                    Wrapper.INSTANCE.mc().playerController.windowClick(Wrapper.INSTANCE.player().openContainer.windowId, slotId, 0, 1, (EntityPlayer)Wrapper.INSTANCE.player());
                    this.delay = 0;
                }
                ++this.delay;
            } else {
                Wrapper.INSTANCE.player().closeScreen();
            }
        }
    }

    private int getNextSlotInContainer(Container container) {
        int slotAmount;
        int n = slotAmount = container.inventorySlots.size() == 90 ? 54 : 27;
        for (int i = 0; i < slotAmount; ++i) {
            if (container.getInventory().get(i) == null) continue;
            return i;
        }
        return -1;
    }

    private boolean isContainerEmpty(Container container) {
        int slotAmount;
        int n = slotAmount = container.inventorySlots.size() == 90 ? 54 : 27;
        for (int i = 0; i < slotAmount; ++i) {
            if (!container.getSlot(i).getHasStack()) continue;
            return false;
        }
        return true;
    }
}

