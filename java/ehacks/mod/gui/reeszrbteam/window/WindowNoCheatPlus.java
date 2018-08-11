package ehacks.mod.gui.reeszrbteam.window;

import ehacks.api.module.ModController;
import ehacks.api.module.Mod;
import ehacks.mod.gui.reeszrbteam.element.YAWWindow;
import ehacks.mod.wrapper.ModuleCategory;

public class WindowNoCheatPlus
extends YAWWindow {
    public WindowNoCheatPlus() {
        super("NoCheatPlus", 462, 2);
    }

    public YAWWindow init() {
        for (Mod mod : ModController.INSTANCE.mods) {
            if (mod.getCategory() != ModuleCategory.NOCHEATPLUS) continue;
            this.addButton(mod);
        }
        this.setup();
        return this;
    }
}

