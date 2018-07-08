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

public class ACommandModuleHelp
extends Command {
    public ACommandModuleHelp() {
        super("cemh");
    }

    @Override
    public void runCommand(String s, String[] subcommands) {
        for (Mod module : APICEMod.INSTANCE.mods) {
            if (!subcommands[0].equalsIgnoreCase(module.getName().replaceAll(" ", ""))) continue;
            Wrapper.INSTANCE.addChatMessage("Help for module " + module.getName() + ": " + module.getDescription());
        }
    }

    @Override
    public String getDescription() {
        return "Shows avaliable help for a specified module";
    }

    @Override
    public String getSyntax() {
        return this.getCommand().concat(" <module name>");
    }
}

