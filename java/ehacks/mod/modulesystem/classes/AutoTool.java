/*
 * Decompiled with CFR 0_128.
 */
package ehacks.mod.modulesystem.classes;

import ehacks.api.module.Mod;
import ehacks.mod.wrapper.ModuleCategories;

public class AutoTool
extends Mod {
    public static boolean isActive = false;

    public AutoTool() {
        super(ModuleCategories.PLAYER);
    }

    @Override
    public String getName() {
        return "AutoTool";
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

