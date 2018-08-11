/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.ReflectionHelper
 *  net.minecraft.client.entity.EntityClientPlayerMP
 *  net.minecraft.entity.Entity
 */
package ehacks.mod.modulesystem.classes;

import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.Entity;
import ehacks.api.module.Mod;
import ehacks.mod.util.Mappings;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;

public class NoWeb
extends Mod {
    public NoWeb() {
        super(ModuleCategory.PLAYER);
    }

    @Override
    public String getName() {
        return "NoWeb";
    }

    @Override
    public void onTicks() {
        ReflectionHelper.setPrivateValue(Entity.class, Wrapper.INSTANCE.player(), (Object)false, (String[])new String[]{Mappings.isInWeb});
    }
}

