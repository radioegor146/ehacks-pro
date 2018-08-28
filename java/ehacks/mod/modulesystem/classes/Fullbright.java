package ehacks.mod.modulesystem.classes;

import ehacks.api.module.Module;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import net.minecraft.world.World;

public class Fullbright
        extends Module {

    public Fullbright() {
        super(ModuleCategory.RENDER);
    }

    private int cooldownTicks = 0;

    @Override
    public String getName() {
        return "Fullbright";
    }

    @Override
    public String getDescription() {
        return "Brights all area around you";
    }

    @Override
    public void onEnableMod() {
        float[] bright = Wrapper.INSTANCE.world().provider.lightBrightnessTable;
        for (int i = 0; i < bright.length; ++i) {
            bright[i] = 1.0f;
        }
    }

    @Override
    public void onTicks() {
        if (cooldownTicks == 0) {
            float[] bright = Wrapper.INSTANCE.world().provider.lightBrightnessTable;
            for (int i = 0; i < bright.length; ++i) {
                bright[i] = 1.0f;
            }
        }
        cooldownTicks = (cooldownTicks + 1) % 80;
    }

    @Override
    public void onDisableMod() {
        Wrapper.INSTANCE.world().provider.registerWorld((World) Wrapper.INSTANCE.world());
    }
}
