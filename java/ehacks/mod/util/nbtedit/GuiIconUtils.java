/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehacks.mod.util.nbtedit;

import ehacks.mod.util.GLUtils;

/**
 *
 * @author radioegor146
 */
public class GuiIconUtils {
    
    public static void drawButtonBack(int x, int y) {
        GLUtils.drawRect(x + 2, y + 2, x + 17, y + 17, GLUtils.getColor(139, 139, 139));
        
        GLUtils.drawRect(x + 3, y + 1, x + 16, y + 2, GLUtils.getColor(0, 0, 0));
        GLUtils.drawRect(x + 1, y + 3, x + 2, y + 16, GLUtils.getColor(0, 0, 0));
        GLUtils.drawRect(x + 3, y + 17, x + 16, y + 18, GLUtils.getColor(0, 0, 0));
        GLUtils.drawRect(x + 17, y + 3, x + 18, y + 16, GLUtils.getColor(0, 0, 0));
        
        GLUtils.drawRect(x + 2, y + 2, x + 3, y + 3, GLUtils.getColor(0, 0, 0));
        GLUtils.drawRect(x + 2, y + 16, x + 3, y + 17, GLUtils.getColor(0, 0, 0));
        GLUtils.drawRect(x + 16, y + 2, x + 17, y + 3, GLUtils.getColor(0, 0, 0));
        GLUtils.drawRect(x + 16, y + 16, x + 17, y + 17, GLUtils.getColor(0, 0, 0));
        
        GLUtils.drawRect(x + 4, y + 3, x + 15, y + 4, GLUtils.getColor(0, 0, 0));
        GLUtils.drawRect(x + 3, y + 4, x + 4, y + 15, GLUtils.getColor(0, 0, 0));
        GLUtils.drawRect(x + 4, y + 15, x + 15, y + 16, GLUtils.getColor(0, 0, 0));
        GLUtils.drawRect(x + 15, y + 4, x + 16, y + 15, GLUtils.getColor(0, 0, 0));
    }
    
    public static int[] colors = new int[] { 
        GLUtils.getColor(97, 154, 54),
        GLUtils.getColor(76, 76, 76),
        GLUtils.getColor(255, 108, 106),
        GLUtils.getColor(247, 146, 87),
        GLUtils.getColor(215, 202, 55),
        GLUtils.getColor(251, 97, 255),
        GLUtils.getColor(225, 102, 255),
        GLUtils.getColor(71, 225, 231),
        GLUtils.getColor(81, 239, 78),
        GLUtils.getColor(114, 145, 255),
        GLUtils.getColor(183, 99, 80),
        
    };
    
    public static void drawButtonIcon(int x, int y, int id) {
        if (id < 11) {
            GLUtils.drawRect(x + 4, y + 4, x + 15, y + 15, colors[id]);
        }
        switch (id) {
            case 0:
                GLUtils.drawRect(x + 5, y + 5, x + 9, y + 9, GLUtils.getColor(255, 108, 106));
                GLUtils.drawRect(x + 5, y + 5, x + 9, y + 6, GLUtils.getColor(0, 0, 0));
                GLUtils.drawRect(x + 5, y + 5, x + 6, y + 9, GLUtils.getColor(0, 0, 0));
                GLUtils.drawRect(x + 8, y + 5, x + 9, y + 9, GLUtils.getColor(0, 0, 0));
                GLUtils.drawRect(x + 5, y + 8, x + 9, y + 9, GLUtils.getColor(0, 0, 0));
                
                GLUtils.drawRect(x + 10, y + 5, x + 14, y + 9, GLUtils.getColor(225, 102, 255));
                GLUtils.drawRect(x + 10, y + 5, x + 14, y + 6, GLUtils.getColor(0, 0, 0));
                GLUtils.drawRect(x + 10, y + 5, x + 11, y + 9, GLUtils.getColor(0, 0, 0));
                GLUtils.drawRect(x + 13, y + 5, x + 14, y + 9, GLUtils.getColor(0, 0, 0));
                GLUtils.drawRect(x + 10, y + 8, x + 14, y + 9, GLUtils.getColor(0, 0, 0));
                
                GLUtils.drawRect(x + 5, y + 10, x + 9, y + 14, GLUtils.getColor(215, 202, 55));
                GLUtils.drawRect(x + 5, y + 10, x + 9, y + 11, GLUtils.getColor(0, 0, 0));
                GLUtils.drawRect(x + 5, y + 10, x + 6, y + 14, GLUtils.getColor(0, 0, 0));
                GLUtils.drawRect(x + 8, y + 10, x + 9, y + 14, GLUtils.getColor(0, 0, 0));
                GLUtils.drawRect(x + 5, y + 13, x + 9, y + 14, GLUtils.getColor(0, 0, 0));
                
                GLUtils.drawRect(x + 10, y + 10, x + 14, y + 14, GLUtils.getColor(71, 225, 231));
                GLUtils.drawRect(x + 10, y + 10, x + 14, y + 11, GLUtils.getColor(0, 0, 0));
                GLUtils.drawRect(x + 10, y + 10, x + 11, y + 14, GLUtils.getColor(0, 0, 0));
                GLUtils.drawRect(x + 13, y + 10, x + 14, y + 14, GLUtils.getColor(0, 0, 0));
                GLUtils.drawRect(x + 10, y + 13, x + 14, y + 14, GLUtils.getColor(0, 0, 0));
                break;
            case 1:
                GLUtils.drawRect(x + 6, y + 6, x + 12, y + 7, GLUtils.getColor(0, 0, 0));
                GLUtils.drawRect(x + 6, y + 6, x + 7, y + 13, GLUtils.getColor(0, 0, 0));
                GLUtils.drawRect(x + 6, y + 9, x + 12, y + 10, GLUtils.getColor(0, 0, 0));
                GLUtils.drawRect(x + 6, y + 12, x + 12, y + 13, GLUtils.getColor(0, 0, 0));
                GLUtils.drawRect(x + 12, y + 7, x + 13, y + 9, GLUtils.getColor(0, 0, 0));
                GLUtils.drawRect(x + 12, y + 10, x + 13, y + 12, GLUtils.getColor(0, 0, 0));
                break;
            case 2:
                GLUtils.drawRect(x + 7, y + 6, x + 13, y + 7, GLUtils.getColor(0, 0, 0));
                GLUtils.drawRect(x + 6, y + 7, x + 7, y + 9, GLUtils.getColor(0, 0, 0));
                GLUtils.drawRect(x + 7, y + 9, x + 12, y + 10, GLUtils.getColor(0, 0, 0));
                GLUtils.drawRect(x + 12, y + 10, x + 13, y + 12, GLUtils.getColor(0, 0, 0));
                GLUtils.drawRect(x + 6, y + 12, x + 12, y + 13, GLUtils.getColor(0, 0, 0));
                break;
            case 3:
                GLUtils.drawRect(x + 6, y + 6, x + 13, y + 7, GLUtils.getColor(0, 0, 0));
                GLUtils.drawRect(x + 9, y + 7, x + 10, y + 12, GLUtils.getColor(0, 0, 0));
                GLUtils.drawRect(x + 6, y + 12, x + 13, y + 13, GLUtils.getColor(0, 0, 0));
                break;
            case 4:
                GLUtils.drawRect(x + 6, y + 6, x + 7, y + 12, GLUtils.getColor(0, 0, 0));
                GLUtils.drawRect(x + 6, y + 12, x + 13, y + 13, GLUtils.getColor(0, 0, 0));
                break;
            case 5:
                GLUtils.drawRect(x + 6, y + 6, x + 7, y + 13, GLUtils.getColor(0, 0, 0));
                GLUtils.drawRect(x + 7, y + 6, x + 13, y + 7, GLUtils.getColor(0, 0, 0));
                GLUtils.drawRect(x + 7, y + 9, x + 12, y + 10, GLUtils.getColor(0, 0, 0));
                break;
            case 6:
                GLUtils.drawRect(x + 6, y + 6, x + 7, y + 13, GLUtils.getColor(0, 0, 0));
                GLUtils.drawRect(x + 6, y + 6, x + 11, y + 7, GLUtils.getColor(0, 0, 0));
                GLUtils.drawRect(x + 6, y + 12, x + 11, y + 13, GLUtils.getColor(0, 0, 0));
                GLUtils.drawRect(x + 11, y + 7, x + 12, y + 8, GLUtils.getColor(0, 0, 0));
                GLUtils.drawRect(x + 11, y + 11, x + 12, y + 12, GLUtils.getColor(0, 0, 0));
                GLUtils.drawRect(x + 12, y + 8, x + 13, y + 11, GLUtils.getColor(0, 0, 0));
                break;
            case 7:
                GLUtils.drawRect(x + 6, y + 6, x + 13, y + 7, GLUtils.getColor(0, 0, 0));
                GLUtils.drawRect(x + 6, y + 7, x + 7, y + 8, GLUtils.getColor(0, 0, 0));
                GLUtils.drawRect(x + 12, y + 7, x + 13, y + 8, GLUtils.getColor(0, 0, 0));
                GLUtils.drawRect(x + 9, y + 7, x + 10, y + 12, GLUtils.getColor(0, 0, 0));
                GLUtils.drawRect(x + 8, y + 12, x + 11, y + 13, GLUtils.getColor(0, 0, 0));
                break;
            case 8:
                GLUtils.drawRect(x + 6, y + 6, x + 13, y + 7, GLUtils.getColor(0, 0, 0));
                GLUtils.drawRect(x + 6, y + 8, x + 13, y + 9, GLUtils.getColor(0, 0, 0));
                GLUtils.drawRect(x + 6, y + 10, x + 13, y + 11, GLUtils.getColor(0, 0, 0));
                GLUtils.drawRect(x + 6, y + 12, x + 13, y + 13, GLUtils.getColor(0, 0, 0));
                break;
            case 9:
                GLUtils.drawRect(x + 5, y + 5, x + 7, y + 6, GLUtils.getColor(0, 0, 0));
                GLUtils.drawRect(x + 5, y + 5, x + 6, y + 14, GLUtils.getColor(0, 0, 0));
                GLUtils.drawRect(x + 5, y + 13, x + 7, y + 14, GLUtils.getColor(0, 0, 0));
                GLUtils.drawRect(x + 12, y + 5, x + 14, y + 6, GLUtils.getColor(0, 0, 0));
                GLUtils.drawRect(x + 13, y + 5, x + 14, y + 14, GLUtils.getColor(0, 0, 0));
                GLUtils.drawRect(x + 12, y + 13, x + 14, y + 14, GLUtils.getColor(0, 0, 0));
                
                GLUtils.drawRect(x + 7, y + 7, x + 8, y + 12, GLUtils.getColor(0, 0, 0));
                GLUtils.drawRect(x + 7, y + 7, x + 11, y + 8, GLUtils.getColor(0, 0, 0));
                GLUtils.drawRect(x + 7, y + 9, x + 11, y + 10, GLUtils.getColor(0, 0, 0));
                GLUtils.drawRect(x + 7, y + 11, x + 11, y + 12, GLUtils.getColor(0, 0, 0));
                GLUtils.drawRect(x + 11, y + 8, x + 12, y + 9, GLUtils.getColor(0, 0, 0));
                GLUtils.drawRect(x + 11, y + 10, x + 12, y + 11, GLUtils.getColor(0, 0, 0));
                break;
            case 10:
                GLUtils.drawRect(x + 5, y + 5, x + 7, y + 6, GLUtils.getColor(0, 0, 0));
                GLUtils.drawRect(x + 5, y + 5, x + 6, y + 14, GLUtils.getColor(0, 0, 0));
                GLUtils.drawRect(x + 5, y + 13, x + 7, y + 14, GLUtils.getColor(0, 0, 0));
                GLUtils.drawRect(x + 12, y + 5, x + 14, y + 6, GLUtils.getColor(0, 0, 0));
                GLUtils.drawRect(x + 13, y + 5, x + 14, y + 14, GLUtils.getColor(0, 0, 0));
                GLUtils.drawRect(x + 12, y + 13, x + 14, y + 14, GLUtils.getColor(0, 0, 0));
                
                GLUtils.drawRect(x + 7, y + 7, x + 12, y + 8, GLUtils.getColor(0, 0, 0));
                GLUtils.drawRect(x + 7, y + 11, x + 12, y + 12, GLUtils.getColor(0, 0, 0));
                GLUtils.drawRect(x + 9, y + 8, x + 10, y + 11, GLUtils.getColor(0, 0, 0));
                break;
        }
    }
}
