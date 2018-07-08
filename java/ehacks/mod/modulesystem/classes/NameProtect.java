/*
 * Decompiled with CFR 0_128.
 */
package ehacks.mod.modulesystem.classes;

import ehacks.api.module.Mod;
import ehacks.mod.wrapper.ModuleCategories;

public class NameProtect
extends Mod {
    public static boolean isActive = false;

    public NameProtect() {
        super(ModuleCategories.RENDER);
    }

    @Override
    public String getName() {
        return "NameProtect";
    }

    @Override
    public void onEnableMod() {
        isActive = true;
    }

    @Override
    public void onDisableMod() {
        isActive = false;
    }
}

