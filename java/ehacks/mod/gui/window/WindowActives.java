package ehacks.mod.gui.window;

import net.minecraft.client.Minecraft;
import ehacks.api.module.ModController;
import ehacks.mod.gui.element.ModWindow;
import ehacks.mod.gui.element.SimpleWindow;
import ehacks.mod.util.GLUtils;
import java.util.ArrayList;

public class WindowActives
extends SimpleWindow {
    public WindowActives() {
        super("Enabled", 2, 300);
        this.width = 88;
        this.height = 0;
    }

    @Override
    public void draw(int x, int y) {
        if (this.isOpen()) {
            if (this.dragging) {
                this.windowDragged(x, y);
            }
            if (this.isExtended()) {
                int i;
                int tsz = 0;
                for (i = 0; i < ModController.INSTANCE.mods.size(); i++) {
                    if (ModController.INSTANCE.mods.get(i).isActive())
                        tsz++;
                }
                if (tsz > 0)
                {
                    this.width = 88;
                    this.height = tsz * 12;
                    super.draw(x, y);
                    int ti = 0;
                    for (i = 0; i < ModController.INSTANCE.mods.size(); i++) {
                        if (ModController.INSTANCE.mods.get(i).isActive())
                        {
                            Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(ModController.INSTANCE.mods.get(i).getName(), this.getX() + 2 + this.dragX, this.getY() + this.dragY + 16 + ti * 12, 5636095);
                            ti++;
                        }
                    }
                }
            }
            else
                super.draw(x, y);
        }
    }
}

