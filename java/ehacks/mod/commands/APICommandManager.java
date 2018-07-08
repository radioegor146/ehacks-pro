/*
 * Decompiled with CFR 0_128.
 */
package ehacks.mod.commands;

import ehacks.api.command.Command;
import ehacks.mod.commands.ACommandAuraRange;
import ehacks.mod.commands.ACommandBind;
import ehacks.mod.commands.ACommandCBreadcrumb;
import ehacks.mod.commands.ACommandFriend;
import ehacks.mod.commands.ACommandModuleList;
import ehacks.mod.commands.ACommandModuleToggle;
import ehacks.mod.commands.ACommandServerIP;
import ehacks.mod.commands.ACommandSkypeResolver;
import ehacks.mod.commands.ACommandSpeedValue;
import ehacks.mod.commands.ACommandStepHeight;
import ehacks.mod.commands.CommandManager;

public class APICommandManager {
    public static void addCommands() {
        CommandManager.instance();
        CommandManager.instance().addCommand(new ACommandModuleList());
        CommandManager.instance().addCommand(new ACommandCBreadcrumb());
        CommandManager.instance().addCommand(new ACommandStepHeight());
        CommandManager.instance().addCommand(new ACommandSpeedValue());
        CommandManager.instance().addCommand(new ACommandServerIP());
        CommandManager.instance().addCommand(new ACommandModuleToggle());
        CommandManager.instance().addCommand(new ACommandAuraRange());
        CommandManager.instance().addCommand(new ACommandSkypeResolver());
        CommandManager.instance().addCommand(new ACommandBind());
        CommandManager.instance().addCommand(new ACommandFriend());
    }
}

