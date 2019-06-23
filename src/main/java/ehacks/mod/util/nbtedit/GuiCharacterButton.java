package ehacks.mod.util.nbtedit;

import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;

public class GuiCharacterButton
        extends Gui {

    public static final int WIDTH = 14;
    public static final int HEIGHT = 14;
    private final byte id;
    private final int x;
    private final int y;
    private boolean enabled;

    public GuiCharacterButton(byte id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public void draw(int mx, int my) {
        if (this.inBounds(mx, my)) {
            Gui.drawRect(this.x, this.y, (this.x + 14), (this.y + 14), -2130706433);
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        //this.drawTexturedModalRect(this.x, this.y, this.id * 14, 27, 14, 14);
        if (!this.enabled) {
            GuiCharacterButton.drawRect(this.x, this.y, (this.x + 14), (this.y + 14), -1071504862);
        }
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean aFlag) {
        this.enabled = aFlag;
    }

    public boolean inBounds(int mx, int my) {
        return this.enabled && mx >= this.x && my >= this.y && mx < this.x + 14 && my < this.y + 14;
    }

    public byte getId() {
        return this.id;
    }
}
