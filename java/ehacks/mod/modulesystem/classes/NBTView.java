package ehacks.mod.modulesystem.classes;

import ehacks.api.module.Module;
import ehacks.mod.util.InteropUtils;
import ehacks.mod.util.Mappings;
import ehacks.mod.util.nbtedit.GuiNBTEdit;
import ehacks.mod.wrapper.Keybinds;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Statics;
import ehacks.mod.wrapper.Wrapper;
import java.lang.reflect.Method;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class NBTView extends Module {

    public NBTView() {
        super(ModuleCategory.EHACKS);
    }

    @Override
    public String getName() {
        return "NBTView";
    }

    @Override
    public String getDescription() {
        return "View item's nbt\nUsage:\n  Numpad7 - open NBT editor/viewer";
    }

    @Override
    public void onEnableMod() {

    }

    private boolean prevState = false;

    @Override
    public void onTicks() {
        boolean newState = Keyboard.isKeyDown(Keybinds.openNbtEdit);
        if (newState && !prevState) {
            prevState = newState;
            GuiScreen screen = Wrapper.INSTANCE.mc().currentScreen;
            if (screen != null && screen instanceof GuiContainer) {
                try {
                    ScaledResolution get = new ScaledResolution(Wrapper.INSTANCE.mc(), Wrapper.INSTANCE.mc().displayWidth, Wrapper.INSTANCE.mc().displayHeight);
                    int mouseX = Mouse.getX() / get.getScaleFactor();
                    int mouseY = Mouse.getY() / get.getScaleFactor();
                    GuiContainer container = (GuiContainer) screen;
                    Method isMouseOverSlot = GuiContainer.class.getDeclaredMethod(Mappings.isMouseOverSlot, Slot.class, Integer.TYPE, Integer.TYPE);
                    isMouseOverSlot.setAccessible(true);
                    for (int i = 0; i < container.inventorySlots.inventorySlots.size(); i++) {
                        if ((Boolean) isMouseOverSlot.invoke(container, container.inventorySlots.inventorySlots.get(i), mouseX, get.getScaledHeight() - mouseY)) {
                            ItemStack stack = null;
                            stack = ((Slot) container.inventorySlots.inventorySlots.get(i)) == null ? null : ((Slot) container.inventorySlots.inventorySlots.get(i)).getStack();
                            if (stack != null) {
                                Statics.STATIC_NBT = stack.getTagCompound();
                            }
                        }
                    }
                } catch (Exception ex) {
                    InteropUtils.log("&cError", this);
                    ex.printStackTrace();
                }
            }
            Statics.STATIC_NBT = Statics.STATIC_NBT == null ? new NBTTagCompound() : Statics.STATIC_NBT;
            Wrapper.INSTANCE.mc().displayGuiScreen((GuiScreen) new GuiNBTEdit(Statics.STATIC_NBT));
        }
        prevState = newState;
    }

    @Override
    public String getModName() {
        return "Minecraft";
    }

    @Override
    public boolean canOnOnStart() {
        return true;
    }
}
