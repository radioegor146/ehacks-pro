package ehacks.mod.external.config.manual;

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
import ehacks.mod.gui.EHacksClickGui;
import ehacks.mod.gui.element.ModWindow;
import ehacks.mod.gui.element.SimpleWindow;

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
            for (SimpleWindow window : EHacksClickGui.unFocusedWindows) {
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
                for (SimpleWindow windows : EHacksClickGui.unFocusedWindows) {
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

