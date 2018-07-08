/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  net.minecraft.command.CommandBase
 *  net.minecraft.command.ICommandSender
 */
package ehacks.mod.commands;

import ehacks.mod.external.config.agce.files.AGCEIntegerList;
import ehacks.mod.relationsystem.Enemy;
import ehacks.mod.relationsystem.Friend;
import ehacks.mod.wrapper.Wrapper;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

public class NCommandEnemy
extends CommandBase {
    public String getCommandName() {
        return "ceenemy";
    }

    public String getCommandUsage(ICommandSender var1) {
        return "Add/remove enemy";
    }

    public void processCommand(ICommandSender var1, String[] var2) {
        if (var2[0].equalsIgnoreCase("add")) {
            Enemy.instance().addEnemy(var2[1]);
            AGCEIntegerList.INSTANCE.modify("CE-EnemyList.cfg", Friend.instance().friendList);
            Wrapper.INSTANCE.addChatMessage("Added Enemy: " + var2[1]);
        }
        if (var2[0].equalsIgnoreCase("remove")) {
            Enemy.instance().removeEnemy(var2[1]);
            AGCEIntegerList.INSTANCE.modify("CE-EnemyList.cfg", Friend.instance().friendList);
            Wrapper.INSTANCE.addChatMessage("Removed Enemy: " + var2[1]);
        }
    }

    public int compareTo(Object o) {
        return 0;
    }
}

