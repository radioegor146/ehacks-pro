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

public class ACommandSpeedValue
extends Command {
    public static float SPEED_VALUE = 2.0f;

    public ACommandSpeedValue() {
        super("sh");
    }

    @Override
    public void runCommand(String s, String[] subcommands) {
        SPEED_VALUE = Float.parseFloat(subcommands[0]);
        GeneralConfiguration.instance().configuration.save();
        GeneralConfiguration.instance().configuration.load();
        Wrapper.INSTANCE.addChatMessage("&9[&bCE Console&9] &fThe speed is set to &a" + SPEED_VALUE);
    }

    @Override
    public String getDescription() {
        return " <speed> - Sets player speed.";
    }

    @Override
    public String getSyntax() {
        return "&9[&bCE Console&9] &cUsage: " + this.getCommand() + " <speed>";
    }
}

