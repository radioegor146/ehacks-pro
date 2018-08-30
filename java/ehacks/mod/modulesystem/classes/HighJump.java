package ehacks.mod.modulesystem.classes;

import ehacks.api.module.Module;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class HighJump
        extends Module {

    public HighJump() {
        super(ModuleCategory.PLAYER);
    }

    @Override
    public String getName() {
        return "HighJump";
    }

    @Override
    public String getDescription() {
        return "Gives you Jump Boost 2 effect";
    }

    @Override
    public void onEnableMod() {
        Wrapper.INSTANCE.player().addPotionEffect(new PotionEffect(Potion.jump.getId(), 9999999, 2));
    }

    @Override
    public void onDisableMod() {
        Wrapper.INSTANCE.player().removePotionEffect(Potion.jump.getId());
    }
}
