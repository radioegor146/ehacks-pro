package ehacks.mod.gui.element;

import ehacks.mod.gui.EHacksClickGui;
import ehacks.mod.modulesystem.handler.EHacksGui;
import ehacks.mod.util.GLUtils;
import ehacks.mod.wrapper.Wrapper;

public class SimpleButton {

    private final SimpleWindow window;
    private final IClickable handler;
    private final String title;
    private final int color;
    private int sizeX;
    private int sizeY;
    private int xPos;
    private int yPos;
    private boolean pressed;

    public SimpleButton(SimpleWindow window, IClickable handler, String title, int color, int xPos, int yPos, int sizeX, int sizeY) {
        this.window = window;
        this.handler = handler;
        this.xPos = xPos;
        this.yPos = yPos;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.color = color;
        this.title = title;
    }

    public void setState(boolean state) {
        pressed = state;
    }

    public void draw() {
        GLUtils.drawGradientBorderedRect(this.getX(), this.getY(), (double) this.getX() + sizeX, this.getY() + sizeY, 0.5f, GLUtils.getColor(96, 96, 96), !pressed ? 0 : GLUtils.getColor(64, 128, 128, 128), !pressed ? GLUtils.getColor(32, 96, 96, 96) : GLUtils.getColor(96, 0, 255, 0));
        Wrapper.INSTANCE.fontRenderer().drawStringWithShadow(this.title, this.getX() + sizeX / 2 - Wrapper.INSTANCE.fontRenderer().getStringWidth(this.title) / 2, this.getY() + (this.sizeY / 2 - Wrapper.INSTANCE.fontRenderer().FONT_HEIGHT / 2), pressed ? EHacksClickGui.mainColor : color);
    }

    public boolean isMouseOver(int x, int y) {
        return (x >= this.getX() && y >= this.getY() && x <= this.getX() + this.sizeX && y <= this.getY() + sizeY && this.window.isOpen() && this.window.isExtended());
    }

    public boolean mouseClicked(int x, int y, int button) {
        if (x >= this.getX() && y >= this.getY() && x <= this.getX() + sizeX && y <= this.getY() + sizeY && button == 0 && this.window.isOpen() && this.window.isExtended()) {
            EHacksGui.clickGui.sendPanelToFront(this.window);
            if (this.handler != null) {
                this.handler.onButtonClick();
            }
            return true;
        }
        return false;
    }

    public int getX() {
        return this.xPos + this.window.getClientX();
    }

    public int getY() {
        return this.yPos + this.window.getClientY();
    }

    public void setSize(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    public void setPosition(int x, int y) {
        this.xPos = x;
        this.yPos = y;
    }

    public IClickable getHandler() {
        return handler;
    }
}
