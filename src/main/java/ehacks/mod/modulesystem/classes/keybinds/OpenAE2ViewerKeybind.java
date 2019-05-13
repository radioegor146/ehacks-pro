/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehacks.mod.modulesystem.classes.keybinds;

import ehacks.mod.api.Module;
import ehacks.mod.api.ModuleController;
import ehacks.mod.wrapper.ModuleCategory;
import org.lwjgl.input.Keyboard;

/**
 *
 * @author radioegor146
 */
public class OpenAE2ViewerKeybind extends Module {

    private static final int DEFAULT_BUTTON = Keyboard.KEY_NUMPAD4;

    public OpenAE2ViewerKeybind() {
        super(ModuleCategory.KEYBIND);
        this.setKeybinding(DEFAULT_BUTTON);
    }

    @Override
    public String getName() {
        return "Open AE2 cell viewer";
    }

    @Override
    public String getModName() {
        return "Keybind";
    }

    @Override
    public void onModuleEnabled() {
        this.off();
    }

    public static int getKey() {
        Module m = ModuleController.INSTANCE.call(OpenAE2ViewerKeybind.class);
        if (m == null) {
            return DEFAULT_BUTTON;
        } else {
            return m.getKeybind();
        }
    }

    @Override
    public int getDefaultKeybind() {
        return DEFAULT_BUTTON;
    }
}
