package ehacks.mod.modulesystem.classes;

import ehacks.api.module.Module;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;

public class DynamicFly
        extends Module {

    public DynamicFly() {
        super(ModuleCategory.PLAYER);
    }

    @Override
    public String getName() {
        return "DynamicFly";
    }

    @Override
    public String getDescription() {
        return "Dynamic fly mode";
    }

    @Override
    public void onTicks() {
        Wrapper.INSTANCE.player().jumpMovementFactor = 0.4f;
        Wrapper.INSTANCE.player().motionX = 0.0;
        Wrapper.INSTANCE.player().motionY = 0.0;
        Wrapper.INSTANCE.player().motionZ = 0.0;
        Wrapper.INSTANCE.player().jumpMovementFactor *= 3.0f;
        if (Wrapper.INSTANCE.mcSettings().keyBindJump.getIsKeyPressed()) {
            Wrapper.INSTANCE.player().motionY += 1.0;
        }
        if (Wrapper.INSTANCE.mcSettings().keyBindSneak.getIsKeyPressed()) {
            Wrapper.INSTANCE.player().motionY -= 1.0;
        }
        Wrapper.INSTANCE.player().motionY -= 0.05;
    }
}
