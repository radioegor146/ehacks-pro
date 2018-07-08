/*
 * Decompiled with CFR 0_128.
 */
package ehacks.mod.external.config.management;

import ehacks.mod.external.config.manual.KeybindConfiguration;
import ehacks.mod.external.config.manual.ModuleStateConfiguration;
import ehacks.mod.external.config.manual.SaveableGuiState;
import ehacks.mod.logger.ModLogger;
import ehacks.mod.main.Main;

public class ConfigurationManager {
    private static volatile ConfigurationManager INSTANCE = new ConfigurationManager();

    public ConfigurationManager() {
        Main.INSTANCE.logger.info("Automated EHacks Configuration System initializing...");
        new KeybindConfiguration();
        new ModuleStateConfiguration();
        new SaveableGuiState();
        Main.INSTANCE.logger.info("Automated EHacks Configuration System initialized.");
    }

    public static ConfigurationManager instance() {
        return INSTANCE;
    }
}

