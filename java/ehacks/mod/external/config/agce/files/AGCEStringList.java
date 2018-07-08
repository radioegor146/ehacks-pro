/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 */
package ehacks.mod.external.config.agce.files;

import ehacks.mod.external.config.agce.AGCEConfiguration;
import ehacks.mod.internal.CEColor;
import ehacks.mod.logger.ModLogger;
import ehacks.mod.main.Main;
import ehacks.mod.util.Utils;
import ehacks.mod.wrapper.Wrapper;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.client.Minecraft;

public class AGCEStringList
extends AGCEConfiguration {
    private Collection list;
    private CEColor[] colorList = new CEColor[10000000];
    public static AGCEStringList INSTANCE = new AGCEStringList();
    public boolean blockFinder = false;

    private AGCEStringList() {
    }

    public AGCEStringList(String name, String path, Collection list) {
        this.name = name;
        this.list = list;
        this.path = path;
        this.file = new File(Minecraft.getMinecraft().mcDataDir, "/config/ehacks/" + path);
        this.blockFinder = false;
        this.createFile();
        this.read();
    }

    public AGCEStringList(String name, String path, Collection list, CEColor[] colorList) {
        this.name = name;
        this.list = list;
        this.colorList = colorList;
        this.path = path;
        this.file = new File(Minecraft.getMinecraft().mcDataDir, "/config/ehacks/" + path);
        this.blockFinder = true;
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
            FileWriter filewriter = new FileWriter(file);
            BufferedWriter buffered = new BufferedWriter(filewriter);
            for (Object s : (CopyOnWriteArrayList)list) {
                Utils.removeDupes(list);
                buffered.write(s.toString() + "\r\n");
            }
            buffered.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void read() {
        Main.INSTANCE.logger.info("Reading //String// List config file: " + this.name);
        try {
            String s;
            FileInputStream imputstream = new FileInputStream(this.file.getAbsolutePath());
            DataInputStream datastream = new DataInputStream(imputstream);
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(datastream));
            while ((s = bufferedreader.readLine()) != null) {
                if (this.blockFinder) {
                    String[] strings = s.toLowerCase().trim().split("-");
                    this.list.add(Integer.parseInt(strings[0]));
                    this.colorList[Integer.parseInt((String)strings[0])] = new CEColor(Integer.parseInt(strings[1]), Integer.parseInt(strings[2]), Integer.parseInt(strings[3]));
                    continue;
                }
                this.list.add(Integer.parseInt(s.toLowerCase().trim()));
            }
            bufferedreader.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

