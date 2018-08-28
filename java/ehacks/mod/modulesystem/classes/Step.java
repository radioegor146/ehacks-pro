package ehacks.mod.modulesystem.classes;

import ehacks.api.module.Module;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;

public class Step
        extends Module {

    public Step() {
        super(ModuleCategory.PLAYER);
    }

    @Override
    public String getName() {
        return "Step";
    }

    @Override
    public String getDescription() {
        return "Allows you to walk on 2 blocks height";
    }

    @Override
    public void onTicks() {
        Wrapper.INSTANCE.player().stepHeight = 2;
    }

    @Override
    public void onDisableMod() {
        Wrapper.INSTANCE.player().stepHeight = 0.5f;
    }
}
