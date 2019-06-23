package ehacks.mod.gui.window;

import ehacks.mod.api.ModStatus;
import ehacks.mod.gui.element.SimpleButton;
import ehacks.mod.gui.element.SimpleWindow;
import ehacks.mod.modulesystem.handler.EHacksGui;
import ehacks.mod.wrapper.Wrapper;

import java.util.ArrayList;
import java.util.List;

public class WindowHub
        extends SimpleWindow {

    private final List<SimpleButton> buttons = new ArrayList<>();

    public WindowHub() {
        super("Gui Hub", 2, 2);
        this.setOpen(true);
        this.setExtended(true);
        int y = 0;
        for (SimpleWindow window : EHacksGui.clickGui.windows) {
            Wrapper.INSTANCE.mc().player.sendChatMessage(String.format("Window %s", window.getClass()));
            if (window != this) {
                buttons.add(new SimpleButton(this, window, window.getTitle(), ModStatus.DEFAULT.color, 1, y * 12 - 1, 86, 12));
                y++;
            }
        }
    }

    @Override
    public void draw(int x, int y) {
        this.setOpen(true);
        this.setClientSize(88, buttons.size() * 12 - 1);
        super.draw(x, y);
        if (this.isExtended()) {
            buttons.stream().map((button) -> {
                button.setState(((SimpleWindow) button.getHandler()).isOpen());
                return button;
            }).forEachOrdered((button) -> {
                button.draw();
            });
        }
    }

    @Override
    public boolean mouseClicked(int x, int y, int button) {
        boolean retval = super.mouseClicked(x, y, button);
        for (SimpleButton xbutton : buttons) {
            if (xbutton.mouseClicked(x, y, button)) {
                retval = true;
                break;
            }
        }
        return retval;
    }
}
