package ehacks.mod.gui.window;

import ehacks.api.module.ModController;
import ehacks.api.module.Mod;
import ehacks.mod.gui.element.ModWindow;
import ehacks.mod.wrapper.ModuleCategory;
import java.util.ArrayList;

public class WindowRender
extends ModWindow {
    public WindowRender() {
        super("Render", 278, 2);
    }

    public ModWindow init() {
        for (Mod mod : ModController.INSTANCE.mods) {
            if (mod.getCategory() != ModuleCategory.RENDER) continue;
            this.addButton(mod);
        }
        this.setup();
        return this;
    }
}

