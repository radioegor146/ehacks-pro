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

public class NCommandMT
extends CommandBase {
    public String getCommandName() {
        return "mt";
    }

    public String getCommandUsage(ICommandSender var1) {
        return "Example usage: /mt ehacks:<mod name, without spaces>";
    }

    public void processCommand(ICommandSender var1, String[] var2) {
        for (Mod mod : APICEMod.INSTANCE.mods) {
            String[] s0 = var2[0].split(":");
            if (!s0[0].equals("ehacks") || !mod.getName().toLowerCase().replaceAll(" ", "").equalsIgnoreCase(s0[1])) continue;
            mod.toggle();
            Wrapper.INSTANCE.addChatMessage("[EHacks] Succefully toggled module via command: " + mod.getName() + " with status: " + mod.isActive());
            break;
        }
    }

    public int compareTo(Object arg0) {
        return 0;
    }

    public boolean canCommandSenderUseCommand(ICommandSender var1) {
        return true;
    }
}

