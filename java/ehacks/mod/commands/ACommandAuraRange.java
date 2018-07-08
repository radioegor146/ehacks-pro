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

public class ACommandAuraRange
extends Command {
    public static float aurarange = 4.6f;

    public ACommandAuraRange() {
        super("aurarange");
    }

    @Override
    public void runCommand(String s, String[] subcommands) {
        aurarange = Integer.parseInt(subcommands[0]);
        GeneralConfiguration.instance().configuration.save();
        GeneralConfiguration.instance().configuration.load();
        Wrapper.INSTANCE.addChatMessage("&9[&bCE Console&9] &fThe range is set to &a" + aurarange);
    }

    @Override
    public String getDescription() {
        return " <range> - Sets aura range.";
    }

    @Override
    public String getSyntax() {
        return "&9[&bCE Console&9] &cUsage: " + this.getCommand() + " <range>";
    }
}

