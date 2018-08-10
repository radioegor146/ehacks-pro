/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.renderer.texture.TextureManager
 *  net.minecraft.nbt.NBTBase
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package ehacks.mod.util.nbtedit;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiNBTNode
extends Gui {
    public static final ResourceLocation WIDGET_TEXTURE = new ResourceLocation("ehacks", "textures/gui/nbt_widgets.png");
    private Minecraft mc = Minecraft.getMinecraft();
    private Node<NamedNBT> node;
    private GuiNBTTree tree;
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
        this.height = this.mc.fontRenderer.FONT_HEIGHT;
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
        this.width = this.mc.fontRenderer.getStringWidth(this.displayString) + 12;
    }

    public void draw(int mx, int my) {
        boolean selected = this.tree.getFocused() == this.node;
        boolean hover = this.inBounds(mx, my);
        boolean chHover = this.inHideShowBounds(mx, my);
        int color = selected ? 255 : (hover ? 16777120 : (this.node.hasParent() ? 14737632 : -6250336));
        this.mc.renderEngine.bindTexture(WIDGET_TEXTURE);
        if (selected) {
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            Gui.drawRect((int)(this.x + 11), (int)this.y, (int)(this.x + this.width), (int)(this.y + this.height), (int)Integer.MIN_VALUE);
        }
        if (this.node.hasChildren()) {
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            this.drawTexturedModalRect(this.x - 9, this.y, this.node.shouldDrawChildren() ? 9 : 0, chHover ? this.height : 0, 9, this.height);
        }
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        this.drawTexturedModalRect(this.x + 1, this.y, (this.node.getObject().getNBT().getId() - 1) * 9, 18, 9, 9);
        this.drawString(this.mc.fontRenderer, this.displayString, this.x + 11, this.y + (this.height - 8) / 2, color);
    }

    public boolean shouldDraw(int top, int bottom) {
        return this.y + this.height >= top && this.y <= bottom;
    }
}

