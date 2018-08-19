/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.multiplayer.WorldClient
 *  net.minecraft.world.World
 *  net.minecraft.world.WorldProvider
 */
package ehacks.mod.modulesystem.classes;

import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import ehacks.api.module.Module;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;

public class Fullbright
extends Module {
    public Fullbright() {
        super(ModuleCategory.RENDER);
    }
    
    private int cooldownTicks = 0;

    @Override
    public String getName() {
        return "Fullbright";
    }    
    
    @Override
    public String getDescription() {
        return "Brights all area around you";
    }

    @Override
    public void onEnableMod() {
        float[] bright = Wrapper.INSTANCE.world().provider.lightBrightnessTable;
        for (int i = 0; i < bright.length; ++i) {
            bright[i] = 1.0f;
        }
    }
    
    @Override
    public void onTicks() {
        if (cooldownTicks == 0)
        {
            float[] bright = Wrapper.INSTANCE.world().provider.lightBrightnessTable;
            for (int i = 0; i < bright.length; ++i) {
                bright[i] = 1.0f;
            }
        }
        cooldownTicks = (cooldownTicks + 1) % 80;
    }

    @Override
    public void onDisableMod() {
        Wrapper.INSTANCE.world().provider.registerWorld((World)Wrapper.INSTANCE.world());
    }
}

