/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package ehacks.mod.commands.apicommands;

import ehacks.api.command.Command;
import ehacks.api.module.APICEMod;
import ehacks.api.module.Mod;
import ehacks.mod.external.config.manual.KeybindConfiguration;
import ehacks.mod.modulesystem.handler.ModuleManagement;
import ehacks.mod.wrapper.Wrapper;
import java.util.ArrayList;
import org.lwjgl.input.Keyboard;

public class ACommandSMKeybind
extends Command {
    public ACommandSMKeybind() {
        super("smkey");
    }

    @Override
    public void runCommand(String s, String[] subcommands) {
        boolean successful = false;
        if (subcommands[0].equalsIgnoreCase("add")) {
            for (Mod m : APICEMod.INSTANCE.mods) {
                int i;
                if (!subcommands[1].equalsIgnoreCase(m.getName().replaceAll(" ", "")) || (i = Keyboard.getKeyIndex((String)subcommands[2].toUpperCase())) == 0) continue;
                successful = true;
                m.setKeybinding(i);
                Wrapper.INSTANCE.addChatMessage("Setted key: " + m.getKeybind() + "(" + subcommands[2] + ")" + " for module: " + m.getName());
                KeybindConfiguration.instance().writeKeybindConfig();
                break;
            }
        }
        if (subcommands[0].equalsIgnoreCase("delete")) {
            for (Mod module : APICEMod.INSTANCE.mods) {
                if (!subcommands[1].equalsIgnoreCase(module.getName().replaceAll(" ", ""))) continue;
                successful = true;
                module.setKeybinding(0);
                Wrapper.INSTANCE.addChatMessage("Removed key for module: " + module.getName());
                KeybindConfiguration.instance().writeKeybindConfig();
                break;
            }
        }
        if (!successful) {
            Wrapper.INSTANCE.addChatMessage("Can't recognize module: " + subcommands[1] + " or subcommand: " + subcommands[0]);
        }
    }

    @Override
    public String getDescription() {
        return "Change module keys";
    }

    @Override
    public String getSyntax() {
        return this.getCommand() + " <add/delete> <module> <key>";
    }
}

