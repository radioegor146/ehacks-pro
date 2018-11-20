/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehacks.mod.util;

import ehacks.mod.modulesystem.handler.EHacksGui;
import org.lwjgl.input.Keyboard;

/**
 *
 * @author radioegor146
 */
public class InteropUtils {

    public static void log(String data, Object from) {
        EHacksGui.clickGui.log(data, from);
    }

    public static boolean isKeyDown(int key) {
        if (key == 0) {
            return false;
        }
        return Keyboard.isKeyDown(key);
    }
}
