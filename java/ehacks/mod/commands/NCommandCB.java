/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  net.minecraft.command.CommandBase
 *  net.minecraft.command.ICommandSender
 */
package ehacks.mod.commands;

import ehacks.mod.modulesystem.classes.Breadcrumb;
import ehacks.mod.wrapper.Wrapper;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

public class NCommandCB
extends CommandBase {
    public String getCommandName() {
        return "breadcrumb";
    }

    public String getCommandUsage(ICommandSender var1) {
        return "Clear breadcrumb";
    }

    public void processCommand(ICommandSender var1, String[] var2) {
        Breadcrumb.positionsList.clear();
        Wrapper.INSTANCE.addChatMessage("Cleared breadcrumb list.");
    }

    public int compareTo(Object arg0) {
        return 0;
    }

    public boolean canCommandSenderUseCommand(ICommandSender var1) {
        return true;
    }
}

