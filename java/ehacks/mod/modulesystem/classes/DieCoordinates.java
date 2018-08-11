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

public class DieCoordinates
extends Mod {
    int countdown = 80;

    public DieCoordinates() {
        super(ModuleCategory.PLAYER);
    }

    @Override
    public String getName() {
        return "Die Coordinates";
    }

    @Override
    public String getDescription() {
        return "Show coordinates on chat when player dies.";
    }

    @Override
    public void onTick() {
        if (Wrapper.INSTANCE.player().isDead && this.countdown == 1) {
            this.countdown = (int)(8.0 * Math.random());
            Wrapper.INSTANCE.addChatMessage("[EHacks] Coordinates on player dead: x:" + (int)Wrapper.INSTANCE.player().posX + " y:" + (int)Wrapper.INSTANCE.player().posY + " z:" + (int)Wrapper.INSTANCE.player().posZ);
        }
        if (!Wrapper.INSTANCE.player().isDead) {
            this.countdown = 1;
        }
    }
}

