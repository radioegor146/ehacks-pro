package ehacks.mod.gui.reeszrbteam.window;

import ehacks.mod.gui.reeszrbteam.Tuple;
import ehacks.mod.gui.reeszrbteam.YouAlwaysWinClickGui;
import ehacks.mod.gui.reeszrbteam.element.YAWWindow;
import ehacks.mod.util.GLUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class WindowPlayerIds 
extends YAWWindow {
    public static HashMap<String, Tuple<Integer, EntityPlayer>> players = new HashMap<String, Tuple<Integer, EntityPlayer>>();
    public static boolean useIt = false;
    
    public WindowPlayerIds() {
        super("Saved Players", 186, 300);
    }
    
    public static List<EntityPlayer> getPlayers() {
        ArrayList<EntityPlayer> retPlayers = new ArrayList<EntityPlayer>();
        for (Map.Entry<String, Tuple<Integer, EntityPlayer>> entry : players.entrySet()) {
            retPlayers.add(entry.getValue().y);
        }
        return retPlayers;
    }
    
    @Override
    public void draw(int x, int y) {
        if (this.isOpen()) {
            if (this.dragging) {
                this.windowDragged(x, y);
            }
            GLUtils.drawGradientBorderedRect(this.getX() + this.dragX, this.getY() + this.dragY, this.getX() + 90 + 110 + this.dragX, this.getY() + 12 + this.dragY, 0.5f, -16777216, -6710887, -8947849);
            Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(this.getTitle(), this.getX() + 2 + this.dragX, this.getY() + this.dragY + 2, 5636095);
            GLUtils.drawGradientBorderedRect(this.getX() + 70 + 110 + this.dragX, this.getY() + 2 + this.dragY, this.getX() + 78 + 110 + this.dragX, this.getY() + 10 + this.dragY, 1.0f, -10066330, this.isPinned() ? -8947849 : -7829368, this.isPinned() ? -11184811 : -10066330);
            GLUtils.drawGradientBorderedRect(this.getX() + 80 + 110 + this.dragX, this.getY() + 2 + this.dragY, this.getX() + 88 + 110 + this.dragX, this.getY() + 10 + this.dragY, 1.0f, -10066330, this.isExtended() ? -8947849 : -7829368, this.isExtended() ? -11184811 : -10066330);
            if (this.isExtended()) {
                int i = 0;
                GLUtils.drawGradientBorderedRect(this.getX() + this.dragX, this.getY() + 14 + this.dragY, this.getX() + 90 + 110 + this.dragX, this.getY() + 14 + this.dragY + players.size() * 12 + 14, 0.5f, -16777216, -6710887, -8947849);
                GLUtils.drawGradientBorderedRect(this.getX() + this.dragX + 2, this.getY() + 14 + this.dragY + 2, this.getX() + 90 + 110 + this.dragX - 2, this.getY() + 14 + this.dragY + 12, 0.5f, -12303292, !useIt ? -8947849 : -11184811, !useIt ? -11184811 : -10066330);
                Minecraft.getMinecraft().fontRenderer.drawStringWithShadow("Use list", this.getX() + this.dragX + 200 / 2 - Minecraft.getMinecraft().fontRenderer.getStringWidth("Use list") / 2, this.getY() + 14 + this.dragY + 3, useIt ? 5636095 : 12303291);
                for (Map.Entry<String, Tuple<Integer, EntityPlayer>> entry : players.entrySet()) {
                    int color = GLUtils.getColor(255 - Math.min(64, entry.getValue().x), 255 - Math.min(64, entry.getValue().x), 255 - Math.min(64, entry.getValue().x));
                    Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(entry.getKey() + ": 0x" + Integer.toHexString(entry.getValue().y.getEntityId()), this.getX() + 2 + this.dragX, this.getY() + this.dragY + 16 + i * 12 + 14, color);
                    entry.getValue().x++;
                    i++;
                }
            }
        }
    }
    
    @Override
    public void mouseClicked(int x, int y, int button) {
        if (x >= this.getX() + this.dragX + 2 && y >= this.getY() + 14 + this.dragY + 2 && (double)x <= this.getX() + 90 + 110 + this.dragX - 2 && y <= this.getY() + 14 + this.dragY + 12 && button == 0 && this.isOpen() && this.isExtended()) {
            useIt = !useIt;
        }
        if (x >= this.xPos + 80 + 110 + this.dragX && y >= this.yPos + 2 + this.dragY && x <= this.xPos + 88 + 110 + this.dragX && y <= this.yPos + 10 + this.dragY) {
            boolean bl = this.isExtended = !this.isExtended;
        }
        if (x >= this.xPos + 70 + 110 + this.dragX && y >= this.yPos + 2 + this.dragY && x <= this.xPos + 78 + 110 + this.dragX && y <= this.yPos + 10 + this.dragY) {
            boolean bl = this.isPinned = !this.isPinned;
        }
        if (x >= this.xPos + this.dragX && y >= this.yPos + this.dragY && x <= this.xPos + 69 + 110 + this.dragX && y <= this.yPos + 12 + this.dragY) {
            YouAlwaysWinClickGui.sendPanelToFront(this);
            this.dragging = !this.dragging;
            this.lastDragX = x - this.dragX;
            this.lastDragY = y - this.dragY;
        }
    }
}