package ehacks.mod.modulesystem.classes.vanilla;

import ehacks.mod.api.Module;
import ehacks.mod.util.EntityFakePlayer;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.PacketHandler;
import ehacks.mod.wrapper.Wrapper;

public class FreeCam
        extends Module {

    public EntityFakePlayer freecamEnt = null;

    public FreeCam() {
        super(ModuleCategory.PLAYER);
    }

    @Override
    public String getName() {
        return "FreeCam";
    }

    @Override
    public String getDescription() {
        return "Allows you to move without moving on the server";
    }

    @Override
    public void onModuleEnabled() {
        if (Wrapper.INSTANCE.player() != null && Wrapper.INSTANCE.world() != null) {
            this.freecamEnt = new EntityFakePlayer(Wrapper.INSTANCE.world(), Wrapper.INSTANCE.player().getGameProfile());
            this.freecamEnt.setPosition(Wrapper.INSTANCE.player().posX, Wrapper.INSTANCE.player().posY, Wrapper.INSTANCE.player().posZ);
            this.freecamEnt.inventory = Wrapper.INSTANCE.player().inventory;
            this.freecamEnt.yOffset = Wrapper.INSTANCE.player().yOffset;
            this.freecamEnt.ySize = Wrapper.INSTANCE.player().ySize;
            this.freecamEnt.rotationPitch = Wrapper.INSTANCE.player().rotationPitch;
            this.freecamEnt.rotationYaw = Wrapper.INSTANCE.player().rotationYaw;
            this.freecamEnt.rotationYawHead = Wrapper.INSTANCE.player().rotationYawHead;
            Wrapper.INSTANCE.world().spawnEntityInWorld(this.freecamEnt);
        }
    }

    @Override
    public void onModuleDisabled() {
        if (this.freecamEnt != null && Wrapper.INSTANCE.world() != null) {
            Wrapper.INSTANCE.player().setPosition(this.freecamEnt.posX, this.freecamEnt.posY, this.freecamEnt.posZ);
            Wrapper.INSTANCE.player().yOffset = this.freecamEnt.yOffset;
            Wrapper.INSTANCE.player().ySize = this.freecamEnt.ySize;
            Wrapper.INSTANCE.player().rotationPitch = this.freecamEnt.rotationPitch;
            Wrapper.INSTANCE.player().rotationYaw = this.freecamEnt.rotationYaw;
            Wrapper.INSTANCE.player().rotationYawHead = this.freecamEnt.rotationYawHead;
            Wrapper.INSTANCE.world().removeEntity(this.freecamEnt);
            this.freecamEnt = null;
        }
    }

    @Override
    public void onTicks() {

    }

    @Override
    public boolean onPacket(Object packet, PacketHandler.Side side) {
        return !(side == PacketHandler.Side.OUT && packet instanceof net.minecraft.network.play.client.C03PacketPlayer);
    }

    @Override
    public boolean canOnOnStart() {
        return false;
    }
}
