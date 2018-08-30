package ehacks.mod.modulesystem.classes;

import ehacks.api.module.ModStatus;
import ehacks.api.module.Module;
import ehacks.mod.util.InteropUtils;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.IInventory;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.event.MouseEvent;

public class ContainerClear
        extends Module {

    public ContainerClear() {
        super(ModuleCategory.EHACKS);
    }

    @Override
    public String getName() {
        return "ContainerClear";
    }

    @Override
    public String getDescription() {
        return "Clears container you're looking to";
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
    public void onEnableMod() {
        try {
            Class.forName("powercrystals.minefactoryreloaded.net.ServerPacketHandler");
            MovingObjectPosition mop = Wrapper.INSTANCE.mc().objectMouseOver;
            TileEntity entity = Wrapper.INSTANCE.world().getTileEntity(mop.blockX, mop.blockY, mop.blockZ);
            if (entity == null) {
                this.off();
                return;
            }
            if (entity instanceof IInventory) {
                IInventory inv = (IInventory) entity;
                for (int i = 0; i < inv.getSizeInventory(); i++) {
                    setSlot(i, mop.blockX, mop.blockY, mop.blockZ);
                }
            }
            InteropUtils.log("Cleared", this);
            this.off();
        } catch (Exception ex) {
            this.off();
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
