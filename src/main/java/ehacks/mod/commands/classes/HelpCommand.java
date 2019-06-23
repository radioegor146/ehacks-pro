/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehacks.mod.commands.classes;

import ehacks.mod.commands.CommandManager;
import ehacks.mod.commands.ICommand;
import ehacks.mod.modulesystem.handler.EHacksGui;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

import static ehacks.mod.commands.CommandManager.format;

/**
 * @author radioegor146
 */
public class HelpCommand implements ICommand {

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public void process(String[] args) {
        int mode = 0;
        int page = 0;
        if (args.length > 0) {
            if (CommandManager.INSTANCE.getCommand(args[0]) != null) {
                EHacksGui.clickGui.consoleGui.printChatMessage(new TextComponentString("\u00a7c/" + CommandManager.INSTANCE.getCommand(args[0]).getName() + " " + CommandManager.INSTANCE.getCommand(args[0]).getCommandArgs()));
                return;
            }
            try {
                if (mode == 0) {
                    page = Integer.parseInt(args[0]);
                    page--;
                    if (page > CommandManager.INSTANCE.commands.size() / 6) {
                        EHacksGui.clickGui.consoleGui.printChatMessage(format(TextFormatting.RED, "commands.generic.num.tooBig", page + 1, CommandManager.INSTANCE.commands.size() / 6 + 1));
                        return;
                    }
                    if (page < 0) {
                        EHacksGui.clickGui.consoleGui.printChatMessage(format(TextFormatting.RED, "commands.generic.num.tooSmall", 1, 1));
                        return;
                    }
                }
            } catch (Exception e) {
                EHacksGui.clickGui.consoleGui.printChatMessage(format(TextFormatting.RED, "commands.generic.notFound"));
                return;
            }
        }
        String[] keys = CommandManager.INSTANCE.commands.keySet().toArray(new String[0]);
        EHacksGui.clickGui.consoleGui.printChatMessage(format(TextFormatting.DARK_GREEN, "commands.help.header", page + 1, keys.length / 6 + 1));
        for (int i = page * 6; i < Math.min(page * 6 + 6, keys.length); i++) {
            EHacksGui.clickGui.consoleGui.printChatMessage(new TextComponentString("/" + keys[i] + " " + CommandManager.INSTANCE.commands.get(keys[i]).getCommandArgs() + " - " + CommandManager.INSTANCE.commands.get(keys[i]).getCommandDescription()));
        }
    }

    @Override
    public String getCommandDescription() {
        return "Help about commands";
    }

    @Override
    public String getCommandArgs() {
        return "[page|name]";
    }

    @Override
    public String[] autoComplete(String[] args) {
        return new String[0];
    }

}
