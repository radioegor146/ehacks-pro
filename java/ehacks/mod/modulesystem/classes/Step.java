/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.entity.EntityClientPlayerMP
 */
package ehacks.mod.modulesystem.classes;

import net.minecraft.client.entity.EntityClientPlayerMP;
import ehacks.api.module.Mod;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;

public class Step
extends Mod {
    public Step() {
        super(ModuleCategory.PLAYER);
    }

    @Override
    public String getName() {
        return "Step";
    }
    
    @Override
    public String getDescription() {
        return "Allows you to walk on 2 blocks height";
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

