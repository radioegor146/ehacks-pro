package ehacks.mod.modulesystem.handler;

import ehacks.mod.api.Module;
import ehacks.mod.api.ModuleController;
import ehacks.mod.modulesystem.classes.keybinds.*;
import ehacks.mod.modulesystem.classes.mods.FMLProxyLogIN;
import ehacks.mod.modulesystem.classes.mods.FMLProxyLogOUT;
import ehacks.mod.modulesystem.classes.mods.MCFMDoorMat;

public class ModuleManagement {

    public static volatile ModuleManagement INSTANCE = new ModuleManagement();

    public ModuleManagement() {
        this.initModules();
    }

    public static ModuleManagement instance() {
        return INSTANCE;
    }

    private void add(Module mod) {
        ModuleController.INSTANCE.enable(mod);
    }

    public void initModules() {
        this.add(new FMLProxyLogIN());
        this.add(new FMLProxyLogOUT());
        this.add(new MCFMDoorMat());

        this.add(new GiveKeybind());
        this.add(new SelectPlayerKeybind());
        this.add(new NEISelectKeybind());
        this.add(new ShowGroupsKeybind());
        this.add(new HideCheatKeybind());
        this.add(new OpenNBTEditKeybind());
        this.add(new OpenAE2ViewerKeybind());
        this.add(new OpenConsoleKeybind());
        this.add(new MagnetKeybind());
        this.add(new SingleDebugMeKeybind());
        this.add(new TickingDebugMeKeybind());

        ModuleController.INSTANCE.sort();

        this.add(new EHacksGui());
    }
}
