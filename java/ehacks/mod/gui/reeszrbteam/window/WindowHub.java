/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 */
package ehacks.mod.gui.reeszrbteam.window;

import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import ehacks.mod.gui.reeszrbteam.YouAlwaysWinClickGui;
import ehacks.mod.gui.reeszrbteam.element.YAWWindow;
import ehacks.mod.util.GLUtils;

public class WindowHub
extends YAWWindow {
    public WindowHub() {
        super("Gui Hub", 2, 2);
    }

    @Override
    public void draw(int x, int y) {
        super.draw(x, y);
        GLUtils.drawGradientBorderedRect(this.xPos + this.dragX, this.yPos + 14 + this.dragY, this.xPos + 90 + this.dragX, YouAlwaysWinClickGui.unFocusedWindows.size() * 11 - 11 + 19 + this.dragY + this.yPos, 0.5f, -16777216, -812017255, -814254217);
        int size = 0;
        for (YAWWindow window : YouAlwaysWinClickGui.unFocusedWindows) {
            if (window.getTitle().equalsIgnoreCase(this.getTitle())) continue;
            
            GLUtils.drawGradientBorderedRect(
                    (double)this.xPos + 2 + 0.5 + (double)this.dragX, 
                    (double)this.yPos + 11 * size + 16 + 0.5 + (double)this.dragY, 
                    (double)this.yPos + 2 + 85.5 + (double)this.dragX, 
                    this.yPos + 11 * size + 16 + 12 + this.dragY, 
            0.5f, -12303292, !window.isOpen() ? -8947849 : -11184811, !window.isOpen() ? -11184811 : -10066330);
            Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(window.getTitle(), this.xPos + 2 + this.dragX - (this.xPos + 2 + 85 + this.dragX) - Minecraft.getMinecraft().fontRenderer.getStringWidth(window.getTitle()) / 2 + 127 + this.xPos + 2 + this.dragX, this.yPos + 11 * size + 16 + 2 + this.dragY, window.isOpen() ? 5636095 : 12303291);
            
            ++size;
        }
    }

    @Override
    public void mouseClicked(int x, int y, int button) {
        super.mouseClicked(x, y, button);
        int size = 0;
        for (YAWWindow window : YouAlwaysWinClickGui.unFocusedWindows) {
            if (window.getTitle().equalsIgnoreCase(this.getTitle())) continue;
            if (x >= ((double)this.xPos + 2 + 0.5 + (double)this.dragX) && y >= ((double)this.yPos + 11 * size + 16 + 0.5 + (double)this.dragY) && x <= ((double)this.yPos + 2 + 85.5 + (double)this.dragX) && y <= (this.yPos + 11 * size + 16 + 12 + this.dragY)) {
                window.setOpen(!window.isOpen());
            }
            ++size;
        }
    }
}

