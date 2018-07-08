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
import ehacks.mod.wrapper.Wrapper;

public class ACommandModuleList
extends Command {
    public ACommandModuleList() {
        super("ml");
    }

    @Override
    public void runCommand(String s, String[] subcommands) {
        int i = 0;
        Wrapper.INSTANCE.addChatMessage("&9\u2588\u2580\u2580\u2580\u2580\u2580\u2580\u2580\u2588 &bModule List &9\u2588\u2580\u2580\u2580\u2580\u2580\u2580\u2580\u2588");
        for (Mod module : APICEMod.INSTANCE.mods) {
            Wrapper.INSTANCE.addChatMessage("&7" + ++i + ". " + module.getName() + " - Key: [" + Keyboard.getKeyName((int)module.getKeybind()) + "]");
        }
        Wrapper.INSTANCE.addChatMessage("&9\u2588\u2584\u2584\u2584\u2584\u2584\u2584\u2584\u2584\u2584\u2584\u2584\u2584\u2584\u2584\u2584\u2584\u2584\u2584\u2584\u2584\u2584\u2584\u2584\u2588");
    }

    @Override
    public String getDescription() {
        return " - List all modules.";
    }

    @Override
    public String getSyntax() {
        return "&9[&bCE Console&9] &cUsage: " + this.getCommand();
    }
}

