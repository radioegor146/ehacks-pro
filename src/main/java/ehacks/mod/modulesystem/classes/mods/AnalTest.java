package ehacks.mod.modulesystem.classes.mods;

import ehacks.mod.api.ModStatus;
import ehacks.mod.api.Module;
import ehacks.mod.util.InteropUtils;
import ehacks.mod.wrapper.ModuleCategory;

public class AnalTest extends Module {
    public AnalTest() {
        super(ModuleCategory.EHACKS);
    }

    @Override
    public String getName() {
        return "AnalTest";
    }

    @Override
    public String getDescription() {
        return "Go fuck yourself.";
    }

    @Override
    public void onModuleEnabled() {
        InteropUtils.log("Module test.", "YourMother");
    }

    @Override
    public ModStatus getModStatus() {
        return ModStatus.WORKING;
    }

    @Override
    public void onTicks() {

    }

    @Override
    public String getModName() {
        return "AnalTest";
    }

    @Override
    public boolean canOnOnStart() {
        return true;
    }
}