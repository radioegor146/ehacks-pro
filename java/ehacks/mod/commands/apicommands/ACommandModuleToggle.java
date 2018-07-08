/*
 * Decompiled with CFR 0_128.
 */
package ehacks.mod.commands.apicommands;

import ehacks.api.command.Command;
import ehacks.api.module.APICEMod;
import ehacks.api.module.Mod;
import ehacks.mod.modulesystem.handler.ModuleManagement;
import ehacks.mod.wrapper.Wrapper;
import java.util.ArrayList;

public class ACommandModuleToggle
extends Command {
    public ACommandModuleToggle() {
        super("mt");
    }

    @Override
    public void runCommand(String s, String[] subcommands) {
        for (Mod mod : APICEMod.INSTANCE.mods) {
            String[] s0 = subcommands[0].split(":");
            if (!s0[0].equals("ehacks") || !mod.getName().toLowerCase().replaceAll(" ", "").equalsIgnoreCase(s0[1])) continue;
            mod.toggle();
            Wrapper.INSTANCE.addChatMessage("[Cheating Essentials] Succefully toggled module via command: " + mod.getName() + " with status: " + mod.isActive());
            break;
        }
    }

    @Override
    public String getDescription() {
        return "Toggles a module";
    }

    @Override
    public String getSyntax() {
        return this.getCommand() + " <module name>";
    }
}

