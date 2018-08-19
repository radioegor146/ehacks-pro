/*
 * Decompiled with CFR 0_128.
 */
package ehacks.mod.external.config.manual;

public class ConfigurationManager {
    private static volatile ConfigurationManager INSTANCE = new ConfigurationManager();

    public ConfigurationManager() {
        new KeybindConfiguration();
        new ModuleStateConfiguration();
        new GuiConfiguration();
    }

    public static ConfigurationManager instance() {
        return INSTANCE;
    }
}

