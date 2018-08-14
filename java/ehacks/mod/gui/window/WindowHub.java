package ehacks.mod.gui.window;

import net.minecraft.client.Minecraft;
import ehacks.mod.gui.EHacksClickGui;
import ehacks.mod.gui.element.ModWindow;
import ehacks.mod.gui.element.SimpleWindow;
import ehacks.mod.util.GLUtils;
import java.util.ArrayList;

public class WindowHub
extends SimpleWindow {
    public WindowHub() {
        super("Gui Hub", 2, 2);
        this.width = 88;
        this.height = EHacksClickGui.unFocusedWindows.size() * 11;
    }

    @Override
    public void draw(int x, int y) {
        if (this.dragging) {
            this.windowDragged(x, y);
        }
        if (this.isExtended)
        {
            this.width = 88;
            this.height = EHacksClickGui.unFocusedWindows.size() * 11 - 5.5;
            super.draw(x, y);
            int size = 0;
            for (SimpleWindow window : EHacksClickGui.unFocusedWindows) {
                if (window.getTitle().equalsIgnoreCase(this.getTitle())) continue;

                GLUtils.drawGradientBorderedRect(
                        (double)this.getX() + 2 + 0.5 + (double)this.dragX, 
                        (double)this.getY() + 11 * size + 16 + 0.5 + (double)this.dragY, 
                        (double)this.getY() + 2 + 86.5 + (double)this.dragX, 
                        this.getY() + 11 * size + 16 + 12 + this.dragY, 
                0.5f, -12303292, !window.isOpen() ? -8947849 : -11184811, !window.isOpen() ? -11184811 : -10066330);
                Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(window.getTitle(), this.getX() + 2 + this.dragX + 86 / 2 - Minecraft.getMinecraft().fontRenderer.getStringWidth(window.getTitle()) / 2, this.getY() + 11 * size + 16 + 2 + this.dragY, window.isOpen() ? 5636095 : 12303291);

                ++size;
            }
        }
        else
            super.draw(x, y);
    }

    @Override
    public boolean mouseClicked(int x, int y, int button) {
        boolean retval = super.mouseClicked(x, y, button);
        int size = 0;
        for (SimpleWindow window : EHacksClickGui.unFocusedWindows) {
            if (window.getTitle().equalsIgnoreCase(this.getTitle())) continue;
            if (x >= ((double)this.getX() + 2 + 0.5 + (double)this.dragX) && y >= ((double)this.getY() + 11 * size + 16 + 0.5 + (double)this.dragY) && x <= ((double)this.getY() + 2 + 86.5 + (double)this.dragX) && y <= (this.getY() + 11 * size + 16 + 12 + this.dragY)) {
                window.setOpen(!window.isOpen());
                retval = true;
            }
            ++size;
        }
        return retval;
    }
}

