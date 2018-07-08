/*
 * Decompiled with CFR 0_128.
 */
package ehacks.api.command;

public abstract class Command {
    private String command;

    public Command(String command) {
        this.command = command;
    }

    public abstract void runCommand(String var1, String[] var2);

    public abstract String getDescription();

    public abstract String getSyntax();

    public String getCommand() {
        return this.command;
    }
}

