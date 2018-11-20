package ehacks.mod.gui.window;

import ehacks.mod.gui.element.SimpleWindow;
import ehacks.mod.util.keygui.GuiControls;
import ehacks.mod.wrapper.Wrapper;

public class WindowFakeKeybindings
        extends SimpleWindow {

    public WindowFakeKeybindings() {
        super("Key config", 600, 300);
    }

    @Override
    public void setOpen(boolean state) {
        if (state) {
            try {
                Wrapper.INSTANCE.mc().displayGuiScreen(new GuiControls(Wrapper.INSTANCE.mc().currentScreen));
            } catch (Exception e) {
            }
        }
    }
}
