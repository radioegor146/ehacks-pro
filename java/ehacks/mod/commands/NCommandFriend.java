/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  net.minecraft.command.CommandBase
 *  net.minecraft.command.ICommandSender
 */
package ehacks.mod.commands;

import ehacks.mod.external.config.agce.files.AGCEIntegerList;
import ehacks.mod.relationsystem.Friend;
import ehacks.mod.wrapper.Wrapper;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

public class NCommandFriend
extends CommandBase {
    public String getCommandName() {
        return "cefriend";
    }

    public String getCommandUsage(ICommandSender var1) {
        return "Add/remove friend.";
    }

    public void processCommand(ICommandSender var1, String[] var2) {
        if (var2[0].equalsIgnoreCase("add")) {
            Friend.instance().addFriend(var2[1]);
            AGCEIntegerList.INSTANCE.modify("CE-FriendList.cfg", Friend.instance().friendList);
            Wrapper.INSTANCE.addChatMessage("Added Friend: " + var2[1]);
        }
        if (var2[0].equalsIgnoreCase("remove")) {
            Friend.instance().removeFriend(var2[1]);
            AGCEIntegerList.INSTANCE.modify("CE-FriendList.cfg", Friend.instance().friendList);
            Wrapper.INSTANCE.addChatMessage("Removed Friend: " + var2[1]);
        }
    }

    public int compareTo(Object arg0) {
        return 0;
    }
}

