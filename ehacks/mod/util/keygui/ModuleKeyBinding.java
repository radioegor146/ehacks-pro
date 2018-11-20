/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehacks.mod.util.keygui;

import ehacks.mod.api.Module;

/**
 *
 * @author radioegor146
 */
public class ModuleKeyBinding implements Comparable {

    private final Module module;
    private final String description;
    private final String category;

    public ModuleKeyBinding(Module module) {
        this.description = (module.getModName().equals("Minecraft") ? "" : (module.getModName() + " - ")) + module.getName();
        this.category = module.getCategory().getName();
        this.module = module;
    }

    public void setKeyCode(int keyCode) {
        module.setKeybinding(keyCode);
    }

    public int getKeyCode() {
        return module.getKeybind();
    }

    public int getKeyCodeDefault() {
        return module.getDefaultKeybind();
    }

    public String getKeyCategory() {
        return this.category;
    }

    public String getKeyDescription() {
        return this.description;
    }

    @Override
    public int compareTo(Object o) {
        int result = this.getKeyCategory().compareTo(((ModuleKeyBinding) o).getKeyCategory());
        if (result == 0) {
            return this.getKeyDescription().compareTo(((ModuleKeyBinding) o).getKeyDescription());
        }
        return result;
    }
}
