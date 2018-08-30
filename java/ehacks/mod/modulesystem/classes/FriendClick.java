/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehacks.mod.modulesystem.classes;

import ehacks.api.module.Module;
import ehacks.mod.external.config.AuraConfiguration;
import ehacks.mod.util.InteropUtils;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;
import org.lwjgl.input.Mouse;

/**
 *
 * @author radioegor146
 */
public class FriendClick extends Module {

    public FriendClick() {
        super(ModuleCategory.COMBAT);
    }

    @Override
    public String getName() {
        return "FriendClick";
    }

    @Override
    public String getDescription() {
        return "You can add and remove friends by clicking on them";
    }

    public boolean prevState = false;

    @Override
    public void onTicks() {
        try {
            MovingObjectPosition position = Wrapper.INSTANCE.mc().objectMouseOver;
            boolean nowState = Mouse.isButtonDown(0);
            if (position.entityHit != null && nowState && !prevState) {
                if (position.entityHit instanceof EntityPlayer) {
                    if (AuraConfiguration.config.friends.contains(position.entityHit.getCommandSenderName())) {
                        AuraConfiguration.config.friends.remove(position.entityHit.getCommandSenderName());
                        InteropUtils.log("Player " + position.entityHit.getCommandSenderName() + " was removed from aura friend list", this);
                    }
                    else {
                        AuraConfiguration.config.friends.add(position.entityHit.getCommandSenderName()); 
                        InteropUtils.log("Player " + position.entityHit.getCommandSenderName() + " was added to aura friend list", this);
                    }
                }
            }
            prevState = nowState;
        } catch (Exception e) {

        }
    }
}
