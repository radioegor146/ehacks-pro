package ehacks.mod.modulesystem.classes;

import ehacks.api.module.Module;
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
    public void onEnableMod() {
        isActive = true;
    }

    @Override
    public void onDisableMod() {
        isActive = false;
    }
}
