package ehacks.mod.modulesystem.classes;

import ehacks.api.module.Module;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;

public class NCPStep
        extends Module {

    public NCPStep() {
        super(ModuleCategory.NOCHEATPLUS);
    }

    @Override
    public String getName() {
        return "NCPStep";
    }

    @Override
    public String getDescription() {
        return "Step for NoCheatPlus";
    }

    @Override
    public void onTicks() {
        if (Wrapper.INSTANCE.player().onGround && Wrapper.INSTANCE.player().isCollidedHorizontally && !Wrapper.INSTANCE.player().isInWater()) {
            Wrapper.INSTANCE.player().boundingBox.offset(0.0, 1.0628, 0.0);
            Wrapper.INSTANCE.player().motionY = -420.0;
            Wrapper.INSTANCE.player().isCollidedHorizontally = false;
        }
    }
}
