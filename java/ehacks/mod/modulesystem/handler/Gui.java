package ehacks.mod.modulesystem.handler;

import ehacks.api.module.Module;
import ehacks.mod.gui.EHacksClickGui;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import net.minecraft.client.gui.GuiScreen;

public class Gui
        extends Module {

    private final EHacksClickGui click = new EHacksClickGui();

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
        Wrapper.INSTANCE.mc().displayGuiScreen((GuiScreen) this.click);
    }
}
