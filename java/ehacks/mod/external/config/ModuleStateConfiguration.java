package ehacks.mod.external.config;

import com.google.gson.Gson;
import ehacks.api.module.ModStatus;
import ehacks.api.module.Module;
import ehacks.api.module.ModuleController;
import ehacks.mod.wrapper.Wrapper;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.HashMap;

public class ModuleStateConfiguration implements IConfiguration {

    private final File moduleConfig;

    public ModuleStateConfiguration() {
        this.moduleConfig = new File(Wrapper.INSTANCE.mc().mcDataDir, "/config/ehacks/modulestatus.txt");
    }

    @Override
    public void write() {
        try {
            FileWriter filewriter = new FileWriter(this.moduleConfig);
            BufferedWriter bufferedwriter = new BufferedWriter(filewriter);
            ModuleStateConfigJson moduleStateConfig = new ModuleStateConfigJson();
            for (Module module : ModuleController.INSTANCE.modules) {
                if (!module.canOnOnStart()) {
                    continue;
                }
                moduleStateConfig.states.put(module.getName().toLowerCase(), module.isActive());
            }
            bufferedwriter.write(new Gson().toJson(moduleStateConfig));
            bufferedwriter.close();
        } catch (Exception exception) {
            exception.printStackTrace();
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
            for (Module module : ModuleController.INSTANCE.modules) {
                if (!module.canOnOnStart() || (module.getModStatus() == ModStatus.NOTWORKING)) {
                    continue;
                }
                if (!moduleStateConfig.states.containsKey(module.getName().toLowerCase())) {
                    continue;
                }
                if (moduleStateConfig.states.get(module.getName().toLowerCase())) {
                    try {
                        module.on();
                    } catch (Exception e) {

                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public String getConfigFilePath() {
        return "modulestatus.txt";
    }

    @Override
    public boolean isConfigUnique() {
        return false;
    }

    private class ModuleStateConfigJson {

        public HashMap<String, Boolean> states = new HashMap();
    }
}
