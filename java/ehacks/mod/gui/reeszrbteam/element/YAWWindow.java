package ehacks.mod.gui.reeszrbteam.element;

import ehacks.api.module.DummyMod;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import ehacks.api.module.Mod;
import ehacks.mod.gui.reeszrbteam.YouAlwaysWinClickGui;
import ehacks.mod.util.GLUtils;
import ehacks.mod.wrapper.Keybinds;
import java.util.HashSet;
import org.lwjgl.input.Keyboard;

public class YAWWindow {
    public String title;
    public int xPos;
    public int yPos;
    private boolean isOpen;
    public boolean isExtended;
    public boolean isPinned;
    public int dragX;
    public int dragY;
    public int lastDragX;
    public int lastDragY;
    protected boolean dragging;
    public ArrayList<ArrayList<YAWButton>> buttons = new ArrayList();
    public ArrayList<YAWButton> buttonsSetup = new ArrayList();
    private int buttonCount = 0;
    private int sliderCount = 0;

    public YAWWindow(String title, int x, int y) {
        this.title = title;
        this.xPos = x;
        this.yPos = y;
        YouAlwaysWinClickGui.windows.add(this);
        YouAlwaysWinClickGui.unFocusedWindows.add(this);
    }

    public void windowDragged(int x, int y) {
        this.dragX = x - this.lastDragX;
        this.dragY = y - this.lastDragY;
    }

    public void addButton(Mod mod) {
        this.buttonsSetup.add(new YAWButton(this, mod, this.xPos + 2, this.yPos + 11 * this.buttons.size() + 16));
    }
    
    public void setup() {
        for (int i = 0; i < buttonsSetup.size() / 20 + ((buttonsSetup.size() % 20 > 0) ? 1 : 0); i++)
            buttons.add(new ArrayList());
        for (int i = 0; i < buttonsSetup.size(); i++)
        {
            int col = i / (buttonsSetup.size() / buttons.size() + ((buttonsSetup.size() % buttons.size() > 0) ? 1 : 0));
            buttonsSetup.get(i).xPos = this.xPos + 2 + 88 * col;
            buttonsSetup.get(i).yPos = this.yPos + 11 * buttons.get(col).size() + 16;
            buttons.get(col).add(buttonsSetup.get(i));
        }
    }

    public void draw(int x, int y) {
        if (this.isOpen) {
            if (this.dragging) {
                this.windowDragged(x, y);
            }
            GLUtils.drawGradientBorderedRect(this.xPos + this.dragX, this.yPos + this.dragY, this.xPos + 3 + this.dragX + this.buttons.size() * 88, this.yPos + 12 + this.dragY, 0.5f, -16777216, -812017255, -814254217);
            Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(this.title, this.xPos + 2 + this.dragX, 2 + this.yPos + this.dragY, 5636095);
            GLUtils.drawGradientBorderedRect(this.xPos + this.buttons.size() * 88 - 18 + this.dragX, this.yPos + 2 + this.dragY, this.xPos + this.buttons.size() * 88 - 10 + this.dragX, this.yPos + 10 + this.dragY, 1.0f, -10066330, this.isPinned ? -8947849 : -7829368, this.isPinned ? -11184811 : -10066330);
            GLUtils.drawGradientBorderedRect(this.xPos + this.buttons.size() * 88 - 8 + this.dragX, this.yPos + 2 + this.dragY, this.xPos + this.buttons.size() * 88 + this.dragX, this.yPos + 10 + this.dragY, 1.0f, -10066330, this.isExtended ? -8947849 : -7829368, this.isExtended ? -11184811 : -10066330);
            if (this.isExtended) {
                if (Keyboard.isKeyDown(Keybinds.showGroups))
                {
                    int ds = 0;
                    HashSet<String> mods = new HashSet<String>();
                    for (ArrayList<YAWButton> buttonList : this.buttons)
                    {
                        mods.clear();
                        for (YAWButton button : buttonList)
                            mods.add(button.mod.getModName());
                        ds = Math.max(ds, 11 * mods.size() + 2 * Math.max(0, mods.size() - 1) + buttonList.size() * 11);
                    }
                    GLUtils.drawGradientBorderedRect(this.xPos + this.dragX, this.yPos + 14 + this.dragY, this.xPos + 3 + this.dragX + this.buttons.size() * 88, this.yPos + (19 + ds) + this.dragY, 0.5f, -16777216, -812017255, -814254217);
                    int col = 0;
                    for (ArrayList<YAWButton> buttonList : this.buttons)
                    {
                        Mod prevMod = null;
                        int ty = 0;
                        int tty = 0;
                        for (YAWButton button : buttonList) {
                            if (prevMod == null || !prevMod.getModName().equals(button.mod.getModName()))
                            {
                                if (prevMod != null)
                                    ty += 2;
                                YAWButton tbutton = new YAWButton(this, new DummyMod(button.mod.getModName()), this.xPos + 2 + 88 * col, this.yPos + ty + 16 + tty);
                                tbutton.draw();
                                ty += 11;
                            }
                            tty += 11;
                            button.drawShift(0, ty);
                            prevMod = button.mod;
                        }
                        col++;
                    }
                }
                else
                {
                    int ysize = 0;
                    for (int i = 0; i < buttons.size(); i++)
                        ysize = Math.max(ysize, buttons.get(i).size());
                    GLUtils.drawGradientBorderedRect(this.xPos + this.dragX, this.yPos + 14 + this.dragY, this.xPos + 3 + this.dragX + this.buttons.size() * 88, this.yPos + (11 * ysize + 19) + this.dragY, 0.5f, -16777216, -812017255, -814254217);
                    for (ArrayList<YAWButton> buttonsCol : this.buttons)
                        for (YAWButton button : buttonsCol)
                            button.draw();
                }
            }
        }
    }

    public void mouseClicked(int x, int y, int button) {
        for (ArrayList<YAWButton> buttonsCol : this.buttons)
            for (YAWButton xButton : buttonsCol)
                xButton.mouseClicked(x, y, button);
        if (x >= this.xPos + this.buttons.size() * 88 - 8 + this.dragX && y >= this.yPos + 2 + this.dragY && x <= this.xPos + this.buttons.size() * 88 + this.dragX && y <= this.yPos + 10 + this.dragY) {
            boolean bl = this.isExtended = !this.isExtended;
        }
        if (x >= this.xPos + this.buttons.size() * 88 - 18 + this.dragX && y >= this.yPos + 2 + this.dragY && x <= this.xPos + this.buttons.size() * 88 - 10 + this.dragX && y <= this.yPos + 10 + this.dragY) {
            boolean bl = this.isPinned = !this.isPinned;
        }
        if (x >= this.xPos + this.dragX && y >= this.yPos + this.dragY && x <= this.xPos + this.buttons.size() * 88 - 19 + this.dragX && y <= this.yPos + 12 + this.dragY) {
            YouAlwaysWinClickGui.sendPanelToFront(this);
            this.dragging = !this.dragging;
            this.lastDragX = x - this.dragX;
            this.lastDragY = y - this.dragY;
        }
    }

    public void mouseMovedOrUp(int x, int y, int b2) {
        if (b2 == 0) {
            this.dragging = false;
        }
    }

    public final String getTitle() {
        return this.title;
    }

    public final int getX() {
        return this.xPos;
    }

    public final int getY() {
        return this.yPos;
    }

    public boolean isExtended() {
        return this.isExtended;
    }

    public boolean isOpen() {
        return this.isOpen;
    }

    public boolean isPinned() {
        return this.dragging ? false : this.isPinned;
    }

    public void setOpen(boolean flag) {
        this.isOpen = flag;
    }

    public void setExtended(boolean flag) {
        this.isExtended = flag;
    }

    public void setPinned(boolean flag) {
        this.isPinned = flag;
    }

    public void setX(int xPos) {
        this.dragX = xPos;
    }

    public void setY(int yPos) {
        this.dragY = yPos;
    }
}

