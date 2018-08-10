/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.audio.ISound
 *  net.minecraft.client.audio.PositionedSoundRecord
 *  net.minecraft.client.audio.SoundHandler
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.texture.TextureManager
 *  net.minecraft.nbt.NBTBase
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.nbt.NBTTagList
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.opengl.GL11
 */
package ehacks.mod.util.nbtedit;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class GuiNBTTree
extends Gui {
    private Minecraft mc = Minecraft.getMinecraft();
    private NBTTree tree;
    private List<GuiNBTNode> nodes;
    private GuiNBTButton[] buttons;
    private final int X_GAP = 10;
    private final int START_X = 10;
    private final int START_Y = 30;
    private final int Y_GAP;
    private int y;
    private int yClick;
    private int bottom;
    private int width;
    private int height;
    private int heightDiff;
    private int offset;
    private Node<NamedNBT> focused;
    private int focusedSlotIndex;
    private GuiEditSingleNBT window;
    private static SaveStates nbtSaves;
    private static NamedNBT clipboard;
    private static GuiSaveSlotButton[] saves;

    public Node<NamedNBT> getFocused() {
        return this.focused;
    }

    public GuiSaveSlotButton getFocusedSaveSlot() {
        return this.focusedSlotIndex != -1 ? saves[this.focusedSlotIndex] : null;
    }

    public NBTTree getNBTTree() {
        return this.tree;
    }

    public GuiNBTTree(NBTTree tree) {
        this.Y_GAP = Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT + 2;
        nbtSaves = new SaveStates(new File(Minecraft.getMinecraft().mcDataDir, "/config/ehacks/nbt.dat"));
        nbtSaves.load();
        nbtSaves.save();
        this.tree = tree;
        this.yClick = -1;
        this.focusedSlotIndex = -1;
        this.nodes = new ArrayList<GuiNBTNode>();
        this.buttons = new GuiNBTButton[16];
        saves = new GuiSaveSlotButton[7];
    }

    private int getHeightDifference() {
        return this.getContentHeight() - (this.bottom - 30 + 2);
    }

    private int getContentHeight() {
        return this.Y_GAP * this.nodes.size();
    }

    public GuiEditSingleNBT getWindow() {
        return this.window;
    }

    public void initGUI(int width, int height, int bottom) {
        this.width = width;
        this.height = height;
        this.bottom = bottom;
        this.yClick = -1;
        this.initGUI(false);
        if (this.window != null) {
            this.window.initGUI((width - 178) / 2, (height - 93) / 2);
        }
    }

    public void updateScreen() {
        if (this.window != null) {
            this.window.update();
        }
        if (this.focusedSlotIndex != -1) {
            saves[this.focusedSlotIndex].update();
        }
    }

    private void setFocused(Node<NamedNBT> toFocus) {
        if (toFocus == null) {
            for (GuiNBTButton b : this.buttons) {
                b.setEnabled(false);
            }
        } else if (toFocus.getObject().getNBT() instanceof NBTTagCompound) {
            for (GuiNBTButton b : this.buttons) {
                b.setEnabled(true);
            }
            this.buttons[12].setEnabled(toFocus != this.tree.getRoot());
            this.buttons[11].setEnabled(toFocus.hasParent() && !(toFocus.getParent().getObject().getNBT() instanceof NBTTagList));
            this.buttons[13].setEnabled(true);
            this.buttons[14].setEnabled(toFocus != this.tree.getRoot());
            this.buttons[15].setEnabled(clipboard != null);
        } else if (toFocus.getObject().getNBT() instanceof NBTTagList) {
            if (toFocus.hasChildren()) {
                byte type = toFocus.getChildren().get(0).getObject().getNBT().getId();
                for (GuiNBTButton b : this.buttons) {
                    b.setEnabled(false);
                }
                this.buttons[type - 1].setEnabled(true);
                this.buttons[12].setEnabled(true);
                this.buttons[11].setEnabled(!(toFocus.getParent().getObject().getNBT() instanceof NBTTagList));
                this.buttons[13].setEnabled(true);
                this.buttons[14].setEnabled(true);
                this.buttons[15].setEnabled(clipboard != null && clipboard.getNBT().getId() == type);
            } else {
                for (GuiNBTButton b : this.buttons) {
                    b.setEnabled(true);
                }
            }
            this.buttons[11].setEnabled(!(toFocus.getParent().getObject().getNBT() instanceof NBTTagList));
            this.buttons[13].setEnabled(true);
            this.buttons[14].setEnabled(true);
            this.buttons[15].setEnabled(clipboard != null);
        } else {
            for (GuiNBTButton b : this.buttons) {
                b.setEnabled(false);
            }
            this.buttons[12].setEnabled(true);
            this.buttons[11].setEnabled(true);
            this.buttons[13].setEnabled(true);
            this.buttons[14].setEnabled(true);
            this.buttons[15].setEnabled(false);
        }
        this.focused = toFocus;
        if (this.focused != null && this.focusedSlotIndex != -1) {
            this.stopEditingSlot();
        }
    }

    public void initGUI() {
        this.initGUI(false);
    }

    public void initGUI(boolean shiftToFocused) {
        this.y = 30;
        this.nodes.clear();
        this.addNodes(this.tree.getRoot(), 10);
        this.addButtons();
        this.addSaveSlotButtons();
        if (this.focused != null && !this.checkValidFocus(this.focused)) {
            this.setFocused(null);
        }
        if (this.focusedSlotIndex != -1) {
            saves[this.focusedSlotIndex].startEditing();
        }
        this.heightDiff = this.getHeightDifference();
        if (this.heightDiff <= 0) {
            this.offset = 0;
        } else {
            if (this.offset < - this.heightDiff) {
                this.offset = - this.heightDiff;
            }
            if (this.offset > 0) {
                this.offset = 0;
            }
            for (GuiNBTNode node : this.nodes) {
                node.shift(this.offset);
            }
            if (shiftToFocused && this.focused != null) {
                this.shiftTo(this.focused);
            }
        }
    }

    private void addSaveSlotButtons() {
        for (int i = 0; i < 7; ++i) {
            GuiNBTTree.saves[i] = new GuiSaveSlotButton(nbtSaves.getSaveState(i), this.width - 15, 38 + i * 25);
        }
    }

    private void addButtons() {
        byte i;
        int x = 18;
        int y = 4;
        for (i = 14; i < 17; i = (byte)(i + 1)) {
            this.buttons[i - 1] = new GuiNBTButton(i, x, y);
            x += 15;
        }
        x += 30;
        for (i = 12; i < 14; i = (byte)(i + 1)) {
            this.buttons[i - 1] = new GuiNBTButton(i, x, y);
            x += 15;
        }
        x = 18;
        y = 17;
        for (i = 1; i < 12; i = (byte)(i + 1)) {
            this.buttons[i - 1] = new GuiNBTButton(i, x, y);
            x += 9;
        }
    }

    private boolean checkValidFocus(Node<NamedNBT> fc) {
        for (GuiNBTNode node : this.nodes) {
            if (node.getNode() != fc) continue;
            this.setFocused(fc);
            return true;
        }
        return fc.hasParent() ? this.checkValidFocus(fc.getParent()) : false;
    }

    private void addNodes(Node<NamedNBT> node, int x) {
        this.nodes.add(new GuiNBTNode(this, node, x, this.y));
        x += 10;
        this.y += this.Y_GAP;
        if (node.shouldDrawChildren()) {
            for (Node<NamedNBT> child : node.getChildren()) {
                this.addNodes(child, x);
            }
        }
    }

    public void draw(int mx, int my) {
        int cmx = mx;
        int cmy = my;
        if (this.window != null) {
            cmx = -1;
            cmy = -1;
        }
        for (GuiNBTNode node : this.nodes) {
            if (!node.shouldDraw(29, this.bottom)) continue;
            node.draw(cmx, cmy);
        }
        this.overlayBackground(0, 29, 255, 255);
        this.overlayBackground(this.bottom, this.height, 255, 255);
        for (GuiNBTButton but : this.buttons) {
            but.draw(cmx, cmy);
        }
        for (GuiSaveSlotButton but : saves) {
            but.draw(cmx, cmy);
        }
        this.drawScrollBar(cmx, cmy);
        if (this.window != null) {
            this.window.draw(mx, my);
        }
    }

    private void drawScrollBar(int mx, int my) {
        if (this.heightDiff > 0) {
            int y;
            if (Mouse.isButtonDown((int)0)) {
                if (this.yClick == -1) {
                    if (mx >= this.width - 10 && mx < this.width && my >= 29 && my < this.bottom) {
                        this.yClick = my;
                    }
                } else {
                    int length;
                    float scrollMultiplier = 1.0f;
                    int height = this.getHeightDifference();
                    if (height < 1) {
                        height = 1;
                    }
                    if ((length = (this.bottom - 29) * (this.bottom - 29) / this.getContentHeight()) < 32) {
                        length = 32;
                    }
                    if (length > this.bottom - 29 - 8) {
                        length = this.bottom - 29 - 8;
                    }
                    this.shift((int)((float)(this.yClick - my) * (scrollMultiplier /= (float)(this.bottom - 29 - length) / (float)height)));
                    this.yClick = my;
                }
            } else {
                this.yClick = -1;
            }
            GuiNBTTree.drawRect((int)(this.width - 10), (int)29, (int)this.width, (int)this.bottom, (int)Integer.MIN_VALUE);
            int length = (this.bottom - 29) * (this.bottom - 29) / this.getContentHeight();
            if (length < 32) {
                length = 32;
            }
            if (length > this.bottom - 29 - 8) {
                length = this.bottom - 29 - 8;
            }
            if ((y = (- this.offset) * (this.bottom - 29 - length) / this.heightDiff + 29) < 29) {
                y = 29;
            }
            this.drawGradientRect(this.width - 10, y, this.width, y + length, 1145324612, 1145324612);
        }
    }

    protected void overlayBackground(int par1, int par2, int par3, int par4) {
        Tessellator var5 = Tessellator.instance;
        this.mc.renderEngine.bindTexture(new ResourceLocation("textures/blocks/bedrock.png"));
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        float var6 = 32.0f;
        var5.startDrawingQuads();
        var5.setColorRGBA_I(4210752, par4);
        var5.addVertexWithUV(0.0, (double)par2, 0.0, 0.0, (double)((float)par2 / var6));
        var5.addVertexWithUV((double)this.width, (double)par2, 0.0, (double)((float)this.width / var6), (double)((float)par2 / var6));
        var5.setColorRGBA_I(4210752, par3);
        var5.addVertexWithUV((double)this.width, (double)par1, 0.0, (double)((float)this.width / var6), (double)((float)par1 / var6));
        var5.addVertexWithUV(0.0, (double)par1, 0.0, 0.0, (double)((float)par1 / var6));
        var5.draw();
    }

    public void mouseClicked(int mx, int my) {
        if (this.window == null) {
            boolean reInit = false;
            for (GuiNBTNode node : this.nodes) {
                if (!node.hideShowClicked(mx, my)) continue;
                reInit = true;
                if (!node.shouldDrawChildren()) break;
                this.offset = 31 - node.y + this.offset;
                break;
            }
            if (!reInit) {
                for (GuiNBTButton button : this.buttons) {
                    if (!button.inBounds(mx, my)) continue;
                    this.buttonClicked(button);
                    return;
                }
                for (GuiSaveSlotButton button : saves) {
                    if (button.inBoundsOfX(mx, my)) {
                        button.reset();
                        nbtSaves.save();
                        this.mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.func_147674_a((ResourceLocation)new ResourceLocation("gui.button.press"), (float)1.0f));
                        return;
                    }
                    if (!button.inBounds(mx, my)) continue;
                    this.saveButtonClicked((GuiSaveSlotButton)((Object)button));
                    return;
                }
                if (my >= 30 && mx <= this.width - 175) {
                    Node<NamedNBT> newFocus = null;
                    for (GuiNBTNode node : this.nodes) {
                        if (!node.clicked(mx, my)) continue;
                        newFocus = node.getNode();
                        break;
                    }
                    if (this.focusedSlotIndex != -1) {
                        this.stopEditingSlot();
                    }
                    this.setFocused(newFocus);
                }
            } else {
                this.initGUI();
            }
        } else {
            this.window.click(mx, my);
        }
    }

    private void saveButtonClicked(GuiSaveSlotButton button) {
        if (button.save.tag.hasNoTags()) {
            Node<NamedNBT> obj = this.focused == null ? this.tree.getRoot() : this.focused;
            NBTBase base = obj.getObject().getNBT();
            String name = obj.getObject().getName();
            if (base instanceof NBTTagList) {
                NBTTagList list = new NBTTagList();
                this.tree.addChildrenToList(obj, list);
                button.save.tag.setTag(name, (NBTBase)list);
            } else if (base instanceof NBTTagCompound) {
                NBTTagCompound compound = new NBTTagCompound();
                this.tree.addChildrenToTag(obj, compound);
                button.save.tag.setTag(name, (NBTBase)compound);
            } else {
                button.save.tag.setTag(name, base.copy());
            }
            button.saved();
            nbtSaves.save();
            this.mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.func_147674_a((ResourceLocation)new ResourceLocation("gui.button.press"), (float)1.0f));
        } else {
            Map<String, NBTBase> nbtMap = NBTHelper.getMap(button.save.tag);
            if (!nbtMap.isEmpty()) {
                if (this.focused == null) {
                    this.setFocused(this.tree.getRoot());
                }
                Map.Entry<String, NBTBase> firstEntry = null;
                Iterator<Map.Entry<String, NBTBase>> nameIt = nbtMap.entrySet().iterator();
                if (nameIt.hasNext()) {
                    Map.Entry<String, NBTBase> entry;
                    firstEntry = entry = nameIt.next();
                }
                String name = firstEntry.getKey();
                NBTBase nbt = firstEntry.getValue().copy();
                if (this.focused == this.tree.getRoot() && nbt instanceof NBTTagCompound && name.equals((Object)"ROOT")) {
                    this.setFocused(null);
                    this.tree = new NBTTree((NBTTagCompound)nbt);
                    this.initGUI();
                    this.mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.func_147674_a((ResourceLocation)new ResourceLocation("gui.button.press"), (float)1.0f));
                } else if (this.canAddToParent(this.focused.getObject().getNBT(), nbt)) {
                    this.focused.setDrawChildren(true);
                    Iterator<Node<NamedNBT>> it = this.focused.getChildren().iterator();
                    while (it.hasNext()) {
                        if (!it.next().getObject().getName().equals(name)) continue;
                        it.remove();
                        break;
                    }
                    Node<NamedNBT> node = this.insert(new NamedNBT((String)((Object)name), nbt));
                    this.tree.addChildrenToTree(node);
                    this.tree.sort(node);
                    this.setFocused(node);
                    this.initGUI(true);
                    this.mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.func_147674_a((ResourceLocation)new ResourceLocation("gui.button.press"), (float)1.0f));
                }
            }
        }
    }

    private void buttonClicked(GuiNBTButton button) {
        block4 : {
            block10 : {
                String type;
                List<Node<NamedNBT>> children;
                block9 : {
                    block8 : {
                        block7 : {
                            block6 : {
                                block5 : {
                                    block3 : {
                                        if (button.getId() != 16) break block3;
                                        this.paste();
                                        break block4;
                                    }
                                    if (button.getId() != 15) break block5;
                                    this.cut();
                                    break block4;
                                }
                                if (button.getId() != 14) break block6;
                                this.copy();
                                break block4;
                            }
                            if (button.getId() != 13) break block7;
                            this.deleteSelected();
                            break block4;
                        }
                        if (button.getId() != 12) break block8;
                        this.edit();
                        break block4;
                    }
                    if (this.focused == null) break block4;
                    this.focused.setDrawChildren(true);
                    children = this.focused.getChildren();
                    type = NBTStringHelper.getButtonName(button.getId());
                    if (!(this.focused.getObject().getNBT() instanceof NBTTagList)) break block9;
                    NBTBase nbt = NBTStringHelper.newTag(button.getId());
                    if (nbt == null) break block10;
                    Node<NamedNBT> newNode = new Node<NamedNBT>(this.focused, new NamedNBT("", nbt));
                    children.add(newNode);
                    this.setFocused(newNode);
                    break block10;
                }
                if (children.size() == 0) {
                    this.setFocused(this.insert(type + "1", button.getId()));
                } else {
                    for (int i = 1; i <= children.size() + 1; ++i) {
                        String name = type + i;
                        if (!this.validName(name, children)) continue;
                        this.setFocused(this.insert(name, button.getId()));
                        break;
                    }
                }
            }
            this.initGUI(true);
        }
    }

    private boolean validName(String name, List<Node<NamedNBT>> list) {
        for (Node<NamedNBT> node : list) {
            if (!node.getObject().getName().equals(name)) continue;
            return false;
        }
        return true;
    }

    private Node<NamedNBT> insert(NamedNBT nbt) {
        Node<NamedNBT> newNode = new Node<NamedNBT>(this.focused, nbt);
        if (this.focused.hasChildren()) {
            List<Node<NamedNBT>> children = this.focused.getChildren();
            boolean added = false;
            for (int i = 0; i < children.size(); ++i) {
                if (NBTTree.SORTER.compare(newNode, children.get(i)) >= 0) continue;
                children.add(i, newNode);
                added = true;
                break;
            }
            if (!added) {
                children.add(newNode);
            }
        } else {
            this.focused.addChild(newNode);
        }
        return newNode;
    }

    private Node<NamedNBT> insert(String name, byte type) {
        NBTBase nbt = NBTStringHelper.newTag(type);
        if (nbt != null) {
            return this.insert(new NamedNBT(name, nbt));
        }
        return null;
    }

    public void deleteSelected() {
        if (this.focused != null && this.tree.delete(this.focused)) {
            Node<NamedNBT> oldFocused = this.focused;
            this.shiftFocus(true);
            if (this.focused == oldFocused) {
                this.setFocused(null);
            }
            this.initGUI();
        }
    }

    public void editSelected() {
        if (this.focused != null) {
            NBTBase base = this.focused.getObject().getNBT();
            if (this.focused.hasChildren() && (base instanceof NBTTagCompound || base instanceof NBTTagList)) {
                this.focused.setDrawChildren(!this.focused.shouldDrawChildren());
                int index = -1;
                if (this.focused.shouldDrawChildren() && (index = this.indexOf(this.focused)) != -1) {
                    this.offset = 31 - this.nodes.get((int)index).y + this.offset;
                }
                this.initGUI();
            } else if (this.buttons[11].isEnabled()) {
                this.edit();
            }
        } else if (this.focusedSlotIndex != -1) {
            this.stopEditingSlot();
        }
    }

    private boolean canAddToParent(NBTBase parent, NBTBase child) {
        if (parent instanceof NBTTagCompound) {
            return true;
        }
        if (parent instanceof NBTTagList) {
            NBTTagList list = (NBTTagList)parent;
            return list.tagCount() == 0 || list.func_150303_d() == child.getId();
        }
        return false;
    }

    private boolean canPaste() {
        if (clipboard != null && this.focused != null) {
            return this.canAddToParent(this.focused.getObject().getNBT(), clipboard.getNBT());
        }
        return false;
    }

    private void paste() {
        if (clipboard != null) {
            this.focused.setDrawChildren(true);
            NamedNBT namedNBT = clipboard.copy();
            if (this.focused.getObject().getNBT() instanceof NBTTagList) {
                namedNBT.setName("");
                Node<NamedNBT> node = new Node<NamedNBT>(this.focused, namedNBT);
                this.focused.addChild(node);
                this.tree.addChildrenToTree(node);
                this.tree.sort(node);
                this.setFocused(node);
            } else {
                List<Node<NamedNBT>> children;
                String name = namedNBT.getName();
                if (!this.validName(name, children = this.focused.getChildren())) {
                    for (int i = 1; i <= children.size() + 1; ++i) {
                        String n = name + "(" + i + ")";
                        if (!this.validName(n, children)) continue;
                        namedNBT.setName(n);
                        break;
                    }
                }
                Node<NamedNBT> node = this.insert(namedNBT);
                this.tree.addChildrenToTree(node);
                this.tree.sort(node);
                this.setFocused(node);
            }
            this.initGUI(true);
        }
    }

    private void copy() {
        if (this.focused != null) {
            NamedNBT namedNBT = this.focused.getObject();
            if (namedNBT.getNBT() instanceof NBTTagList) {
                NBTTagList list = new NBTTagList();
                this.tree.addChildrenToList(this.focused, list);
                clipboard = new NamedNBT(namedNBT.getName(), (NBTBase)list);
            } else if (namedNBT.getNBT() instanceof NBTTagCompound) {
                NBTTagCompound compound = new NBTTagCompound();
                this.tree.addChildrenToTag(this.focused, compound);
                clipboard = new NamedNBT(namedNBT.getName(), (NBTBase)compound);
            } else {
                clipboard = this.focused.getObject().copy();
            }
            this.setFocused(this.focused);
        }
    }

    private void cut() {
        this.copy();
        this.deleteSelected();
    }

    private void edit() {
        NBTBase base = this.focused.getObject().getNBT();
        NBTBase parent = this.focused.getParent().getObject().getNBT();
        this.window = new GuiEditSingleNBT(this, this.focused, !(parent instanceof NBTTagList), !(base instanceof NBTTagCompound) && !(base instanceof NBTTagList));
        this.window.initGUI((this.width - 178) / 2, (this.height - 93) / 2);
    }

    public void nodeEdited(Node<NamedNBT> node) {
        Node<NamedNBT> parent = node.getParent();
        Collections.sort(parent.getChildren(), NBTTree.SORTER);
        this.initGUI(true);
    }

    public void arrowKeyPressed(boolean up) {
        if (this.focused == null) {
            this.shift(up ? this.Y_GAP : - this.Y_GAP);
        } else {
            this.shiftFocus(up);
        }
    }

    private int indexOf(Node<NamedNBT> node) {
        for (int i = 0; i < this.nodes.size(); ++i) {
            if (this.nodes.get(i).getNode() != node) continue;
            return i;
        }
        return -1;
    }

    private void shiftFocus(boolean up) {
        int index = this.indexOf(this.focused);
        if (index != -1 && (index += up ? -1 : 1) >= 0 && index < this.nodes.size()) {
            this.setFocused(this.nodes.get(index).getNode());
            this.shift(up ? this.Y_GAP : - this.Y_GAP);
        }
    }

    private void shiftTo(Node<NamedNBT> node) {
        int index = this.indexOf(node);
        if (index != -1) {
            GuiNBTNode gui = this.nodes.get(index);
            this.shift((this.bottom + 30 + 1) / 2 - (gui.y + gui.height));
        }
    }

    public void shift(int i) {
        if (this.heightDiff <= 0 || this.window != null) {
            return;
        }
        int dif = this.offset + i;
        if (dif > 0) {
            dif = 0;
        }
        if (dif < - this.heightDiff) {
            dif = - this.heightDiff;
        }
        for (GuiNBTNode node : this.nodes) {
            node.shift(dif - this.offset);
        }
        this.offset = dif;
    }

    public void closeWindow() {
        this.window = null;
    }

    public boolean isEditingSlot() {
        return this.focusedSlotIndex != -1;
    }

    public void stopEditingSlot() {
        saves[this.focusedSlotIndex].stopEditing();
        nbtSaves.save();
        this.focusedSlotIndex = -1;
    }

    public void rightClick(int mx, int my) {
        for (int i = 0; i < 7; ++i) {
            if (!saves[i].inBounds(mx, my)) continue;
            this.setFocused(null);
            if (this.focusedSlotIndex != -1) {
                if (this.focusedSlotIndex != i) {
                    saves[this.focusedSlotIndex].stopEditing();
                    nbtSaves.save();
                } else {
                    return;
                }
            }
            saves[i].startEditing();
            this.focusedSlotIndex = i;
            break;
        }
    }

    public void keyTyped(char ch, int key) {
        if (this.focusedSlotIndex != -1) {
            saves[this.focusedSlotIndex].keyTyped(ch, key);
        }
    }

    static {
        clipboard = null;
    }
}

