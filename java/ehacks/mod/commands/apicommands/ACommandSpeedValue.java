/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  net.minecraft.command.ICommandSender
 *  net.minecraftforge.common.config.Configuration
 */
package ehacks.mod.commands.apicommands;

import ehacks.api.command.Command;
import ehacks.mod.external.config.forge.GeneralConfiguration;
import ehacks.mod.modulesystem.classes.Speed;
import ehacks.mod.wrapper.Wrapper;
import net.minecraft.command.ICommandSender;
import net.minecraftforge.common.config.Configuration;

public class ACommandSpeedValue
extends Command {
    public ACommandSpeedValue() {
        super("cesv");
    }

    @Override
    public void runCommand(String s, String[] subcommands) {
        Double speedvalue = Double.parseDouble(subcommands[0]);
        //Speed.SPEED_VALUE = speedvalue;
        GeneralConfiguration.instance().configuration.save();
        GeneralConfiguration.instance().configuration.load();
        Wrapper.INSTANCE.addChatMessage("Set speed to: " + speedvalue);
    }

    @Override
    public String getDescription() {
        return "Sets player speed (Speed Module)";
    }

    @Override
    public String getSyntax() {
        return this.getCommand() + " number of blocks/second";
    }

    public boolean canCommandSenderUseCommand(ICommandSender var1) {
        return true;
    }
}

