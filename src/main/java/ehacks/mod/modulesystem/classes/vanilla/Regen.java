package ehacks.mod.modulesystem.classes.vanilla;

import ehacks.mod.api.Module;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Regen
        extends Module {

    public Regen() {
        super(ModuleCategory.PLAYER);
    }

    @Override
    public String getName() {
        return "Regen";
    }

    @Override
    public String getDescription() {
        return "Regenerates you";
    }

    @Override
    public void onTicks() {
        boolean shouldHeal;
        if (!Wrapper.INSTANCE.player().onGround) {
            return;
        }
        boolean canHeal = Wrapper.INSTANCE.player().onGround || Wrapper.INSTANCE.player().isInWater() || Wrapper.INSTANCE.player().isOnLadder();
        shouldHeal = Wrapper.INSTANCE.player().getHealth() <= 18.5f && Wrapper.INSTANCE.player().getFoodStats().getFoodLevel() > 8;
        if (canHeal && shouldHeal) {
            for (int i = 0; i < 1000; ++i) {
                Wrapper.INSTANCE.player().sendQueue.addToSendQueue(new C03PacketPlayer(false));
            }
        }
    }
}
