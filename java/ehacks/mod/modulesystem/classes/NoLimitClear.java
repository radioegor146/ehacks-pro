package ehacks.mod.modulesystem.classes;

import cpw.mods.fml.relauncher.ReflectionHelper;
import ehacks.api.module.ModStatus;
import ehacks.api.module.Module;
import ehacks.mod.util.InteropUtils;
import ehacks.mod.util.Mappings;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.util.List;
import net.minecraft.client.multiplayer.ChunkProviderClient;
import net.minecraft.inventory.IInventory;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;

public class NoLimitClear
        extends Module {

    public NoLimitClear() {
        super(ModuleCategory.EHACKS);
    }

    @Override
    public String getName() {
        return "NoLimitClear";
    }

    @Override
    public ModStatus getModStatus() {
        try {
            Class.forName("powercrystals.minefactoryreloaded.net.ServerPacketHandler");
            return ModStatus.WORKING;
        } catch (Exception e) {
            return ModStatus.NOTWORKING;
        }
    }

    @Override
    public String getDescription() {
        return "Clears all containers around you";
    }

    @Override
    public void onEnableMod() {
        try {
            Class.forName("powercrystals.minefactoryreloaded.net.ServerPacketHandler");
            int count = 0;
            IChunkProvider chunkProvider = Wrapper.INSTANCE.world().getChunkProvider();
            if (chunkProvider instanceof ChunkProviderClient) {
                ChunkProviderClient clientProvider = (ChunkProviderClient) chunkProvider;
                List<Chunk> chunks = ReflectionHelper.getPrivateValue(ChunkProviderClient.class, clientProvider, Mappings.chunkListing);
                for (Chunk chunk : chunks) {
                    for (Object entityObj : chunk.chunkTileEntityMap.values()) {
                        if (!(entityObj instanceof TileEntity)) {
                            return;
                        }
                        TileEntity entity = (TileEntity) entityObj;
                        if (entity instanceof IInventory) {
                            IInventory inv = (IInventory) entity;
                            TileEntity ent = entity;
                            for (int i = 0; i < inv.getSizeInventory(); i++) {
                                setSlot(i, ent.xCoord, ent.yCoord, ent.zCoord);
                            }
                            count++;
                        }
                    }
                }
            }
            InteropUtils.log("Cleared " + String.valueOf(count) + " containers", this);
            this.off();
        } catch (Exception ex) {
            this.off();
            ex.printStackTrace();
        }
    }
    
    public void setSlot(int slotId, int x, int y, int z) {
        int playerId = Wrapper.INSTANCE.player().getEntityId();
        ByteBuf buf = Unpooled.buffer(0);
        buf.writeByte(0);
        buf.writeInt(Wrapper.INSTANCE.player().dimension);
        buf.writeShort(20);
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        buf.writeInt(playerId);
        buf.writeInt(slotId);
        buf.writeByte(0);
        C17PacketCustomPayload packet = new C17PacketCustomPayload("MFReloaded", buf);
        Wrapper.INSTANCE.player().sendQueue.addToSendQueue(packet);
    }

    @Override
    public String getModName() {
        return "MFR";
    }
}
