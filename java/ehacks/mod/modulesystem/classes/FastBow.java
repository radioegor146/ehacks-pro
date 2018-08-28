package ehacks.mod.modulesystem.classes;

import ehacks.api.module.Module;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import net.minecraft.item.ItemBow;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;

public class FastBow
        extends Module {

    public FastBow() {
        super(ModuleCategory.COMBAT);
    }

    @Override
    public String getName() {
        return "FastBow";
    }

    @Override
    public String getDescription() {
        return "Shoots arrows very fast";
    }

    @Override
    public void onTicks() {
        if (!Wrapper.INSTANCE.mc().isSingleplayer()) {
            new Thread() {

                @Override
                public void run() {
                    if (Wrapper.INSTANCE.player().isUsingItem() && Wrapper.INSTANCE.player().inventory.getCurrentItem().getItem() instanceof ItemBow && Wrapper.INSTANCE.player().onGround) {
                        try {
                            Wrapper.INSTANCE.player().sendQueue.addToSendQueue((Packet) new C08PacketPlayerBlockPlacement(-1, -1, -1, 255, Wrapper.INSTANCE.player().inventory.getCurrentItem(), -1.0f, -1.0f, -1.0f));
                            for (int i = 0; i < 25; ++i) {
                                Wrapper.INSTANCE.player().sendQueue.addToSendQueue((Packet) new C03PacketPlayer.C05PacketPlayerLook(Wrapper.INSTANCE.player().rotationYaw, Wrapper.INSTANCE.player().rotationPitch, Wrapper.INSTANCE.player().onGround));
                                Thread.sleep(1L);
                            }
                            Wrapper.INSTANCE.player().sendQueue.addToSendQueue((Packet) new C07PacketPlayerDigging(5, 0, 0, 0, 255));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }.start();
        } else if (Wrapper.INSTANCE.player().isUsingItem() && Wrapper.INSTANCE.player().inventory.getCurrentItem().getItem() instanceof ItemBow && Wrapper.INSTANCE.player().onGround) {
            try {
                Wrapper.INSTANCE.player().setSprinting(true);
                Wrapper.INSTANCE.player().sendQueue.addToSendQueue((Packet) new C08PacketPlayerBlockPlacement(-1, -1, -1, 255, Wrapper.INSTANCE.player().inventory.getCurrentItem(), 1.0f, 1.0f, 1.0f));
                for (int i = 0; i < 20; ++i) {
                    Thread.sleep(1L);
                    Wrapper.INSTANCE.player().sendQueue.addToSendQueue((Packet) new C03PacketPlayer.C05PacketPlayerLook(Wrapper.INSTANCE.player().rotationYaw, Wrapper.INSTANCE.player().rotationPitch, Wrapper.INSTANCE.player().onGround));
                }
                Wrapper.INSTANCE.player().sendQueue.addToSendQueue((Packet) new C07PacketPlayerDigging(5, 0, 0, 0, 255));
                Wrapper.INSTANCE.player().setSprinting(false);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}
