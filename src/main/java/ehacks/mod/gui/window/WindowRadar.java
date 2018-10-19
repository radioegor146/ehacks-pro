package ehacks.mod.gui.window;

import ehacks.mod.gui.EHacksClickGui;
import ehacks.mod.gui.element.SimpleWindow;
import ehacks.mod.wrapper.Wrapper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class WindowRadar
        extends SimpleWindow {

    public WindowRadar() {
        super("Radar", 94, 300);
        this.setClientSize(88, 0);
    }

    @Override
    public void draw(int x, int y) {
        if (this.isOpen()) {
            if (this.isExtended()) {
                int rect = 0;
                for (Object o : Wrapper.INSTANCE.world().playerEntities) {
                    if (!(o == Wrapper.INSTANCE.player() || ((Entity)o).isDead))
                        rect += 12;
                }
                if (rect == 0) {
                    this.setClientSize(88, 9);
                    super.draw(x, y);
                    Wrapper.INSTANCE.fontRenderer().drawStringWithShadow("No one in range.", this.getClientX() + 1, this.getClientY() + 1, EHacksClickGui.mainColor);
                    return;
                }
                this.setClientSize(88, rect - 3);
                super.draw(x, y);
                int count = 0;
                for (Object o : Wrapper.INSTANCE.world().playerEntities) {
                    EntityPlayer e = (EntityPlayer) o;
                    if (e == Wrapper.INSTANCE.player() || e.isDead) {
                        continue;
                    }
                    int distance = (int) Wrapper.INSTANCE.player().getDistanceToEntity(e);
                    String text;
                    if (distance <= 20) {
                        text = "\u00a7c" + e.getDisplayName() + "\u00a7f: " + distance;
                    } else if (distance <= 50) {
                        text = "\u00a76" + e.getDisplayName() + "\u00a7f: " + distance;
                    } else {
                        text = "\u00a7a" + e.getDisplayName() + "\u00a7f: " + distance;
                    }
                    int xPosition = this.getClientX() + 1;
                    int yPosition = this.getClientY() + 12 * count + 1;
                    Wrapper.INSTANCE.fontRenderer().drawStringWithShadow(text, xPosition, yPosition, EHacksClickGui.mainColor);
                    ++count;
                }
            } else {
                super.draw(x, y);
            }
        }
    }
}
