/*
 * Decompiled with CFR 0_128.
 */
package ehacks.mod.commands.apicommands;

import ehacks.api.command.Command;
import ehacks.mod.commands.apicommands.CommandManager;
import ehacks.mod.wrapper.Wrapper;
import java.util.ArrayList;

public class CommandHelp
extends Command {
    public CommandHelp() {
        super("help");
    }

    @Override
    public void runCommand(String s, String[] subcommands) {
        for (Command commands : CommandManager.commands) {
            Wrapper.INSTANCE.addChatMessage("Command: " + commands.getCommand() + " - " + commands.getDescription());
        }
    }

    @Override
    public String getDescription() {
        return "Show help";
    }

    @Override
    public String getSyntax() {
        return null;
    }
}

