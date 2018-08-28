package ehacks.mod.modulesystem.classes;

import ehacks.api.module.Module;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

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
                    Wrapper.INSTANCE.mc().playerController.windowClick(Wrapper.INSTANCE.player().openContainer.windowId, slotId, 0, 1, (EntityPlayer) Wrapper.INSTANCE.player());
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
            if (container.getInventory().get(i) == null) {
                continue;
            }
            return i;
        }
        return -1;
    }

    private boolean isContainerEmpty(Container container) {
        int slotAmount;
        int n = slotAmount = container.inventorySlots.size() == 90 ? 54 : 27;
        for (int i = 0; i < slotAmount; ++i) {
            if (!container.getSlot(i).getHasStack()) {
                continue;
            }
            return false;
        }
        return true;
    }
}
