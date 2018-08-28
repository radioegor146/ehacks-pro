package ehacks.mod.gui.window;

import ehacks.mod.external.config.LocalConfigStorage;
import ehacks.mod.gui.element.SimpleWindow;

public class WindowFakeImportConfig
        extends SimpleWindow {

    public WindowFakeImportConfig() {
        super("Import config", 600, 300);
    }

    @Override
    public void setOpen(boolean state) {
        if (state) {
            LocalConfigStorage.importConfig();
        }
    }
}
