/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package ehacks.mod.commands;

import java.util.ArrayList;
import org.lwjgl.input.Keyboard;
import ehacks.api.command.Command;
import ehacks.api.module.APICEMod;
import ehacks.api.module.Mod;
import ehacks.mod.external.config.manual.KeybindConfiguration;
import ehacks.mod.wrapper.Wrapper;

public class ACommandBind
extends Command {
    public ACommandBind() {
        super("bind");
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
                Wrapper.INSTANCE.addChatMessage("&9[&bCE Console&9] &fKey &c" + m.getKeybind() + "[" + subcommands[2].toUpperCase() + "] &fbinds to &c" + m.getName());
                KeybindConfiguration.instance().writeKeybindConfig();
            }
        }
        if (subcommands[0].equalsIgnoreCase("del")) {
            for (Mod module : APICEMod.INSTANCE.mods) {
                if (!subcommands[1].equalsIgnoreCase(module.getName().replaceAll(" ", ""))) continue;
                successful = true;
                module.setKeybinding(0);
                Wrapper.INSTANCE.addChatMessage("&9[&bCE Console&9] &fBind removed from &c" + module.getName() + " module.");
                KeybindConfiguration.instance().writeKeybindConfig();
            }
        }
        if (!successful) {
            Wrapper.INSTANCE.addChatMessage(this.getSyntax());
        }
    }

    @Override
    public String getDescription() {
        return " <add/del> <module> <key> - Change module keys.";
    }

    @Override
    public String getSyntax() {
        return "&9[&bCE Console&9] &cUsage: " + this.getCommand() + " <add/del> <module> <key>";
    }
}

