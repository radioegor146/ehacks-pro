/*
 * Decompiled with CFR 0_128.
 */
package ehacks.mod.commands;

import java.util.concurrent.CopyOnWriteArrayList;
import ehacks.api.command.Command;
import ehacks.mod.modulesystem.classes.Breadcrumb;
import ehacks.mod.wrapper.Wrapper;

public class ACommandCBreadcrumb
extends Command {
    public ACommandCBreadcrumb() {
        super("bc");
    }

    @Override
    public void runCommand(String s, String[] subcommands) {
        Breadcrumb.positionsList.clear();
        Wrapper.INSTANCE.addChatMessage("&9[&bCE Console&9] &fCleared Breadcrumbs.");
    }

    @Override
    public String getDescription() {
        return " - Clear breadcrumbs.";
    }

    @Override
    public String getSyntax() {
        return "&9[&bCE Console&9] &cUsage: " + this.getCommand();
    }
}

