package ehacks.mod.modulesystem.classes;

import ehacks.api.module.Module;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;

public class Sprint
        extends Module {

    public Sprint() {
        super(ModuleCategory.PLAYER);
    }

    @Override
    public String getName() {
        return "Sprint";
    }

    @Override
    public String getDescription() {
        return "Sprints automatically when you should be walking";
    }

    @Override
    public void onTicks() {
        if (Wrapper.INSTANCE.player().moveForward > 0.0f) {
            Wrapper.INSTANCE.player().setSprinting(true);
        }
    }
}
