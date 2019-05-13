package ehacks.mod.gui.window;

import ehacks.mod.gui.EHacksClickGui;
import ehacks.mod.gui.element.SimpleWindow;
import ehacks.mod.wrapper.Wrapper;

public class WindowInfo
        extends SimpleWindow {

    public WindowInfo() {
        super("Info", 554, 2);
        this.setClientSize(88, 48);
    }

    @Override
    public void draw(int x, int y) {
        if (this.isOpen()) {
            if (this.isExtended()) {
                this.setClientSize(88, 45);
                super.draw(x, y);
                Wrapper.INSTANCE.fontRenderer().drawStringWithShadow(Wrapper.INSTANCE.mc().debug.split(",")[0].toUpperCase(), this.getClientX() + 1, this.getClientY() + 1, EHacksClickGui.mainColor);
                Wrapper.INSTANCE.fontRenderer().drawStringWithShadow("X: " + (int) Wrapper.INSTANCE.player().posX, this.getClientX() + 1, this.getClientY() + 12 + 1, EHacksClickGui.mainColor);
                Wrapper.INSTANCE.fontRenderer().drawStringWithShadow("Y: " + (int) Wrapper.INSTANCE.player().posY, this.getClientX() + 1, this.getClientY() + 24 + 1, EHacksClickGui.mainColor);
                Wrapper.INSTANCE.fontRenderer().drawStringWithShadow("Z: " + (int) Wrapper.INSTANCE.player().posZ, this.getClientX() + 1, this.getClientY() + 36 + 1, EHacksClickGui.mainColor);
            } else {
                super.draw(x, y);
            }
        }
    }
}
