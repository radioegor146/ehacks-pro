package ehacks.mod.modulesystem.classes;

import ehacks.api.module.Module;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.PacketHandler.Side;
import ehacks.mod.wrapper.Wrapper;

public class CreativeFly
        extends Module {

    public CreativeFly() {
        super(ModuleCategory.PLAYER);
        this.setKeybinding(19);
    }

    @Override
    public String getName() {
        return "CreativeFly";
    }

    @Override
    public String getDescription() {
        return "Fly like in creative mode";
    }

    @Override
    public void onEnableMod() {

    }

    @Override
    public void onTicks() {
        if (Wrapper.INSTANCE.player() != null && !Wrapper.INSTANCE.player().capabilities.isCreativeMode) {
            Wrapper.INSTANCE.player().capabilities.allowFlying = true;
        }
    }

    @Override
    public void onDisableMod() {
        if (Wrapper.INSTANCE.player() != null && !Wrapper.INSTANCE.player().capabilities.isCreativeMode) {
            Wrapper.INSTANCE.player().capabilities.allowFlying = false;
        }
    }

    @Override
    public boolean onPacket(Object packet, Side side) {
        return !(packet instanceof net.minecraft.network.play.client.C13PacketPlayerAbilities);
    }
}
