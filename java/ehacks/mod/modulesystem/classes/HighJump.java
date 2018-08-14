/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.entity.EntityClientPlayerMP
 *  net.minecraft.potion.Potion
 *  net.minecraft.potion.PotionEffect
 */
package ehacks.mod.modulesystem.classes;

import ehacks.api.module.Mod;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class HighJump
extends Mod {
    public HighJump() {
        super(ModuleCategory.PLAYER);
    }

    @Override
    public String getName() {
        return "High Jump";
    }

    @Override
    public String getDescription() {
        return "Gives you Jump Boost 2 effect";
    }

    @Override
    public void onEnableMod() {
        Wrapper.INSTANCE.player().addPotionEffect(new PotionEffect(Potion.jump.getId(), 9999999, 2));
    }

    @Override
    public void onDisableMod() {
        Wrapper.INSTANCE.player().removePotionEffect(Potion.jump.getId());
    }
}

