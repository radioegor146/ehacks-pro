package ehacks.mod.config;

import com.google.gson.Gson;
import ehacks.mod.main.Main;
import ehacks.mod.wrapper.Wrapper;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

/**
 *
 * @author radioegor146
 */
public class ModIdConfiguration implements IConfiguration {

    private final File configFile;

    public ModIdConfiguration() {
        this.configFile = new File(Wrapper.INSTANCE.mc().mcDataDir, "/config/ehacks/modid.json");
    }

    @Override
    public void write() {
        try {
            FileWriter filewriter = new FileWriter(this.configFile);
            try (BufferedWriter bufferedwriter = new BufferedWriter(filewriter)) {
                bufferedwriter.write(new Gson().toJson(new ModIdConfigJson(Main.modId, Main.modVersion)));
            }
        } catch (Exception exception) {
        }
    }

    @Override
    public void read() {
        try {
            FileInputStream inputstream = new FileInputStream(this.configFile.getAbsolutePath());
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(inputstream));
            ModIdConfigJson modidConfig = new Gson().fromJson(bufferedreader.readLine(), ModIdConfigJson.class);
            Main.modId = modidConfig.modid;
            Main.modVersion = modidConfig.version;
            Main.applyModChanges();
        } catch (Exception ex) {
        }
    }

    @Override
    public String getConfigFilePath() {
        return "modid.json";
    }

    @Override
    public boolean isConfigUnique() {
        return true;
    }

    private class ModIdConfigJson {

        public String modid;
        public String version;

        public ModIdConfigJson(String modid, String version) {
            this.modid = modid;
            this.version = version;
        }
    }
}
