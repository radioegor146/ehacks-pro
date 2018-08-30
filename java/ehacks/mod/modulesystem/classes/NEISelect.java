package ehacks.mod.modulesystem.classes;

import ehacks.api.module.ModStatus;
import ehacks.api.module.Module;
import ehacks.mod.util.InteropUtils;
import ehacks.mod.wrapper.Keybinds;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Statics;
import ehacks.mod.wrapper.Wrapper;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.lwjgl.input.Keyboard;

public class NEISelect extends Module {

    public NEISelect() {
        super(ModuleCategory.EHACKS);
    }

    @Override
    public String getName() {
        return "NEISelect";
    }

    @Override
    public String getDescription() {
        return "Select ItemStack from NEI\nUsage:\n  Tab - select single item\n  Shift+Tab - select stack of item";
    }

    @Override
    public void onEnableMod() {
        try {
            Class.forName("codechicken.nei.guihook.GuiContainerManager");
        } catch (Exception ex) {
            this.off();
        }
    }

    @Override
    public ModStatus getModStatus() {
        try {
            Class.forName("codechicken.nei.guihook.GuiContainerManager");
            return ModStatus.WORKING;
        } catch (Exception e) {
            return ModStatus.NOTWORKING;
        }
    }

    private boolean prevState = false;

    @Override
    public void onTicks() {
        boolean newState = Keyboard.isKeyDown(Keybinds.neiSelect);
        if (newState && !prevState) {
            prevState = newState;
            try {
                GuiContainer container = Wrapper.INSTANCE.mc().currentScreen instanceof GuiContainer ? ((GuiContainer) Wrapper.INSTANCE.mc().currentScreen) : null;
                if (container == null) {
                    return;
                }
                Object checkItem = Class.forName("codechicken.nei.guihook.GuiContainerManager").getDeclaredMethod("getStackMouseOver", GuiContainer.class).invoke(null, container);
                if (checkItem instanceof ItemStack) {
                    ItemStack item = (ItemStack) checkItem;
                    int count = GuiContainer.isShiftKeyDown() ? item.getMaxStackSize() : 1;
                    Statics.STATIC_ITEMSTACK = item.copy().splitStack(count);
                    Statics.STATIC_NBT = Statics.STATIC_ITEMSTACK.getTagCompound() == null ? new NBTTagCompound() : Statics.STATIC_ITEMSTACK.getTagCompound();
                }
                InteropUtils.log("ItemStack selected", this);
            } catch (Exception ex) {

            }
        }
        prevState = newState;
    }

    @Override
    public String getModName() {
        return "NEI";
    }
    
    @Override
    public boolean canOnOnStart() {
        return true;
    }
}
