/*
 * Decompiled with CFR 0_128.
 */
package ehacks.mod.commands.apicommands;

import ehacks.api.command.Command;
import ehacks.mod.commands.apicommands.ACommandCBreadcrumb;
import ehacks.mod.commands.apicommands.ACommandEnemy;
import ehacks.mod.commands.apicommands.ACommandFlySpeed;
import ehacks.mod.commands.apicommands.ACommandFriend;
import ehacks.mod.commands.apicommands.ACommandModuleHelp;
import ehacks.mod.commands.apicommands.ACommandModuleList;
import ehacks.mod.commands.apicommands.ACommandModuleToggle;
import ehacks.mod.commands.apicommands.ACommandSMKeybind;
import ehacks.mod.commands.apicommands.ACommandSpeedValue;
import ehacks.mod.commands.apicommands.ACommandStepHeight;
import ehacks.mod.commands.apicommands.CommandManager;

public class APICommandManager {
    public static void addCommands() {
        CommandManager.instance();
        CommandManager.instance().addCommand(new ACommandCBreadcrumb());
        CommandManager.instance().addCommand(new ACommandEnemy());
        CommandManager.instance().addCommand(new ACommandFlySpeed());
        CommandManager.instance().addCommand(new ACommandFriend());
        CommandManager.instance().addCommand(new ACommandModuleHelp());
        CommandManager.instance().addCommand(new ACommandModuleList());
        CommandManager.instance().addCommand(new ACommandModuleToggle());
        CommandManager.instance().addCommand(new ACommandSMKeybind());
        CommandManager.instance().addCommand(new ACommandSpeedValue());
        CommandManager.instance().addCommand(new ACommandStepHeight());
    }
}

