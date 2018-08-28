package ehacks.mod.modulesystem.classes;

import ehacks.api.module.Module;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;

public class Fly
        extends Module {

    public static float FLY_SPEED = 0.05f;

    public Fly() {
        super(ModuleCategory.PLAYER);
    }

    @Override
    public String getName() {
        return "Fly";
    }

    @Override
    public String getDescription() {
        return "I believe I can fly (8)~";
    }

    @Override
    public void onEnableMod() {
        Wrapper.INSTANCE.player().capabilities.setFlySpeed(FLY_SPEED);
        Wrapper.INSTANCE.player().capabilities.isFlying = true;
    }

    @Override
    public void onDisableMod() {
        Wrapper.INSTANCE.player().capabilities.isFlying = false;
    }

    @Override
    public void onTicks() {
        Wrapper.INSTANCE.player().capabilities.isFlying = true;
    }
}
