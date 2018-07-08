/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.common.config.Configuration
 */
package ehacks.mod.commands;

import net.minecraftforge.common.config.Configuration;
import ehacks.api.command.Command;
import ehacks.mod.external.config.forge.GeneralConfiguration;
import ehacks.mod.wrapper.Wrapper;

public class ACommandStepHeight
extends Command {
    public static float STEP_HEIGHT_VALUE = 1.0f;

    public ACommandStepHeight() {
        super("step");
    }

    @Override
    public void runCommand(String s, String[] subcommands) {
        STEP_HEIGHT_VALUE = Float.parseFloat(subcommands[0]);
        GeneralConfiguration.instance().configuration.save();
        GeneralConfiguration.instance().configuration.load();
        Wrapper.INSTANCE.addChatMessage("&9[&bCE Console&9] &fThe step height is set to &a" + STEP_HEIGHT_VALUE);
    }

    @Override
    public String getDescription() {
        return " <height> - Sets step height.";
    }

    @Override
    public String getSyntax() {
        return "&9[&bCE Console&9] &cUsage: " + this.getCommand() + " <height>";
    }
}

