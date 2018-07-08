/*
 * Decompiled with CFR 0_128.
 */
package ehacks.api.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import ehacks.api.command.Command;

public class APICommand {
    public static ArrayList<Command> commands = new ArrayList();
    private static volatile APICommand instance = new APICommand();

    private APICommand() {
    }

    public void addCommandToCE(Command command) {
        if (!commands.contains(command)) {
            commands.add(command);
        }
    }

    public List getCommandList() {
        return Collections.unmodifiableList(commands);
    }

    public static APICommand instance() {
        return instance;
    }
}

