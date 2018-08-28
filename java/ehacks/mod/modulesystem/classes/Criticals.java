package ehacks.mod.modulesystem.classes;

import ehacks.api.module.Module;
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
    public void onEnableMod() {
        isActive = true;
    }

    @Override
    public void onDisableMod() {
        isActive = false;
    }
}
