/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 */
package ehacks.mod.external.config.agce.files;

import ehacks.mod.external.config.agce.AGCEConfiguration;
import ehacks.mod.logger.ModLogger;
import ehacks.mod.main.Main;
import ehacks.mod.util.Utils;
import ehacks.mod.wrapper.Wrapper;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.client.Minecraft;

public class AGCEIntegerList
extends AGCEConfiguration {
    private Collection list;
    public static volatile AGCEIntegerList INSTANCE = new AGCEIntegerList();

    private AGCEIntegerList() {
    }

    public AGCEIntegerList(String name, String path, Collection list) {
        this.name = name;
        this.path = path;
        this.list = list;
        this.file = new File(Minecraft.getMinecraft().mcDataDir, "/config/ehacks/" + path);
        this.createFile();
        this.read();
    }

    @Override
    public void modify(String fileName, Object o) {
        this.path = fileName;
        this.file = new File(Wrapper.INSTANCE.mc().mcDataDir, "/config/ehacks/" + this.path);
        this.create(this.file, (Collection)o);
    }

    protected void create(File file, Collection list) {
        Main.INSTANCE.logger.info("Writing //String// List config file to " + this.path);
        try {
            FileOutputStream filewriter = new FileOutputStream(file);
            BufferedWriter buffered = new BufferedWriter(new OutputStreamWriter(filewriter));
            Iterator i$ = ((CopyOnWriteArrayList)list).iterator();
            while (i$.hasNext()) {
                int s = (Integer)i$.next();
                Utils.removeDupes(list);
                buffered.write("" + s + "\r\n");
            }
            buffered.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void read() {
        Main.INSTANCE.logger.info("Reading //Integer// List config file: " + this.name);
        try {
            String s;
            FileInputStream imputstream = new FileInputStream(this.file.getAbsolutePath());
            DataInputStream datastream = new DataInputStream(imputstream);
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(datastream));
            while ((s = bufferedreader.readLine()) != null) {
                Integer i = Integer.parseInt(s);
                this.list.add(i);
            }
            bufferedreader.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

