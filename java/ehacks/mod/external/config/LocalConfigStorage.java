/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehacks.mod.external.config;

import ehacks.mod.gui.EHacksClickGui;
import ehacks.mod.wrapper.Wrapper;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 *
 * @author radioegor146
 */
public class LocalConfigStorage {

    public static void importConfig() {
        try {
            File cfgFiles = new File(System.getProperty("user.home") + File.separator + "ehackscfg.zip");
            ZipInputStream in = new ZipInputStream(new FileInputStream(cfgFiles));
            ZipEntry entry = null;
            byte[] buffer = new byte[2048];
            while ((entry = in.getNextEntry()) != null) {
                FileOutputStream output = null;
                output = new FileOutputStream(new File(Wrapper.INSTANCE.mc().mcDataDir, "/config/ehacks/" + entry.getName()));
                int len = 0;
                while ((len = in.read(buffer)) > 0) {
                    output.write(buffer, 0, len);
                }
                output.close();
            }
            for (IConfiguration config : ConfigurationManager.instance().configurations) {
                if (config.isConfigUnique()) {
                    continue;
                }
                config.read();
                config.write();
            }
            EHacksClickGui.log("[ConfigStorage] Local config has been imported");
        } catch (Exception e) {
            EHacksClickGui.log("[ConfigStorage] Can't import local config");
            e.printStackTrace();
        }
    }

    public static void exportConfig() {
        try {
            ConfigurationManager.instance().saveConfigs();
            File cfgFiles = new File(System.getProperty("user.home") + File.separator + "ehackscfg.zip");
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(cfgFiles));
            for (IConfiguration config : ConfigurationManager.instance().configurations) {
                if (config.isConfigUnique()) {
                    continue;
                }
                ZipEntry e = new ZipEntry(config.getConfigFilePath());
                out.putNextEntry(e);
                FileInputStream fis = new FileInputStream(new File(Wrapper.INSTANCE.mc().mcDataDir, "/config/ehacks/" + config.getConfigFilePath()));
                byte[] bytes = new byte[1024];
                int length;
                while ((length = fis.read(bytes)) >= 0) {
                    out.write(bytes, 0, length);
                }
                out.closeEntry();
            }
            out.close();
            EHacksClickGui.log("[ConfigStorage] Local config has been exported");
        } catch (Exception e) {
            EHacksClickGui.log("[ConfigStorage] Can't export local config");
            e.printStackTrace();
        }
    }
}
