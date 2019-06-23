package ehacks.mod.util.nbtedit;

import ehacks.mod.util.GLUtils;
import ehacks.mod.wrapper.Wrapper;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;

public class GuiNBTNode
        extends Gui {

    public static final int[] NBT_ICON_MAPPING = new int[]{1, 2, 3, 4, 5, 6, 9, 7, 8, 0, 10, 0};
    private final Node<NamedNBT> node;
    private final GuiNBTTree tree;
    protected int width;
    protected int height;
    protected int x;
    protected int y;
    private String displayString;

    public GuiNBTNode(GuiNBTTree tree, Node<NamedNBT> node, int x, int y) {
        this.tree = tree;
        this.node = node;
        this.x = x;
        this.y = y;
        this.height = Wrapper.INSTANCE.fontRenderer().FONT_HEIGHT;
        this.updateDisplay();
    }

    private boolean inBounds(int mx, int my) {
        return mx >= this.x && my >= this.y && mx < this.width + this.x && my < this.height + this.y;
    }

    private boolean inHideShowBounds(int mx, int my) {
        return mx >= this.x - 9 && my >= this.y && mx < this.x && my < this.y + this.height;
    }

    public boolean shouldDrawChildren() {
        return this.node.shouldDrawChildren();
    }

    public boolean clicked(int mx, int my) {
        return this.inBounds(mx, my);
    }

    public boolean hideShowClicked(int mx, int my) {
        if (this.node.hasChildren() && this.inHideShowBounds(mx, my)) {
            this.node.setDrawChildren(!this.node.shouldDrawChildren());
            return true;
        }
        return false;
    }

    public Node<NamedNBT> getNode() {
        return this.node;
    }

    public void shift(int dy) {
        this.y += dy;
    }

    public void updateDisplay() {
        this.displayString = NBTStringHelper.getNBTNameSpecial(this.node.getObject());
        this.width = Wrapper.INSTANCE.fontRenderer().getStringWidth(this.displayString) + 12;
    }

    public void draw(int mx, int my) {
        boolean selected = this.tree.getFocused() == this.node;
        boolean hover = this.inBounds(mx, my);
        boolean chHover = this.inHideShowBounds(mx, my);
        int color = selected ? 255 : (hover ? 16777120 : (this.node.hasParent() ? 14737632 : -6250336));
        if (selected) {
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            Gui.drawRect((this.x + 11), this.y, (this.x + this.width), (this.y + this.height), Integer.MIN_VALUE);
        }
        GL11.glScalef(.5f, .5f, .5f);
        if (this.node.hasChildren()) {
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GuiIconUtils.drawButtonBack((this.x - 9) * 2, this.y * 2 - 3);
            if (chHover) {
                GLUtils.drawRect((this.x - 9) * 2 + 4, this.y * 2 + 4 - 3, (this.x - 9) * 2 + 15, this.y * 2 + 15 - 3, GLUtils.getColor(255, 255, 255));
            }
            GLUtils.drawRect((this.x - 9) * 2 + 6, this.y * 2 + 9 - 3, (this.x - 9) * 2 + 13, this.y * 2 + 10 - 3, GLUtils.getColor(0, 0, 0));
            if (!this.node.shouldDrawChildren()) {
                GLUtils.drawRect((this.x - 9) * 2 + 9, this.y * 2 + 6 - 3, (this.x - 9) * 2 + 10, this.y * 2 + 13 - 3, GLUtils.getColor(0, 0, 0));
            }
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GuiIconUtils.drawButtonBack((this.x + 1) * 2, this.y * 2 - 3);
        GuiIconUtils.drawButtonIcon((this.x + 1) * 2, this.y * 2 - 3, NBT_ICON_MAPPING[this.node.getObject().getNBT().getId() - 1]);
        GL11.glScalef(2f, 2f, 2f);
        this.drawString(Wrapper.INSTANCE.fontRenderer(), this.displayString, this.x + 11, this.y + (this.height - 8) / 2, color);
    }

    public boolean shouldDraw(int top, int bottom) {
        return this.y + this.height >= top && this.y <= bottom;
    }
}
