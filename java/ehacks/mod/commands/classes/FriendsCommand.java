/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehacks.mod.commands.classes;

import ehacks.mod.commands.ICommand;
import ehacks.mod.external.config.AuraConfiguration;
import ehacks.mod.external.config.ConfigurationManager;
import ehacks.mod.modulesystem.handler.EHacksGui;
import ehacks.mod.util.InteropUtils;
import ehacks.mod.wrapper.Wrapper;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.GuiPlayerInfo;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;

/**
 *
 * @author radioegor146
 */
public class FriendsCommand implements ICommand {

    @Override
    public String getName() {
        return "friends";
    }

    @Override
    public void process(String[] args) {
        if (args.length > 0) {
            if (args[0].equals("add") && args.length > 1) {
                if (!AuraConfiguration.config.friends.contains(args[1])) {
                    AuraConfiguration.config.friends.add(args[1]);
                    InteropUtils.log("Successfully added", "Friends");
                }
                else {
                    InteropUtils.log("&cFriend already exists", "Friends");
                }
                ConfigurationManager.instance().saveConfigs();
                return;
            }
            if (args[0].equals("list")) {
                InteropUtils.log("&aAura friendlist:", "Friends");
                int i = 1;
                for (String nick : AuraConfiguration.config.friends) {
                    InteropUtils.log(i + ". &f" + nick, "Friends");
                    i++;
                }
                if (i == 1)
                    InteropUtils.log("&cYou have no friends", "Friends");
                InteropUtils.log("&aTip: you can use FriendClick to add friends", "Friends");
                return;
            }
            if (args[0].equals("clear")) {
                AuraConfiguration.config.friends.clear();
                InteropUtils.log("Succesfully cleared", "Friends");
                ConfigurationManager.instance().saveConfigs();
                return;
            }
            if (args[0].equals("remove") && args.length > 1) {
                if (AuraConfiguration.config.friends.contains(args[1])) {
                    AuraConfiguration.config.friends.remove(args[1]);
                    InteropUtils.log("Succesfully removed", "Friends");
                }
                else {
                    InteropUtils.log("&cNo such nick in friends", "Friends");
                }
                ConfigurationManager.instance().saveConfigs();
                return;
            }
        }
        EHacksGui.clickGui.consoleGui.printChatMessage(new ChatComponentText("\u00a7c/" + this.getName() + " " + this.getCommandArgs()));
    }
    
    public static ChatComponentTranslation format(EnumChatFormatting color, String str, Object... args)
    {
        ChatComponentTranslation ret = new ChatComponentTranslation(str, args);
        ret.getChatStyle().setColor(color);
        return ret;
    }

    @Override
    public String getCommandDescription() {
        return "Edit aura friend list";
    }

    @Override
    public String getCommandArgs() {
        return "<add|list|remove|clear> [nickname]";
    }
    
    private boolean contains(Object[] array, Object object) {
        for (Object o : array)
            if (o.equals(object))
                return true;
        return false;
    }
    
    private String[] getTabList() {
        List<GuiPlayerInfo> players = Wrapper.INSTANCE.player().sendQueue.playerInfoList;
        ArrayList<String> playerNicks = new ArrayList();
        for (GuiPlayerInfo playerInfo : players) {
            playerNicks.add(playerInfo.name);
        }
        return playerNicks.toArray(new String[playerNicks.size()]);
    }

    @Override
    public String[] autoComplete(String[] args) {
        if (args.length == 0) {
            return new String[] { "add", "list", "remove", "clear" };
        }
        if (args.length == 1) {
            if (contains(new String[] { "add", "list", "remove", "clear" }, args[0])) {
                if ("add".equals(args[0]))
                    return getTabList();
                if ("add".equals(args[0]))
                    return new String[0];
                if ("clear".equals(args[0]))
                    return new String[0];
                if ("remove".equals(args[0]))
                    return AuraConfiguration.config.friends.toArray(new String[AuraConfiguration.config.friends.size()]);
            }
            else {
                ArrayList<String> avaibleNames = new ArrayList();
                for (String name : new String[] { "add", "list", "remove", "clear" }) {
                    if (name.startsWith(args[0])) {
                        avaibleNames.add(name);
                    }
                }
                return avaibleNames.toArray(new String[avaibleNames.size()]);
            }
        }
        if (args.length == 2) {
            if (args[0].equals("add")) {
                ArrayList<String> avaibleNames = new ArrayList();
                for (String name : getTabList()) {
                    if (!AuraConfiguration.config.friends.contains(name) && name.startsWith(args[0])) {
                        avaibleNames.add(name);
                    }
                }
                return avaibleNames.toArray(new String[avaibleNames.size()]);
            }
            if (args[0].equals("remove")) {
                ArrayList<String> avaibleNames = new ArrayList();
                for (String name : AuraConfiguration.config.friends) {
                    if (name.startsWith(args[0])) {
                        avaibleNames.add(name);
                    }
                }
                return avaibleNames.toArray(new String[avaibleNames.size()]);
            }
        }
        return new String[0];
    }
    
}
