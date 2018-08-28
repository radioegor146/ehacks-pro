/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehacks.mod.gui.element;

import ehacks.api.module.IIncludable;
import ehacks.mod.gui.EHacksClickGui;
import ehacks.mod.util.GLUtils;
import ehacks.mod.wrapper.Wrapper;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;

/**
 *
 * @author radioegor146
 */
public class SimpleWindow implements IIncludable, IClickable {

    private final String title;
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
    public boolean canExtend = true;
    public boolean canPin = true;
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
        ScaledResolution res = new ScaledResolution(Wrapper.INSTANCE.mc(), Wrapper.INSTANCE.mc().displayWidth, Wrapper.INSTANCE.mc().displayHeight);
        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            if (this.xPos < 20) {
                this.xPos = 2;
            }
            if (this.yPos < 20) {
                this.yPos = 2;
            }
            if (this.xPos + this.width > res.getScaledWidth() - 20) {
                this.xPos = (int) (res.getScaledWidth() - this.width - 2);
            }
            if (this.yPos + this.height > res.getScaledHeight() - 20) {
                this.yPos = (int) (res.getScaledHeight() - this.height - 2);
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
            GLUtils.drawGradientBorderedRect(this.xPos, this.yPos, this.xPos + width, this.yPos + 12, 0.5f, -16777216, -812017255, -814254217);
            Wrapper.INSTANCE.fontRenderer().drawStringWithShadow(this.title, this.xPos + 2, 2 + this.yPos, 5636095);
            if (canPin) {
                GLUtils.drawGradientBorderedRect(this.xPos + width - 20, this.yPos + 2, this.xPos + width - 12, this.yPos + 10, 1.0f, -10066330, this.isPinned ? -8947849 : -7829368, this.isPinned ? -11184811 : -10066330);
            }
            if (canExtend) {
                GLUtils.drawGradientBorderedRect(this.xPos + width - 10, this.yPos + 2, this.xPos + width - 2, this.yPos + 10, 1.0f, -10066330, this.isExtended ? -8947849 : -7829368, this.isExtended ? -11184811 : -10066330);
            }
            if (this.isExtended || !canExtend) {
                GLUtils.drawGradientBorderedRect(this.xPos, this.yPos + 14, this.xPos + width, this.yPos + height, 0.5f, -16777216, -812017255, -814254217);
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
            EHacksClickGui.sendPanelToFront(this);
            this.dragging = !this.dragging;
            this.prevXPos = this.xPos;
            this.prevYPos = this.yPos;
            this.dragX = x;
            this.dragY = y;
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

    public final int getWidth() {
        return this.width;
    }

    public final int getHeight() {
        return this.height;
    }

    public final int getClientX() {
        return this.xPos + 1;
    }

    public final int getClientY() {
        return this.yPos + 15;
    }

    public final int getClientWidth() {
        return this.width - 2;
    }

    public final int getClientHeight() {
        return this.height - 16;
    }

    public final void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public final void setClientSize(int cWidth, int cHeight) {
        this.width = cWidth + 2;
        this.height = cHeight + 16;
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
