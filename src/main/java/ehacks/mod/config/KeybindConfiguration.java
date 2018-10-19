package ehacks.mod.config;

import com.google.gson.Gson;
import ehacks.mod.api.ModuleController;
import ehacks.mod.wrapper.Wrapper;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.HashMap;
import org.lwjgl.input.Keyboard;

public class KeybindConfiguration implements IConfiguration {

    private final File keybindConfig;

    public KeybindConfiguration() {
        this.keybindConfig = new File(Wrapper.INSTANCE.mc().mcDataDir, "/config/ehacks/keybinds.json");
    }

    @Override
    public void write() {
        try {
            FileWriter filewriter = new FileWriter(this.keybindConfig);
            try (BufferedWriter bufferedwriter = new BufferedWriter(filewriter)) {
                KeybindConfigJson keybindConfig = new KeybindConfigJson();
                ModuleController.INSTANCE.modules.forEach((module) -> {
                    String s = Keyboard.getKeyName(module.getKeybind());
                    keybindConfig.keybinds.put(module.getClass().getName().toLowerCase(), s);
                });
                bufferedwriter.write(new Gson().toJson(keybindConfig));
            }
        } catch (Exception exception) {
        }
    }

    @Override
    public void read() {
        try {
            String key;
            FileInputStream imputstream = new FileInputStream(this.keybindConfig.getAbsolutePath());
            DataInputStream datastream = new DataInputStream(imputstream);
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(datastream));
            KeybindConfigJson keybindConfig = new Gson().fromJson(bufferedreader.readLine(), KeybindConfigJson.class);
            ModuleController.INSTANCE.modules.stream().filter((module) -> !(!keybindConfig.keybinds.containsKey(module.getClass().getName().toLowerCase()))).forEachOrdered((module) -> {
                module.setKeybinding(Keyboard.getKeyIndex(keybindConfig.keybinds.get(module.getClass().getName().toLowerCase())));
            });
        } catch (Exception e) {
        }
    }

    @Override
    public String getConfigFilePath() {
        return "keybinds.json";
    }

    @Override
    public boolean isConfigUnique() {
        return false;
    }

    private class KeybindConfigJson {

        public HashMap<String, String> keybinds = new HashMap<>();
    }
}
