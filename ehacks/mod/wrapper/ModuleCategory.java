package ehacks.mod.wrapper;

public enum ModuleCategory {
    PLAYER("Player"),
    RENDER("Render"),
    COMBAT("Combat"),
    MINIGAMES("MiniGames"),
    NOCHEATPLUS("NoCheatPlus"),
    NONE("None"),
    EHACKS("EHacks"),
    KEYBIND("Keybind");

    ModuleCategory(String name) {
        this.name = name;
    }

    private final String name;

    public String getName() {
        return this.name;
    }
}
