/*
 * Decompiled with CFR 0_128.
 */
package ehacks.mod.commands;

import java.util.ArrayList;
import ehacks.api.command.Command;
import ehacks.api.module.APICEMod;
import ehacks.api.module.Mod;
import ehacks.mod.wrapper.Wrapper;

public class ACommandModuleToggle
extends Command {
    public ACommandModuleToggle() {
        super("mt");
    }

    @Override
    public void runCommand(String s, String[] subcommands) {
        for (Mod mod : APICEMod.INSTANCE.mods) {
            if (!mod.getName().equalsIgnoreCase(subcommands[0].replace(" ", ""))) continue;
            mod.toggle();
            Wrapper.INSTANCE.addChatMessage("&9[&bEHacks Console&9] &e" + mod.getName() + " &fis " + (mod.isActive() ? "&aenabled" : "&cdisabled") + "&f.");
        }
    }

    @Override
    public String getDescription() {
        return " <modulename> - Toggles a module.";
    }

    @Override
    public String getSyntax() {
        return "&9[&bEHacks Console&9] &cUsage: " + this.getCommand() + " <modulename>";
    }
}

