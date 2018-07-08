/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.multiplayer.WorldClient
 */
package ehacks.mod.modulesystem.classes;

import net.minecraft.client.multiplayer.WorldClient;
import ehacks.api.module.Mod;
import ehacks.mod.wrapper.ModuleCategories;
import ehacks.mod.wrapper.Wrapper;

public class NoWeather
extends Mod {
    public NoWeather() {
        super(ModuleCategories.RENDER);
    }

    @Override
    public String getName() {
        return "NoWeather";
    }

    @Override
    public String getDescription() {
        return "Stops rain.";
    }

    @Override
    public void onTicks() {
        if (Wrapper.INSTANCE.world() != null) {
            Wrapper.INSTANCE.world().setRainStrength(0.0f);
        }
    }
}

