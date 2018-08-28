package ehacks.mod.modulesystem.classes;

import ehacks.api.module.Module;
import ehacks.mod.wrapper.ModuleCategory;

public class NameProtect
        extends Module {

    public static boolean isActive = false;

    public NameProtect() {
        super(ModuleCategory.RENDER);
    }

    @Override
    public String getName() {
        return "NameProtect";
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
