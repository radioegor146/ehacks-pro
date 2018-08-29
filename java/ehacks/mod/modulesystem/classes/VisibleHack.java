package ehacks.mod.modulesystem.classes;

import ehacks.api.module.ModStatus;
import ehacks.api.module.Module;
import ehacks.mod.gui.EHacksClickGui;
import ehacks.mod.util.InteropUtils;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.network.play.client.C17PacketCustomPayload;

public class VisibleHack
        extends Module {

    public VisibleHack() {
        super(ModuleCategory.EHACKS);
    }

    @Override
    public String getName() {
        return "VisibleHack";
    }

    @Override
    public String getDescription() {
        return "Toggles your visiblity";
    }

    @Override
    public void onEnableMod() {
        try {
            Class.forName("com.kentington.thaumichorizons.common.lib.PacketToggleInvisibleToServer");
            toggleVisiblity();
            InteropUtils.log("Toggled", this);
            this.off();
        } catch (Exception ex) {
            this.off();
        }
    }

    @Override
    public ModStatus getModStatus() {
        try {
            Class.forName("com.kentington.thaumichorizons.common.lib.PacketToggleInvisibleToServer");
            return ModStatus.WORKING;
        } catch (Exception e) {
            return ModStatus.NOTWORKING;
        }
    }

    @Override
    public void onDisableMod() {

    }

    public void toggleVisiblity() {
        ByteBuf buf = Unpooled.buffer(0);
        buf.writeByte(13);
        buf.writeInt(Wrapper.INSTANCE.player().getEntityId());
        buf.writeInt(Wrapper.INSTANCE.player().dimension);
        C17PacketCustomPayload packet = new C17PacketCustomPayload("thaumichorizons", buf);
        Wrapper.INSTANCE.player().sendQueue.addToSendQueue(packet);
    }

    @Override
    public String getModName() {
        return "Horizons";
    }
}
