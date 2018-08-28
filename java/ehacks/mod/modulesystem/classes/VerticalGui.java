package ehacks.mod.modulesystem.classes;

import ehacks.api.module.ModStatus;
import ehacks.api.module.Module;
import ehacks.mod.gui.EHacksClickGui;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import org.lwjgl.input.Keyboard;

public class VerticalGui
        extends Module {

    public VerticalGui() {
        super(ModuleCategory.EHACKS);
    }

    @Override
    public String getName() {
        return "VerticalGui";
    }

    @Override
    public String getDescription() {
        return "Opens a gui to perform craft dupe\nUsage:\n  Y - Open gui";
    }

    @Override
    public void onEnableMod() {
        try {
            Class.forName("com.kentington.thaumichorizons.common.lib.PacketFingersToServer");
            EHacksClickGui.log("[VerticalGui] Press Y for GUI");
        } catch (Exception ex) {
            this.off();
            EHacksClickGui.log("[VerticalGui] Not working");
        }
    }

    @Override
    public ModStatus getModStatus() {
        try {
            Class.forName("com.kentington.thaumichorizons.common.lib.PacketFingersToServer");
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
        boolean newState = Keyboard.isKeyDown(Keyboard.KEY_Y);
        if (newState && !prevState) {
            openGui();
        }
        prevState = newState;
    }

    public void openGui() {
        ByteBuf buf = Unpooled.buffer(0);
        buf.writeByte(9);
        buf.writeInt(Wrapper.INSTANCE.player().getEntityId());
        buf.writeInt(Wrapper.INSTANCE.player().dimension);
        C17PacketCustomPayload packet = new C17PacketCustomPayload("thaumichorizons", buf);
        Wrapper.INSTANCE.player().sendQueue.addToSendQueue(packet);
        EHacksClickGui.log("[VerticalGui] Opened");
    }

    @Override
    public String getModName() {
        return "Horizons";
    }
}
