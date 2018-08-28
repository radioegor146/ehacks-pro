package ehacks.mod.gui.window;

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
                this.setClientSize(88, 48);
                super.draw(x, y);
                Wrapper.INSTANCE.fontRenderer().drawStringWithShadow(Wrapper.INSTANCE.mc().debug.split(",")[0].toUpperCase(), this.getClientX(), this.getClientY(), 5636095);
                Wrapper.INSTANCE.fontRenderer().drawStringWithShadow("X: " + (int) Wrapper.INSTANCE.player().posX, this.getClientX(), this.getClientY() + 12, 5636095);
                Wrapper.INSTANCE.fontRenderer().drawStringWithShadow("Y: " + (int) Wrapper.INSTANCE.player().posY, this.getClientX(), this.getClientY() + 24, 5636095);
                Wrapper.INSTANCE.fontRenderer().drawStringWithShadow("Z: " + (int) Wrapper.INSTANCE.player().posZ, this.getClientX(), this.getClientY() + 36, 5636095);
            } else {
                super.draw(x, y);
            }
        }
    }
}
