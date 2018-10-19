package ehacks.mod.modulesystem.classes.mods.thaumichorizons;

import ehacks.mod.api.ModStatus;
import ehacks.mod.api.Module;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class NowYouSeeMe extends Module {

    public NowYouSeeMe() {
        super(ModuleCategory.EHACKS);
    }

    @Override
    public String getName() {
        return "NowYouSeeMe";
    }

    @Override
    public ModStatus getModStatus() {
        try {
            Class.forName("com.kentington.thaumichorizons.common.lib.PacketToggleInvisibleToServer");
            return ModStatus.WORKING;
        } catch (Exception e) {
            return ModStatus.NOTWORKING;
        }
    }

    @Override
    public String getModName() {
        return "ThaumicHorizons";
    }

    @Override
    public String getDescription() {
        return "Turn yourself invisible";
    }

    @Override
    public void onModuleEnabled() {
        sendPacket();
    }

    @Override
    public void onModuleDisabled() {
        sendPacket();
    }

    //Check if the player is invisible for a LONG time
    @Override
    public boolean isActive() {
        EntityClientPlayerMP player = Wrapper.INSTANCE.player();
        if (player == null) {
            return false;
        }

        if (player.isPotionActive(Potion.invisibility)) {
            PotionEffect activePotionEffect = player.getActivePotionEffect(Potion.invisibility);
            return activePotionEffect.getDuration() > 20000;
        }
        return false;
    }

    private void sendPacket() {
        ByteBuf buf = Unpooled.buffer(0);
        buf.writeByte(13);//Index of packet in packet handler
        buf.writeInt(Wrapper.INSTANCE.player().getEntityId());
        buf.writeInt(Wrapper.INSTANCE.player().dimension);
        C17PacketCustomPayload packet = new C17PacketCustomPayload("thaumichorizons", buf);
        Wrapper.INSTANCE.player().sendQueue.addToSendQueue(packet);
    }

}
