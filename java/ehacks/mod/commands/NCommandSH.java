/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  net.minecraft.command.CommandBase
 *  net.minecraft.command.ICommandSender
 *  net.minecraftforge.common.config.Configuration
 */
package ehacks.mod.commands;

import ehacks.mod.external.config.forge.GeneralConfiguration;
import ehacks.mod.modulesystem.classes.Step;
import ehacks.mod.wrapper.Wrapper;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraftforge.common.config.Configuration;

public class NCommandSH
extends CommandBase {
    public String getCommandName() {
        return "cesh";
    }

    public String getCommandUsage(ICommandSender var1) {
        return "Changes step height";
    }

    public void processCommand(ICommandSender var1, String[] var2) {
        try {
            float string = Float.parseFloat(var2[0]);
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

    public int compareTo(Object o) {
        return 0;
    }
}

