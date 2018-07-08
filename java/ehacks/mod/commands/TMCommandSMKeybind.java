/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  net.minecraft.command.CommandBase
 *  net.minecraft.command.ICommandSender
 *  org.lwjgl.input.Keyboard
 */
package ehacks.mod.commands;

import ehacks.api.module.APICEMod;
import ehacks.api.module.Mod;
import ehacks.mod.external.config.manual.KeybindConfiguration;
import ehacks.mod.modulesystem.handler.ModuleManagement;
import ehacks.mod.wrapper.Wrapper;
import java.util.ArrayList;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import org.lwjgl.input.Keyboard;

public class TMCommandSMKeybind
extends CommandBase {
    public String getCommandName() {
        return "smkey";
    }

    public String getCommandUsage(ICommandSender var1) {
        return "Change module keybinding (add/delete)";
    }

    public void processCommand(ICommandSender var1, String[] var2) {
        boolean successful = false;
        if (var2[0].equalsIgnoreCase("add")) {
            for (Mod m : APICEMod.INSTANCE.mods) {
                int i;
                if (!var2[1].equalsIgnoreCase(m.getName().replaceAll(" ", "")) || (i = Keyboard.getKeyIndex((String)var2[2].toUpperCase())) == 0) continue;
                successful = true;
                m.setKeybinding(i);
                Wrapper.INSTANCE.addChatMessage("Setted key: " + m.getKeybind() + "(" + var2[2] + ")" + " for module: " + m.getName());
                KeybindConfiguration.instance().writeKeybindConfig();
                break;
            }
        }
        if (var2[0].equalsIgnoreCase("delete")) {
            for (Mod module : APICEMod.INSTANCE.mods) {
                if (!var2[1].equalsIgnoreCase(module.getName().replaceAll(" ", ""))) continue;
                successful = true;
                module.setKeybinding(0);
                Wrapper.INSTANCE.addChatMessage("Removed key for module: " + module.getName());
                KeybindConfiguration.instance().writeKeybindConfig();
                break;
            }
        }
        if (!successful) {
            Wrapper.INSTANCE.addChatMessage("Can't recognize module: " + var2[1] + " or subcommand: " + var2[0]);
        }
    }

    public int compareTo(Object o) {
        return 0;
    }

    public boolean canCommandSenderUseCommand(ICommandSender var1) {
        return true;
    }
}

