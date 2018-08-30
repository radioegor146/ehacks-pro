package ehacks.mod.modulesystem.classes;

import ehacks.api.module.Module;
import ehacks.mod.util.InteropUtils;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;

public class DieCoordinates
        extends Module {

    int countdown = 80;

    public DieCoordinates() {
        super(ModuleCategory.PLAYER);
    }

    @Override
    public String getName() {
        return "DieCoordinates";
    }

    @Override
    public String getDescription() {
        return "Show coordinates on chat when player dies";
    }

    @Override
    public void onTicks() {
        if (Wrapper.INSTANCE.player().isDead && this.countdown == 1) {
            this.countdown = (int) (8.0 * Math.random());
            InteropUtils.log("Coordinates on player dead: x:" + (int) Wrapper.INSTANCE.player().posX + " y:" + (int) Wrapper.INSTANCE.player().posY + " z:" + (int) Wrapper.INSTANCE.player().posZ, "DieCoordinates");
        }
        if (!Wrapper.INSTANCE.player().isDead) {
            this.countdown = 1;
        }
    }
}
