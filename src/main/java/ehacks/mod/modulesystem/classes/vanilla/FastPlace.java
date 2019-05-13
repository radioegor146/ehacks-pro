package ehacks.mod.modulesystem.classes.vanilla;

import cpw.mods.fml.relauncher.ReflectionHelper;
import ehacks.mod.api.Module;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import java.lang.reflect.Method;
import net.minecraft.client.Minecraft;

public class FastPlace
        extends Module {

    public FastPlace() {
        super(ModuleCategory.PLAYER);
    }

    @Override
    public String getName() {
        return "FastPlace";
    }

    @Override
    public String getDescription() {
        return "You can place blocks instantly";
    }

    @Override
    public void onTicks() {
        if (Wrapper.INSTANCE.mcSettings().keyBindUseItem.getIsKeyPressed()) {
            try {
                Method m = ReflectionHelper.findMethod(Minecraft.class, Wrapper.INSTANCE.mc(), new String[]{"func_147121_ag"}, (Class[]) null);
                m.invoke(Wrapper.INSTANCE.mc());
            } catch (Exception e) {
            }
        }
    }
}
