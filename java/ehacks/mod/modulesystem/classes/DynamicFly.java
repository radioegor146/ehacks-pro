package ehacks.mod.modulesystem.classes;

import ehacks.api.module.Module;
import ehacks.mod.external.config.CheatConfiguration;
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
        Wrapper.INSTANCE.player().jumpMovementFactor *= (float)CheatConfiguration.config.flyspeed * 3f;
        if (Wrapper.INSTANCE.mcSettings().keyBindJump.getIsKeyPressed()) {
            Wrapper.INSTANCE.player().motionY += CheatConfiguration.config.flyspeed;
        }
        if (Wrapper.INSTANCE.mcSettings().keyBindSneak.getIsKeyPressed()) {
            Wrapper.INSTANCE.player().motionY -= CheatConfiguration.config.flyspeed;
        }
        Wrapper.INSTANCE.player().motionY -= 0.05;
    }
}
