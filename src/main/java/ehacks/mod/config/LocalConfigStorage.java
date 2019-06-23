/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehacks.mod.config;

import ehacks.mod.util.InteropUtils;
import ehacks.mod.wrapper.Wrapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * @author radioegor146
 */
public class LocalConfigStorage {

    public static void importConfig() {
        try {
            File cfgFiles = new File(System.getProperty("user.home") + File.separator + "ehackscfg.zip");
            ZipInputStream in = new ZipInputStream(new FileInputStream(cfgFiles));
            ZipEntry entry;
            byte[] buffer = new byte[2048];
            while ((entry = in.getNextEntry()) != null) {
                FileOutputStream output;
                output = new FileOutputStream(new File(Wrapper.INSTANCE.mc().mcDataDir, "/config/ehacks/" + entry.getName()));
                int len;
                while ((len = in.read(buffer)) > 0) {
                    output.write(buffer, 0, len);
                }
                output.close();
            }
            ConfigurationManager.instance().configurations.stream().filter((config) -> !(config.isConfigUnique())).map((config) -> {
                config.read();
                return config;
            }).forEachOrdered((config) -> {
                config.write();
            });
            InteropUtils.log("Local config has been imported", "ConfigStorage");
        } catch (Exception e) {
            InteropUtils.log("Can't import local config", "ConfigStorage");
        }
    }

    public static void exportConfig() {
        try {
            ConfigurationManager.instance().saveConfigs();
            File cfgFiles = new File(System.getProperty("user.home") + File.separator + "ehackscfg.zip");
            try (ZipOutputStream out = new ZipOutputStream(new FileOutputStream(cfgFiles))) {
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
            }
            InteropUtils.log("Local config has been exported", "ConfigStorage");
        } catch (Exception e) {
            InteropUtils.log("Can't export local config", "ConfigStorage");
        }
    }
}
