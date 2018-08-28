package ehacks.mod.modulesystem.classes;

import ehacks.api.module.Module;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import net.minecraftforge.client.event.RenderWorldLastEvent;

public class AntiKnockBack
        extends Module {

    public AntiKnockBack() {
        super(ModuleCategory.PLAYER);
    }

    @Override
    public String getName() {
        return "AntiKnockBack";
    }

    @Override
    public String getDescription() {
        return "Removes knockback on you";
    }

    @Override
    public void onWorldRender(RenderWorldLastEvent event) {
        if (Wrapper.INSTANCE.player().hurtResistantTime > 0 && Wrapper.INSTANCE.player().hurtTime > 0) {
            Wrapper.INSTANCE.player().hurtResistantTime = 0;
            Wrapper.INSTANCE.player().hurtTime = 0;
            Wrapper.INSTANCE.player().motionX = 0.0;
            Wrapper.INSTANCE.player().motionY /= 10.0;
            Wrapper.INSTANCE.player().motionZ = 0.0;
        }
    }
}
