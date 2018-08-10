/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.renderer.texture.TextureManager
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package ehacks.mod.util.nbtedit;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiNBTButton
extends Gui {
    public static final int WIDTH = 9;
    public static final int HEIGHT = 9;
    private Minecraft mc = Minecraft.getMinecraft();
    private byte id;
    private int x;
    private int y;
    private boolean enabled;
    private long hoverTime;

    public GuiNBTButton(byte id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public void draw(int mx, int my) {
        this.mc.renderEngine.bindTexture(GuiNBTNode.WIDGET_TEXTURE);
        if (this.inBounds(mx, my)) {
            Gui.drawRect((int)this.x, (int)this.y, (int)(this.x + 9), (int)(this.y + 9), (int)-2130706433);
            if (this.hoverTime == -1L) {
                this.hoverTime = System.currentTimeMillis();
            }
        } else {
            this.hoverTime = -1L;
        }
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        this.drawTexturedModalRect(this.x, this.y, (this.id - 1) * 9, 18, 9, 9);
        if (!this.enabled) {
            GuiNBTButton.drawRect((int)this.x, (int)this.y, (int)(this.x + 9), (int)(this.y + 9), (int)-1071504862);
        } else if (this.hoverTime != -1L && System.currentTimeMillis() - this.hoverTime > 300L) {
            this.drawToolTip(mx, my);
        }
    }

    private void drawToolTip(int mx, int my) {
        String s = NBTStringHelper.getButtonName(this.id);
        int width = this.mc.fontRenderer.getStringWidth(s);
        GuiNBTButton.drawRect((int)(mx + 4), (int)(my + 7), (int)(mx + 5 + width), (int)(my + 17), (int)-16777216);
        this.mc.fontRenderer.drawString(s, mx + 5, my + 8, 16777215);
    }

    public void setEnabled(boolean aFlag) {
        this.enabled = aFlag;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public boolean inBounds(int mx, int my) {
        return this.enabled && mx >= this.x && my >= this.y && mx < this.x + 9 && my < this.y + 9;
    }

    public byte getId() {
        return this.id;
    }
}

