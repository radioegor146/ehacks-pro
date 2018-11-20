package ehacks.mod.modulesystem.classes.vanilla;

import ehacks.mod.api.Module;
import ehacks.mod.modulesystem.classes.keybinds.OpenNBTEditKeybind;
import ehacks.mod.util.InteropUtils;
import ehacks.mod.util.Mappings;
import ehacks.mod.util.nbtedit.GuiNBTEdit;
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
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class NBTEdit extends Module {

    public NBTEdit() {
        super(ModuleCategory.EHACKS);
    }

    @Override
    public String getName() {
        return "NBTEdit";
    }

    @Override
    public String getDescription() {
        return "Edit item's nbt\nUsage:\n  NBT Edit button - open NBT editor/viewer";
    }

    private boolean prevState = false;

    @Override
    public void onTicks() {
        boolean newState = Keyboard.isKeyDown(OpenNBTEditKeybind.getKey());
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
                }
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
                MovingObjectPosition mop = Wrapper.INSTANCE.mc().objectMouseOver;
                if (mop.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                    NBTTagCompound tag = new NBTTagCompound();
                    TileEntity te = Wrapper.INSTANCE.world().getTileEntity(mop.blockX, mop.blockY, mop.blockZ);
                    if (te != null) {
                        te.writeToNBT(tag);
                        Statics.STATIC_NBT = tag;
                        InteropUtils.log("Read NBT from block x: " + mop.blockX + "; y: " + mop.blockY + "; z: " + mop.blockZ, this);
                    } else {
                        InteropUtils.log("Can't read NBT from block - TileEntity not found", this);
                        return;
                    }
                }
                if (mop.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) {
                    NBTTagCompound tag = new NBTTagCompound();
                    mop.entityHit.writeToNBT(tag);
                    Statics.STATIC_NBT = tag;
                    InteropUtils.log("Read NBT from entity " + mop.entityHit, this);
                }
                if (mop.typeOfHit == MovingObjectPosition.MovingObjectType.MISS) {
                    Statics.STATIC_NBT = null;
                    InteropUtils.log("NBT has been cleared", this);
                    return;
                }
            }
            Statics.STATIC_NBT = Statics.STATIC_NBT == null ? new NBTTagCompound() : Statics.STATIC_NBT;
            Wrapper.INSTANCE.mc().displayGuiScreen(new GuiNBTEdit(Statics.STATIC_NBT));
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
