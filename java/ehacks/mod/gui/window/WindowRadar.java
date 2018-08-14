package ehacks.mod.gui.window;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import ehacks.mod.gui.element.ModButton;
import ehacks.mod.gui.element.ModWindow;
import ehacks.mod.gui.element.SimpleWindow;
import ehacks.mod.util.GLUtils;
import java.util.ArrayList;

public class WindowRadar
extends SimpleWindow {
    public WindowRadar() {
        super("Radar", 94, 300);
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
                int rect = 0;
                for (Object o : Minecraft.getMinecraft().theWorld.playerEntities) {
                    EntityPlayer e = (EntityPlayer)o;
                    if (e == Minecraft.getMinecraft().thePlayer || e.isDead) continue;
                    rect += 12;
                }
                this.height = rect;
                super.draw(x, y);
                int count = 0;
                for (Object o : Minecraft.getMinecraft().theWorld.playerEntities) {
                    EntityPlayer e = (EntityPlayer)o;
                    if (e == Minecraft.getMinecraft().thePlayer || e.isDead) continue;
                    int distance = (int)Minecraft.getMinecraft().thePlayer.getDistanceToEntity((Entity)e);
                    String text = "";
                    if (distance <= 20) {
                        text = "\u00a7c" + e.getDisplayName() + "\u00a7f: " + distance;
                    } else if (distance <= 50 && distance > 20) {
                        text = "\u00a76" + e.getDisplayName() + "\u00a7f: " + distance;
                    } else if (distance > 50) {
                        text = "\u00a7a" + e.getDisplayName() + "\u00a7f: " + distance;
                    }
                    int xPosition = this.getX() + 2 + this.dragX;
                    int yPosition = this.getY() + 12 * count + 16 + this.dragY;
                    Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(text, xPosition, yPosition, 5636095);
                    ++count;
                }
                if (rect == 0 && count == 0) {
                    GLUtils.drawGradientBorderedRect(this.getX() + this.dragX, this.getY() + 14 + this.dragY, this.getX() + 90 + this.dragX, (double)this.getY() + 26 + (double)this.dragY, 0.5f, -16777216, -6710887, -8947849);
                    Minecraft.getMinecraft().fontRenderer.drawStringWithShadow("No one in range.", this.getX() + 2 + this.dragX, this.getY() + 16 + this.dragY, 5636095);
                }
            }
            else
                super.draw(x, y);
        }
    }
}

