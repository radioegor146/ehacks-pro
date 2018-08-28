package ehacks.mod.modulesystem.classes;

import cpw.mods.fml.relauncher.ReflectionHelper;
import ehacks.api.module.Module;
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
        return "You are not in web";
    }

    @Override
    public void onTicks() {
        ReflectionHelper.setPrivateValue(Entity.class, Wrapper.INSTANCE.player(), (Object) false, (String[]) new String[]{Mappings.isInWeb});
    }
}
