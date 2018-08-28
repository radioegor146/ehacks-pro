package ehacks.mod.external.config;

import java.util.ArrayList;
import java.util.List;

public class ConfigurationManager {

    private static volatile ConfigurationManager INSTANCE = new ConfigurationManager();

    public List<IConfiguration> configurations = new ArrayList();

    public ConfigurationManager() {
        configurations.add(new KeybindConfiguration());
        configurations.add(new ModuleStateConfiguration());
        configurations.add(new GuiConfiguration());
        configurations.add(new NBTConfiguration());
        configurations.add(new ModIdConfiguration());
    }

    public static ConfigurationManager instance() {
        return INSTANCE;
    }

    public void initConfigs() {
        for (IConfiguration config : this.configurations) {
            config.read();
            config.write();
        }
    }

    public void saveConfigs() {
        for (IConfiguration config : this.configurations) {
            config.write();
        }
    }
}
