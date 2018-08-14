/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityClientPlayerMP
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.GuiTextField
 *  net.minecraft.nbt.NBTTagCompound
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.input.Mouse
 */
package ehacks.mod.util.nbtedit;

import ehacks.debugme.Debug;
import ehacks.mod.gui.EHacksClickGui;
import ehacks.mod.wrapper.Statics;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.nbt.NBTTagCompound;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class GuiNBTEdit
extends GuiScreen {
    private GuiNBTTree guiTree;
    private GuiTextField nbtString;

    public GuiNBTEdit(NBTTagCompound tag) {
        this.guiTree = new GuiNBTTree(new NBTTree(tag));
    }

    public void initGui() {
        Keyboard.enableRepeatEvents((boolean)true);
        this.buttonList.clear();
        this.guiTree.initGUI(this.width, this.height, this.height - 35);
        this.nbtString = new GuiTextField(this.mc.fontRenderer, 274, this.height - 27, this.width - 286, 20);
        this.nbtString.setMaxStringLength(32000);
        this.nbtString.setText(Debug.INSTANCE.NBTToJson(guiTree.getNBTTree().toNBTTagCompound()));
        this.nbtString.setCursorPositionZero();
        this.buttonList.add(new GuiButton(0, 10, this.height - 27, 60, 20, "Save"));
        this.buttonList.add(new GuiButton(1, 75, this.height - 27, 60, 20, "\u00a7cCancel"));
        this.buttonList.add(new GuiButton(2, 140, this.height - 27, 60, 20, "From JSON"));
        this.buttonList.add(new GuiButton(3, 205, this.height - 27, 60, 20, "To JSON"));
    }

    public void onGuiClosed() {
        Keyboard.enableRepeatEvents((boolean)false);
    }

    protected void keyTyped(char par1, int key) {
        GuiEditSingleNBT window = this.guiTree.getWindow();
        if (window != null) {
            window.keyTyped(par1, key);
        } else {
            this.nbtString.textboxKeyTyped(par1, key);
            if (key == 1) {
                if (this.guiTree.isEditingSlot()) {
                    this.guiTree.stopEditingSlot();
                } else {
                    this.quitWithoutSaving();
                }
            } else if (key == 211) {
                this.guiTree.deleteSelected();
            } else if (key == 28) {
                this.guiTree.editSelected();
            } else if (key == 200) {
                this.guiTree.arrowKeyPressed(true);
            } else if (key == 208) {
                this.guiTree.arrowKeyPressed(false);
            } else {
                this.guiTree.keyTyped(par1, key);
            }
        }
    }

    protected void mouseClicked(int x, int y, int t) {
        if (this.guiTree.getWindow() == null) {
            this.nbtString.mouseClicked(x, y, t);
        }
        super.mouseClicked(x, y, t);
        if (t == 0) {
            this.guiTree.mouseClicked(x, y);
        }
        if (t == 1) {
            this.guiTree.rightClick(x, y);
        }
    }

    public void handleMouseInput() {
        super.handleMouseInput();
        int ofs = Mouse.getEventDWheel();
        if (ofs != 0) {
            this.guiTree.shift(ofs >= 1 ? 6 : -6);
        }
    }

    protected void actionPerformed(GuiButton b) {
        if (b.enabled) {
            switch (b.id) {
                case 0: {
                    this.quitWithSave();
                    break;
                }
                case 1: {
                    this.quitWithoutSaving();
                    break;
                }
                case 2: {
                    try
                    {
                        NBTTagCompound check = Debug.INSTANCE.jsonToNBT(this.nbtString.getText());
                        Minecraft.getMinecraft().displayGuiScreen((GuiScreen)new GuiNBTEdit(check));
                    } catch (Exception e) {
                        EHacksClickGui.log("[NBTView] Invalid JSON");
                    }
                }
                case 3: {
                    this.nbtString.setText(Debug.INSTANCE.NBTToJson(this.guiTree.getNBTTree().toNBTTagCompound()));
                    this.nbtString.setCursorPositionZero();
                }
            }
        }
    }

    public void updateScreen() {
        if (!this.mc.thePlayer.isEntityAlive()) {
            this.quitWithoutSaving();
        } else {
            this.guiTree.updateScreen();
        }
        this.nbtString.updateCursorCounter();
    }

    private void quitWithSave() {
        Statics.STATIC_NBT = this.guiTree.getNBTTree().toNBTTagCompound();
        if (Statics.STATIC_ITEMSTACK != null)
            Statics.STATIC_ITEMSTACK.setTagCompound(Statics.STATIC_NBT);
        this.mc.displayGuiScreen(null);
        this.mc.setIngameFocus();
    }

    private void quitWithoutSaving() {
        this.mc.displayGuiScreen(null);
    }

    public void drawScreen(int x, int y, float par3) {
        this.drawDefaultBackground();
        this.guiTree.draw(x, y);
        this.drawCenteredString(this.mc.fontRenderer, "NBTTagCompound viewer/editor", this.width / 2, 17, 10526880);
        this.nbtString.drawTextBox();
        if (this.guiTree.getWindow() == null) {
            super.drawScreen(x, y, par3);
        } else {
            super.drawScreen(-1, -1, par3);
        }
    }

    public boolean doesGuiPauseGame() {
        return false;
    }
}

