package ehacks.mod.modulesystem.classes.vanilla;

import ehacks.mod.api.ModStatus;
import ehacks.mod.api.Module;
import ehacks.mod.modulesystem.classes.keybinds.GiveKeybind;
import ehacks.mod.util.InteropUtils;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Statics;
import ehacks.mod.wrapper.Wrapper;
import net.minecraft.item.ItemStack;
import org.lwjgl.input.Keyboard;

public class CreativeGive
        extends Module {

    public CreativeGive() {
        super(ModuleCategory.EHACKS);
    }

    @Override
    public String getName() {
        return "CreativeGive";
    }

    @Override
    public String getDescription() {
        return "You can give the selected ItemStack when you are in creative mode\nUsage: \n  Numpad0 - perform give";
    }

    @Override
    public void onModuleEnabled() {

    }

    @Override
    public ModStatus getModStatus() {
        return ModStatus.DEFAULT;
    }

    @Override
    public void onModuleDisabled() {

    }

    private boolean prevState = false;

    @Override
    public void onTicks() {
        boolean newState = Keyboard.isKeyDown(GiveKeybind.getKey());
        if (newState && !prevState) {
            prevState = newState;
            int slotId = 36 + Wrapper.INSTANCE.player().inventory.currentItem;
            if (Statics.STATIC_ITEMSTACK == null) {
                return;
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
                for (int i = 9; i < 45; i++) {
                    setCreative(Statics.STATIC_ITEMSTACK, i);
                }
            } else {
                setCreative(Statics.STATIC_ITEMSTACK, slotId);
            }
            InteropUtils.log("Set", this);
        }
        prevState = newState;
    }

    public void setCreative(ItemStack item, int slotId) {
        try {
            Wrapper.INSTANCE.player().sendQueue.addToSendQueue(new net.minecraft.network.play.client.C10PacketCreativeInventoryAction(slotId, item));
        } catch (Exception ex) {
        }
    }

}
