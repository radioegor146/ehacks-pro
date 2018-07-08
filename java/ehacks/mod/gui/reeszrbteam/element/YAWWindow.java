/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 */
package ehacks.mod.gui.reeszrbteam.element;

import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import ehacks.api.module.Mod;
import ehacks.mod.gui.reeszrbteam.YouAlwaysWinClickGui;
import ehacks.mod.gui.reeszrbteam.element.YAWButton;
import ehacks.mod.util.GLUtils;

public class YAWWindow {
    private String title;
    public int xPos;
    public int yPos;
    private boolean isOpen;
    private boolean isExtended;
    private boolean isPinned;
    public int dragX;
    public int dragY;
    public int lastDragX;
    public int lastDragY;
    protected boolean dragging;
    public ArrayList<YAWButton> buttons = new ArrayList();
    private int buttonCount = 0;
    private int sliderCount = 0;

    public YAWWindow(String title, int x, int y) {
        this.title = title;
        this.xPos = x;
        this.yPos = y;
        YouAlwaysWinClickGui.windows.add(this);
        YouAlwaysWinClickGui.unFocusedWindows.add(this);
    }

    public void windowDragged(int x, int y) {
        this.dragX = x - this.lastDragX;
        this.dragY = y - this.lastDragY;
    }

    public void addButton(Mod mod) {
        this.buttons.add(new YAWButton(this, mod, this.xPos + 2, this.yPos + 11 * this.buttons.size() + 16));
    }

    public void draw(int x, int y) {
        if (this.isOpen) {
            if (this.dragging) {
                this.windowDragged(x, y);
            }
            GLUtils.drawGradientBorderedRect(this.xPos + this.dragX, this.yPos + this.dragY, this.xPos + 90 + this.dragX, this.yPos + 12 + this.dragY, 0.5f, -16777216, -812017255, -814254217);
            Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(this.title, this.xPos + 2 + this.dragX, 2 + this.yPos + this.dragY, 5636095);
            GLUtils.drawGradientBorderedRect(this.xPos + 70 + this.dragX, this.yPos + 2 + this.dragY, this.xPos + 78 + this.dragX, this.yPos + 10 + this.dragY, 1.0f, -10066330, this.isPinned ? -8947849 : -7829368, this.isPinned ? -11184811 : -10066330);
            GLUtils.drawGradientBorderedRect(this.xPos + 80 + this.dragX, this.yPos + 2 + this.dragY, this.xPos + 88 + this.dragX, this.yPos + 10 + this.dragY, 1.0f, -10066330, this.isExtended ? -8947849 : -7829368, this.isExtended ? -11184811 : -10066330);
            if (this.isExtended) {
                GLUtils.drawGradientBorderedRect(this.xPos + this.dragX, this.yPos + 14 + this.dragY, this.xPos + 90 + this.dragX, this.yPos + (11 * this.buttons.size() + 19) + this.dragY, 0.5f, -16777216, -812017255, -814254217);
                for (YAWButton button : this.buttons) {
                    button.draw();
                    if (x >= button.getX() + this.dragX && y >= button.getY() + 1 + this.dragY && x <= button.getX() + 23 + this.dragX && y <= button.getY() + 10 + this.dragY) {
                        button.isOverButton = true;
                        continue;
                    }
                    button.isOverButton = false;
                }
            }
        }
    }

    public void mouseClicked(int x, int y, int button) {
        for (YAWButton xButton : this.buttons) {
            xButton.mouseClicked(x, y, button);
        }
        if (x >= this.xPos + 80 + this.dragX && y >= this.yPos + 2 + this.dragY && x <= this.xPos + 88 + this.dragX && y <= this.yPos + 10 + this.dragY) {
            boolean bl = this.isExtended = !this.isExtended;
        }
        if (x >= this.xPos + 70 + this.dragX && y >= this.yPos + 2 + this.dragY && x <= this.xPos + 78 + this.dragX && y <= this.yPos + 10 + this.dragY) {
            boolean bl = this.isPinned = !this.isPinned;
        }
        if (x >= this.xPos + this.dragX && y >= this.yPos + this.dragY && x <= this.xPos + 69 + this.dragX && y <= this.yPos + 12 + this.dragY) {
            YouAlwaysWinClickGui.sendPanelToFront(this);
            this.dragging = !this.dragging;
            this.lastDragX = x - this.dragX;
            this.lastDragY = y - this.dragY;
        }
    }

    public void mouseMovedOrUp(int x, int y, int b2) {
        if (b2 == 0) {
            this.dragging = false;
        }
    }

    public final String getTitle() {
        return this.title;
    }

    public final int getX() {
        return this.xPos;
    }

    public final int getY() {
        return this.yPos;
    }

    public boolean isExtended() {
        return this.isExtended;
    }

    public boolean isOpen() {
        return this.isOpen;
    }

    public boolean isPinned() {
        return this.dragging ? false : this.isPinned;
    }

    public void setOpen(boolean flag) {
        this.isOpen = flag;
    }

    public void setExtended(boolean flag) {
        this.isExtended = flag;
    }

    public void setPinned(boolean flag) {
        this.isPinned = flag;
    }

    public void setX(int xPos) {
        this.dragX = xPos;
    }

    public void setY(int yPos) {
        this.dragY = yPos;
    }
}

