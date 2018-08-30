package ehacks.mod.modulesystem.classes;

import ehacks.api.module.ModStatus;
import ehacks.api.module.Module;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraftforge.client.event.MouseEvent;
import org.lwjgl.input.Mouse;

public class RocketChaos
        extends Module {

    public RocketChaos() {
        super(ModuleCategory.EHACKS);
    }

    @Override
    public String getName() {
        return "RocketChaos";
    }

    @Override
    public String getDescription() {
        return "You can throw rockets with left click";
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
    public ModStatus getModStatus() {
        try {
            Class.forName("powercrystals.minefactoryreloaded.net.ServerPacketHandler");
            Class.forName("powercrystals.minefactoryreloaded.entity.EntityRocket");
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
    public void onMouse(MouseEvent event) {
        if (Mouse.isButtonDown(0)) {
            sendRocket(0);
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
