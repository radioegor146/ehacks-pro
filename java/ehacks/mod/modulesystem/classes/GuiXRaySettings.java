package ehacks.mod.modulesystem.classes;

import ehacks.api.module.Module;
import ehacks.mod.gui.xraysettings.XRayGui;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import net.minecraft.client.gui.GuiScreen;

public class GuiXRaySettings
        extends Module {

    private final XRayGui gui = new XRayGui();

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
        Wrapper.INSTANCE.mc().displayGuiScreen((GuiScreen) this.gui);
    }
}
