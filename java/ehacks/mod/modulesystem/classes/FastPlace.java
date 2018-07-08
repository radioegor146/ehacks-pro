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
import ehacks.mod.wrapper.ModuleCategories;
import ehacks.mod.wrapper.Wrapper;

public class FastPlace
extends Mod {
    public FastPlace() {
        super(ModuleCategories.PLAYER);
    }

    @Override
    public String getName() {
        return "FastPlace";
    }

    @Override
    public void onTicks() {
        if (Wrapper.INSTANCE.mc().gameSettings.keyBindUseItem.getIsKeyPressed()) {
            try {
                Method m = ReflectionHelper.findMethod(Minecraft.class, Wrapper.INSTANCE.mc(), (String[])new String[]{"func_147121_ag"}, (Class[])null);
                m.invoke((Object)Minecraft.getMinecraft(), new Object[0]);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

