/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehacks.mod.util;

import ehacks.mod.modulesystem.handler.EHacksGui;

/**
 *
 * @author radioegor146
 */
public class InteropUtils {
    public static void log(String data, Object from) {
        EHacksGui.clickGui.log(data, from);
    }
}
