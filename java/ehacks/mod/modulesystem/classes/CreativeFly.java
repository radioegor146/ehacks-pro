/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.entity.EntityClientPlayerMP
 *  net.minecraft.entity.player.PlayerCapabilities
 */
package ehacks.mod.modulesystem.classes;

import ehacks.api.module.Mod;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.PacketHandler.Side;
import ehacks.mod.wrapper.Wrapper;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.PlayerCapabilities;

public class CreativeFly
extends Mod {
    public CreativeFly() {
        super(ModuleCategory.PLAYER);
        this.setKeybinding(19);
    }

    @Override
    public String getName() {
        return "Creative Fly";
    }

    @Override
    public String getDescription() {
        return "Fly like in creative mode";
    }

    @Override
    public void onEnableMod() {
        
    }
    
    @Override
    public void onTicks() {
        if (Wrapper.INSTANCE.player() != null && !Wrapper.INSTANCE.player().capabilities.isCreativeMode) {
            Wrapper.INSTANCE.player().capabilities.allowFlying = true;
        }
    }

    @Override
    public void onDisableMod() {
        if (Wrapper.INSTANCE.player() != null && !Wrapper.INSTANCE.player().capabilities.isCreativeMode) {
            Wrapper.INSTANCE.player().capabilities.allowFlying = false;
        }
    }
    
    @Override
    public boolean onPacket(Object packet, Side side) {
        if (packet instanceof net.minecraft.network.play.client.C13PacketPlayerAbilities)
            return false;
        return true;
    }
}

