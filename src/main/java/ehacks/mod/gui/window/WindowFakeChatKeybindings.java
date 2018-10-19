/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehacks.mod.gui.window;

import ehacks.mod.gui.element.SimpleWindow;
import ehacks.mod.util.keygui.GuiControls;
import ehacks.mod.wrapper.Wrapper;

/**
 *
 * @author radioegor146
 */
public class WindowFakeChatKeybindings extends SimpleWindow {
    public WindowFakeChatKeybindings() {
        super("Chat keybinds", 0, 0);
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
