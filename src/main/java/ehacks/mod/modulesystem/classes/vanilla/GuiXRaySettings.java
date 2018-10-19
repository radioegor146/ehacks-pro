package ehacks.mod.modulesystem.classes.vanilla;

import ehacks.mod.api.Module;
import ehacks.mod.gui.xraysettings.XRayGui;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;

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
        Wrapper.INSTANCE.mc().displayGuiScreen(this.gui);
    }

    @Override
    public int getDefaultKeybind() {
        return 65;
    }
}
