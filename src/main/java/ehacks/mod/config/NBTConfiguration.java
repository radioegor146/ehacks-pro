package ehacks.mod.config;

import com.google.gson.Gson;
import ehacks.debugme.Debug;
import ehacks.mod.util.nbtedit.GuiNBTTree;
import ehacks.mod.wrapper.Wrapper;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import net.minecraft.nbt.NBTTagCompound;

/**
 *
 * @author radioegor146
 */
public class NBTConfiguration implements IConfiguration {

    private final File configFile;

    public NBTConfiguration() {
        this.configFile = new File(System.getProperty("user.home") + "/ehacks/nbt.json");
    }

    @Override
    public void write() {
        try {
            FileWriter filewriter = new FileWriter(this.configFile);
            try (BufferedWriter bufferedwriter = new BufferedWriter(filewriter)) {
                bufferedwriter.write(new Gson().toJson(new NBTConfigJson(GuiNBTTree.saveSlots)));
            }
        } catch (Exception exception) {
        }
    }

    @Override
    public void read() {
        try {
            FileInputStream inputstream = new FileInputStream(this.configFile.getAbsolutePath());
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(inputstream));
            NBTConfigJson nbtConfig = new Gson().fromJson(bufferedreader.readLine(), NBTConfigJson.class);
            for (int i = 0; i < GuiNBTTree.saveSlots.length; i++) {
                GuiNBTTree.saveSlots[i] = null;
            }
            for (int i = 0; i < Math.min(nbtConfig.nbts.length, GuiNBTTree.saveSlots.length); i++) {
                GuiNBTTree.saveSlots[i] = Debug.jsonToNBT(nbtConfig.nbts[i]);
            }
        } catch (Exception ex) {
        }
    }

    @Override
    public String getConfigFilePath() {
        return "nbt.json";
    }

    @Override
    public boolean isConfigUnique() {
        return false;
    }

    private class NBTConfigJson {

        public String[] nbts;

        public NBTConfigJson(NBTTagCompound[] nbts) {
            this.nbts = new String[nbts.length];
            for (int i = 0; i < nbts.length; i++) {
                this.nbts[i] = nbts[i] == null ? null : Debug.NBTToJson(nbts[i]);
            }
        }
    }
}
