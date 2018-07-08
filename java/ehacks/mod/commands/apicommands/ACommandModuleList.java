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
import ehacks.mod.modulesystem.handler.ModuleManagement;
import ehacks.mod.wrapper.Wrapper;
import java.util.ArrayList;
import org.lwjgl.input.Keyboard;

public class ACommandModuleList
extends Command {
    public ACommandModuleList() {
        super("ceml");
    }

    @Override
    public void runCommand(String s, String[] subcommands) {
        for (Mod module : APICEMod.INSTANCE.mods) {
            Wrapper.INSTANCE.addChatMessage("Module: " + module.getName() + " - Key: [" + Keyboard.getKeyName((int)module.getKeybind()) + "]");
        }
    }

    @Override
    public String getDescription() {
        return "List all modules";
    }

    @Override
    public String getSyntax() {
        return null;
    }
}

