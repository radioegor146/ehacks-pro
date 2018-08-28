package ehacks.mod.modulesystem.classes;

import ehacks.api.module.Module;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;

public class NoFall
        extends Module {

    public NoFall() {
        super(ModuleCategory.PLAYER);
    }

    @Override
    public String getName() {
        return "NoFall";
    }

    @Override
    public String getDescription() {
        return "Gives you zero damage on fall";
    }

    @Override
    public void onTicks() {
        if (Wrapper.INSTANCE.player().fallDistance > 2.0f) {
            Wrapper.INSTANCE.player().sendQueue.addToSendQueue((Packet) new C03PacketPlayer.C04PacketPlayerPosition(Wrapper.INSTANCE.player().motionX, -999.0, -999.0, Wrapper.INSTANCE.player().motionZ, !Wrapper.INSTANCE.player().onGround));
        }
    }
}
