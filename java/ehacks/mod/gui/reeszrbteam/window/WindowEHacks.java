/*
 * Decompiled with CFR 0_128.
 */
package ehacks.mod.gui.reeszrbteam.window;

import java.util.ArrayList;
import ehacks.api.module.APICEMod;
import ehacks.api.module.Mod;
import ehacks.mod.gui.reeszrbteam.element.YAWWindow;
import ehacks.mod.wrapper.ModuleCategories;

public class WindowEHacks
extends YAWWindow {
    public WindowEHacks() {
        super("EHacks", 94, 300);
    }

    public YAWWindow init() {
        for (Mod mod : APICEMod.INSTANCE.mods) {
            if (mod.getCategory() != ModuleCategories.EHACKS) continue;
            this.addButton(mod);
        }
        return this;
    }
}

