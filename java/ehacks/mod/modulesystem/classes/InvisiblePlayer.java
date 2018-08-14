/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.entity.EntityClientPlayerMP
 */
package ehacks.mod.modulesystem.classes;

import ehacks.api.module.Mod;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import net.minecraft.client.entity.EntityClientPlayerMP;

public class InvisiblePlayer
extends Mod {
    public InvisiblePlayer() {
        super(ModuleCategory.PLAYER);
    }

    @Override
    public String getName() {
        return "Invisible Player";
    }

    @Override
    public String getDescription() {
        return "Makes the player... invisible :)";
    }

    @Override
    public void onTicks() {
        Wrapper.INSTANCE.player().setInvisible(true);
    }

    @Override
    public void onDisableMod() {
        Wrapper.INSTANCE.player().setInvisible(false);
    }
}

