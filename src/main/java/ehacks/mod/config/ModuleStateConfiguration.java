package ehacks.mod.config;

import com.google.gson.Gson;
import ehacks.mod.api.ModStatus;
import ehacks.mod.api.ModuleController;
import ehacks.mod.wrapper.Wrapper;

import java.io.*;
import java.util.HashMap;

public class ModuleStateConfiguration implements IConfiguration {

    private final File moduleConfig;

    public ModuleStateConfiguration() {
        this.moduleConfig = new File(Wrapper.INSTANCE.mc().mcDataDir, "/config/ehacks/modulestatus.json");
    }

    @Override
    public void write() {
        try {
            FileWriter filewriter = new FileWriter(this.moduleConfig);
            try (BufferedWriter bufferedwriter = new BufferedWriter(filewriter)) {
                ModuleStateConfigJson moduleStateConfig = new ModuleStateConfigJson();
                ModuleController.INSTANCE.modules.stream().filter((module) -> !(!module.canOnOnStart())).forEachOrdered((module) -> {
                    moduleStateConfig.states.put(module.getName().toLowerCase(), module.isActive());
                });
                bufferedwriter.write(new Gson().toJson(moduleStateConfig));
            }
        } catch (Exception exception) {
        }
    }

    @Override
    public void read() {
        try {
            String string;
            FileInputStream inputstream = new FileInputStream(this.moduleConfig.getAbsolutePath());
            DataInputStream datastream = new DataInputStream(inputstream);
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(inputstream));
            ModuleStateConfigJson moduleStateConfig = new Gson().fromJson(bufferedreader.readLine(), ModuleStateConfigJson.class);
            ModuleController.INSTANCE.modules.stream().filter((module) -> !(!module.canOnOnStart() || (module.getModStatus() == ModStatus.NOTWORKING))).filter((module) -> !(!moduleStateConfig.states.containsKey(module.getName().toLowerCase()))).filter((module) -> (moduleStateConfig.states.get(module.getName().toLowerCase()))).forEachOrdered((module) -> {
                try {
                    module.on();
                } catch (Exception ignored) {

                }
            });
        } catch (Exception ex) {
        }
    }

    @Override
    public String getConfigFilePath() {
        return "modulestatus.json";
    }

    @Override
    public boolean isConfigUnique() {
        return false;
    }

    private class ModuleStateConfigJson {

        public HashMap<String, Boolean> states = new HashMap<>();
    }
}
