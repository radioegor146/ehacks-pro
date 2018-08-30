package ehacks.mod.modulesystem.classes;

import ehacks.api.module.ModStatus;
import ehacks.api.module.Module;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.network.play.client.C17PacketCustomPayload;

public class MetaHackAdd extends Module {

    public MetaHackAdd() {
        super(ModuleCategory.EHACKS);
    }

    @Override
    public String getName() {
        return "MetaHackAdd";
    }

    @Override
    public String getDescription() {
        return "Allows you to add 1 to metadata of item";
    }

    @Override
    public ModStatus getModStatus() {
        try {
            Class.forName("com.riciJak.Ztones.network.ToggleMetaData");
            return ModStatus.WORKING;
        } catch (Exception e) {
            return ModStatus.NOTWORKING;
        }
    }

    @Override
    public void onEnableMod() {
        try {
            Class.forName("com.riciJak.Ztones.network.ToggleMetaData").getConstructor(Boolean.TYPE);
            ByteBuf buf = Unpooled.buffer(0);
            buf.writeInt(0);
            buf.writeBoolean(true);
            C17PacketCustomPayload packet = new C17PacketCustomPayload("Ztones", buf);
            Wrapper.INSTANCE.player().sendQueue.addToSendQueue(packet);
            this.off();
        } catch (Exception ex) {
            this.off();
        }
    }

    @Override
    public String getModName() {
        return "ZTones";
    }
}
