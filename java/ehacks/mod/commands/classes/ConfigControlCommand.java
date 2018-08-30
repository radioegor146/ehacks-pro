/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehacks.mod.commands.classes;

import ehacks.mod.commands.ICommand;
import ehacks.mod.external.config.CheatConfiguration;
import ehacks.mod.external.config.ConfigurationManager;
import ehacks.mod.modulesystem.handler.EHacksGui;
import ehacks.mod.util.InteropUtils;
import java.lang.reflect.Field;
import java.util.ArrayList;
import net.minecraft.util.ChatComponentText;


/**
 *
 * @author radioegor146
 */
public class ConfigControlCommand implements ICommand {

    @Override
    public String getName() {
        return "cfg";
    }
    
    private String escape(String text) {
        return text.replace("&", "&&");
    }

    @Override
    public void process(String[] args) {
        if (args.length > 0) {
            if (args[0].equals("save")) {
                ConfigurationManager.instance().saveConfigs();
                InteropUtils.log("Successfully saved", "ConfigControl");
                return;
            }
            if (args[0].equals("reload")) {
                ConfigurationManager.instance().saveConfigs();
                InteropUtils.log("Successfully reloaded", "ConfigControl");
                return;
            }
            if (args[0].equals("list")) {
                InteropUtils.log("&aFields of config:", "ConfigControl");
                int i = 1;
                for (Field nick : CheatConfiguration.CheatConfigJson.class.getFields()) {
                    InteropUtils.log(i + ". &f" + nick.getName(), "ConfigControl");
                    i++;
                }
                return;
            }
            if (args[0].equals("get") && args.length > 1) {
                Field f = null;
                try {
                    f = CheatConfiguration.CheatConfigJson.class.getField(args[1]);
                } catch (NoSuchFieldException ex) {
                    InteropUtils.log("&cNo such field '" + escape(args[1]) + "'", "ConfigControl");
                    return;
                }
                try {
                    InteropUtils.log("Value of '" + f.getName() + "': " + f.get(CheatConfiguration.config), "ConfigControl");
                } catch (Exception ex) {
                    
                }
                return;
            }
            if (args[0].equals("set") && args.length > 2) {
                Field f = null;
                try {
                    f = CheatConfiguration.CheatConfigJson.class.getField(args[1]);
                } catch (NoSuchFieldException ex) {
                    InteropUtils.log("&cNo such field '" + escape(args[1]) + "'", "ConfigControl");
                    return;
                }
                if (f.getType() == Double.TYPE) {
                    try {
                        f.setDouble(CheatConfiguration.config, Double.parseDouble(args[2]));
                    } catch (Exception ex) {
                        InteropUtils.log("&cCan't assign '" + escape(args[2]) + "' to '" + f.getName() + "'", "ConfigControl");
                        return;
                    }
                }
                if (f.getType() == Integer.TYPE) {
                    try {
                        f.setInt(CheatConfiguration.config, Integer.parseInt(args[2]));
                    } catch (Exception ex) {
                        InteropUtils.log("&cCan't assign '" + escape(args[2]) + "' to '" + f.getName() + "'", "ConfigControl");
                        return;
                    }
                }
                InteropUtils.log("Successfully set", "ConfigControl");
                ConfigurationManager.instance().saveConfigs();
                return;
            }
        }
        EHacksGui.clickGui.consoleGui.printChatMessage(new ChatComponentText("\u00a7c/" + this.getName() + " " + this.getCommandArgs()));
    }

    @Override
    public String getCommandDescription() {
        return "Edit config";
    }

    @Override
    public String getCommandArgs() {
        return "<save|reload|list|set|get> [field] [value]";
    }
    
    private boolean contains(Object[] array, Object object) {
        for (Object o : array)
            if (o.equals(object))
                return true;
        return false;
    }
    
    @Override
    public String[] autoComplete(String[] args) {
        if (args.length == 0) {
            return new String[] { "save", "reload", "list", "set", "get" };
        }
        if (args.length == 1) {
            if (contains(new String[] { "save", "reload", "list", "set", "get" }, args[0])) {
                if ("set".equals(args[0]) || "get".equals(args[0])) {
                    ArrayList<String> avaible = new ArrayList();
                    for (Field nick : CheatConfiguration.CheatConfigJson.class.getFields()) {
                        avaible.add(nick.getName());
                    }
                    return avaible.toArray(new String[avaible.size()]);
                }
            }
            else {
                ArrayList<String> avaibleNames = new ArrayList();
                for (String name : new String[] { "save", "reload", "list", "set", "get" }) {
                    if (name.startsWith(args[0])) {
                        avaibleNames.add(name);
                    }
                }
                return avaibleNames.toArray(new String[avaibleNames.size()]);
            }
        }
        if (args.length == 2) {
            if ("get".equals(args[0]) || "set".equals(args[0])) {
                ArrayList<String> avaibleNames = new ArrayList();
                for (Field f : CheatConfiguration.CheatConfigJson.class.getFields()) {
                    if (f.getName().startsWith(args[1])) {
                        avaibleNames.add(f.getName());
                    }
                }
                return avaibleNames.toArray(new String[avaibleNames.size()]);
            }
        }
        return new String[0];
    }
    
}
