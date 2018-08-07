package ehacks.mod.gui.reeszrbteam.window;

import net.minecraft.client.Minecraft;
import ehacks.api.module.ModController;
import ehacks.mod.gui.reeszrbteam.element.YAWWindow;
import ehacks.mod.util.GLUtils;
import java.util.ArrayList;

public class WindowActives
extends YAWWindow {
    public WindowActives() {
        super("Enabled", 2, 300);
        this.buttons = new ArrayList();
        this.buttons.add(new ArrayList());
    }

    @Override
    public void draw(int x, int y) {
        if (this.isOpen()) {
            if (this.dragging) {
                this.windowDragged(x, y);
            }
            GLUtils.drawGradientBorderedRect(this.getX() + this.dragX, this.getY() + this.dragY, this.getX() + 90 + this.dragX, this.getY() + 12 + this.dragY, 0.5f, -16777216, -6710887, -8947849);
            Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(this.getTitle(), this.getX() + 2 + this.dragX, this.getY() + this.dragY + 2, 5636095);
            GLUtils.drawGradientBorderedRect(this.getX() + 70 + this.dragX, this.getY() + 2 + this.dragY, this.getX() + 78 + this.dragX, this.getY() + 10 + this.dragY, 1.0f, -10066330, this.isPinned() ? -8947849 : -7829368, this.isPinned() ? -11184811 : -10066330);
            GLUtils.drawGradientBorderedRect(this.getX() + 80 + this.dragX, this.getY() + 2 + this.dragY, this.getX() + 88 + this.dragX, this.getY() + 10 + this.dragY, 1.0f, -10066330, this.isExtended() ? -8947849 : -7829368, this.isExtended() ? -11184811 : -10066330);
            if (this.isExtended()) {
                int i;
                int tsz = 0;
                for (i = 0; i < ModController.INSTANCE.mods.size(); i++) {
                    if (ModController.INSTANCE.mods.get(i).isActive())
                        tsz++;
                }
                if (tsz > 0)
                {
                    GLUtils.drawGradientBorderedRect(this.getX() + this.dragX, this.getY() + 14 + this.dragY, this.getX() + 90 + this.dragX, this.getY() + 14 + this.dragY + tsz * 12, 0.5f, -16777216, -6710887, -8947849);
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
        }
    }
}

