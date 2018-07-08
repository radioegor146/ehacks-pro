/*
 * Decompiled with CFR 0_128.
 */
package ehacks.mod.commands.apicommands;

import ehacks.api.command.Command;
import ehacks.mod.modulesystem.classes.Breadcrumb;
import ehacks.mod.wrapper.Wrapper;
import java.util.concurrent.CopyOnWriteArrayList;

public class ACommandCBreadcrumb
extends Command {
    public ACommandCBreadcrumb() {
        super("cebc");
    }

    @Override
    public void runCommand(String s, String[] subcommands) {
        Breadcrumb.positionsList.clear();
        Wrapper.INSTANCE.addChatMessage("Cleared Breadcrumbs");
    }

    @Override
    public String getDescription() {
        return "Clear breadcrumbs from world";
    }

    @Override
    public String getSyntax() {
        return null;
    }
}

