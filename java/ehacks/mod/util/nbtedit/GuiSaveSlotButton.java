package ehacks.mod.util.nbtedit;

import ehacks.mod.wrapper.Wrapper;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiSaveSlotButton
        extends Gui {

    public static final ResourceLocation TEXTURE = new ResourceLocation("textures/gui/widgets.png");
    public final int saveId;
    private final int rightX;
    private int x;
    private final int y;
    private int width;
    private String text;
    private boolean xVisible;

    public GuiSaveSlotButton(int saveId, int rightX, int y) {
        this.saveId = saveId;
        this.rightX = rightX;
        this.y = y;
        this.xVisible = GuiNBTTree.saveSlots[saveId] != null;
        this.text = (GuiNBTTree.saveSlots[saveId] == null ? "Save slot " : "Load slot ") + saveId;
        this.updatePosition();
    }

    public void draw(int mx, int my) {
        int textColor = this.inBounds(mx, my) ? 16777120 : 16777215;
        this.renderVanillaButton(this.x, this.y, 0, 66, this.width, 20, this.inBounds(mx, my) ? 2 : 1);
        this.drawCenteredString(Wrapper.INSTANCE.fontRenderer(), this.text, this.x + this.width / 2, this.y + 6, textColor);
        if (this.xVisible) {
            textColor = this.inBoundsOfX(mx, my) ? 16777120 : 16777215;
            this.renderVanillaButton(this.leftBoundOfX(), this.topBoundOfX(), 0, 66, 14, 14, this.inBoundsOfX(mx, my) ? 2 : 1);
            this.drawCenteredString(Wrapper.INSTANCE.fontRenderer(), "\u00a7c\u00a7lx", this.x - 3 - 7, this.y + 6, textColor);
        }
    }

    private void renderVanillaButton(int x, int y, int u, int v, int width, int height, int k) {
        GL11.glColor4f((float) 1.0f, (float) 1.0f, (float) 1.0f, (float) 1.0f);
        Wrapper.INSTANCE.mc().renderEngine.bindTexture(TEXTURE);
        this.drawTexturedModalRect(x, y, 0, 46 + k * 20, width / 2, height);
        this.drawTexturedModalRect(x + width / 2, y, 200 - width / 2, 46 + k * 20, width / 2, height);
    }

    private int leftBoundOfX() {
        return this.x - 14 - 3;
    }

    private int topBoundOfX() {
        return this.y + 3;
    }

    public boolean inBoundsOfX(int mx, int my) {
        int buttonX = this.leftBoundOfX();
        int buttonY = this.topBoundOfX();
        return this.xVisible && mx >= buttonX && my >= buttonY && mx < buttonX + 14 && my < buttonY + 14;
    }

    public boolean inBounds(int mx, int my) {
        return mx >= this.x && my >= this.y && mx < this.x + this.width && my < this.y + 20;
    }

    private void updatePosition() {
        this.width = Wrapper.INSTANCE.fontRenderer().getStringWidth(this.text) + 24;
        if (this.width % 2 == 1) {
            ++this.width;
        }
        this.width = MathHelper.clamp_int((int) this.width, (int) 82, (int) 150);
        this.x = this.rightX - this.width;
    }

    public void reset() {
        this.xVisible = false;
        GuiNBTTree.saveSlots[this.saveId] = null;
        this.text = "Save slot " + saveId;
        this.updatePosition();
    }

    public void saved() {
        this.xVisible = true;
        this.text = "Load slot " + saveId;
        this.updatePosition();
    }
}
