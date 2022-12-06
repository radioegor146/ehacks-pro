/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehacks.mod.config;

import com.google.gson.Gson;
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
public class CheatConfiguration implements IConfiguration {

    public static CheatConfigJson config = new CheatConfigJson();
    private final File configFile;

    public CheatConfiguration() {
        this.configFile = new File(System.getProperty("user.home") + "/ehacks/cheat.json");
    }

    @Override
    public void write() {
        try {
            FileWriter filewriter = new FileWriter(this.configFile);
            try (BufferedWriter bufferedwriter = new BufferedWriter(filewriter)) {
                bufferedwriter.write(new Gson().toJson(config));
            }
        } catch (Exception exception) {
        }
    }

    @Override
    public void read() {
        try {
            FileInputStream inputstream = new FileInputStream(this.configFile.getAbsolutePath());
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(inputstream));
            config = new Gson().fromJson(bufferedreader.readLine(), CheatConfigJson.class);
        } catch (Exception ex) {
        }
    }

    @Override
    public String getConfigFilePath() {
        return "cheat.json";
    }

    @Override
    public boolean isConfigUnique() {
        return false;
    }

    public static class CheatConfigJson {

        public double auraradius = 4;
        public double flyspeed = 1;
        public double speedhack = 3;
        public int nukerradius = 4;
        public double aimbotdistance = 6;
    }
}
