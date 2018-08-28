package ehacks.mod.external.config;

import com.google.gson.Gson;
import ehacks.mod.gui.EHacksClickGui;
import ehacks.mod.gui.element.SimpleWindow;
import ehacks.mod.wrapper.Wrapper;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.HashMap;

public class GuiConfiguration implements IConfiguration {

    private final File guiConfig;

    public GuiConfiguration() {
        this.guiConfig = new File(Wrapper.INSTANCE.mc().mcDataDir, "/config/ehacks/gui.txt");
    }

    @Override
    public void write() {
        try {
            FileWriter filewriter = new FileWriter(this.guiConfig);
            BufferedWriter buffered = new BufferedWriter(filewriter);
            GuiConfigJson configJson = new GuiConfigJson();
            for (SimpleWindow window : EHacksClickGui.windows) {
                configJson.windows.put(window.getTitle().toLowerCase(), new WindowInfoJson(window));
            }
            buffered.write(new Gson().toJson(configJson));
            buffered.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void read() {
        try {
            FileInputStream input = new FileInputStream(this.guiConfig.getAbsolutePath());
            DataInputStream data = new DataInputStream(input);
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(data));
            GuiConfigJson configJson = new Gson().fromJson(bufferedreader.readLine(), GuiConfigJson.class);
            for (SimpleWindow window : EHacksClickGui.windows) {
                if (!configJson.windows.containsKey(window.getTitle().toLowerCase())) {
                    continue;
                }
                WindowInfoJson windowInfo = configJson.windows.get(window.getTitle().toLowerCase());
                window.setPosition(windowInfo.x, windowInfo.y);
                window.setOpen(windowInfo.opened);
                window.setExtended(windowInfo.extended);
                window.setPinned(windowInfo.pinned);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getConfigFilePath() {
        return "gui.txt";
    }

    @Override
    public boolean isConfigUnique() {
        return false;
    }

    private class GuiConfigJson {

        public HashMap<String, WindowInfoJson> windows = new HashMap();
    }

    private class WindowInfoJson {

        public int x;
        public int y;
        public boolean opened;
        public boolean extended;
        public boolean pinned;

        public WindowInfoJson(SimpleWindow window) {
            this.x = window.getX();
            this.y = window.getY();
            this.opened = window.isOpen();
            this.pinned = window.isPinned();
            this.extended = window.isExtended();
        }
    }
}
