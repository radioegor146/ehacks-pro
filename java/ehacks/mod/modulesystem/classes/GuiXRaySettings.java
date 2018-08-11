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
import ehacks.mod.gui.xraysettings.XRayGui;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;

public class GuiXRaySettings
extends Mod {
    private XRayGui gui = new XRayGui();

    public GuiXRaySettings() {
        super(ModuleCategory.NONE);
        this.setKeybinding(65);
    }

    @Override
    public String getName() {
        return "GuiXRaySettings";
    }

    @Override
    public void toggle() {
        Wrapper.INSTANCE.mc().displayGuiScreen((GuiScreen)this.gui);
    }
}

