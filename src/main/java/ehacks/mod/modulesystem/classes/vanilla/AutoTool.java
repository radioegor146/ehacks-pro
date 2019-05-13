package ehacks.mod.modulesystem.classes.vanilla;

import ehacks.mod.api.Module;
import ehacks.mod.wrapper.ModuleCategory;

public class AutoTool
        extends Module {

    public static boolean isActive = false;

    public AutoTool() {
        super(ModuleCategory.PLAYER);
    }

    @Override
    public String getName() {
        return "AutoTool";
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
