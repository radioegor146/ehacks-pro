/*
 * Decompiled with CFR 0_128.
 */
package ehacks.mod.modulesystem.classes;

import ehacks.api.module.Mod;
import ehacks.mod.wrapper.ModuleCategory;

public class SeeHealth
extends Mod {
    public static boolean isActive = false;

    public SeeHealth() {
        super(ModuleCategory.COMBAT);
    }

    @Override
    public String getName() {
        return "SeeHealth";
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

