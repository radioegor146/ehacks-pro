/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  net.minecraft.command.CommandBase
 *  net.minecraft.command.ICommandSender
 */
package ehacks.mod.commands;

import ehacks.api.module.APICEMod;
import ehacks.api.module.Mod;
import ehacks.mod.modulesystem.handler.ModuleManagement;
import ehacks.mod.wrapper.Wrapper;
import java.util.ArrayList;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

public class NCommandMList
extends CommandBase {
    public String getCommandName() {
        return "ml";
    }

    public String getCommandUsage(ICommandSender var1) {
        return "Shows module list.";
    }

    public void processCommand(ICommandSender var1, String[] var2) {
        for (Mod mod : APICEMod.INSTANCE.mods) {
            Wrapper.INSTANCE.addChatMessage(mod.getName() + " - " + mod.getDescription() + " - " + mod.getAlias());
        }
    }

    public int compareTo(Object arg0) {
        return 0;
    }

    public boolean canCommandSenderUseCommand(ICommandSender var1) {
        return true;
    }
}

