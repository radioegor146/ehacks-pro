package ehacks.mod.modulesystem.classes.mods.buildcraft;

import ehacks.mod.api.ModStatus;
import ehacks.mod.api.Module;
import ehacks.mod.modulesystem.classes.keybinds.GiveKeybind;
import ehacks.mod.util.InteropUtils;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Statics;
import ehacks.mod.wrapper.Wrapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import org.lwjgl.input.Keyboard;

public class PipeGive
        extends Module {

    public PipeGive() {
        super(ModuleCategory.EHACKS);
    }

    @Override
    public String getName() {
        return "PipeGive";
    }

    @Override
    public String getDescription() {
        return "You can put any ItemStack in Construction Marker from BuildCraft\nUsage: \n  Numpad0 - Perform action on container";
    }

    @Override
    public void onModuleEnabled() {
        try {
            Class.forName("buildcraft.builders.TileConstructionMarker");
            Class.forName("buildcraft.core.Box");
            try {
                Class.forName("buildcraft.core.lib.utils.NetworkUtils");
            } catch (Exception ex) {
                Class.forName("buildcraft.core.utils.Utils");
            }
        } catch (Exception ex) {
            this.off();
        }
    }

    @Override
    public ModStatus getModStatus() {
        try {
            Class.forName("buildcraft.builders.TileConstructionMarker");
            Class.forName("buildcraft.core.Box");
            try {
                Class.forName("buildcraft.core.lib.utils.NetworkUtils");
            } catch (Exception ex) {
                Class.forName("buildcraft.core.utils.Utils");
            }
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
                if (entity != null && Class.forName("buildcraft.builders.TileConstructionMarker").isInstance(entity)) {
                    setSlot(mop.blockX, mop.blockY, mop.blockZ);
                    InteropUtils.log("Set", this);
                }
            }
            prevState = newState;
        } catch (Exception e) {
        }
    }

    public void setSlot(int x, int y, int z) throws Exception {
        ByteBuf buf = Unpooled.buffer(0);
        buf.writeShort(0);
        buf.writeInt(x);
        buf.writeShort(y);
        buf.writeInt(z);
        Class.forName("buildcraft.core.Box").getMethod("writeData", ByteBuf.class).invoke(Class.forName("buildcraft.core.Box").getConstructor().newInstance(), buf);
        buf.writeByte(2);
        try {
            Class.forName("buildcraft.core.lib.utils.NetworkUtils").getMethod("writeStack", ByteBuf.class, ItemStack.class).invoke(null, buf, Statics.STATIC_ITEMSTACK);
        } catch (Exception ex) {
            Class.forName("buildcraft.core.utils.Utils").getMethod("writeStack", ByteBuf.class, ItemStack.class).invoke(null, buf, Statics.STATIC_ITEMSTACK);
        }
        C17PacketCustomPayload packet = new C17PacketCustomPayload("BC-CORE", buf);
        Wrapper.INSTANCE.player().sendQueue.addToSendQueue(packet);
    }

    @Override
    public String getModName() {
        return "BuildCraft";
    }
}
