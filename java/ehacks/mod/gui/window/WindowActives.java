package ehacks.mod.gui.window;

import ehacks.api.module.ModuleController;
import ehacks.mod.gui.element.SimpleWindow;
import ehacks.mod.wrapper.Wrapper;

public class WindowActives
        extends SimpleWindow {

    public WindowActives() {
        super("Enabled", 2, 300);
        this.setClientSize(88, 0);
    }

    @Override
    public void draw(int x, int y) {
        if (this.isOpen()) {
            if (this.isExtended()) {
                int i;
                int tsz = 0;
                for (i = 0; i < ModuleController.INSTANCE.modules.size(); i++) {
                    if (ModuleController.INSTANCE.modules.get(i).isActive()) {
                        tsz++;
                    }
                }
                this.setClientSize(88, tsz * 12 - 2);
                super.draw(x, y);
                if (tsz > 0) {
                    int ti = 0;
                    for (i = 0; i < ModuleController.INSTANCE.modules.size(); i++) {
                        if (ModuleController.INSTANCE.modules.get(i).isActive()) {
                            Wrapper.INSTANCE.fontRenderer().drawStringWithShadow(ModuleController.INSTANCE.modules.get(i).getName(), this.getClientX() + 1, this.getClientY() + ti * 12 + 1, 5636095);
                            ti++;
                        }
                    }
                }
            } else {
                super.draw(x, y);
            }
        }
    }
}
