/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  org.lwjgl.input.Keyboard
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
import org.lwjgl.input.Keyboard;
import ehacks.api.module.APICEMod;
import ehacks.api.module.Mod;
import ehacks.mod.logger.ModLogger;
import ehacks.mod.main.Main;

public class KeybindConfiguration {
    private static volatile KeybindConfiguration instance = new KeybindConfiguration();
    private File keybindConfig;

    public KeybindConfiguration() {
        this.keybindConfig = new File(Minecraft.getMinecraft().mcDataDir, "/config/ehacks/keyconfig.txt");
        this.write();
        this.readKeybindConfig();
    }

    public void writeKeybindConfig() {
        Main.INSTANCE.logger.info("Writing keybinding config file...");
        try {
            FileWriter filewriter = new FileWriter(this.keybindConfig);
            BufferedWriter bufferedwriter = new BufferedWriter(filewriter);
            for (Mod module : APICEMod.INSTANCE.mods) {
                String s = Keyboard.getKeyName((int)module.getKeybind());
                bufferedwriter.write(module.getName().toLowerCase().replaceAll(" ", "") + ":" + s + "\r\n");
            }
            bufferedwriter.close();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void readKeybindConfig() {
        try {
            String key;
            Main.INSTANCE.logger.info("Reading Keybinding config file...");
            FileInputStream imputstream = new FileInputStream(this.keybindConfig.getAbsolutePath());
            DataInputStream datastream = new DataInputStream(imputstream);
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(datastream));
            while ((key = bufferedreader.readLine()) != null) {
                String line = key.trim();
                String[] string = line.split(":");
                String module1 = string[0];
                String keybinding = string[1].toUpperCase();
                for (Mod module : APICEMod.INSTANCE.mods) {
                    List<String> modules = Arrays.asList(module.getName());
                    for (int i = 0; i < modules.size(); ++i) {
                        if (!module1.equalsIgnoreCase(modules.get(i).toLowerCase().replaceAll(" ", ""))) continue;
                        module.setKeybinding(Keyboard.getKeyIndex((String)keybinding));
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void write() {
        if (!this.keybindConfig.exists()) {
            this.keybindConfig.getParentFile().mkdirs();
            try {
                this.keybindConfig.createNewFile();
                this.writeKeybindConfig();
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
    }

    public static KeybindConfiguration instance() {
        return instance;
    }
}

