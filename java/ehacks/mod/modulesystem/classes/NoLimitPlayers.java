package ehacks.mod.modulesystem.classes;

import ehacks.api.module.ModStatus;
import ehacks.api.module.Module;
import ehacks.mod.gui.EHacksClickGui;
import ehacks.mod.gui.window.WindowPlayerIds;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C17PacketCustomPayload;

public class NoLimitPlayers
        extends Module {

    public NoLimitPlayers() {
        super(ModuleCategory.EHACKS);
    }

    @Override
    public String getName() {
        return "NoLimitPlayers";
    }

    @Override
    public String getDescription() {
        return "Gives Float.MAX_VALUE of damage to all players around you";
    }

    @Override
    public void onEnableMod() {
        try {
            Class.forName("taintedmagic.common.network.PacketKatanaAttack");
        } catch (Exception ex) {
            this.off();
            EHacksClickGui.log("[NoLimitPlayers] Not working");
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
    public void onTicks() {
        try {
            List<EntityPlayer> players = WindowPlayerIds.useIt ? WindowPlayerIds.getPlayers() : Wrapper.INSTANCE.world().playerEntities;
            for (Object o : players) {
                if (((Entity) o).getEntityId() != Wrapper.INSTANCE.player().getEntityId()) {
                    killEntity(((Entity) o).getEntityId());
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
