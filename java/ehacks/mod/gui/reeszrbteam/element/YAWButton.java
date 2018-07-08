/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 */
package ehacks.mod.gui.reeszrbteam.element;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import ehacks.api.module.Mod;
import ehacks.mod.gui.reeszrbteam.YouAlwaysWinClickGui;
import ehacks.mod.gui.reeszrbteam.element.YAWWindow;
import ehacks.mod.util.GLUtils;

public class YAWButton {
    private YAWWindow window;
    private Mod mod;
    private int xPos;
    private int yPos;
    public boolean isOverButton;

    public YAWButton(YAWWindow window, Mod mod, int xPos, int yPos) {
        this.window = window;
        this.mod = mod;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public void draw() {
        GLUtils.drawGradientBorderedRect((double)this.getX() + 0.5 + (double)this.window.dragX, (double)this.getY() + 0.5 + (double)this.window.dragY, (double)this.getX() + 85.5 + (double)this.window.dragX, this.getY() + 12 + this.window.dragY, 0.5f, -12303292, !this.mod.isActive() ? -8947849 : -11184811, !this.mod.isActive() ? -11184811 : -10066330);
        Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(this.mod.getName(), this.getX() + this.window.dragX - (this.getX() + 85 + this.window.dragX) - Minecraft.getMinecraft().fontRenderer.getStringWidth(this.mod.getName()) / 2 + 127 + this.getX() + this.window.dragX, this.getY() + 2 + this.window.dragY, this.mod.isActive() ? 5636095 : 12303291);
    }

    public void mouseClicked(int x, int y, int button) {
        if (x >= this.getX() + this.window.dragX && y >= this.getY() + this.window.dragY && (double)x <= (double)this.getX() + 85.5 + (double)this.window.dragX && y <= this.getY() + 9 + this.window.dragY && button == 0 && this.window.isOpen() && this.window.isExtended()) {
            YouAlwaysWinClickGui.sendPanelToFront(this.window);
            this.mod.toggle();
        }
    }

    public int getX() {
        return this.xPos;
    }

    public int getY() {
        return this.yPos;
    }
}

