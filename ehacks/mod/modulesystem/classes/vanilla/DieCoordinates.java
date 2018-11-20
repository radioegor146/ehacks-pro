package ehacks.mod.modulesystem.classes.vanilla;

import ehacks.mod.api.Module;
import ehacks.mod.util.InteropUtils;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;

public class DieCoordinates
        extends Module {

    private int countdown = 80;

    public DieCoordinates() {
        super(ModuleCategory.PLAYER);
    }

    @Override
    public String getName() {
        return "DieCoordinates";
    }

    @Override
    public String getDescription() {
        return "Show coordinates in chat when you die";
    }

    @Override
    public void onTicks() {
        if (Wrapper.INSTANCE.player().isDead && this.countdown == 1) {
            this.countdown = (int) (8.0 * Math.random());
            InteropUtils.log("Coordinates on death: x:" + (int) Wrapper.INSTANCE.player().posX + " y:" + (int) Wrapper.INSTANCE.player().posY + " z:" + (int) Wrapper.INSTANCE.player().posZ, this);
        }
        if (!Wrapper.INSTANCE.player().isDead) {
            this.countdown = 1;
        }
    }
}
