/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiScreen
 */
package ehacks.mod.modulesystem.classes;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import ehacks.api.module.Mod;
import ehacks.mod.gui.reeszrbteam.YouAlwaysWinClickGui;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;

public class Gui
extends Mod {
    private YouAlwaysWinClickGui click = new YouAlwaysWinClickGui();

    public Gui() {
        super(ModuleCategory.NONE);
        this.setKeybinding(34);
    }

    @Override
    public String getName() {
        return "Gui";
    }

    @Override
    public void toggle() {
        Wrapper.INSTANCE.mc().displayGuiScreen((GuiScreen)this.click);
    }
}

