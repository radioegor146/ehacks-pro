package ehacks.mod.gui.reeszrbteam.window;

import net.minecraft.client.Minecraft;
import ehacks.mod.gui.reeszrbteam.YouAlwaysWinClickGui;
import ehacks.mod.gui.reeszrbteam.element.YAWWindow;
import ehacks.mod.util.GLUtils;
import java.util.ArrayList;

public class WindowHub
extends YAWWindow {
    public WindowHub() {
        super("Gui Hub", 2, 2);
        this.buttons = new ArrayList();
        this.buttons.add(new ArrayList());
    }

    @Override
    public void draw(int x, int y) {
        GLUtils.drawGradientBorderedRect(this.getX() + this.dragX, this.getY() + this.dragY, this.getX() + 3 + this.dragX + this.buttons.size() * 88, this.getY() + 12 + this.dragY, 0.5f, -16777216, -812017255, -814254217);
        Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(this.title, this.getX() + 2 + this.dragX, 2 + this.getY() + this.dragY, 5636095);
        GLUtils.drawGradientBorderedRect(this.getX() + this.buttons.size() * 88 - 18 + this.dragX, this.getY() + 2 + this.dragY, this.getX() + this.buttons.size() * 88 - 10 + this.dragX, this.getY() + 10 + this.dragY, 1.0f, -10066330, this.isPinned ? -8947849 : -7829368, this.isPinned ? -11184811 : -10066330);
        GLUtils.drawGradientBorderedRect(this.getX() + this.buttons.size() * 88 - 8 + this.dragX, this.getY() + 2 + this.dragY, this.getX() + this.buttons.size() * 88 + this.dragX, this.getY() + 10 + this.dragY, 1.0f, -10066330, this.isExtended ? -8947849 : -7829368, this.isExtended ? -11184811 : -10066330);
        if (this.dragging) {
            this.windowDragged(x, y);
        }
        if (this.isExtended)
        {
            GLUtils.drawGradientBorderedRect(this.getX() + this.dragX, this.getY() + 14 + this.dragY, this.getX() + 90 + this.dragX, YouAlwaysWinClickGui.unFocusedWindows.size() * 11 - 11 + 19 + this.dragY + this.getY(), 0.5f, -16777216, -812017255, -814254217);
            int size = 0;
            for (YAWWindow window : YouAlwaysWinClickGui.unFocusedWindows) {
                if (window.getTitle().equalsIgnoreCase(this.getTitle())) continue;

                GLUtils.drawGradientBorderedRect(
                        (double)this.getX() + 2 + 0.5 + (double)this.dragX, 
                        (double)this.getY() + 11 * size + 16 + 0.5 + (double)this.dragY, 
                        (double)this.getY() + 2 + 85.5 + (double)this.dragX, 
                        this.getY() + 11 * size + 16 + 12 + this.dragY, 
                0.5f, -12303292, !window.isOpen() ? -8947849 : -11184811, !window.isOpen() ? -11184811 : -10066330);
                Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(window.getTitle(), this.getX() + 2 + this.dragX - (this.getX() + 2 + 85 + this.dragX) - Minecraft.getMinecraft().fontRenderer.getStringWidth(window.getTitle()) / 2 + 127 + this.getX() + 2 + this.dragX, this.getY() + 11 * size + 16 + 2 + this.dragY, window.isOpen() ? 5636095 : 12303291);

                ++size;
            }
        }
    }

    @Override
    public void mouseClicked(int x, int y, int button) {
        super.mouseClicked(x, y, button);
        int size = 0;
        for (YAWWindow window : YouAlwaysWinClickGui.unFocusedWindows) {
            if (window.getTitle().equalsIgnoreCase(this.getTitle())) continue;
            if (x >= ((double)this.getX() + 2 + 0.5 + (double)this.dragX) && y >= ((double)this.getY() + 11 * size + 16 + 0.5 + (double)this.dragY) && x <= ((double)this.getY() + 2 + 85.5 + (double)this.dragX) && y <= (this.getY() + 11 * size + 16 + 12 + this.dragY)) {
                window.setOpen(!window.isOpen());
            }
            ++size;
        }
    }
}

