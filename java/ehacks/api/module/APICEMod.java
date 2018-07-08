/*
 * Decompiled with CFR 0_128.
 */
package ehacks.api.module;

import java.util.ArrayList;
import ehacks.api.module.Mod;

public class APICEMod {
    public static volatile APICEMod INSTANCE = new APICEMod();
    public ArrayList<Mod> mods = new ArrayList();

    public void enable(Mod mod) {
        this.mods.add(mod);
    }

    public void disable(Mod mod) {
        this.mods.remove(mod);
    }

    public Mod call(Class clazz) {
        try {
            for (Mod mod : this.mods) {
                if (mod.getClass() != clazz) continue;
                return mod;
            }
        }
        catch (Exception exception) {
            throw new IllegalStateException("Why you use this for a non-module class?");
        }
        return null;
    }
}

