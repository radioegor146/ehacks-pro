/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehacks.mod.commands.classes;

import ehacks.api.module.Module;
import ehacks.api.module.ModuleController;
import ehacks.mod.commands.ICommand;
import ehacks.mod.external.config.ConfigurationManager;
import ehacks.mod.modulesystem.handler.EHacksGui;
import ehacks.mod.util.InteropUtils;
import java.util.ArrayList;
import net.minecraft.util.ChatComponentText;
import org.lwjgl.input.Keyboard;

/**
 *
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
                if (mod.getName().toLowerCase().replace(" ", "").equals(args[0])) 
                    smod = mod;
            }
            if (smod == null) {
                InteropUtils.log("&cNo such module '" + escape(args[0]) + "'", "Keybind");
                return;
            }
            if (args.length == 1)
            {
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
        EHacksGui.clickGui.consoleGui.printChatMessage(new ChatComponentText("\u00a7c/" + this.getName() + " " + this.getCommandArgs()));
    }

    private boolean contains(Object[] array, Object object) {
        for (Object o : array)
            if (o.equals(object))
                return true;
        return false;
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
            ArrayList<String> allModules = new ArrayList();
            for (Module mod : ModuleController.INSTANCE.modules) {
                if (mod.getName().toLowerCase().replace(" ", "").startsWith(args[0].toLowerCase()))
                    allModules.add(mod.getName().toLowerCase().replace(" ", ""));
            }
            return allModules.toArray(new String[allModules.size()]);
        }
        return new String[0];
    }
    
}
