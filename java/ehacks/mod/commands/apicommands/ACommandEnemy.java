/*
 * Decompiled with CFR 0_128.
 */
package ehacks.mod.commands.apicommands;

import ehacks.api.command.Command;

public class ACommandEnemy
extends Command {
    public ACommandEnemy() {
        super("ceec");
    }

    @Override
    public void runCommand(String s, String[] subcommands) {
        if (subcommands[0].equalsIgnoreCase("add")) {
            // empty if block
        }
        if (subcommands[0].equalsIgnoreCase("delete")) {
            // empty if block
        }
    }

    @Override
    public String getDescription() {
        return "Adds/removes a enemy from list";
    }

    @Override
    public String getSyntax() {
        return this.getCommand().concat(" <add/delete> <player name>");
    }
}

