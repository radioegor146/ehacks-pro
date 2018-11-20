package ehacks.mod.modulesystem.classes.vanilla;

import cpw.mods.fml.relauncher.ReflectionHelper;
import ehacks.mod.api.Module;
import ehacks.mod.util.Mappings;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import net.minecraft.entity.Entity;

public class NoWeb
        extends Module {

    public NoWeb() {
        super(ModuleCategory.PLAYER);
    }

    @Override
    public String getName() {
        return "NoWeb";
    }

    @Override
    public String getDescription() {
        return "Death to spiders";
    }

    @Override
    public void onTicks() {
        ReflectionHelper.setPrivateValue(Entity.class, Wrapper.INSTANCE.player(), false, new String[]{Mappings.isInWeb});
    }
}
