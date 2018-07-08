/*
 * Decompiled with CFR 0_128.
 */
package ehacks.mod.commands;

import java.util.ArrayList;
import ehacks.api.command.Command;
import ehacks.mod.commands.CommandManager;
import ehacks.mod.wrapper.Wrapper;

public class CommandHelp
extends Command {
    public CommandHelp() {
        super("help");
    }

    @Override
    public void runCommand(String s, String[] subcommands) {
        int i = 0;
        Wrapper.INSTANCE.addChatMessage("&9\u2588\u2580\u2580\u2580\u2580\u2580\u2580\u2580\u2580\u2580\u2580\u2580\u2580\u2580\u2588 &bCE Help &9\u2588\u2580\u2580\u2580\u2580\u2580\u2580\u2580\u2580\u2580\u2580\u2580\u2580\u2580\u2588");
        for (Command commands : CommandManager.commands) {
            Wrapper.INSTANCE.addChatMessage("&7" + ++i + ". " + commands.getCommand() + commands.getDescription());
        }
        Wrapper.INSTANCE.addChatMessage("&9\u2588\u2584\u2584\u2584\u2584\u2584\u2584\u2584\u2584\u2584\u2584\u2584\u2584\u2584\u2584\u2584\u2584\u2584\u2584\u2584\u2584\u2584\u2584\u2584\u2584\u2584\u2584\u2584\u2584\u2584\u2584\u2584\u2584\u2584\u2588");
    }

    @Override
    public String getDescription() {
        return " - Show help.";
    }

    @Override
    public String getSyntax() {
        return "&9[&bCE Console&9] &cUsage: " + this.getCommand();
    }
}

