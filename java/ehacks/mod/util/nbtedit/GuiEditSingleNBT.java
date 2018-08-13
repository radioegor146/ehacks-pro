/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.renderer.texture.TextureManager
 *  net.minecraft.nbt.NBTBase
 *  net.minecraft.nbt.NBTTagByte
 *  net.minecraft.nbt.NBTTagByteArray
 *  net.minecraft.nbt.NBTTagDouble
 *  net.minecraft.nbt.NBTTagFloat
 *  net.minecraft.nbt.NBTTagInt
 *  net.minecraft.nbt.NBTTagIntArray
 *  net.minecraft.nbt.NBTTagLong
 *  net.minecraft.nbt.NBTTagShort
 *  net.minecraft.nbt.NBTTagString
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package ehacks.mod.util.nbtedit;

import ehacks.mod.util.GLUtils;
import ehacks.mod.util.MinecraftGuiUtils;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagByteArray;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagLong;
import net.minecraft.nbt.NBTTagShort;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiEditSingleNBT
extends Gui {
    public static final int WIDTH = 178;
    public static final int HEIGHT = 93;
    private Minecraft mc = Minecraft.getMinecraft();
    private Node<NamedNBT> node;
    private NBTBase nbt;
    private boolean canEditText;
    private boolean canEditValue;
    private GuiNBTTree parent;
    private int x;
    private int y;
    private GuiTextField key;
    private GuiTextField value;
    private GuiButton save;
    private GuiButton cancel;
    private String kError;
    private String vError;
    private GuiCharacterButton newLine;
    private GuiCharacterButton section;

    public GuiEditSingleNBT(GuiNBTTree parent, Node<NamedNBT> node, boolean editText, boolean editValue) {
        this.parent = parent;
        this.node = node;
        this.nbt = node.getObject().getNBT();
        this.canEditText = editText;
        this.canEditValue = editValue;
    }

    public void initGUI(int x, int y) {
        this.x = x;
        this.y = y;
        this.section = new GuiCharacterButton((byte)0, x + 178 - 1, y + 34);
        this.newLine = new GuiCharacterButton((byte)1, x + 178 - 1, y + 50);
        String sKey = this.key == null ? this.node.getObject().getName() : this.key.getText();
        String sValue = this.value == null ? GuiEditSingleNBT.getValue(this.nbt) : this.value.getText();
        this.key = new GuiTextField(this.mc.fontRenderer, x + 46, y + 18, 116, 15, false);
        this.value = new GuiTextField(this.mc.fontRenderer, x + 46, y + 44, 116, 15, true);
        this.key.setText(sKey);
        this.key.setEnableBackgroundDrawing(false);
        this.key.func_82265_c(this.canEditText);
        this.value.setMaxStringLength(256);
        this.value.setText(sValue);
        this.value.setEnableBackgroundDrawing(false);
        this.value.func_82265_c(this.canEditValue);
        this.save = new GuiButton(1, x + 9, y + 62, 75, 20, "Save");
        if (!this.key.isFocused() && !this.value.isFocused()) {
            if (this.canEditText) {
                this.key.setFocused(true);
            } else if (this.canEditValue) {
                this.value.setFocused(true);
            }
        }
        this.section.setEnabled(this.value.isFocused());
        this.newLine.setEnabled(this.value.isFocused());
        this.cancel = new GuiButton(0, x + 93, y + 62, 75, 20, "\u00a7cCancel");
    }

    public void click(int mx, int my) {
        if (this.newLine.inBounds(mx, my) && this.value.isFocused()) {
            this.value.writeText("\n");
            this.checkValidInput();
        } else if (this.section.inBounds(mx, my) && this.value.isFocused()) {
            this.value.writeText("\u00a7");
            this.checkValidInput();
        } else {
            this.key.mouseClicked(mx, my, 0);
            this.value.mouseClicked(mx, my, 0);
            if (this.save.mousePressed(this.mc, mx, my)) {
                this.saveAndQuit();
            }
            if (this.cancel.mousePressed(this.mc, mx, my)) {
                this.parent.closeWindow();
            }
            this.section.setEnabled(this.value.isFocused());
            this.newLine.setEnabled(this.value.isFocused());
        }
    }

    private void saveAndQuit() {
        if (this.canEditText) {
            this.node.getObject().setName(this.key.getText());
        }
        GuiEditSingleNBT.setValidValue(this.node, this.value.getText());
        this.parent.nodeEdited(this.node);
        this.parent.closeWindow();
    }
    
    public void draw(int mx, int my) {
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        
        MinecraftGuiUtils.drawBack(x, y, 176, 89);
        
        MinecraftGuiUtils.drawInputField(x + 42, y + 15, 127, 16);
        MinecraftGuiUtils.drawInputField(x + 42, y + 41, 127, 16);
        
        
        this.mc.fontRenderer.drawString("Name", x + 14, y + 19, GLUtils.getColor(63, 63, 63));
        this.mc.fontRenderer.drawString("Value", x + 11, y + 45, GLUtils.getColor(63, 63, 63));

        if (!this.canEditText) {
            GuiEditSingleNBT.drawRect((int)(this.x + 42), (int)(this.y + 15), (int)(this.x + 169), (int)(this.y + 31), (int)Integer.MIN_VALUE);
        }
        if (!this.canEditValue) {
            GuiEditSingleNBT.drawRect((int)(this.x + 42), (int)(this.y + 41), (int)(this.x + 169), (int)(this.y + 57), (int)Integer.MIN_VALUE);
        }
        this.key.drawTextBox();
        this.value.drawTextBox();
        this.save.drawButton(this.mc, mx, my);
        this.cancel.drawButton(this.mc, mx, my);
        if (this.kError != null) {
            this.drawCenteredString(this.mc.fontRenderer, this.kError, this.x + 89, this.y + 4, 16711680);
        }
        if (this.vError != null) {
            this.drawCenteredString(this.mc.fontRenderer, this.vError, this.x + 89, this.y + 32, 16711680);
        }
        this.newLine.draw(mx, my);
        this.section.draw(mx, my);
    }

    public void drawCenteredString(FontRenderer par1FontRenderer, String par2Str, int par3, int par4, int par5) {
        par1FontRenderer.drawString(par2Str, par3 - par1FontRenderer.getStringWidth(par2Str) / 2, par4, par5);
    }

    public void update() {
        this.value.updateCursorCounter();
        this.key.updateCursorCounter();
    }

    public void keyTyped(char c, int i) {
        if (i == 1) {
            this.parent.closeWindow();
        } else if (i == 15) {
            if (this.key.isFocused() && this.canEditValue) {
                this.key.setFocused(false);
                this.value.setFocused(true);
            } else if (this.value.isFocused() && this.canEditText) {
                this.key.setFocused(true);
                this.value.setFocused(false);
            }
            this.section.setEnabled(this.value.isFocused());
            this.newLine.setEnabled(this.value.isFocused());
        } else if (i == 28) {
            this.checkValidInput();
            if (this.save.enabled) {
                this.saveAndQuit();
            }
        } else {
            this.key.textboxKeyTyped(c, i);
            this.value.textboxKeyTyped(c, i);
            this.checkValidInput();
        }
    }

    private void checkValidInput() {
        boolean valid = true;
        this.kError = null;
        this.vError = null;
        if (this.canEditText && !this.validName()) {
            valid = false;
            this.kError = "Duplicate Tag Name";
        }
        try {
            GuiEditSingleNBT.validValue(this.value.getText(), this.nbt.getId());
            valid &= true;
        }
        catch (NumberFormatException e) {
            this.vError = e.getMessage();
            valid = false;
        }
        this.save.enabled = valid;
    }

    private boolean validName() {
        for (Node<NamedNBT> node : this.node.getParent().getChildren()) {
            NBTBase base = node.getObject().getNBT();
            if (base == this.nbt || !node.getObject().getName().equals(this.key.getText())) continue;
            return false;
        }
        return true;
    }

    private static void setValidValue(Node<NamedNBT> node, String value) {
        NamedNBT named = node.getObject();
        NBTBase base = named.getNBT();
        if (base instanceof NBTTagByte) {
            named.setNBT((NBTBase)new NBTTagByte(ParseHelper.parseByte(value)));
        }
        if (base instanceof NBTTagShort) {
            named.setNBT((NBTBase)new NBTTagShort(ParseHelper.parseShort(value)));
        }
        if (base instanceof NBTTagInt) {
            named.setNBT((NBTBase)new NBTTagInt(ParseHelper.parseInt(value)));
        }
        if (base instanceof NBTTagLong) {
            named.setNBT((NBTBase)new NBTTagLong(ParseHelper.parseLong(value)));
        }
        if (base instanceof NBTTagFloat) {
            named.setNBT((NBTBase)new NBTTagFloat(ParseHelper.parseFloat(value)));
        }
        if (base instanceof NBTTagDouble) {
            named.setNBT((NBTBase)new NBTTagDouble(ParseHelper.parseDouble(value)));
        }
        if (base instanceof NBTTagByteArray) {
            named.setNBT((NBTBase)new NBTTagByteArray(ParseHelper.parseByteArray(value)));
        }
        if (base instanceof NBTTagIntArray) {
            named.setNBT((NBTBase)new NBTTagIntArray(ParseHelper.parseIntArray(value)));
        }
        if (base instanceof NBTTagString) {
            named.setNBT((NBTBase)new NBTTagString(value));
        }
    }

    private static void validValue(String value, byte type) throws NumberFormatException {
        switch (type) {
            case 1: {
                ParseHelper.parseByte(value);
                break;
            }
            case 2: {
                ParseHelper.parseShort(value);
                break;
            }
            case 3: {
                ParseHelper.parseInt(value);
                break;
            }
            case 4: {
                ParseHelper.parseLong(value);
                break;
            }
            case 5: {
                ParseHelper.parseFloat(value);
                break;
            }
            case 6: {
                ParseHelper.parseDouble(value);
                break;
            }
            case 7: {
                ParseHelper.parseByteArray(value);
                break;
            }
            case 11: {
                ParseHelper.parseIntArray(value);
            }
        }
    }

    private static String getValue(NBTBase base) {
        switch (base.getId()) {
            case 7: {
                String s = "";
                for (byte b : ((NBTTagByteArray)base).func_150292_c()) {
                    s = s + b + " ";
                }
                return s;
            }
            case 9: {
                return "TagList";
            }
            case 10: {
                return "TagCompound";
            }
            case 11: {
                String i = "";
                for (int a : ((NBTTagIntArray)base).func_150302_c()) {
                    i = i + a + " ";
                }
                return i;
            }
        }
        return NBTStringHelper.toString(base);
    }
}

