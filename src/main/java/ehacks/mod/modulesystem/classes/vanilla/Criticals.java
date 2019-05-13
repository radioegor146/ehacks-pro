package ehacks.mod.modulesystem.classes.vanilla;

import ehacks.mod.api.Module;
import ehacks.mod.wrapper.ModuleCategory;

public class Criticals
        extends Module {

    public static boolean isActive = false;

    public Criticals() {
        super(ModuleCategory.COMBAT);
    }

    @Override
    public String getName() {
        return "Criticals";
    }

    @Override
    public String getDescription() {
        return "Jumps on left click";
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
