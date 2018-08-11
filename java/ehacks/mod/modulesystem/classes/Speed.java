/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Timer
 */
package ehacks.mod.modulesystem.classes;

import net.minecraft.util.Timer;
import ehacks.api.module.Mod;
import ehacks.mod.util.TimerUtils;
import ehacks.mod.wrapper.ModuleCategory;

public class Speed
extends Mod {
    public Speed() {
        super(ModuleCategory.PLAYER);
    }

    @Override
    public String getName() {
        return "Speed";
    }

    @Override
    public void onTicks() {
        TimerUtils.getTimer().timerSpeed = 3.0f;
    }

    @Override
    public void onDisableMod() {
        TimerUtils.getTimer().timerSpeed = 1.0f;
    }
}

