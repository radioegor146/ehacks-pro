package ehacks.mod.gui.window;

import ehacks.mod.external.config.LocalConfigStorage;
import ehacks.mod.gui.element.SimpleWindow;

public class WindowFakeExportConfig
        extends SimpleWindow {

    public WindowFakeExportConfig() {
        super("Export config", 600, 300);
    }

    @Override
    public void setOpen(boolean state) {
        if (state) {
            LocalConfigStorage.exportConfig();
        }
    }
}
