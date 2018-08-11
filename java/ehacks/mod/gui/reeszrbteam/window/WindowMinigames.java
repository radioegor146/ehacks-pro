package ehacks.mod.gui.reeszrbteam.window;

import ehacks.api.module.ModController;
import ehacks.api.module.Mod;
import ehacks.mod.gui.reeszrbteam.element.YAWWindow;
import ehacks.mod.wrapper.ModuleCategory;

public class WindowMinigames
extends YAWWindow {
    public WindowMinigames() {
        super("Minigames", 370, 2);
    }

    public YAWWindow init() {
        for (Mod mod : ModController.INSTANCE.mods) {
            if (mod.getCategory() != ModuleCategory.MINIGAMES) continue;
            this.addButton(mod);
        }
        this.setup();
        return this;
    }
}

