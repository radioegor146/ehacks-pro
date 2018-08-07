/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.entity.EntityClientPlayerMP
 *  net.minecraft.entity.player.PlayerCapabilities
 */
package ehacks.mod.modulesystem.classes;

import ehacks.api.module.Mod;
import ehacks.mod.wrapper.ModuleCategories;
import ehacks.mod.wrapper.Wrapper;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.PlayerCapabilities;

public class CreativeFly
extends Mod {
    public CreativeFly() {
        super(ModuleCategories.PLAYER);
        this.setKeybinding(19);
    }

    @Override
    public String getName() {
        return "Creative Fly";
    }

    @Override
    public String getDescription() {
        return "Fly like in creative mode!";
    }

    @Override
    public void onEnableMod() {
        if (!Wrapper.INSTANCE.player().capabilities.isCreativeMode) {
            Wrapper.INSTANCE.player().capabilities.allowFlying = true;
            Wrapper.INSTANCE.player().sendPlayerAbilities();
        }
    }
    
    @Override
    public void onTicks() {
        if (!Wrapper.INSTANCE.player().capabilities.isCreativeMode) {
            Wrapper.INSTANCE.player().capabilities.allowFlying = true;
            Wrapper.INSTANCE.player().sendPlayerAbilities();
        }
    }

    @Override
    public void onDisableMod() {
        if (!Wrapper.INSTANCE.player().capabilities.isCreativeMode) {
            Wrapper.INSTANCE.player().capabilities.allowFlying = false;
            Wrapper.INSTANCE.player().sendPlayerAbilities();
        }
    }
}

