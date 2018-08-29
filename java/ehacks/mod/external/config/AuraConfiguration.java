/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehacks.mod.external.config;

import com.google.gson.Gson;
import static ehacks.mod.external.config.CheatConfiguration.config;
import ehacks.mod.wrapper.Wrapper;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author radioegor146
 */
public class AuraConfiguration implements IConfiguration {
    public static AuraConfigJson config = new AuraConfigJson();
    
    private final File configFile;

    public AuraConfiguration() {
        this.configFile = new File(Wrapper.INSTANCE.mc().mcDataDir, "/config/ehacks/aura.txt");
    }

    @Override
    public void write() {
        try {
            FileWriter filewriter = new FileWriter(this.configFile);
            BufferedWriter bufferedwriter = new BufferedWriter(filewriter);
            bufferedwriter.write(new Gson().toJson(config));
            bufferedwriter.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void read() {
        try {
            FileInputStream inputstream = new FileInputStream(this.configFile.getAbsolutePath());
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(inputstream));
            config = new Gson().fromJson(bufferedreader.readLine(), AuraConfigJson.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public String getConfigFilePath() {
        return "aura.txt";
    }

    @Override
    public boolean isConfigUnique() {
        return false;
    }
    
    public static class AuraConfigJson {
        public HashSet<String> friends = new HashSet();
    }
}
