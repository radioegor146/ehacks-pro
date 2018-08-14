package ehacks.mod.gui.window;

import net.minecraft.client.Minecraft;
import ehacks.mod.gui.element.ModButton;
import ehacks.mod.gui.element.ModWindow;
import ehacks.mod.gui.element.SimpleWindow;
import ehacks.mod.util.GLUtils;
import java.util.ArrayList;

public class WindowInfo
extends SimpleWindow {
    public WindowInfo() {
        super("Info", 554, 2);
        this.width = 88;
    }

    @Override
    public void draw(int x, int y) {
        if (this.isOpen()) {
            if (this.dragging) {
                this.windowDragged(x, y);
            }
            if (this.isExtended)
            {
                this.height = 62 - 14;
                super.draw(x, y);
                Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(Minecraft.getMinecraft().debug.split(",")[0].toUpperCase(), this.getX() + 2 + this.dragX, this.getY() + 16 + this.dragY, 5636095);
                Minecraft.getMinecraft().fontRenderer.drawStringWithShadow("X: " + (int)Minecraft.getMinecraft().thePlayer.posX, this.getX() + 2 + this.dragX, this.getY() + 28 + this.dragY, 5636095);
                Minecraft.getMinecraft().fontRenderer.drawStringWithShadow("Y: " + (int)Minecraft.getMinecraft().thePlayer.posY, this.getX() + 2 + this.dragX, this.getY() + 40 + this.dragY, 5636095);
                Minecraft.getMinecraft().fontRenderer.drawStringWithShadow("Z: " + (int)Minecraft.getMinecraft().thePlayer.posZ, this.getX() + 2 + this.dragX, this.getY() + 52 + this.dragY, 5636095);
            }
            else
                super.draw(x, y);
        }
    }
}

