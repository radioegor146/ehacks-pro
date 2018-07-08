/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 */
package ehacks.mod.external.config.agce;

import java.io.File;
import net.minecraft.client.Minecraft;
import ehacks.mod.wrapper.Wrapper;

public class AGCEConfiguration {
    protected Object obj;
    protected Object newobj;
    protected String name;
    protected File file;
    protected String path;

    protected void createFile() {
        if (!this.file.exists()) {
            this.file.getParentFile().mkdirs();
            try {
                this.file.createNewFile();
                this.create(this.file, this.obj);
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }

    public void modify(String fileName, Object o) {
        this.path = fileName;
        this.file = new File(Wrapper.INSTANCE.mc().mcDataDir, "/config/ehacks/" + this.path);
        this.create(this.file, o);
    }

    protected void create(File file, Object obj) {
    }

    protected void read() {
    }
}

