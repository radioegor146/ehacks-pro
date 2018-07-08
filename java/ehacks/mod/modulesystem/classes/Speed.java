/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Timer
 */
package ehacks.mod.modulesystem.classes;

import net.minecraft.util.Timer;
import ehacks.api.module.Mod;
import ehacks.mod.commands.ACommandSpeedValue;
import ehacks.mod.util.TimerUtils;
import ehacks.mod.wrapper.ModuleCategories;

public class Speed
extends Mod {
    public Speed() {
        super(ModuleCategories.PLAYER);
    }

    @Override
    public String getName() {
        return "Speed";
    }

    @Override
    public void onTicks() {
        TimerUtils.getTimer().timerSpeed = ACommandSpeedValue.SPEED_VALUE;
    }

    @Override
    public void onDisableMod() {
        TimerUtils.getTimer().timerSpeed = 1.0f;
    }
}

