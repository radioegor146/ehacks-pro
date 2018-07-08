/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.common.config.Configuration
 */
package ehacks.mod.commands.apicommands;

import ehacks.api.command.Command;
import ehacks.mod.external.config.forge.GeneralConfiguration;
import ehacks.mod.modulesystem.classes.Step;
import ehacks.mod.wrapper.Wrapper;
import net.minecraftforge.common.config.Configuration;

public class ACommandStepHeight
extends Command {
    public ACommandStepHeight() {
        super("cesh");
    }

    @Override
    public void runCommand(String s, String[] subcommands) {
        try {
            float string = Float.parseFloat(subcommands[0]);
            if (string < 100.0f) {
                //Step.DEFAULT_STEP_HEIGHT = string;
                Wrapper.INSTANCE.addChatMessage("Set step height to: " + string);
                GeneralConfiguration.instance().configuration.save();
                GeneralConfiguration.instance().configuration.load();
            } else {
                Wrapper.INSTANCE.addChatMessage("Can't set step height more than 100 blocks!");
            }
        }
        catch (Exception e) {
            // empty catch block
        }
    }

    @Override
    public String getDescription() {
        return "Sets step height";
    }

    @Override
    public String getSyntax() {
        return this.getCommand() + " <number of blocks (float)>";
    }
}

