package ehacks.mod.gui.xraysettings;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.opengl.GL11;

public class XRayGuiSlider
        extends GuiButton {

    public float percent;
    public boolean isClicked;

    public XRayGuiSlider(int id, int x, int y, String name, float percentage) {
        super(id, x, y, 150, 20, name);
        this.percent = percentage;
    }

    @Override
    public int getHoverState(boolean p_146114_1_) {
        return 0;
    }

    @Override
    protected void mouseDragged(Minecraft p_146119_1_, int p_146119_2_, int p_146119_3_) {
        if (this.visible) {
            if (this.isClicked) {
                this.percent = (float) (p_146119_2_ - (this.xPosition + 4)) / (float) (this.width - 8);
                if (this.percent < 0.0f) {
                    this.percent = 0.0f;
                }
                if (this.percent > 1.0f) {
                    this.percent = 1.0f;
                }
            }
            GL11.glColor4f((float) 1.0f, (float) 1.0f, (float) 1.0f, (float) 1.0f);
            this.drawTexturedModalRect(this.xPosition + (int) (this.percent * (float) (this.width - 8)), this.yPosition, 0, 66, 4, 20);
            this.drawTexturedModalRect(this.xPosition + (int) (this.percent * (float) (this.width - 8)) + 4, this.yPosition, 196, 66, 4, 20);
        }
    }

    @Override
    public boolean mousePressed(Minecraft p_146116_1_, int x, int y) {
        if (super.mousePressed(p_146116_1_, x, y)) {
            this.percent = (float) (x - (this.xPosition + 4)) / (float) (this.width - 8);
            if (this.percent < 0.0f) {
                this.percent = 0.0f;
            }
            if (this.percent > 1.0f) {
                this.percent = 1.0f;
            }
            this.isClicked = true;
            return true;
        }
        return false;
    }

    @Override
    public void mouseReleased(int x, int y) {
        this.isClicked = false;
    }
}
