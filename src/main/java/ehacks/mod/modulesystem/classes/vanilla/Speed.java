package ehacks.mod.modulesystem.classes.vanilla;

import ehacks.mod.api.Module;
import ehacks.mod.config.CheatConfiguration;
import ehacks.mod.util.TimerUtils;
import ehacks.mod.wrapper.ModuleCategory;

public class Speed
        extends Module {

    public Speed() {
        super(ModuleCategory.PLAYER);
    }

    @Override
    public String getName() {
        return "Speed";
    }

    @Override
    public String getDescription() {
        return "Your game will run 3x faster";
    }

    @Override
    public void onTicks() {
        TimerUtils.getTimer().timerSpeed = (float) CheatConfiguration.config.speedhack;
    }

    @Override
    public void onModuleDisabled() {
        TimerUtils.getTimer().timerSpeed = 1.0f;
    }
}
