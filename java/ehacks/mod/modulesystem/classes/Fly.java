/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityClientPlayerMP
 *  net.minecraft.entity.player.PlayerCapabilities
 */
package ehacks.mod.modulesystem.classes;

import ehacks.api.module.Mod;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.PlayerCapabilities;

public class Fly
extends Mod {
    public static float FLY_SPEED = 0.05f;

    public Fly() {
        super(ModuleCategory.PLAYER);
    }

    @Override
    public String getName() {
        return "Fly";
    }

    @Override
    public String getDescription() {
        return "I believe I can fly (8)~";
    }

    @Override
    public void onEnableMod() {
        Minecraft.getMinecraft().thePlayer.capabilities.setFlySpeed(FLY_SPEED);
        Wrapper.INSTANCE.player().capabilities.isFlying = true;
    }

    @Override
    public void onDisableMod() {
        Wrapper.INSTANCE.player().capabilities.isFlying = false;
    }

    @Override
    public void onTick() {
        Wrapper.INSTANCE.player().capabilities.isFlying = true;
    }
}

