package ehacks.mod.modulesystem.classes;

import cpw.mods.fml.relauncher.ReflectionHelper;
import ehacks.api.module.Module;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import java.lang.reflect.Method;
import net.minecraft.client.Minecraft;

public class FastClick
        extends Module {

    public FastClick() {
        super(ModuleCategory.COMBAT);
    }

    @Override
    public String getName() {
        return "FastClick";
    }

    @Override
    public String getDescription() {
        return "Makes you click very fast";
    }

    @Override
    public void onTicks() {
        if (Wrapper.INSTANCE.mcSettings().keyBindAttack.getIsKeyPressed()) {
            try {
                for (int i = 0; i < 30; i++) {
                    ReflectionHelper.findField(Minecraft.class, "leftClickCounter", "field_71429_W").set(Wrapper.INSTANCE.mc(), 0);
                    Method m = ReflectionHelper.findMethod(Minecraft.class, Wrapper.INSTANCE.mc(), (String[]) new String[]{"func_147116_af"}, (Class[]) null);
                    m.invoke((Object) Wrapper.INSTANCE.mc(), new Object[0]);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
