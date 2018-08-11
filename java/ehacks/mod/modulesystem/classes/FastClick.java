/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.ReflectionHelper
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.settings.GameSettings
 *  net.minecraft.client.settings.KeyBinding
 */
package ehacks.mod.modulesystem.classes;

import cpw.mods.fml.relauncher.ReflectionHelper;
import java.lang.reflect.Method;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import ehacks.api.module.Mod;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;

public class FastClick
extends Mod {
    public FastClick() {
        super(ModuleCategory.COMBAT);
    }

    @Override
    public String getName() {
        return "FastClick";
    }

    @Override
    public String getDescription() {
        return "FastClick";
    }

    @Override
    public void onTicks() {
        if (Wrapper.INSTANCE.mc().gameSettings.keyBindAttack.getIsKeyPressed()) {
            try {
                Method m = ReflectionHelper.findMethod(Minecraft.class, Wrapper.INSTANCE.mc(), (String[])new String[]{"func_147116_af"}, (Class[])null);
                m.invoke((Object)Wrapper.INSTANCE.mc(), new Object[0]);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

