/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 */
package ehacks.mod.external.config.manual;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.Minecraft;
import ehacks.api.module.APICEMod;
import ehacks.api.module.Mod;
import ehacks.mod.logger.ModLogger;
import ehacks.mod.main.Main;
import ehacks.mod.wrapper.ModuleCategories;

public class ModuleStateConfiguration {
    private static volatile ModuleStateConfiguration instance = new ModuleStateConfiguration();
    private File moduleConfig;

    public ModuleStateConfiguration() {
        this.moduleConfig = new File(Minecraft.getMinecraft().mcDataDir, "/config/ehacks/modulestatus.txt");
        this.write();
    }

    public void writeToFile() {
        try {
            FileWriter filewriter = new FileWriter(this.moduleConfig);
            BufferedWriter bufferedwriter = new BufferedWriter(filewriter);
            for (Mod module : APICEMod.INSTANCE.mods) {
                Boolean s = module.isActive();
                if (module.getCategory() == ModuleCategories.NONE) continue;
                bufferedwriter.write(module.getName().toLowerCase().replaceAll(" ", "") + ":" + s + "\r\n");
            }
            bufferedwriter.close();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void read() {
        Main.INSTANCE.logger.info("Reading module config file...");
        try {
            String string;
            FileInputStream inputstream = new FileInputStream(this.moduleConfig.getAbsolutePath());
            DataInputStream datastream = new DataInputStream(inputstream);
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(inputstream));
            while ((string = bufferedreader.readLine()) != null) {
                String line = string.trim();
                String[] string2 = string.split(":");
                String moduleName = string2[0];
                String booleanState = string2[1];
                for (Mod module : APICEMod.INSTANCE.mods) {
                    if (module.getCategory() == ModuleCategories.NONE) continue;
                    List<String> modules = Arrays.asList(module.getName());
                    for (int i = 0; i < modules.size(); ++i) {
                        if (!moduleName.equalsIgnoreCase(modules.get(i).toLowerCase().replaceAll(" ", "")) || !booleanState.equalsIgnoreCase("true")) continue;
                        try {
                            module.on();
                            continue;
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void write() {
        if (!this.moduleConfig.exists()) {
            this.moduleConfig.getParentFile().mkdirs();
            try {
                this.moduleConfig.createNewFile();
                this.writeToFile();
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
    }

    public static ModuleStateConfiguration instance() {
        return instance;
    }
}

