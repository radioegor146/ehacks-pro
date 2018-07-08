/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.entity.EntityClientPlayerMP
 *  net.minecraft.util.AxisAlignedBB
 */
package ehacks.mod.modulesystem.classes;

import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.util.AxisAlignedBB;
import ehacks.api.module.Mod;
import ehacks.mod.wrapper.ModuleCategories;
import ehacks.mod.wrapper.Wrapper;

public class NCPStep
extends Mod {
    public NCPStep() {
        super(ModuleCategories.NOCHEATPLUS);
    }

    @Override
    public String getName() {
        return "Step";
    }

    @Override
    public String getDescription() {
        return "Step for NoCheatPlus";
    }

    @Override
    public void onTicks() {
        if (Wrapper.INSTANCE.player().onGround && Wrapper.INSTANCE.player().isCollidedHorizontally && !Wrapper.INSTANCE.player().isInWater()) {
            Wrapper.INSTANCE.player().boundingBox.offset(0.0, 1.0628, 0.0);
            Wrapper.INSTANCE.player().motionY = -420.0;
            Wrapper.INSTANCE.player().isCollidedHorizontally = false;
        }
    }
}

