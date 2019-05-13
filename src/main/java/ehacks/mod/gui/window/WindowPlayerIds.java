package ehacks.mod.gui.window;

import ehacks.mod.api.ModStatus;
import ehacks.mod.gui.Tuple;
import ehacks.mod.gui.element.IClickable;
import ehacks.mod.gui.element.SimpleButton;
import ehacks.mod.gui.element.SimpleWindow;
import ehacks.mod.util.GLUtils;
import ehacks.mod.wrapper.Wrapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.entity.player.EntityPlayer;

public class WindowPlayerIds
        extends SimpleWindow {

    public static HashMap<String, Tuple<Integer, EntityPlayer>> players = new HashMap<>();
    public static boolean useIt = false;
    private final SimpleButton useItButton;

    private class ButtonHandler implements IClickable {

        @Override
        public void onButtonClick() {
            useIt = !useIt;
        }
    }

    public WindowPlayerIds() {
        super("Saved Players", 186, 300);
        this.useItButton = new SimpleButton(this, new ButtonHandler(), "Use it", ModStatus.DEFAULT.color, 1, 1, 198, 12);
    }

    public static List<EntityPlayer> getPlayers() {
        ArrayList<EntityPlayer> retPlayers = new ArrayList<>();
        players.entrySet().forEach((entry) -> {
            retPlayers.add(entry.getValue().y);
        });
        return retPlayers;
    }

    @Override
    public void draw(int x, int y) {
        if (this.isOpen()) {
            this.setClientSize(200, players.size() * 12 + 14);
            super.draw(x, y);
            if (this.isExtended()) {
                useItButton.draw();
                int i = 0;
                for (Map.Entry<String, Tuple<Integer, EntityPlayer>> entry : players.entrySet()) {
                    int color = GLUtils.getColor(255 - Math.min(64, entry.getValue().x), 255 - Math.min(64, entry.getValue().x), 255 - Math.min(64, entry.getValue().x));
                    Wrapper.INSTANCE.fontRenderer().drawStringWithShadow(entry.getKey() + ": 0x" + Integer.toHexString(entry.getValue().y.getEntityId()), this.getClientX() + 1, this.getClientY() + i * 12 + 16, color);
                    entry.getValue().x++;
                    i++;
                }
            }
        }
    }

    @Override
    public boolean mouseClicked(int x, int y, int button) {
        boolean retval = super.mouseClicked(x, y, button);
        retval |= useItButton.mouseClicked(x, y, button);
        return retval;
    }

}
