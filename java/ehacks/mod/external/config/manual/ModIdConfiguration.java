/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehacks.mod.external.config.manual;

import ehacks.api.module.Module;
import ehacks.api.module.ModController;
import ehacks.mod.main.Main;
import ehacks.mod.wrapper.ModuleCategory;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.Minecraft;

/**
 *
 * @author radioegor146
 */
public class ModIdConfiguration {
    private static volatile ModIdConfiguration instance = new ModIdConfiguration();
    private File configFile;

    public ModIdConfiguration() {
        this.configFile = new File(Minecraft.getMinecraft().mcDataDir, "/config/ehacks/modid.txt");
        this.write();
    }

    public void writeToFile() {
        try {
            FileWriter filewriter = new FileWriter(this.configFile);
            BufferedWriter bufferedwriter = new BufferedWriter(filewriter);
            bufferedwriter.write(Main.modId + "\r\n");
            bufferedwriter.write(Main.modVersion + "\r\n");
            bufferedwriter.close();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void read() {
        try {
            FileInputStream inputstream = new FileInputStream(this.configFile.getAbsolutePath());
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(inputstream));
            Main.modId = bufferedreader.readLine();
            Main.modVersion = bufferedreader.readLine();
            Main.applyModChanges();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void write() {
        if (!this.configFile.exists()) {
            this.configFile.getParentFile().mkdirs();
            try {
                this.configFile.createNewFile();
                this.writeToFile();
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
    }

    public static ModIdConfiguration instance() {
        return instance;
    }
}
