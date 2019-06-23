package ehacks.mod.wrapper;

public enum ModuleCategory {
    NONE("None"),
    EHACKS("EHacks"),
    KEYBIND("Keybind");

    private final String name;

    ModuleCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
