package ehacks.mod.modulesystem.classes;

import ehacks.api.module.Module;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;

public class NoWeather
        extends Module {

    public NoWeather() {
        super(ModuleCategory.RENDER);
    }

    @Override
    public String getName() {
        return "NoWeather";
    }

    @Override
    public String getDescription() {
        return "Stops rain";
    }

    @Override
    public void onTicks() {
        if (Wrapper.INSTANCE.world() != null) {
            Wrapper.INSTANCE.world().setRainStrength(0.0f);
        }
    }
}
