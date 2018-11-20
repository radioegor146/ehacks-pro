package ehacks.mod.modulesystem.classes.vanilla;

import ehacks.mod.api.Module;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;

public class AutoRespawn
        extends Module {

    public AutoRespawn() {
        super(ModuleCategory.PLAYER);
    }

    @Override
    public String getName() {
        return "AutoRespawn";
    }

    @Override
    public String getDescription() {
        return "Automatically respawns you";
    }

    @Override
    public void onTicks() {
        if (Wrapper.INSTANCE.player().isDead) {
            Wrapper.INSTANCE.player().respawnPlayer();
        }
    }
}
