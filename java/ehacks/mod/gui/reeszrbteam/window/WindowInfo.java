package ehacks.mod.gui.reeszrbteam.window;

import net.minecraft.client.Minecraft;
import ehacks.mod.gui.reeszrbteam.element.YAWButton;
import ehacks.mod.gui.reeszrbteam.element.YAWWindow;
import ehacks.mod.util.GLUtils;
import java.util.ArrayList;

public class WindowInfo
extends YAWWindow {
    public WindowInfo() {
        super("Info", 554, 2);
        this.buttons = new ArrayList();
        this.buttons.add(new ArrayList());
    }

    @Override
    public void draw(int x, int y) {
        if (this.isOpen()) {
            if (this.dragging) {
                this.windowDragged(x, y);
            }
            GLUtils.drawGradientBorderedRect(this.getX() + this.dragX, this.getY() + this.dragY, this.getX() + 90 + this.dragX, this.getY() + 12 + this.dragY, 0.5f, -16777216, -6710887, -8947849);
            Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(this.getTitle(), this.getX() + 2 + this.dragX, this.getY() + this.dragY + 2, 5636095);
            GLUtils.drawGradientBorderedRect(this.getX() + 70 + this.dragX, this.getY() + 2 + this.dragY, this.getX() + 78 + this.dragX, this.getY() + 10 + this.dragY, 1.0f, -10066330, this.isPinned() ? -8947849 : -7829368, this.isPinned() ? -11184811 : -10066330);
            GLUtils.drawGradientBorderedRect(this.getX() + 80 + this.dragX, this.getY() + 2 + this.dragY, this.getX() + 88 + this.dragX, this.getY() + 10 + this.dragY, 1.0f, -10066330, this.isExtended() ? -8947849 : -7829368, this.isExtended() ? -11184811 : -10066330);
            if (this.isExtended)
            {
                GLUtils.drawGradientBorderedRect(this.getX() + this.dragX, this.getY() + 14 + this.dragY, this.getX() + 90 + this.dragX, this.getY() + 62 + this.dragY, 0.5f, -16777216, -6710887, -8947849);
                Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(Minecraft.getMinecraft().debug.split(",")[0].toUpperCase(), this.getX() + 2 + this.dragX, this.getY() + 16 + this.dragY, 5636095);
                Minecraft.getMinecraft().fontRenderer.drawStringWithShadow("X: " + (int)Minecraft.getMinecraft().thePlayer.posX, this.getX() + 2 + this.dragX, this.getY() + 28 + this.dragY, 5636095);
                Minecraft.getMinecraft().fontRenderer.drawStringWithShadow("Y: " + (int)Minecraft.getMinecraft().thePlayer.posY, this.getX() + 2 + this.dragX, this.getY() + 40 + this.dragY, 5636095);
                Minecraft.getMinecraft().fontRenderer.drawStringWithShadow("Z: " + (int)Minecraft.getMinecraft().thePlayer.posZ, this.getX() + 2 + this.dragX, this.getY() + 52 + this.dragY, 5636095);
            }
        }
    }
}

