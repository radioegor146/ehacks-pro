package ehacks.mod.modulesystem.classes;

import ehacks.api.module.Module;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;

public class InvisiblePlayer
        extends Module {

    public InvisiblePlayer() {
        super(ModuleCategory.PLAYER);
    }

    @Override
    public String getName() {
        return "InvisiblePlayer";
    }

    @Override
    public String getDescription() {
        return "Makes the player... invisible :)";
    }

    @Override
    public void onTicks() {
        Wrapper.INSTANCE.player().setInvisible(true);
    }

    @Override
    public void onDisableMod() {
        Wrapper.INSTANCE.player().setInvisible(false);
    }
}
