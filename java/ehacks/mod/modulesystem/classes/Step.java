/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.entity.EntityClientPlayerMP
 */
package ehacks.mod.modulesystem.classes;

import net.minecraft.client.entity.EntityClientPlayerMP;
import ehacks.api.module.Mod;
import ehacks.mod.wrapper.ModuleCategories;
import ehacks.mod.wrapper.Wrapper;

public class Step
extends Mod {
    public Step() {
        super(ModuleCategories.PLAYER);
    }

    @Override
    public String getName() {
        return "Step";
    }

    @Override
    public void onTicks() {
        Wrapper.INSTANCE.player().stepHeight = 2;
    }

    @Override
    public void onDisableMod() {
        Wrapper.INSTANCE.player().stepHeight = 0.5f;
    }
}

