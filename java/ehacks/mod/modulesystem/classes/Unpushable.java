package ehacks.mod.modulesystem.classes;

import ehacks.api.module.Module;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;

public class Unpushable
        extends Module {

    public Unpushable() {
        super(ModuleCategory.PLAYER);
    }

    @Override
    public String getName() {
        return "Unpushable";
    }

    @Override
    public String getDescription() {
        return "No more knockback! :)";
    }

    @Override
    public void onTicks() {
        if (Wrapper.INSTANCE.player().hurtResistantTime > 0 && Wrapper.INSTANCE.player().hurtTime > 0) {
            Wrapper.INSTANCE.player().motionX = 0.0;
            Wrapper.INSTANCE.player().motionZ = 0.0;
        }
    }
}
