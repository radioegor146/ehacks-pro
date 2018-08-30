package ehacks.mod.modulesystem.classes;

import ehacks.api.module.ModStatus;
import ehacks.api.module.Module;
import ehacks.mod.util.InteropUtils;
import ehacks.mod.wrapper.Keybinds;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Statics;
import ehacks.mod.wrapper.Wrapper;
import net.minecraft.item.ItemStack;
import org.lwjgl.input.Keyboard;

public class RedHack
        extends Module {

    public RedHack() {
        super(ModuleCategory.EHACKS);
    }

    @Override
    public String getName() {
        return "RedHack";
    }

    @Override
    public String getDescription() {
        return "Allows you to give any ItemStack to your hand\nUsage:\n  Numpad0 - Gives an item";
    }

    @Override
    public void onEnableMod() {
        try {
            Class.forName("mrtjp.projectred.transportation.TransportationSPH$");
            Class.forName("codechicken.lib.packet.PacketCustom");
        } catch (Exception ex) {
            this.off();
        }
    }

    @Override
    public ModStatus getModStatus() {
        try {
            Class.forName("mrtjp.projectred.transportation.TransportationSPH$");
            Class.forName("codechicken.lib.packet.PacketCustom");
            return ModStatus.WORKING;
        } catch (Exception e) {
            return ModStatus.NOTWORKING;
        }
    }

    @Override
    public void onDisableMod() {

    }

    private boolean prevState = false;

    @Override
    public void onTicks() {
        boolean newState = Keyboard.isKeyDown(Keybinds.give);
        if (newState && !prevState) {
            prevState = newState;
            int slotId = 27 + Wrapper.INSTANCE.player().inventory.currentItem;
            if (Statics.STATIC_ITEMSTACK == null) {
                return;
            }
            setRed(Statics.STATIC_ITEMSTACK, slotId);
            InteropUtils.log("Set", this);
        }
        prevState = newState;
    }

    public void setRed(ItemStack item, int slotId) {
        try {
            Object packetInstance = Class.forName("codechicken.lib.packet.PacketCustom").getConstructor(Object.class, Integer.TYPE).newInstance("PR|Transp", 4);
            Class.forName("codechicken.lib.packet.PacketCustom").getMethod("writeByte", Byte.TYPE).invoke(packetInstance, (byte) slotId);
            Class.forName("codechicken.lib.packet.PacketCustom").getMethod("writeItemStack", ItemStack.class).invoke(packetInstance, item);
            Class.forName("codechicken.lib.packet.PacketCustom").getMethod("sendToServer").invoke(packetInstance);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public String getModName() {
        return "Project Red";
    }
}
