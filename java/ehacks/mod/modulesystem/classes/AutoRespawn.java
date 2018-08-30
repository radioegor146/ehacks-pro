package ehacks.mod.modulesystem.classes;

import ehacks.api.module.Module;
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
        return "Auto Respawn the player when dead";
    }

    @Override
    public void onTicks() {
        if (Wrapper.INSTANCE.player().isDead) {
            Wrapper.INSTANCE.player().respawnPlayer();
        }
    }
}
