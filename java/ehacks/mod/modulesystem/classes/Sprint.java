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

public class Sprint
extends Mod {
    public Sprint() {
        super(ModuleCategories.PLAYER);
    }

    @Override
    public String getName() {
        return "Sprint";
    }

    @Override
    public String getDescription() {
        return "Sprints automatically when you should be walking.";
    }

    @Override
    public void onTicks() {
        if (Wrapper.INSTANCE.player().moveForward > 0.0f) {
            Wrapper.INSTANCE.player().setSprinting(true);
        }
    }
}

