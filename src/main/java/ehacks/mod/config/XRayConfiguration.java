/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehacks.mod.config;

import com.google.gson.Gson;
import ehacks.mod.gui.xraysettings.XRayBlock;
import ehacks.mod.wrapper.Wrapper;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 *
 * @author radioegor146
 */
public class XRayConfiguration implements IConfiguration {

    public static XRayConfigJson config = new XRayConfigJson();

    private final File configFile;

    public XRayConfiguration() {
        this.configFile = new File(System.getProperty("user.home") + "/ehacks/xray.json");
    }

    @Override
    public void write() {
        try {
            FileWriter filewriter = new FileWriter(this.configFile);
            config.blocks = XRayBlock.blocks;
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
            config = new Gson().fromJson(bufferedreader.readLine(), XRayConfigJson.class);
            XRayBlock.blocks = config.blocks;
        } catch (Exception ex) {
        }
    }

    @Override
    public String getConfigFilePath() {
        return "xray.json";
    }

    @Override
    public boolean isConfigUnique() {
        return true;
    }

    public static class XRayConfigJson {

        public ArrayList<XRayBlock> blocks = new ArrayList<>();
    }
}
