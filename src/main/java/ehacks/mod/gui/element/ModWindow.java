package ehacks.mod.gui.element;

import ehacks.mod.api.Module;
import ehacks.mod.api.ModuleController;
import ehacks.mod.gui.EHacksClickGui;
import ehacks.mod.gui.Tooltip;
import ehacks.mod.modulesystem.classes.keybinds.ShowGroupsKeybind;
import ehacks.mod.util.GLUtils;
import ehacks.mod.wrapper.ModuleCategory;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.HashSet;

public class ModWindow extends SimpleWindow {

    private final ModuleCategory windowCategory;
    public ArrayList<ArrayList<SimpleButton>> buttons = new ArrayList<>();
    public ArrayList<SimpleButton> buttonsSetup = new ArrayList<>();

    public ModWindow(String title, int x, int y, ModuleCategory windowCategory) {
        super(title, x, y);
        this.windowCategory = windowCategory;
        ModuleController.INSTANCE.modules.stream().filter((mod) -> !(mod.getCategory() != windowCategory)).forEachOrdered((mod) -> {
            this.addButton(mod);
        });
        this.setup();
    }

    public void addButton(Module mod) {
        this.buttonsSetup.add(new SimpleButton(this, mod, mod.getName(), mod.getModStatus().color, 0, 13 * this.buttons.size(), 86, 12));
    }

    public void setup() {
        for (int i = 0; i < buttonsSetup.size() / 20 + ((buttonsSetup.size() % 20 > 0) ? 1 : 0); i++) {
            buttons.add(new ArrayList<>());
        }
        for (int i = 0; i < buttonsSetup.size(); i++) {
            int col = i / (buttonsSetup.size() / buttons.size() + ((buttonsSetup.size() % buttons.size() > 0) ? 1 : 0));
            buttons.get(col).add(buttonsSetup.get(i));
        }
    }

    @Override
    public void draw(int x, int y) {
        this.setClientSize(this.buttons.size() * 86 + 2, 0);
        if (!this.isOpen() || !this.isExtended()) {
            super.draw(x, y);
            return;
        }

        Module hoverMod = null;
        boolean alwaysShowHover = true;

        if (Keyboard.isKeyDown(ShowGroupsKeybind.getKey())) {
            int ds = 0;
            HashSet<String> mods = new HashSet<>();
            for (ArrayList<SimpleButton> buttonList : this.buttons) {
                mods.clear();
                buttonList.forEach((button) -> {
                    mods.add(((Module) button.getHandler()).getModName());
                });
                ds = Math.max(ds, 12 * mods.size() + buttonList.size() * 12);
            }

            this.setClientSize(this.buttons.size() * 86 + 2, ds - 1);
            super.draw(x, y);
            int col = 0;
            for (ArrayList<SimpleButton> buttonList : this.buttons) {
                Module prevMod = null;
                int ty = 0;
                int tty = 0;
                int row = 0;
                for (SimpleButton button : buttonList) {
                    if (prevMod == null || !prevMod.getModName().equals(((Module) button.getHandler()).getModName())) {
                        SimpleButton tbutton = new SimpleButton(this, null, ((Module) button.getHandler()).getModName(), GLUtils.getColor(255, 255, 255), 86 * col + 1, ty + tty - 1, 86, 12);
                        tbutton.draw();
                        ty += 12;
                    }
                    tty += 12;
                    button.setState(((Module) button.getHandler()).isActive());
                    button.setPosition(col * 86 + 1, row * 12 + ty - 1);
                    button.draw();
                    if (button.isMouseOver(x, y)) {
                        hoverMod = ((Module) button.getHandler());
                    }
                    prevMod = ((Module) button.getHandler());
                    row++;
                }
                col++;
            }
        } else {
            int ysize = 0;
            for (ArrayList<SimpleButton> button1 : buttons) {
                ysize = Math.max(ysize, button1.size());
            }
            this.setClientSize(this.buttons.size() * 86 + 2, 12 * ysize - 1);
            super.draw(x, y);
            int col = 0;
            for (ArrayList<SimpleButton> buttonsCol : this.buttons) {
                int row = 0;
                for (SimpleButton button : buttonsCol) {
                    button.setState(((Module) button.getHandler()).isActive());
                    button.setPosition(col * 86 + 1, row * 12 - 1);
                    button.draw();
                    //noinspection ConstantConditions
                    if (alwaysShowHover && button.isMouseOver(x, y)) {
                        hoverMod = ((Module) button.getHandler());
                    }
                    row++;
                }
                col++;
            }
        }

        if (hoverMod != null) {
            EHacksClickGui.tooltip = new Tooltip("\u00A7b" + hoverMod.getName() + "\n\u00A7e" + hoverMod.getModName() + "\n\u00A7f" + hoverMod.getDescription(), x, y);
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
