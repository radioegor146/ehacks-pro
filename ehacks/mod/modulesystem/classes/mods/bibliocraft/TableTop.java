package ehacks.mod.modulesystem.classes.mods.bibliocraft;

import cpw.mods.fml.common.network.ByteBufUtils;
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

public class TableTop
        extends Module {

    public TableTop() {
        super(ModuleCategory.EHACKS);
    }

    @Override
    public String getName() {
        return "TableTop";
    }

    @Override
    public String getDescription() {
        return "You can give any itemstack to writing table\nUsage:\n  Numpad0 - give";
    }

    @Override
    public void onModuleEnabled() {
        try {
            Class.forName("jds.bibliocraft.BiblioCraft");
        } catch (Exception ex) {
            this.off();
        }
    }

    @Override
    public ModStatus getModStatus() {
        try {
            Class.forName("jds.bibliocraft.BiblioCraft");
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
                if (entity != null && Class.forName("jds.bibliocraft.tileentities.TileEntityWritingDesk").isInstance(entity)) {
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
        ItemStack stack = Statics.STATIC_ITEMSTACK.copy();
        stack.setStackDisplayName("Mega Super Spell");
        ByteBufUtils.writeItemStack(buf, stack);
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        buf.writeInt(1);
        C17PacketCustomPayload packet = new C17PacketCustomPayload("BiblioMCBEdit", buf);
        Wrapper.INSTANCE.player().sendQueue.addToSendQueue(packet);
    }

    @Override
    public String getModName() {
        return "BiblioCraft";
    }
}
