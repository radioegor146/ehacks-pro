package ehacks.mod.modulesystem.classes;

import ehacks.api.module.Module;
import ehacks.mod.external.config.CheatConfiguration;
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
        TimerUtils.getTimer().timerSpeed = (float)CheatConfiguration.config.speedhack;
    }

    @Override
    public void onDisableMod() {
        TimerUtils.getTimer().timerSpeed = 1.0f;
    }
}
