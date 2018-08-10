/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompressedStreamTools
 *  net.minecraft.nbt.NBTBase
 *  net.minecraft.nbt.NBTTagCompound
 */
package ehacks.mod.util.nbtedit;

import java.io.File;
import java.io.IOException;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

public class SaveStates {
    private File file;
    private SaveState[] tags;

    public SaveStates(File file) {
        this.file = file;
        this.tags = new SaveState[7];
        for (int i = 0; i < 7; ++i) {
            this.tags[i] = new SaveState("slot " + (i + 1));
        }
    }

    public void read() throws IOException {
        if (this.file.exists() && this.file.canRead()) {
            NBTTagCompound root = CompressedStreamTools.read((File)this.file);
            for (int i = 0; i < 7; ++i) {
                String name = "slot" + (i + 1);
                if (root.hasKey(name)) {
                    this.tags[i].tag = root.getCompoundTag(name);
                }
                if (!root.hasKey(name + "Name")) continue;
                this.tags[i].name = root.getString(name + "Name");
            }
        }
    }

    public void write() throws IOException {
        NBTTagCompound root = new NBTTagCompound();
        for (int i = 0; i < 7; ++i) {
            root.setTag("slot" + (i + 1), (NBTBase)this.tags[i].tag);
            root.setString("slot" + (i + 1) + "Name", this.tags[i].name);
        }
        CompressedStreamTools.write((NBTTagCompound)root, (File)this.file);
    }

    public void save() {
        try {
            this.write();
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    public void load() {
        try {
            this.read();
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    public SaveState getSaveState(int index) {
        return this.tags[index];
    }

    public static final class SaveState {
        public String name;
        public NBTTagCompound tag;

        public SaveState(String name) {
            this.name = name;
            this.tag = new NBTTagCompound();
        }
    }

}

