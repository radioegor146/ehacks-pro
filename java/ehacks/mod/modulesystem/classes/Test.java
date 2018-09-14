package ehacks.mod.modulesystem.classes;

import ehacks.api.module.Module;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.PacketHandler;

public class Test
        extends Module {

    public Test() {
        super(ModuleCategory.EHACKS);
    }

    @Override
    public String getName() {
        return "Test";
    }

    @Override
    public void onEnableMod() {

    }

    @Override
    public String getModName() {
        return "Test";
    }

    @Override
    public String getDescription() {
        return "Test";
    }

    @Override
    public boolean onPacket(Object packet, PacketHandler.PacketSide side) {
        return !(side == PacketHandler.PacketSide.OUT && (packet instanceof net.minecraft.network.play.client.C07PacketPlayerDigging));
    }

    @Override
    public boolean shouldInclude() {
        return false;
    }
}
