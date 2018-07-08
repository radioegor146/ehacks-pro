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

public class AntiKnockBack
extends Mod {
    public AntiKnockBack() {
        super(ModuleCategories.PLAYER);
    }

    @Override
    public String getName() {
        return "AntiKnockBack";
    }

    @Override
    public void onWorldRender() {
        if (Wrapper.INSTANCE.player().hurtResistantTime > 0 && Wrapper.INSTANCE.player().hurtTime > 0) {
            Wrapper.INSTANCE.player().hurtResistantTime = 0;
            Wrapper.INSTANCE.player().hurtTime = 0;
            Wrapper.INSTANCE.player().motionX = 0.0;
            Wrapper.INSTANCE.player().motionY /= 10.0;
            Wrapper.INSTANCE.player().motionZ = 0.0;
        }
    }
}

