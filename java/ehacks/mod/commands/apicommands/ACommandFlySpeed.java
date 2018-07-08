/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityClientPlayerMP
 *  net.minecraft.entity.player.PlayerCapabilities
 *  net.minecraftforge.common.config.Configuration
 */
package ehacks.mod.commands.apicommands;

import ehacks.api.command.Command;
import ehacks.mod.external.config.forge.GeneralConfiguration;
import ehacks.mod.modulesystem.classes.Fly;
import ehacks.mod.wrapper.Wrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraftforge.common.config.Configuration;

public class ACommandFlySpeed
extends Command {
    public ACommandFlySpeed() {
        super("flyspeed");
    }

    @Override
    public void runCommand(String s, String[] subcommands) {
        float string;
        Fly.FLY_SPEED = string = Float.parseFloat(subcommands[0]);
        try {
            if (string < 0.7f) {
                Minecraft.getMinecraft().thePlayer.capabilities.setFlySpeed(Fly.FLY_SPEED);
                GeneralConfiguration.instance().configuration.save();
                GeneralConfiguration.instance().configuration.load();
                Wrapper.INSTANCE.addChatMessage("Set Fly Speed to: " + Fly.FLY_SPEED);
            } else {
                Wrapper.INSTANCE.addChatMessage("Can't set values higher than 0.7");
            }
        }
        catch (Exception e) {
            // empty catch block
        }
    }

    @Override
    public String getDescription() {
        return "Sets player fly speed";
    }

    @Override
    public String getSyntax() {
        return this.getCommand().concat(" <blocks/second>");
    }
}

