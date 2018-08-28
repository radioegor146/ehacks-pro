package ehacks.mod.modulesystem.classes;

import ehacks.api.module.Module;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;

public class AntiFire
        extends Module {

    public AntiFire() {
        super(ModuleCategory.PLAYER);
    }

    @Override
    public String getName() {
        return "AntiFire";
    }

    @Override
    public String getDescription() {
        return "Removes fire on you";
    }

    @Override
    public void onTicks() {
        if (!WaterWalk.isOnLiquid(Wrapper.INSTANCE.player().boundingBox) && Wrapper.INSTANCE.player().isBurning() && Wrapper.INSTANCE.player().onGround) {
            for (int i = 0; i < 10; ++i) {
                Wrapper.INSTANCE.player().sendQueue.addToSendQueue((Packet) new C03PacketPlayer(false));
            }
        }
    }
}
