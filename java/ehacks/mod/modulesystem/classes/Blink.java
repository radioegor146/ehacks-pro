package ehacks.mod.modulesystem.classes;

import ehacks.api.module.Module;
import ehacks.mod.util.EntityFakePlayer;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.PacketHandler;
import ehacks.mod.wrapper.Wrapper;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class Blink
        extends Module {

    public EntityFakePlayer freecamEnt = null;

    public Blink() {
        super(ModuleCategory.PLAYER);
    }

    @Override
    public String getName() {
        return "Blink";
    }

    @Override
    public String getDescription() {
        return "As freecam but teleports you when it is off";
    }

    @Override
    public void onEnableMod() {
        if (Wrapper.INSTANCE.player() != null && Wrapper.INSTANCE.world() != null) {
            this.freecamEnt = new EntityFakePlayer((World) Wrapper.INSTANCE.world(), Wrapper.INSTANCE.player().getGameProfile());
            this.freecamEnt.setPosition(Wrapper.INSTANCE.player().posX, Wrapper.INSTANCE.player().posY, Wrapper.INSTANCE.player().posZ);
            this.freecamEnt.inventory = Wrapper.INSTANCE.player().inventory;
            this.freecamEnt.yOffset = Wrapper.INSTANCE.player().yOffset;
            this.freecamEnt.ySize = Wrapper.INSTANCE.player().ySize;
            this.freecamEnt.rotationPitch = Wrapper.INSTANCE.player().rotationPitch;
            this.freecamEnt.rotationYaw = Wrapper.INSTANCE.player().rotationYaw;
            this.freecamEnt.rotationYawHead = Wrapper.INSTANCE.player().rotationYawHead;
            Wrapper.INSTANCE.world().spawnEntityInWorld((Entity) this.freecamEnt);
        }
    }

    @Override
    public void onDisableMod() {
        if (this.freecamEnt != null && Wrapper.INSTANCE.world() != null) {
            Wrapper.INSTANCE.world().removeEntity((Entity) this.freecamEnt);
            this.freecamEnt = null;
        }
    }

    @Override
    public void onTicks() {

    }

    @Override
    public boolean onPacket(Object packet, PacketHandler.Side side) {
        return !(side == PacketHandler.Side.OUT && (packet instanceof net.minecraft.network.play.client.C03PacketPlayer
                || packet instanceof net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition
                || packet instanceof net.minecraft.network.play.client.C03PacketPlayer.C05PacketPlayerLook
                || packet instanceof net.minecraft.network.play.client.C03PacketPlayer.C06PacketPlayerPosLook));
    }

    @Override
    public boolean canOnOnStart() {
        return false;
    }
}
