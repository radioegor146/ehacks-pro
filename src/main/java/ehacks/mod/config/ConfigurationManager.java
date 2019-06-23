package ehacks.mod.config;

import java.util.ArrayList;
import java.util.List;

public class ConfigurationManager {

    private static volatile ConfigurationManager INSTANCE = new ConfigurationManager();

    public List<IConfiguration> configurations = new ArrayList<>();

    public ConfigurationManager() {
        configurations.add(new AuraConfiguration());
        configurations.add(new ChatKeyBindsConfiguration());
        configurations.add(new CheatConfiguration());
        configurations.add(new GuiConfiguration());
        configurations.add(new KeyBindConfiguration());
        configurations.add(new ModuleStateConfiguration());
        configurations.add(new ModIdConfiguration());
        configurations.add(new NBTConfiguration());
    }

    public static ConfigurationManager instance() {
        return INSTANCE;
    }

    public void initConfigs() {
        this.configurations.stream().map((config) -> {
            config.read();
            return config;
        }).forEachOrdered((config) -> {
            config.write();
        });
    }

    public void saveConfigs() {
        this.configurations.forEach((config) -> {
            config.write();
        });
    }
}
