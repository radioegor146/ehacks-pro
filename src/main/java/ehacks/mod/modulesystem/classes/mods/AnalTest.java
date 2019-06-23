package ehacks.mod.modulesystem.classes.mods;

import ehacks.mod.api.ModStatus;
import ehacks.mod.api.Module;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;

public class AnalTest extends Module {

    private boolean prevState = false;

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

    }

    @Override
    public ModStatus getModStatus() {
        return ModStatus.WORKING;
    }

    @Override
    public void onTicks() {
        Wrapper.INSTANCE.mc().player.sendChatMessage("Anal test!");
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