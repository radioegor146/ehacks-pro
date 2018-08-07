/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehacks.api.module;

import ehacks.mod.wrapper.ModuleCategories;

/**
 *
 * @author radioegor146
 */
public class DummyMod extends Mod {

    public DummyMod(String name) {
        super(ModuleCategories.NONE);
        this.name = name;
    }
    
    @Override
    public void onEnableMod() {
        this.off();
    }
    
    @Override
    public ModStatus getModStatus() {
        return ModStatus.CATEGORY;
    }
}
