package ehacks.mod.gui.element;

import ehacks.api.module.Module;
import ehacks.api.module.ModuleController;
import ehacks.mod.gui.EHacksClickGui;
import ehacks.mod.gui.Tooltip;
import ehacks.mod.util.GLUtils;
import ehacks.mod.wrapper.Keybinds;
import ehacks.mod.wrapper.ModuleCategory;
import java.util.ArrayList;
import java.util.HashSet;
import org.lwjgl.input.Keyboard;

public class ModWindow extends SimpleWindow {

    public ArrayList<ArrayList<SimpleButton>> buttons = new ArrayList();
    public ArrayList<SimpleButton> buttonsSetup = new ArrayList();
    private ModuleCategory windowCategory;

    public ModWindow(String title, int x, int y, ModuleCategory windowCategory) {
        super(title, x, y);
        this.windowCategory = windowCategory;
        for (Module mod : ModuleController.INSTANCE.modules) {
            if (mod.getCategory() != windowCategory) {
                continue;
            }
            this.addButton(mod);
        }
        this.setup();
    }

    public void addButton(Module mod) {
        this.buttonsSetup.add(new SimpleButton(this, mod, mod.getName(), mod.getModStatus().color, 0, 13 * this.buttons.size(), 86, 12));
    }

    public void setup() {
        for (int i = 0; i < buttonsSetup.size() / 20 + ((buttonsSetup.size() % 20 > 0) ? 1 : 0); i++) {
            buttons.add(new ArrayList());
        }
        for (int i = 0; i < buttonsSetup.size(); i++) {
            int col = i / (buttonsSetup.size() / buttons.size() + ((buttonsSetup.size() % buttons.size() > 0) ? 1 : 0));
            buttons.get(col).add(buttonsSetup.get(i));
        }
    }

    @Override
    public void draw(int x, int y) {
        this.setClientSize(this.buttons.size() * 88, 0);
        if (this.isOpen()) {
            Module hoverMod = null;
            if (this.isExtended()) {
                if (Keyboard.isKeyDown(Keybinds.showGroups)) {
                    int ds = 0;
                    HashSet<String> mods = new HashSet();
                    for (ArrayList<SimpleButton> buttonList : this.buttons) {
                        mods.clear();
                        for (SimpleButton button : buttonList) {
                            mods.add(((Module) button.getHandler()).getModName());
                        }
                        ds = Math.max(ds, 13 * mods.size() + 2 * Math.max(0, mods.size() - 1) + buttonList.size() * 13);
                    }
                    this.setClientSize(this.buttons.size() * 88, ds + 1);
                    super.draw(x, y);
                    int col = 0;
                    for (ArrayList<SimpleButton> buttonList : this.buttons) {
                        Module prevMod = null;
                        int ty = 0;
                        int tty = 0;
                        int row = 0;
                        for (SimpleButton button : buttonList) {
                            if (prevMod == null || !prevMod.getModName().equals(((Module) button.getHandler()).getModName())) {
                                if (prevMod != null) {
                                    ty += 2;
                                }
                                SimpleButton tbutton = new SimpleButton(this, null, ((Module) button.getHandler()).getModName(), GLUtils.getColor(255, 255, 255), 88 * col + 1, ty + tty + 1, 86, 12);
                                tbutton.draw();
                                ty += 13;
                            }
                            tty += 13;
                            button.setState(((Module) button.getHandler()).isActive());
                            button.setPosition(col * 88 + 1, row * 13 + ty + 1);
                            button.draw();
                            if (button.isMouseOver(x, y)) {
                                hoverMod = ((Module) button.getHandler());
                            }
                            prevMod = ((Module) button.getHandler());
                            row++;
                        }
                        col++;
                    }
                    if (hoverMod != null) {
                        EHacksClickGui.tooltip = new Tooltip("\u00A7b" + hoverMod.getName() + "\n\u00A7e" + hoverMod.getModName() + "\n\u00A7f" + hoverMod.getDescription(), x, y);
                    }
                } else {
                    int ysize = 0;
                    for (int i = 0; i < buttons.size(); i++) {
                        ysize = Math.max(ysize, buttons.get(i).size());
                    }
                    this.setClientSize(this.buttons.size() * 88, 13 * ysize + 1);
                    super.draw(x, y);
                    int col = 0;
                    for (ArrayList<SimpleButton> buttonsCol : this.buttons) {
                        int row = 0;
                        for (SimpleButton button : buttonsCol) {
                            button.setState(((Module) button.getHandler()).isActive());
                            button.setPosition(col * 88 + 1, row * 13 + 1);
                            button.draw();
                            row++;
                        }
                        col++;
                    }
                }
            } else {
                super.draw(x, y);
            }
        }
    }

    @Override
    public boolean mouseClicked(int x, int y, int button) {
        boolean retval = super.mouseClicked(x, y, button);
        buttonloops:
        for (ArrayList<SimpleButton> buttonsCol : this.buttons) {
            for (SimpleButton xButton : buttonsCol) {
                if (xButton.mouseClicked(x, y, button)) {
                    retval = true;
                    break buttonloops;
                }
            }
        }
        return retval;
    }
}
