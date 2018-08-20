/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehacks.mod.gui.element;

import ehacks.api.module.DummyMod;
import ehacks.api.module.IIncludable;
import ehacks.api.module.Module;
import ehacks.mod.gui.EHacksClickGui;
import ehacks.mod.util.GLUtils;
import ehacks.mod.wrapper.Keybinds;
import java.util.ArrayList;
import java.util.HashSet;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

/**
 *
 * @author radioegor146
 */
public class SimpleWindow implements IIncludable {
    public String title;
    public int xPos;
    public int yPos;
    private boolean isOpen;
    public boolean isExtended;
    public boolean isPinned;
    public int dragX;
    public int dragY;
    public int lastDragX;
    public int lastDragY;
    protected boolean dragging;
    public boolean canExtend = true;
    public boolean canPin = true;
    public double width;
    public double height;
    
    public SimpleWindow(String title, int x, int y) {
        this.title = title;
        this.xPos = x;
        this.yPos = y;
        EHacksClickGui.windows.add(this);
        EHacksClickGui.unFocusedWindows.add(this);
    }
    
    public void windowDragged(int x, int y) {
        this.dragX = x - this.lastDragX;
        this.dragY = y - this.lastDragY;
    }

    public void draw(int x, int y) {
        if (this.isOpen) {
            if (x >= this.xPos + this.dragX && y >= this.yPos + this.dragY && x <= this.xPos + width + this.dragX && y <= this.yPos + 12 + this.dragY) {
                EHacksClickGui.tooltip = null;
            }
            if (this.dragging) {
                this.windowDragged(x, y);
            }
            GLUtils.drawGradientBorderedRect(this.xPos + this.dragX, this.yPos + this.dragY, this.xPos + 3 + this.dragX + width, this.yPos + 12 + this.dragY, 0.5f, -16777216, -812017255, -814254217);
            Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(this.title, this.xPos + 2 + this.dragX, 2 + this.yPos + this.dragY, 5636095);
            if (canPin)
                GLUtils.drawGradientBorderedRect(this.xPos + width - 18 + this.dragX, this.yPos + 2 + this.dragY, this.xPos + width - 10 + this.dragX, this.yPos + 10 + this.dragY, 1.0f, -10066330, this.isPinned ? -8947849 : -7829368, this.isPinned ? -11184811 : -10066330);
            if (canExtend)
                GLUtils.drawGradientBorderedRect(this.xPos + width - 8 + this.dragX, this.yPos + 2 + this.dragY, this.xPos + width + this.dragX, this.yPos + 10 + this.dragY, 1.0f, -10066330, this.isExtended ? -8947849 : -7829368, this.isExtended ? -11184811 : -10066330);
            if (this.isExtended || !canExtend) {
                GLUtils.drawGradientBorderedRect(this.xPos + this.dragX, this.yPos + 14 + this.dragY, this.xPos + 3 + this.dragX + width, this.yPos + height + this.dragY + 14, 0.5f, -16777216, -812017255, -814254217);
                if (x >= this.xPos + this.dragX && y >= this.yPos + this.dragY + 14 && x <= this.xPos + width + this.dragX + 3 && y <= this.yPos + 14 + this.dragY + height) {
                EHacksClickGui.tooltip = null;
            }
            }
        }
    }

    public boolean mouseClicked(int x, int y, int button) {
        boolean retval = false;
        if (canExtend && x >= this.xPos + width - 8 + this.dragX && y >= this.yPos + 2 + this.dragY && x <= this.xPos + width + this.dragX && y <= this.yPos + 10 + this.dragY) {
            boolean bl = this.isExtended = !this.isExtended;
            retval = true;
        }
        if (canPin && x >= this.xPos + width - 18 + this.dragX && y >= this.yPos + 2 + this.dragY && x <= this.xPos + width - 10 + this.dragX && y <= this.yPos + 10 + this.dragY) {
            boolean bl = this.isPinned = !this.isPinned;
            retval = true;
        }
        if (x >= this.xPos + this.dragX && y >= this.yPos + this.dragY && x <= this.xPos + width - 19 + this.dragX && y <= this.yPos + 12 + this.dragY) {
            EHacksClickGui.sendPanelToFront(this);
            this.dragging = !this.dragging;
            this.lastDragX = x - this.dragX;
            this.lastDragY = y - this.dragY;
            retval = true;
        }
        return retval;
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

    @Override
    public boolean shouldInclude() {
        return true;
    }
    
    public void init() {
        
    }
}
