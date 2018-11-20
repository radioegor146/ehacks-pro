package ehacks.mod.modulesystem.classes.mods.taintedmagic;

import ehacks.mod.api.ModStatus;
import ehacks.mod.api.Module;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.C17PacketCustomPayload;

public class NoLimitAura
        extends Module {

    public NoLimitAura() {
        super(ModuleCategory.EHACKS);
    }

    @Override
    public String getName() {
        return "NoLimitAura";
    }

    @Override
    public String getDescription() {
        return "Gives Float.MAX_VALUE of damage to all entities around you";
    }

    @Override
    public void onModuleEnabled() {
        try {
            Class.forName("taintedmagic.common.network.PacketKatanaAttack");
        } catch (Exception ex) {
            this.off();
        }
    }

    @Override
    public ModStatus getModStatus() {
        try {
            Class.forName("taintedmagic.common.network.PacketKatanaAttack");
            return ModStatus.WORKING;
        } catch (Exception e) {
            return ModStatus.NOTWORKING;
        }
    }

    private float getDistanceToEntity(Entity from, Entity to) {
        return from.getDistanceToEntity(to);
    }

    private boolean isWithinRange(float range, Entity e) {
        return this.getDistanceToEntity(e, Wrapper.INSTANCE.player()) <= range;
    }

    @Override
    public void onTicks() {
        try {
            Wrapper.INSTANCE.world().loadedEntityList.stream().filter((o) -> (((Entity) o).getEntityId() != Wrapper.INSTANCE.player().getEntityId())).forEachOrdered((o) -> {
                killEntity(((Entity) o).getEntityId());
            });
        } catch (Exception e) {
        }
    }

    public void killEntity(int entityId) {
        int playerId = Wrapper.INSTANCE.player().getEntityId();
        int dimensionId = Wrapper.INSTANCE.player().dimension;
        ByteBuf buf = Unpooled.buffer(0);
        buf.writeByte(0);
        buf.writeInt(entityId);
        buf.writeInt(playerId);
        buf.writeInt(dimensionId);
        buf.writeFloat(Float.MAX_VALUE);
        buf.writeBoolean(false);
        C17PacketCustomPayload packet = new C17PacketCustomPayload("taintedmagic", buf);
        Wrapper.INSTANCE.player().sendQueue.addToSendQueue(packet);
    }

    @Override
    public String getModName() {
        return "Tainted Magic";
    }
}
