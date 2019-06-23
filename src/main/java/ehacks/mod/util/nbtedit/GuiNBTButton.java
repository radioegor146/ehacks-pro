package ehacks.mod.util.nbtedit;

import ehacks.mod.wrapper.Wrapper;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;

public class GuiNBTButton
        extends Gui {

    public static final int WIDTH = 9;
    public static final int HEIGHT = 9;
    private final byte id;
    private final int x;
    private final int y;
    private boolean enabled;
    private long hoverTime;

    public GuiNBTButton(byte id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public void draw(int mx, int my) {
        GL11.glScalef(.5f, .5f, .5f);
        if (this.inBounds(mx, my)) {
            Gui.drawRect(this.x * 2, this.y * 2, (this.x * 2 + 19), (this.y * 2 + 19), -2130706433);
            if (this.hoverTime == -1L) {
                this.hoverTime = System.currentTimeMillis();
            }
        } else {
            this.hoverTime = -1L;
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GuiIconUtils.drawButtonBack(this.x * 2, this.y * 2);
        GuiIconUtils.drawButtonIcon(this.x * 2, this.y * 2, id - 1);

        if (!this.enabled) {
            GuiNBTButton.drawRect(this.x * 2, this.y * 2, (this.x * 2 + 19), (this.y * 2 + 19), -1071504862);
        }
        GL11.glScalef(2f, 2f, 2f);
        if (this.enabled && this.hoverTime != -1L && System.currentTimeMillis() - this.hoverTime > 300L) {
            this.drawToolTip(mx, my);
        }
    }

    private void drawToolTip(int mx, int my) {
        String s = NBTStringHelper.getButtonName(this.id);
        int width = Wrapper.INSTANCE.fontRenderer().getStringWidth(s);
        GuiNBTButton.drawRect((mx + 4), (my + 7), (mx + 5 + width), (my + 17), -16777216);
        Wrapper.INSTANCE.fontRenderer().drawString(s, mx + 5, my + 8, 16777215);
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean aFlag) {
        this.enabled = aFlag;
    }

    public boolean inBounds(int mx, int my) {
        return this.enabled && mx >= this.x && my >= this.y && mx < this.x + 9 && my < this.y + 9;
    }

    public byte getId() {
        return this.id;
    }
}
