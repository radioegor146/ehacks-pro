
package ehacks.mod.commands;

import ehacks.mod.commands.classes.*;
import ehacks.mod.modulesystem.handler.EHacksGui;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;

/**
 *
 * @author radioegor146
 */
public class CommandManager {
    public static CommandManager INSTANCE = new CommandManager();
    
    public TreeMap<String, ICommand> commands = new TreeMap();
    
    public CommandManager() {
        add(new ItemSelectCommand());
        add(new HelpCommand());
        add(new FriendsCommand());
        add(new ConfigControlCommand());
        add(new KeybindCommand());
    }
    
    private void add(ICommand command) {
        commands.put(command.getName(), command);
    }
    
    public void processString(String message) {
        message = message.trim();

        if (message.startsWith("/"))
        {
            message = message.substring(1);
        }

        String[] temp = message.split(" ");
        String[] args = new String[temp.length - 1];
        String commandName = temp[0];
        System.arraycopy(temp, 1, args, 0, args.length);
        try {
            processCommand(getCommand(commandName), args);
        }
        catch (Exception e) {
            EHacksGui.clickGui.consoleGui.printChatMessage(format(EnumChatFormatting.RED, "commands.generic.exception"));
            e.printStackTrace();
        }
    }
    
    public static ChatComponentTranslation format(EnumChatFormatting color, String str, Object... args)
    {
        ChatComponentTranslation ret = new ChatComponentTranslation(str, args);
        ret.getChatStyle().setColor(color);
        return ret;
    }
    
    public void processCommand(ICommand command, String[] args) {
        if (command == null) {
            EHacksGui.clickGui.consoleGui.printChatMessage(format(EnumChatFormatting.RED, "commands.generic.notFound"));
            return;
        }
        command.process(args);
    }
    
    public ICommand getCommand(String name) {
        return commands.getOrDefault(name, null);
    }
    
    public String[] autoComplete(String message) {
        if (message.startsWith("/"))
        {
            message = message.substring(1);
        }

        String[] temp = message.trim().split(" ");
        String[] args = new String[temp.length - 1];
        String commandName = temp[0];
        System.arraycopy(temp, 1, args, 0, args.length);
        
        if (commands.containsKey(commandName)) {
            ArrayList<String> targs = new ArrayList();
            targs.addAll(Arrays.asList(args));
            if (message.endsWith(" "))
                targs.add("");
            return commands.get(commandName).autoComplete(targs.toArray(new String[targs.size()]));
        }
        else if (args.length == 0 && !message.endsWith(" ")){
            ArrayList<String> avaibleNames = new ArrayList();
            for (String name : commands.keySet()) {
                if (name.startsWith(commandName)) {
                    avaibleNames.add("/" + name);
                }
            }
            return avaibleNames.toArray(new String[avaibleNames.size()]);
        }
        return new String[0];
    }
}
