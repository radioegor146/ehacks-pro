package ehacks.mod.modulesystem.classes.vanilla;

import cpw.mods.fml.relauncher.ReflectionHelper;
import ehacks.mod.api.Module;
import ehacks.mod.config.CheatConfiguration;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import ehacks.mod.util.Mappings;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Timer;

public class Speed
        extends Module {

    public Speed() {
        super(ModuleCategory.PLAYER);
    }

    @Override
    public String getName() {
        return "Speed";
    }

    @Override
    public String getDescription() {
        return "Your game will run 3x faster";
    }

    @Override
    public void onTicks() {
        Timer timer = (Timer) ReflectionHelper.getPrivateValue(Minecraft.class, Wrapper.INSTANCE.mc(), new String[]{Mappings.timer});
        timer.timerSpeed = (float) CheatConfiguration.config.speedhack;
    }

    @Override
    public void onModuleDisabled() {
        Timer timer = (Timer) ReflectionHelper.getPrivateValue(Minecraft.class, Wrapper.INSTANCE.mc(), new String[]{Mappings.timer});
        timer.timerSpeed = 1.0f;
    }
}
