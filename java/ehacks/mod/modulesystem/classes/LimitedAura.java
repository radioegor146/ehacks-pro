package ehacks.mod.modulesystem.classes;

import ehacks.api.module.ModStatus;
import ehacks.api.module.Module;
import ehacks.mod.gui.EHacksClickGui;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.C17PacketCustomPayload;

public class LimitedAura
        extends Module {

    public LimitedAura() {
        super(ModuleCategory.EHACKS);
    }

    @Override
    public String getName() {
        return "LimitedAura";
    }

    @Override
    public String getDescription() {
        return "Performs Float.MAX_VALUE damage on all entities around you in radius of 4 blocks";
    }

    @Override
    public void onEnableMod() {
        try {
            Class.forName("taintedmagic.common.network.PacketKatanaAttack");
        } catch (Exception ex) {
            this.off();
            EHacksClickGui.log("[LimitedAura] Not working");
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

    @Override
    public void onDisableMod() {

    }

    private float getDistanceToEntity(Entity from, Entity to) {
        return from.getDistanceToEntity(to);
    }

    private boolean isWithinRange(float range, Entity e) {
        return this.getDistanceToEntity(e, (Entity) Wrapper.INSTANCE.player()) <= range;
    }

    @Override
    public void onTicks() {
        try {
            for (Object o : Wrapper.INSTANCE.world().loadedEntityList) {
                if (isWithinRange(4, (Entity) o)) {
                    if (((Entity) o).getEntityId() != Wrapper.INSTANCE.player().getEntityId()) {
                        killEntity(((Entity) o).getEntityId());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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
