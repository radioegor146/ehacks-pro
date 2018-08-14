package ehacks.mod.gui.window;

import ehacks.api.module.ModController;
import ehacks.api.module.Mod;
import ehacks.mod.gui.element.ModWindow;
import ehacks.mod.wrapper.ModuleCategory;

public class WindowCombat
extends ModWindow {
    public WindowCombat() {
        super("Combat", 186, 2);
    }

    public ModWindow init() {
        for (Mod mod : ModController.INSTANCE.mods) {
            if (mod.getCategory() != ModuleCategory.COMBAT) continue;
            this.addButton(mod);
        }
        this.setup();
        return this;
    }
}

