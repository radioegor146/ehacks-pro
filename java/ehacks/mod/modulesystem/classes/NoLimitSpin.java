package ehacks.mod.modulesystem.classes;

import ehacks.api.module.ModStatus;
import ehacks.api.module.Module;
import ehacks.mod.gui.window.WindowPlayerIds;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.util.List;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C17PacketCustomPayload;

public class NoLimitSpin
        extends Module {

    public Random R = new Random();

    public NoLimitSpin() {
        super(ModuleCategory.EHACKS);
    }

    @Override
    public String getDescription() {
        return "Rotates all entities around you randomly";
    }

    @Override
    public String getName() {
        return "NoLimitSpin";
    }

    @Override
    public void onEnableMod() {
        try {
            Class.forName("micdoodle8.mods.galacticraft.core.network.PacketRotateRocket").getConstructor();
        } catch (Exception ex) {
            this.off();
        }
    }

    @Override
    public ModStatus getModStatus() {
        try {
            Class.forName("micdoodle8.mods.galacticraft.core.network.PacketRotateRocket");
            return ModStatus.WORKING;
        } catch (Exception e) {
            return ModStatus.NOTWORKING;
        }
    }

    @Override
    public void onDisableMod() {

    }

    @Override
    public void onTicks() {
        try {
            List<EntityPlayer> players = WindowPlayerIds.useIt ? WindowPlayerIds.getPlayers() : Wrapper.INSTANCE.world().playerEntities;
            for (Object o : players) {
                spinEntity(((Entity) o).getEntityId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void spinEntity(int entityId) {
        ByteBuf buf = Unpooled.buffer(0);
        buf.writeByte(1);
        buf.writeInt(entityId);
        buf.writeFloat(R.nextFloat() * 180f - 90f);
        buf.writeFloat(R.nextFloat() * 360f);
        C17PacketCustomPayload packet = new C17PacketCustomPayload("GalacticraftCore", buf);
        Wrapper.INSTANCE.player().sendQueue.addToSendQueue(packet);
    }

    @Override
    public String getModName() {
        return "Galacticraft";
    }
}
