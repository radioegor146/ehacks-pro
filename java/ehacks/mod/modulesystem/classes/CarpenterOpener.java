package ehacks.mod.modulesystem.classes;

import ehacks.api.module.ModStatus;
import ehacks.api.module.Module;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.event.MouseEvent;
import org.lwjgl.input.Mouse;

public class CarpenterOpener extends Module {

    public CarpenterOpener() {
        super(ModuleCategory.EHACKS);
    }

    @Override
    public String getName() {
        return "ContOpener";
    }

    @Override
    public String getDescription() {
        return "Allows to open containers on right click on servers with bad WorldGuard";
    }

    @Override
    public void onEnableMod() {
        try {
            Class.forName("com.carpentersblocks.network.PacketActivateBlock");
        } catch (Exception ex) {
            this.off();
        }
    }

    @Override
    public ModStatus getModStatus() {
        try {
            Class.forName("com.carpentersblocks.network.PacketActivateBlock");
            return ModStatus.WORKING;
        } catch (Exception e) {
            return ModStatus.NOTWORKING;
        }
    }

    @Override
    public void onMouse(MouseEvent event) {
        try {
            MovingObjectPosition position = Wrapper.INSTANCE.mc().objectMouseOver;
            if (position.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && Mouse.isButtonDown(1)) {
                if (event.isCancelable()) {
                    event.setCanceled(true);
                }
                ByteBuf buf = Unpooled.buffer(0);
                buf.writeInt(0);
                buf.writeInt(position.blockX);
                buf.writeInt(position.blockY);
                buf.writeInt(position.blockZ);
                buf.writeInt(position.sideHit);
                C17PacketCustomPayload packet = new C17PacketCustomPayload("CarpentersBlocks", buf);
                Wrapper.INSTANCE.player().sendQueue.addToSendQueue(packet);
            }
        } catch (Exception e) {

        }
    }

    @Override
    public String getModName() {
        return "Carpenters";
    }
}
