package ehacks.mod.modulesystem.handler;

import ehacks.api.module.Module;
import ehacks.mod.gui.EHacksClickGui;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;

public class EHacksGui
        extends Module {

    public static final EHacksClickGui clickGui = new EHacksClickGui();

    public EHacksGui() {
        super(ModuleCategory.NONE);
        this.setKeybinding(34);
        clickGui.initWindows();
        ItemStack a;
    }

    @Override
    public String getName() {
        return "Gui";
    }

    @Override
    public void toggle() {
        Wrapper.INSTANCE.mc().displayGuiScreen((GuiScreen)EHacksGui.clickGui);
    }
}
