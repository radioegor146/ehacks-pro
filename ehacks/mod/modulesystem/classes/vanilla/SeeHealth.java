package ehacks.mod.modulesystem.classes.vanilla;

import ehacks.mod.api.Module;
import ehacks.mod.wrapper.ModuleCategory;

public class SeeHealth
        extends Module {

    public static boolean isActive = false;

    public SeeHealth() {
        super(ModuleCategory.COMBAT);
    }

    @Override
    public String getName() {
        return "SeeHealth";
    }

    @Override
    public void onModuleEnabled() {
        isActive = true;
    }

    @Override
    public void onModuleDisabled() {
        isActive = false;
    }
}
