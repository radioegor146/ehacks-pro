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

public class ExtendedDestroyer
        extends Module {

    public ExtendedDestroyer() {
        super(ModuleCategory.EHACKS);
    }

    @Override
    public String getName() {
        return "ExtDestroyer";
    }

    @Override
    public String getDescription() {
        return "Breaks blocks instantly on left click";
    }

    @Override
    public void onEnableMod() {
        try {
            Class.forName("com.mrcrayfish.furniture.network.message.MessageTakeWater");
        } catch (Exception ex) {
            this.off();
        }
    }

    @Override
    public ModStatus getModStatus() {
        try {
            Class.forName("com.mrcrayfish.furniture.network.message.MessageTakeWater");
            return ModStatus.WORKING;
        } catch (Exception e) {
            return ModStatus.NOTWORKING;
        }
    }

    public boolean prevState;
    
    @Override
    public void onMouse(MouseEvent event) {
        try {
            MovingObjectPosition position = Wrapper.INSTANCE.mc().objectMouseOver;
            boolean nowState = Mouse.isButtonDown(0);
            if (position.sideHit != -1 && nowState && !prevState) {
                ByteBuf buf = Unpooled.buffer(0);
                buf.writeByte(14);
                buf.writeInt(position.blockX);
                buf.writeInt(position.blockY);
                buf.writeInt(position.blockZ);
                C17PacketCustomPayload packet = new C17PacketCustomPayload("cfm", buf);
                Wrapper.INSTANCE.player().sendQueue.addToSendQueue(packet);
            }
            prevState = nowState;
        } catch (Exception e) {

        }
    }

    @Override
    public String getModName() {
        return "Furniture";
    }
}
