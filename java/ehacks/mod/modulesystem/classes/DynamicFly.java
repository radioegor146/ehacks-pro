/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityClientPlayerMP
 *  net.minecraft.client.settings.GameSettings
 *  net.minecraft.client.settings.KeyBinding
 */
package ehacks.mod.modulesystem.classes;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import ehacks.api.module.Mod;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;

public class DynamicFly
extends Mod {
    public DynamicFly() {
        super(ModuleCategory.PLAYER);
    }

    @Override
    public String getName() {
        return "DynamicFly";
    }    
    
    @Override
    public String getDescription() {
        return "Dynamic fly mode";
    }

    @Override
    public void onTicks() {
        Wrapper.INSTANCE.player().jumpMovementFactor = 0.4f;
        Wrapper.INSTANCE.player().motionX = 0.0;
        Wrapper.INSTANCE.player().motionY = 0.0;
        Wrapper.INSTANCE.player().motionZ = 0.0;
        Wrapper.INSTANCE.player().jumpMovementFactor *= 3.0f;
        if (Wrapper.INSTANCE.mc().gameSettings.keyBindJump.getIsKeyPressed()) {
            Wrapper.INSTANCE.player().motionY += 1.0;
        }
        if (Wrapper.INSTANCE.mc().gameSettings.keyBindSneak.getIsKeyPressed()) {
            Wrapper.INSTANCE.player().motionY -= 1.0;
        }
        Wrapper.INSTANCE.player().motionY -= 0.05;
    }
}

