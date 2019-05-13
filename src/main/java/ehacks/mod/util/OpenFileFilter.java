/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehacks.mod.util;

import java.io.File;
import javax.swing.filechooser.FileFilter;

public class OpenFileFilter
        extends FileFilter {

    String extension;
    String description;

    public OpenFileFilter(String extension, String description) {
        this.extension = extension;
        this.description = description;
    }

    @Override
    public boolean accept(File file) {
        if (file != null) {
            if (file.isDirectory()) {
                return true;
            }
            if (this.extension == null) {
                return this.extension.length() == 0;
            }
            return file.getName().endsWith(this.extension);
        }
        return false;
    }

    @Override
    public String getDescription() {
        return this.description;
    }
}
