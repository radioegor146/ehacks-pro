/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityClientPlayerMP
 *  net.minecraft.client.gui.FontRenderer
 */
package ehacks.mod.gui.reeszrbteam.window;

import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.FontRenderer;
import ehacks.mod.gui.reeszrbteam.element.YAWButton;
import ehacks.mod.gui.reeszrbteam.element.YAWWindow;
import ehacks.mod.util.GLUtils;

public class WindowInfo
extends YAWWindow {
    public WindowInfo() {
        super("Info", 554, 2);
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
            GLUtils.drawGradientBorderedRect(this.getX() + this.dragX, this.getY() + 14 + this.dragY, this.getX() + 90 + this.dragX, this.getY() + 64 + this.dragY, 0.5f, -16777216, -6710887, -8947849);
            Minecraft.getMinecraft().fontRenderer.drawStringWithShadow("Counter: " + Minecraft.getMinecraft().debug.split(",")[0].toUpperCase(), this.getX() + 2 + this.dragX, this.getY() + 15 + this.dragY, 5636095);
            Minecraft.getMinecraft().fontRenderer.drawStringWithShadow("X: " + (int)Minecraft.getMinecraft().thePlayer.posX, this.getX() + 2 + this.dragX, this.getY() + 35 + this.dragY, 5636095);
            Minecraft.getMinecraft().fontRenderer.drawStringWithShadow("Y: " + (int)Minecraft.getMinecraft().thePlayer.posY, this.getX() + 2 + this.dragX, this.getY() + 45 + this.dragY, 5636095);
            Minecraft.getMinecraft().fontRenderer.drawStringWithShadow("Z: " + (int)Minecraft.getMinecraft().thePlayer.posZ, this.getX() + 2 + this.dragX, this.getY() + 55 + this.dragY, 5636095);
            Minecraft.getMinecraft().fontRenderer.drawStringWithShadow("Ver.: " + "8".replace("-", ""), this.getX() + 2 + this.dragX, this.getY() + 25 + this.dragY, 5636095);
            for (YAWButton button : this.buttons) {
                button.draw();
            }
        }
    }
}

