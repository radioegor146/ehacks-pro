package ehacks.mod.modulesystem.classes;

import ehacks.api.module.Module;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;

public class NCPFly
        extends Module {

    public NCPFly() {
        super(ModuleCategory.NOCHEATPLUS);
    }

    @Override
    public String getName() {
        return "NCPFly";
    }

    @Override
    public String getDescription() {
        return "Fly for NoCheatPlus";
    }

    @Override
    public void onEnableMod() {
        for (int i = 0; i < 4; ++i) {
            Wrapper.INSTANCE.player().sendQueue.addToSendQueue((Packet) new C03PacketPlayer.C04PacketPlayerPosition(Wrapper.INSTANCE.player().posX, Wrapper.INSTANCE.player().boundingBox.minY + 1.01, Wrapper.INSTANCE.player().posY + 1.01, Wrapper.INSTANCE.player().posZ, false));
            Wrapper.INSTANCE.player().sendQueue.addToSendQueue((Packet) new C03PacketPlayer.C04PacketPlayerPosition(Wrapper.INSTANCE.player().posX, Wrapper.INSTANCE.player().boundingBox.minY, Wrapper.INSTANCE.player().posY, Wrapper.INSTANCE.player().posZ, false));
        }
        Wrapper.INSTANCE.player().setPosition(Wrapper.INSTANCE.player().posX, Wrapper.INSTANCE.player().posY + 0.8, Wrapper.INSTANCE.player().posZ);
    }

    @Override
    public void onTicks() {
        Wrapper.INSTANCE.player().motionY = -0.04;
        if (Wrapper.INSTANCE.mcSettings().keyBindJump.getIsKeyPressed()) {
            Wrapper.INSTANCE.player().motionY = 0.3;
        }
        if (Wrapper.INSTANCE.mcSettings().keyBindSneak.getIsKeyPressed()) {
            Wrapper.INSTANCE.player().motionY = -0.3;
        }
    }
}
