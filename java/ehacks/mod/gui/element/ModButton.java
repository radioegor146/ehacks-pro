package ehacks.mod.gui.element;

import net.minecraft.client.Minecraft;
import ehacks.api.module.Mod;
import ehacks.api.module.ModStatus;
import ehacks.mod.gui.EHacksClickGui;
import ehacks.mod.util.GLUtils;

public class ModButton {
    private ModWindow window;
    public Mod mod;
    public int xPos;
    public int yPos;
    public boolean isOverButton;
    public int shiftY = 0;

    public ModButton(ModWindow window, Mod mod, int xPos, int yPos) {
        this.window = window;
        this.mod = mod;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public void draw() {
        GLUtils.drawGradientBorderedRect((double)this.getX() + 0.5 + (double)this.window.dragX, (double)this.getY() + 0.5 + (double)this.window.dragY, (double)this.getX() + 86.5 + (double)this.window.dragX, this.getY() + 11 + this.window.dragY, 0.5f, -12303292, !this.mod.isActive() ? -8947849 : -11184811, !this.mod.isActive() ? -11184811 : -10066330);
        Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(this.mod.getName(), this.getX() + this.window.dragX + 86 / 2 - Minecraft.getMinecraft().fontRenderer.getStringWidth(this.mod.getName()) / 2, this.getY() + 2 + this.window.dragY, this.mod.isActive() ? 5636095 : this.mod.getModStatus().Color);
    }
    
    public void drawShift(int x, int y) {
        GLUtils.drawGradientBorderedRect((double)this.getX() + 0.5 + (double)this.window.dragX + x, (double)this.getY() + 0.5 + (double)this.window.dragY + y, (double)this.getX() + 86.5 + (double)this.window.dragX + x, this.getY() + 11 + this.window.dragY + y, 0.5f, -12303292, !this.mod.isActive() ? -8947849 : -11184811, !this.mod.isActive() ? -11184811 : -10066330);
        Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(this.mod.getName(), this.getX() + this.window.dragX + 86 / 2 - Minecraft.getMinecraft().fontRenderer.getStringWidth(this.mod.getName()) / 2, this.getY() + 2 + this.window.dragY + y, this.mod.isActive() ? 5636095 : this.mod.getModStatus().Color);
    }

    public boolean mouseClicked(int x, int y, int button) {
        y -= shiftY;
        if (x >= this.getX() + this.window.dragX && y >= this.getY() + this.window.dragY && (double)x <= (double)this.getX() + 86.5 + (double)this.window.dragX && y <= this.getY() + 9 + this.window.dragY && button == 0 && this.window.isOpen() && this.window.isExtended() && this.mod.getModStatus() != ModStatus.NOTWORKING) {
            EHacksClickGui.sendPanelToFront(this.window);
            this.mod.toggle();
            return true;
        }
        return false;
    }
    
    public boolean isMouseOver(int x, int y) {
        y -= shiftY;
        return (x >= this.getX() + this.window.dragX && y >= this.getY() + this.window.dragY && (double)x <= (double)this.getX() + 86.5 + (double)this.window.dragX && y <= this.getY() + 9 + this.window.dragY && this.window.isOpen() && this.window.isExtended());    
    }

    public int getX() {
        return this.xPos;
    }

    public int getY() {
        return this.yPos;
    }
}

