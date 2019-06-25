package ehacks.mod.wrapper;

public enum ModuleCategory {
    NONE("None"),
    BEUHACKS("BeuHacks"),
    KEYBIND("Keybind");

    private final String name;

    ModuleCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
