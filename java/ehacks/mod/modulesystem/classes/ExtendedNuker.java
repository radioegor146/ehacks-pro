package ehacks.mod.modulesystem.classes;

import ehacks.api.module.ModStatus;
import ehacks.api.module.Module;
import ehacks.mod.external.config.CheatConfiguration;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C17PacketCustomPayload;

public class ExtendedNuker
        extends Module {

    public ExtendedNuker() {
        super(ModuleCategory.EHACKS);
    }

    @Override
    public String getName() {
        return "ExtNuker";
    }

    @Override
    public String getDescription() {
        return "Breaks blocks instantly in radius of 4 blocks around you";
    }

    @Override
    public void onEnableMod() {
        try {
            Class.forName("com.mrcrayfish.furniture.network.message.MessageTakeWater").getConstructor(Integer.TYPE, Integer.TYPE, Integer.TYPE);
        } catch (Exception ex) {
            this.off();
        }
    }

    @Override
    public ModStatus getModStatus() {
        try {
            Class.forName("com.mrcrayfish.furniture.network.message.MessageTakeWater");
            return ModStatus.WORKING;
        } catch (Exception e) {
            return ModStatus.NOTWORKING;
        }
    }

    @Override
    public void onTicks() {
        int radius = CheatConfiguration.config.nukerradius;
        if (Wrapper.INSTANCE.mc().playerController.isInCreativeMode()) {
            for (int i = radius; i >= -radius; --i) {
                for (int k = radius; k >= -radius; --k) {
                    for (int j = -radius; j <= radius; ++j) {
                        int x = (int) (Wrapper.INSTANCE.player().posX + (double) i);
                        int y = (int) (Wrapper.INSTANCE.player().posY + (double) j);
                        int z = (int) (Wrapper.INSTANCE.player().posZ + (double) k);
                        Block blockID = Wrapper.INSTANCE.world().getBlock(x, y, z);
                        if (blockID.getMaterial() == Material.air) {
                            continue;
                        }
                        Wrapper.INSTANCE.player().sendQueue.addToSendQueue((Packet) new C07PacketPlayerDigging(0, x, y, z, 0));
                    }
                }
            }
        }
        if (Wrapper.INSTANCE.mc().playerController.isNotCreative()) {
            for (int i = radius; i >= -radius; --i) {
                for (int k = radius; k >= -radius; --k) {
                    for (int j = -radius; j <= radius; ++j) {
                        int x = (int) (Wrapper.INSTANCE.player().posX + (double) i);
                        int y = (int) (Wrapper.INSTANCE.player().posY + (double) j);
                        int z = (int) (Wrapper.INSTANCE.player().posZ + (double) k);
                        Block block = Wrapper.INSTANCE.world().getBlock(x, y, z);
                        if (block.getMaterial() == Material.air) {
                            continue;
                        }
                        ByteBuf buf = Unpooled.buffer(0);
                        buf.writeByte(14);
                        buf.writeInt(x);
                        buf.writeInt(y);
                        buf.writeInt(z);
                        C17PacketCustomPayload packet = new C17PacketCustomPayload("cfm", buf);
                        Wrapper.INSTANCE.player().sendQueue.addToSendQueue(packet);
                    }
                }
            }
        }
    }

    @Override
    public String getModName() {
        return "Furniture";
    }
}
