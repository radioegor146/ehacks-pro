/*
 * Decompiled with CFR 0_128.
 */
package ehacks.mod.commands.apicommands;

import ehacks.api.command.Command;
import ehacks.mod.commands.apicommands.CommandHelp;
import ehacks.mod.logger.ModLogger;
import ehacks.mod.main.Main;
import ehacks.mod.wrapper.Wrapper;
import java.util.ArrayList;

public class CommandManager {
    public static ArrayList<Command> commands = new ArrayList();
    private static volatile CommandManager instance;
    public static char cmdPrefix;

    public CommandManager() {
        Main.INSTANCE.logger.info("ReesZRB Console API [" + this + "] starting on EHacks...");
        this.addCommand(new CommandHelp());
    }

    public void runCommands(String s) {
        boolean commandResolved = false;
        String readString = s.trim().substring(Character.toString(cmdPrefix).length()).trim();
        boolean hasArgs = readString.trim().contains(" ");
        String commandName = hasArgs ? readString.split(" ")[0] : readString.trim();
        String[] args = hasArgs ? readString.substring(commandName.length()).trim().split(" ") : new String[]{};
        for (Command command : commands) {
            if (!command.getCommand().trim().equalsIgnoreCase(commandName.trim())) continue;
            command.runCommand(readString, args);
            commandResolved = true;
            break;
        }
        if (!commandResolved) {
            Wrapper.INSTANCE.addChatMessage("[Console API] Invalid command. Type help in Console for a list of commands.");
        }
    }

    public void addCommand(Command command) {
        commands.add(command);
    }

    public static CommandManager instance() {
        if (instance == null) {
            instance = new CommandManager();
        }
        return instance;
    }

    static {
        cmdPrefix = (char)46;
    }
}

