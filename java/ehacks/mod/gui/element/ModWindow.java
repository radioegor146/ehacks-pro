package ehacks.mod.gui.element;

import ehacks.api.module.DummyMod;
import ehacks.api.module.ModController;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import ehacks.api.module.Module;
import ehacks.mod.gui.EHacksClickGui;
import ehacks.mod.gui.Tooltip;
import ehacks.mod.util.GLUtils;
import ehacks.mod.wrapper.Keybinds;
import ehacks.mod.wrapper.ModuleCategory;
import java.util.HashSet;
import org.lwjgl.input.Keyboard;

public class ModWindow extends SimpleWindow {
    public ArrayList<ArrayList<ModButton>> buttons = new ArrayList();
    public ArrayList<ModButton> buttonsSetup = new ArrayList();
    private ModuleCategory windowCategory;
    
    public ModWindow(String title, int x, int y, ModuleCategory windowCategory) {
        super(title, x, y);
        this.windowCategory = windowCategory;
    }
    
    @Override
    public void init() {
        for (Module mod : ModController.INSTANCE.mods) {
            if (mod.getCategory() != windowCategory) continue;
            this.addButton(mod);
        }
        this.setup();
    }

    public void addButton(Module mod) {
        this.buttonsSetup.add(new ModButton(this, mod, this.xPos + 2, this.yPos + 11 * this.buttons.size() + 16));
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
        this.width = this.buttons.size() * 88;
        if (this.isOpen()) {
            if (this.dragging) {
                this.windowDragged(x, y);
            }
            Module hoverMod = null;
            if (this.isExtended) {
                if (Keyboard.isKeyDown(Keybinds.showGroups))
                {
                    int ds = 0;
                    HashSet<String> mods = new HashSet<String>();
                    for (ArrayList<ModButton> buttonList : this.buttons)
                    {
                        mods.clear();
                        for (ModButton button : buttonList)
                            mods.add(button.mod.getModName());
                        ds = Math.max(ds, 11 * mods.size() + 2 * Math.max(0, mods.size() - 1) + buttonList.size() * 11);
                    }
                    this.height = (4.5 + ds);
                    super.draw(x, y);
                    int col = 0;
                    for (ArrayList<ModButton> buttonList : this.buttons)
                    {
                        Module prevMod = null;
                        int ty = 0;
                        int tty = 0;
                        for (ModButton button : buttonList) {
                            if (prevMod == null || !prevMod.getModName().equals(button.mod.getModName()))
                            {
                                if (prevMod != null)
                                    ty += 2;
                                ModButton tbutton = new ModButton(this, new DummyMod(button.mod.getModName()), this.xPos + 2 + 88 * col, this.yPos + ty + 16 + tty);
                                tbutton.draw();
                                ty += 11;
                            }
                            tty += 11;
                            button.drawShift(0, ty);
                            button.shiftY = ty;
                            if (button.isMouseOver(x, y))
                                hoverMod = button.mod;
                            prevMod = button.mod;
                        }
                        col++;
                    }
                    if (hoverMod != null) {
                        EHacksClickGui.tooltip = new Tooltip("\u00A7b" + hoverMod.getName() + "\n\u00A7e" + hoverMod.getModName() + "\n\u00A7f" + hoverMod.getDescription(), x, y);
                    }
                }
                else
                {
                    int ysize = 0;
                    for (int i = 0; i < buttons.size(); i++)
                        ysize = Math.max(ysize, buttons.get(i).size());
                    this.height = (11 * ysize + 4.5);
                    super.draw(x, y);
                    for (ArrayList<ModButton> buttonsCol : this.buttons)
                        for (ModButton button : buttonsCol) {
                            button.draw();
                            button.shiftY = 0;
                        }
                }
            }
            else {
                super.draw(x, y);
            }
        }
    }

    @Override
    public boolean mouseClicked(int x, int y, int button) {
        boolean retval = false;
        buttonloops:
        for (ArrayList<ModButton> buttonsCol : this.buttons)
            for (ModButton xButton : buttonsCol)
                if (xButton.mouseClicked(x, y, button))
                {
                    retval = true;
                    break buttonloops;
                }
        retval &= super.mouseClicked(x, y, button);
        return retval;
    }
}

