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
import ehacks.mod.gui.reeszrbteam.YouAlwaysWinClickGui;
import ehacks.mod.gui.reeszrbteam.element.YAWWindow;

public class SaveableGuiState {
    private File guiConfig;
    private static volatile SaveableGuiState inst = new SaveableGuiState();

    public SaveableGuiState() {
        this.guiConfig = new File(Minecraft.getMinecraft().mcDataDir, "/config/ehacks/tgc.txt");
        this.write();
    }

    public void writeToFile() {
        try {
            FileWriter filewriter = new FileWriter(this.guiConfig);
            BufferedWriter buffered = new BufferedWriter(filewriter);
            for (YAWWindow window : YouAlwaysWinClickGui.unFocusedWindows) {
                int x = window.lastDragX;
                int y = window.lastDragY;
                boolean open = window.isOpen();
                buffered.write(window.getTitle().toLowerCase().replaceAll(" ", "") + ":" + x + ":" + y + ":" + open + "\r\n");
            }
            buffered.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void read() {
        try {
            String x;
            FileInputStream input = new FileInputStream(this.guiConfig.getAbsolutePath());
            DataInputStream data = new DataInputStream(input);
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(data));
            while ((x = bufferedreader.readLine()) != null) {
                String line = x.trim();
                String[] array = x.split(":");
                String window = array[0];
                String xPos = array[1];
                String yPos = array[2];
                String open = array[3];
                for (YAWWindow windows : YouAlwaysWinClickGui.unFocusedWindows) {
                    List<String> windowsN = Arrays.asList(windows.getTitle());
                    for (int i = 0; i < windowsN.size(); ++i) {
                        if (!window.equalsIgnoreCase(windowsN.get(i).toLowerCase().replaceAll(" ", ""))) continue;
                        windows.setOpen(Boolean.parseBoolean(open));
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void write() {
        if (!this.guiConfig.exists()) {
            this.guiConfig.getParentFile().mkdirs();
            try {
                this.guiConfig.createNewFile();
                this.writeToFile();
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
    }

    public static SaveableGuiState instance() {
        return inst;
    }
}

