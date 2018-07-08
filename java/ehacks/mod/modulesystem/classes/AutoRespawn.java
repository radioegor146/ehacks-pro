/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.entity.EntityClientPlayerMP
 */
package ehacks.mod.modulesystem.classes;

import ehacks.api.module.Mod;
import ehacks.mod.wrapper.ModuleCategories;
import ehacks.mod.wrapper.Wrapper;
import net.minecraft.client.entity.EntityClientPlayerMP;

public class AutoRespawn
extends Mod {
    public AutoRespawn() {
        super(ModuleCategories.PLAYER);
    }

    @Override
    public String getName() {
        return "Auto Respawn";
    }

    @Override
    public String getDescription() {
        return "Auto Respawn the player when dead.";
    }

    @Override
    public void onTick() {
        if (Wrapper.INSTANCE.player().isDead) {
            Wrapper.INSTANCE.player().respawnPlayer();
        }
    }
}

