package ehacks.mod.modulesystem.classes.vanilla;

import ehacks.mod.api.Module;
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
    public void onModuleEnabled() {
        isActive = true;
    }

    @Override
    public void onModuleDisabled() {
        isActive = false;
    }
}
