package ehacks.mod.modulesystem.classes;

import ehacks.api.module.ModStatus;
import ehacks.api.module.Module;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.network.play.client.C17PacketCustomPayload;

public class NoLimitRocket
        extends Module {

    public NoLimitRocket() {
        super(ModuleCategory.EHACKS);
    }

    @Override
    public String getName() {
        return "NoLimitRocket";
    }

    @Override
    public String getDescription() {
        return "Fires rocket to all entities around you every 4 seconds";
    }

    @Override
    public void onEnableMod() {
        try {
            Class.forName("powercrystals.minefactoryreloaded.net.ServerPacketHandler");
            Class.forName("powercrystals.minefactoryreloaded.entity.EntityRocket");
        } catch (Exception ex) {
            this.off();
        }
    }

    @Override
    public void onDisableMod() {

    }

    @Override
    public ModStatus getModStatus() {
        try {
            Class.forName("powercrystals.minefactoryreloaded.net.ServerPacketHandler");
            Class.forName("powercrystals.minefactoryreloaded.entity.EntityRocket");
            return ModStatus.WORKING;
        } catch (Exception e) {
            return ModStatus.NOTWORKING;
        }
    }

    private int ticksWait;

    @Override
    public void onTicks() {
        ticksWait++;
        if (ticksWait % 80 != 0) {
            return;
        }
        try {
            List<Entity> players = Wrapper.INSTANCE.world().loadedEntityList;
            for (Object o : players) {
                if (((Entity) o).getEntityId() != Wrapper.INSTANCE.player().getEntityId() && !Class.forName("powercrystals.minefactoryreloaded.entity.EntityRocket").isInstance(o) && !(o instanceof EntityItem)) {
                    sendRocket(((Entity) o).getEntityId());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendRocket(int entityId) {
        int playerId = Wrapper.INSTANCE.player().getEntityId();
        ByteBuf buf = Unpooled.buffer(0);
        buf.writeByte(0);
        buf.writeInt(Wrapper.INSTANCE.player().dimension);
        buf.writeShort(11);
        buf.writeInt(playerId);
        buf.writeInt(entityId);
        C17PacketCustomPayload packet = new C17PacketCustomPayload("MFReloaded", buf);
        Wrapper.INSTANCE.player().sendQueue.addToSendQueue(packet);
    }

    @Override
    public String getModName() {
        return "MFR";
    }
}
