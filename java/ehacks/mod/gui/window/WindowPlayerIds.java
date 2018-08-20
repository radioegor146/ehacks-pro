package ehacks.mod.gui.window;

import ehacks.mod.gui.Tuple;
import ehacks.mod.gui.EHacksClickGui;
import ehacks.mod.gui.element.ModWindow;
import ehacks.mod.gui.element.SimpleWindow;
import ehacks.mod.util.GLUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class WindowPlayerIds 
extends SimpleWindow {
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
            this.width = 200;
            this.height = players.size() * 12 + 14;
            super.draw(x, y);
            if (this.isExtended()) {
                int i = 0;
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
    public boolean mouseClicked(int x, int y, int button) {
        boolean retval = false;
        if (x >= this.getX() + this.dragX + 2 && y >= this.getY() + 14 + this.dragY + 2 && (double)x <= this.getX() + 90 + 110 + this.dragX - 2 && y <= this.getY() + 14 + this.dragY + 12 && button == 0 && this.isOpen() && this.isExtended()) {
            useIt = !useIt;
            retval = true;
        }
        retval &= super.mouseClicked(x, y, button);
        return retval;
    }
}