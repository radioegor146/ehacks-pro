package ehacks.mod.modulesystem.classes.mods.tinkers;

import ehacks.mod.api.ModStatus;
import ehacks.mod.api.Module;
import ehacks.mod.util.InteropUtils;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.network.play.client.C17PacketCustomPayload;

public class CloudStorage
        extends Module {

    public CloudStorage() {
        super(ModuleCategory.EHACKS);
    }

    @Override
    public String getName() {
        return "CloudStorage";
    }

    @Override
    public String getDescription() {
        return "Allow you to open knapsnack from Tinker's Construct";
    }

    @Override
    public void onModuleEnabled() {
        try {
            Class.forName("tconstruct.util.network.AccessoryInventoryPacket");
            ByteBuf buf = Unpooled.buffer(0);
            buf.writeByte(1);
            buf.writeInt(102);
            C17PacketCustomPayload packet = new C17PacketCustomPayload("TConstruct", buf);
            Wrapper.INSTANCE.player().sendQueue.addToSendQueue(packet);
            InteropUtils.log("Opened", this);
            this.off();
        } catch (ClassNotFoundException ex) {
            this.off();
        }
    }

    @Override
    public ModStatus getModStatus() {
        try {
            Class.forName("tconstruct.util.network.AccessoryInventoryPacket");
            return ModStatus.WORKING;
        } catch (ClassNotFoundException e) {
            return ModStatus.NOTWORKING;
        }
    }

    @Override
    public String getModName() {
        return "Tinker's";
    }
}
