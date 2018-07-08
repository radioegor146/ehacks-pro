/*
 * Decompiled with CFR 0_128.
 */
package ehacks.mod.commands;

import ehacks.api.command.Command;
import ehacks.mod.relationsystem.Friend;

public class ACommandFriend
extends Command {
    public ACommandFriend() {
        super("friend");
    }

    @Override
    public void runCommand(String s, String[] subcommands) {
        if (subcommands[0].equalsIgnoreCase("add")) {
            Friend.instance().addFriend(subcommands[1]);
        }
        if (subcommands[0].equalsIgnoreCase("del")) {
            Friend.instance().removeFriend(subcommands[1]);
        }
    }

    @Override
    public String getDescription() {
        return " <add/del> <nickname> - Manage list of friends.";
    }

    @Override
    public String getSyntax() {
        return "&9[&bCE Console&9] &cUsage: " + this.getCommand() + " <add/del> <nickname>";
    }
}

