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
import ehacks.mod.modulesystem.classes.Speed;
import ehacks.mod.wrapper.Wrapper;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraftforge.common.config.Configuration;

public class NCommandSV
extends CommandBase {
    public String getCommandName() {
        return "cespeedvalue";
    }

    public String getCommandUsage(ICommandSender var1) {
        return "Changes the speed of the speed module.";
    }

    public void processCommand(ICommandSender var1, String[] var2) {
        Double speedvalue = Double.parseDouble(var2[0]);
        //Speed.SPEED_VALUE = speedvalue;
        GeneralConfiguration.instance().configuration.save();
        GeneralConfiguration.instance().configuration.load();
        Wrapper.INSTANCE.addChatMessage("Set speed to: " + speedvalue);
    }

    public int compareTo(Object o) {
        return 0;
    }

    public boolean canCommandSenderUseCommand(ICommandSender var1) {
        return true;
    }
}

