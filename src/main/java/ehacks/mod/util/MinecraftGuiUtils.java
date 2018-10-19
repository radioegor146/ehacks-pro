/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehacks.mod.util;

/**
 *
 * @author radioegor146
 */
public class MinecraftGuiUtils {

    public static void drawSlotBack(int x, int y) {
        GLUtils.drawRect(x, y, x + 18, y + 18, GLUtils.getColor(139, 139, 139));
        GLUtils.drawRect(x, y, x + 18, y + 1, GLUtils.getColor(55, 55, 55));
        GLUtils.drawRect(x, y, x + 1, y + 18, GLUtils.getColor(55, 55, 55));
        GLUtils.drawRect(x + 17, y, x + 18, y + 18, GLUtils.getColor(255, 255, 255));
        GLUtils.drawRect(x, y + 17, x + 18, y + 18, GLUtils.getColor(255, 255, 255));
        GLUtils.drawRect(x + 17, y, x + 18, y + 1, GLUtils.getColor(139, 139, 139));
        GLUtils.drawRect(x, y + 17, x + 1, y + 18, GLUtils.getColor(139, 139, 139));
    }

    public static void drawBack(int x, int y, int sX, int sY) {
        sX = Math.max(sX, 8);
        sY = Math.max(sY, 8);
        GLUtils.drawRect(x + 3, y + 3, x + sX - 3, y + sY - 3, GLUtils.getColor(198, 198, 198));
        GLUtils.drawRect(x + 3, y + 3, x + 4, y + 4, GLUtils.getColor(255, 255, 255));
        GLUtils.drawRect(x + sX - 4, y + sY - 4, x + sX - 3, y + sY - 3, GLUtils.getColor(85, 85, 85));
        GLUtils.drawRect(x + 1, y + 2, x + 3, y + sY - 3, GLUtils.getColor(255, 255, 255));
        GLUtils.drawRect(x + 2, y + 1, x + sX - 3, y + 3, GLUtils.getColor(255, 255, 255));
        GLUtils.drawRect(x + 2, y + sY - 3, x + 3, y + sY - 2, GLUtils.getColor(198, 198, 198));
        GLUtils.drawRect(x + sX - 3, y + 2, x + sX - 2, y + 3, GLUtils.getColor(198, 198, 198));
        GLUtils.drawRect(x + 3, y + sY - 3, x + sX - 2, y + sY - 1, GLUtils.getColor(85, 85, 85));
        GLUtils.drawRect(x + sX - 3, y + 3, x + sX - 1, y + sY - 2, GLUtils.getColor(85, 85, 85));

        GLUtils.drawRect(x + 2, y, x + sX - 3, y + 1, GLUtils.getColor(0, 0, 0));
        GLUtils.drawRect(x, y + 2, x + 1, y + sY - 3, GLUtils.getColor(0, 0, 0));
        GLUtils.drawRect(x + 1, y + 1, x + 2, y + 2, GLUtils.getColor(0, 0, 0));

        GLUtils.drawRect(x + sX - 1, y + 3, x + sX, y + sY - 2, GLUtils.getColor(0, 0, 0));
        GLUtils.drawRect(x + 3, y + sY - 1, x + sX - 2, y + sY, GLUtils.getColor(0, 0, 0));
        GLUtils.drawRect(x + sX - 2, y + sY - 2, x + sX - 1, y + sY - 1, GLUtils.getColor(0, 0, 0));

        GLUtils.drawRect(x + 1, y + sY - 3, x + 2, y + sY - 2, GLUtils.getColor(0, 0, 0));
        GLUtils.drawRect(x + 2, y + sY - 2, x + 3, y + sY - 1, GLUtils.getColor(0, 0, 0));

        GLUtils.drawRect(x + sX - 3, y + 1, x + sX - 2, y + 2, GLUtils.getColor(0, 0, 0));
        GLUtils.drawRect(x + sX - 2, y + 2, x + sX - 1, y + 3, GLUtils.getColor(0, 0, 0));
    }

    public static void drawInputField(int x, int y, int sX, int sY) {
        GLUtils.drawRect(x, y, x + sX - 1, y + 1, GLUtils.getColor(55, 55, 55));
        GLUtils.drawRect(x, y, x + 1, y + sY - 1, GLUtils.getColor(55, 55, 55));

        GLUtils.drawRect(x + 1, y + sY - 1, x + sX, y + sY, GLUtils.getColor(255, 255, 255));
        GLUtils.drawRect(x + sX - 1, y + 1, x + sX, y + sY, GLUtils.getColor(255, 255, 255));

        GLUtils.drawRect(x + 2, y + 2, x + sX - 2, y + sY - 2, GLUtils.getColor(160, 145, 114));

        GLUtils.drawRect(x + 1, y + 1, x + sX - 2, y + 2, GLUtils.getColor(224, 202, 159));
        GLUtils.drawRect(x + 1, y + 1, x + 2, y + sY - 2, GLUtils.getColor(224, 202, 159));

        GLUtils.drawRect(x + 2, y + sY - 2, x + sX - 1, y + sY - 1, GLUtils.getColor(84, 76, 59));
        GLUtils.drawRect(x + sX - 2, y + 2, x + sX - 1, y + sY - 1, GLUtils.getColor(84, 76, 59));

        GLUtils.drawRect(x + 1, y + sY - 2, x + 2, y + sY - 1, GLUtils.getColor(160, 145, 114));
        GLUtils.drawRect(x + sX - 2, y + 1, x + sX - 1, y + 2, GLUtils.getColor(160, 145, 114));
    }
}
