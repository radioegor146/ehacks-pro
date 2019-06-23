/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehacks.mod.commands.classes;

import ehacks.mod.api.Module;
import ehacks.mod.api.ModuleController;
import ehacks.mod.commands.ICommand;
import ehacks.mod.config.ConfigurationManager;
import ehacks.mod.modulesystem.handler.EHacksGui;
import ehacks.mod.util.InteropUtils;
import net.minecraft.util.text.TextComponentString;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

/**
 * @author radioegor146
 */
public class KeybindCommand implements ICommand {

    @Override
    public String getName() {
        return "key";
    }

    private String escape(String text) {
        return text.replace("&", "&&");
    }

    @Override
    public void process(String[] args) {
        if (args.length > 0) {
            Module smod = null;
            for (Module mod : ModuleController.INSTANCE.modules) {
                if (mod.getClass().getSimpleName().toLowerCase().replace(" ", "").equals(args[0])) {
                    smod = mod;
                }
            }
            if (smod == null) {
                InteropUtils.log("&cNo such module '" + escape(args[0]) + "'", "Keybind");
                return;
            }
            if (args.length == 1) {
                smod.setKeybinding(0);
                InteropUtils.log("Keybinding cleared", "Keybind");
                ConfigurationManager.instance().saveConfigs();
                return;
            }
            if (Keyboard.getKeyIndex(args[1].toUpperCase()) == 0) {
                InteropUtils.log("&cNo such key '" + escape(args[1].toUpperCase()) + "'", "Keybind");
                return;
            }
            smod.setKeybinding(Keyboard.getKeyIndex(args[1].toUpperCase()));
            InteropUtils.log("Keybinding set", "Keybind");
            ConfigurationManager.instance().saveConfigs();
            return;
        }
        EHacksGui.clickGui.consoleGui.printChatMessage(new TextComponentString("\u00a7c/" + this.getName() + " " + this.getCommandArgs()));
    }

    @Override
    public String getCommandDescription() {
        return "Sets keybinds";
    }

    @Override
    public String getCommandArgs() {
        return "<module> <key>";
    }

    @Override
    public String[] autoComplete(String[] args) {

        if (args.length == 1) {
            ArrayList<String> allModules = new ArrayList<>();
            ModuleController.INSTANCE.modules.stream().filter((mod) -> (mod.getClass().getSimpleName().replace(" ", "").toLowerCase().startsWith(args[0].toLowerCase()))).forEachOrdered((mod) -> {
                allModules.add(mod.getClass().getSimpleName().toLowerCase().replace(" ", ""));
            });
            return allModules.toArray(new String[allModules.size()]);
        }
        return new String[0];
    }

}
