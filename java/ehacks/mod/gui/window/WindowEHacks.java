package ehacks.mod.gui.window;

import ehacks.api.module.ModController;
import ehacks.api.module.Mod;
import ehacks.mod.gui.element.ModWindow;
import ehacks.mod.wrapper.ModuleCategory;

public class WindowEHacks
extends ModWindow {
    public WindowEHacks() {
        super("EHacks", 646, 2);
    }

    public ModWindow init() {
        for (Mod mod : ModController.INSTANCE.mods) {
            if (mod.getCategory() != ModuleCategory.EHACKS) continue;
            this.addButton(mod);
        }
        this.setup();
        return this;
    }
    
    
}

