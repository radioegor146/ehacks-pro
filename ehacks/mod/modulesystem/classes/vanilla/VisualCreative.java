/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehacks.mod.modulesystem.classes.vanilla;

import ehacks.mod.api.Module;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import net.minecraft.world.WorldSettings;

/**
 *
 * @author radioegor146
 */
public class VisualCreative extends Module {

    public VisualCreative() {
        super(ModuleCategory.EHACKS);
    }

    @Override
    public String getName() {
        return "VisualCreative";
    }

    @Override
    public String getDescription() {
        return "Gives you visual creative mode";
    }

    @Override
    public void onModuleEnabled() {
        Wrapper.INSTANCE.mc().playerController.setGameType(WorldSettings.GameType.CREATIVE);
        WorldSettings.GameType.CREATIVE.configurePlayerCapabilities(Wrapper.INSTANCE.player().capabilities);
        Wrapper.INSTANCE.player().sendPlayerAbilities();
    }

    @Override
    public void onModuleDisabled() {
        Wrapper.INSTANCE.mc().playerController.setGameType(WorldSettings.GameType.SURVIVAL);
        WorldSettings.GameType.CREATIVE.configurePlayerCapabilities(Wrapper.INSTANCE.player().capabilities);
        Wrapper.INSTANCE.player().sendPlayerAbilities();
    }
}
