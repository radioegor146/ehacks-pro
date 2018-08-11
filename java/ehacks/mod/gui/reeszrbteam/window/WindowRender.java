package ehacks.mod.gui.reeszrbteam.window;

import ehacks.api.module.ModController;
import ehacks.api.module.Mod;
import ehacks.mod.gui.reeszrbteam.element.YAWWindow;
import ehacks.mod.wrapper.ModuleCategory;
import java.util.ArrayList;

public class WindowRender
extends YAWWindow {
    public WindowRender() {
        super("Render", 278, 2);
    }

    public YAWWindow init() {
        for (Mod mod : ModController.INSTANCE.mods) {
            if (mod.getCategory() != ModuleCategory.RENDER) continue;
            this.addButton(mod);
        }
        this.setup();
        return this;
    }
}

