package ehacks.mod.gui.reeszrbteam.window;

import ehacks.api.module.ModController;
import ehacks.api.module.Mod;
import ehacks.mod.gui.reeszrbteam.element.YAWWindow;
import ehacks.mod.wrapper.ModuleCategories;

public class WindowCombat
extends YAWWindow {
    public WindowCombat() {
        super("Combat", 186, 2);
    }

    public YAWWindow init() {
        for (Mod mod : ModController.INSTANCE.mods) {
            if (mod.getCategory() != ModuleCategories.COMBAT) continue;
            this.addButton(mod);
        }
        this.setup();
        return this;
    }
}

