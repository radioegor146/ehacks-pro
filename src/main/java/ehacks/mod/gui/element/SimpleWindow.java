/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehacks.mod.gui.element;

import ehacks.mod.api.IIncludable;
import ehacks.mod.gui.EHacksClickGui;
import ehacks.mod.modulesystem.handler.EHacksGui;
import ehacks.mod.util.GLUtils;
import ehacks.mod.wrapper.Wrapper;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;

/**
 * @author radioegor146
 */
public class SimpleWindow implements IIncludable, IClickable {

    private final String title;
    public boolean canExtend = true;
    public boolean canPin = true;
    private int xPos;
    private int yPos;
    private boolean isOpen;
    private boolean isExtended;
    private boolean isPinned;
    private int prevXPos;
    private int prevYPos;
    private int dragX;
    private int dragY;
    private boolean dragging;
    private int width;
    private int height;

    public SimpleWindow(String title, int x, int y) {
        this.title = title;
        this.xPos = x;
        this.yPos = y;
    }

    public void windowDragged(int x, int y) {
        if (!dragging) {
            return;
        }
        this.xPos = this.prevXPos + (x - this.dragX);
        this.yPos = this.prevYPos + (y - this.dragY);
        ScaledResolution res = new ScaledResolution(Wrapper.INSTANCE.mc());
        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            if (this.xPos < 20) {
                this.xPos = 2;
            }
            if (this.yPos < 20) {
                this.yPos = 2;
            }
            if (this.xPos + this.width > res.getScaledWidth() - 20) {
                this.xPos = res.getScaledWidth() - this.width - 2;
            }
            if (this.yPos + this.height > res.getScaledHeight() - 20) {
                this.yPos = res.getScaledHeight() - this.height - 2;
            }
        }
    }

    public void draw(int x, int y) {
        if (this.dragging) {
            this.windowDragged(x, y);
        }
        if (this.isOpen) {
            if (x >= this.xPos && y >= this.yPos && x <= this.xPos + width && y <= this.yPos + 12) {
                EHacksClickGui.tooltip = null;
            }

            int borderColor = GLUtils.getColor(96, 96, 96);
            int backColor = GLUtils.getColor(128, 96, 96, 96);
            int buttonColor = GLUtils.getColor(192, 96, 96, 96);

            GLUtils.drawRect(this.xPos, this.yPos, this.xPos + width - 20, this.yPos + 12, backColor);
            GLUtils.drawRect(this.xPos + width - 20, this.yPos, this.xPos + width, this.yPos + 2, backColor);
            GLUtils.drawRect(this.xPos + width - 20, this.yPos + 10, this.xPos + width, this.yPos + 12, backColor);
            GLUtils.drawRect(this.xPos + width - 12, this.yPos + 2, this.xPos + width - 10, this.yPos + 10, backColor);
            GLUtils.drawRect(this.xPos + width - 2, this.yPos + 2, this.xPos + width, this.yPos + 10, backColor);
            if (this.isExtended || !canExtend) {
                GLUtils.drawRect(this.xPos, this.yPos + 12, this.xPos + width, this.yPos + height, backColor);
                GLUtils.drawGradientBorderedRect(this.xPos, this.yPos, this.xPos + width, this.yPos + height, 0.5f, borderColor, -812017255, 0);
            } else {
                GLUtils.drawGradientBorderedRect(this.xPos, this.yPos, this.xPos + width, this.yPos + 12, 0.5f, borderColor, -812017255, 0);
            }
            Wrapper.INSTANCE.fontRenderer().drawStringWithShadow(this.title, this.xPos + 2, 2 + this.yPos, EHacksClickGui.mainColor);
            if (canPin) {
                GLUtils.drawGradientBorderedRect(this.xPos + width - 20, this.yPos + 2, this.xPos + width - 12, this.yPos + 10, 1.0f, borderColor, this.isPinned ? buttonColor : 0, this.isPinned ? buttonColor : 0);
            } else {
                GLUtils.drawRect(this.xPos + width - 20, this.yPos + 2, this.xPos + width - 12, this.yPos + 10, backColor);
            }
            if (canExtend) {
                GLUtils.drawGradientBorderedRect(this.xPos + width - 10, this.yPos + 2, this.xPos + width - 2, this.yPos + 10, 1.0f, borderColor, this.isExtended ? buttonColor : 0, this.isExtended ? buttonColor : 0);
            } else {
                GLUtils.drawRect(this.xPos + width - 10, this.yPos + 2, this.xPos + width - 2, this.yPos + 10, backColor);
            }
            if (this.isExtended || !canExtend) {
                if (x >= this.xPos && y >= this.yPos + 14 && x <= this.xPos + width + 3 && y <= this.yPos + height) {
                    EHacksClickGui.tooltip = null;
                }
            }
        }
        //GLUtils.drawBorderedRect(getClientX(), getClientY(), getClientX() + getClientWidth(), getClientY() + getClientHeight(), 1, GLUtils.getColor(192, 255, 255, 0), GLUtils.getColor(100, 255, 255, 0));
    }

    public boolean mouseClicked(int x, int y, int button) {
        boolean retval = false;
        if (canPin && x >= this.xPos + width - 20 && y >= this.yPos + 2 && x <= this.xPos + width - 12 && y <= this.yPos + 10) {
            boolean bl = this.isPinned = !this.isPinned;
            retval = true;
        }
        if (canExtend && x >= this.xPos + width - 10 && y >= this.yPos + 2 && x <= this.xPos + width - 2 && y <= this.yPos + 10) {
            boolean bl = this.isExtended = !this.isExtended;
            retval = true;
        }
        if (x >= this.xPos && y >= this.yPos && x <= this.xPos + width - 19 && y <= this.yPos + 12) {
            EHacksGui.clickGui.sendPanelToFront(this);
            this.dragging = !this.dragging;
            this.prevXPos = this.xPos;
            this.prevYPos = this.yPos;
            this.dragX = x;
            this.dragY = y;
            retval = true;
        }
        return retval;
    }

    public void mouseReleased(int mouseX, int mouseY, int state) {
        this.dragging = false;
    }

    public void mouseMovedOrUp(int x, int y, int b2) {
        if (b2 == 0) {
            this.dragging = false;
        }
    }

    public String getTitle() {
        return this.title;
    }

    public int getX() {
        return this.xPos;
    }

    public int getY() {
        return this.yPos;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getClientX() {
        return this.xPos + 1;
    }

    public int getClientY() {
        return this.yPos + 13;
    }

    public int getClientWidth() {
        return this.width - 2;
    }

    public int getClientHeight() {
        return this.height - 15;
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void setClientSize(int cWidth, int cHeight) {
        this.width = cWidth + 2;
        this.height = cHeight + 15;
    }

    public boolean isExtended() {
        return this.isExtended;
    }

    public void setExtended(boolean flag) {
        this.isExtended = flag;
    }

    public boolean isOpen() {
        return this.isOpen;
    }

    public void setOpen(boolean flag) {
        this.isOpen = flag;
    }

    public boolean isPinned() {
        return !this.dragging && this.isPinned;
    }

    public void setPinned(boolean flag) {
        this.isPinned = flag;
    }

    public void setPosition(int x, int y) {
        this.xPos = x;
        this.yPos = y;
    }

    @Override
    public boolean shouldInclude() {
        return true;
    }

    @Override
    public void onButtonClick() {
        this.setOpen(!this.isOpen);
    }
}
