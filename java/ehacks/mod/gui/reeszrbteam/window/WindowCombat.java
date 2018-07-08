/*
 * Decompiled with CFR 0_128.
 */
package ehacks.mod.gui.reeszrbteam.window;

import java.util.ArrayList;
import ehacks.api.module.APICEMod;
import ehacks.api.module.Mod;
import ehacks.mod.gui.reeszrbteam.element.YAWWindow;
import ehacks.mod.wrapper.ModuleCategories;

public class WindowCombat
extends YAWWindow {
    public WindowCombat() {
        super("Combat", 186, 2);
    }

    public YAWWindow init() {
        for (Mod mod : APICEMod.INSTANCE.mods) {
            if (mod.getCategory() != ModuleCategories.COMBAT) continue;
            this.addButton(mod);
        }
        return this;
    }
}

