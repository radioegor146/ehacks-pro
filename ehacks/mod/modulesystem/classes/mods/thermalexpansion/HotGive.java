package ehacks.mod.modulesystem.classes.mods.thermalexpansion;

import ehacks.mod.api.ModStatus;
import ehacks.mod.api.Module;
import ehacks.mod.modulesystem.classes.keybinds.GiveKeybind;
import ehacks.mod.util.InteropUtils;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Statics;
import ehacks.mod.wrapper.Wrapper;
import java.util.UUID;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import org.lwjgl.input.Keyboard;

public class HotGive
        extends Module {

    public HotGive() {
        super(ModuleCategory.EHACKS);
    }

    @Override
    public String getName() {
        return "HotGive";
    }

    @Override
    public String getDescription() {
        return "You can fill TileCache with any itemstack\nUsage: \n  Numpad0 - Perform action on container";
    }

    @Override
    public void onModuleEnabled() {
        try {
            Class.forName("cofh.thermalexpansion.block.cache.TileCache");
            Class.forName("cofh.core.network.PacketCoFHBase");
            Class.forName("cofh.core.network.PacketHandler");
        } catch (Exception ex) {
            this.off();
        }
    }

    @Override
    public ModStatus getModStatus() {
        try {
            Class.forName("cofh.thermalexpansion.block.cache.TileCache");
            Class.forName("cofh.core.network.PacketCoFHBase");
            Class.forName("cofh.core.network.PacketHandler");
            return ModStatus.WORKING;
        } catch (Exception e) {
            return ModStatus.NOTWORKING;
        }
    }

    private boolean prevState = false;

    @Override
    public void onTicks() {
        try {
            boolean newState = Keyboard.isKeyDown(GiveKeybind.getKey());
            if (newState && !prevState) {
                prevState = newState;
                MovingObjectPosition mop = Wrapper.INSTANCE.mc().objectMouseOver;
                TileEntity entity = Wrapper.INSTANCE.world().getTileEntity(mop.blockX, mop.blockY, mop.blockZ);
                if (entity != null && Class.forName("cofh.thermalexpansion.block.cache.TileCache").isInstance(entity)) {
                    setSlot(entity, Statics.STATIC_ITEMSTACK);
                    InteropUtils.log("Set", this);
                }
            }
            prevState = newState;
        } catch (Exception e) {
        }
    }

    public void setSlot(TileEntity tileEntity, ItemStack itemstack) throws Exception {
        Object packetTile = Class.forName("cofh.core.network.PacketTile").getConstructor(TileEntity.class).newInstance(tileEntity);

        Class.forName("cofh.core.network.PacketCoFHBase").getMethod("addString", String.class).invoke(packetTile, "");

        Class.forName("cofh.core.network.PacketCoFHBase").getMethod("addByte", Byte.TYPE).invoke(packetTile, (byte) 0);
        Class.forName("cofh.core.network.PacketCoFHBase").getMethod("addUUID", UUID.class).invoke(packetTile, UUID.randomUUID());
        Class.forName("cofh.core.network.PacketCoFHBase").getMethod("addString", String.class).invoke(packetTile, "");

        Class.forName("cofh.core.network.PacketCoFHBase").getMethod("addBool", Boolean.TYPE).invoke(packetTile, true);
        Class.forName("cofh.core.network.PacketCoFHBase").getMethod("addByte", Byte.TYPE).invoke(packetTile, (byte) 0);
        Class.forName("cofh.core.network.PacketCoFHBase").getMethod("addBool", Boolean.TYPE).invoke(packetTile, true);

        Class.forName("cofh.core.network.PacketCoFHBase").getMethod("addInt", Integer.TYPE).invoke(packetTile, 0);

        Class.forName("cofh.core.network.PacketCoFHBase").getMethod("addByteArray", byte[].class).invoke(packetTile, new Object[]{new byte[6]});
        Class.forName("cofh.core.network.PacketCoFHBase").getMethod("addByte", Byte.TYPE).invoke(packetTile, (byte) 0);

        Class.forName("cofh.core.network.PacketCoFHBase").getMethod("addBool", Boolean.TYPE).invoke(packetTile, false);

        Class.forName("cofh.core.network.PacketCoFHBase").getMethod("addItemStack", ItemStack.class).invoke(packetTile, itemstack);
        Class.forName("cofh.core.network.PacketCoFHBase").getMethod("addInt", Integer.TYPE).invoke(packetTile, 1024);

        Class.forName("cofh.core.network.PacketHandler").getMethod("sendToServer", Class.forName("cofh.core.network.PacketBase")).invoke(null, packetTile);

    }

    @Override
    public String getModName() {
        return "ThermalExpansion";
    }
}
