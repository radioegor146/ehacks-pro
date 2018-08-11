package ehacks.mod.modulesystem.classes;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovementInput;
import net.minecraft.world.World;
import ehacks.api.module.Mod;
import ehacks.mod.util.EntityFakePlayer;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.PacketHandler;
import ehacks.mod.wrapper.Wrapper;

public class Blink
extends Mod {
    public EntityFakePlayer freecamEnt = null;

    public Blink() {
        super(ModuleCategory.PLAYER);
    }

    @Override
    public String getName() {
        return "Blink";
    }

    @Override
    public void onEnableMod() {
        if (Wrapper.INSTANCE.player() != null && Wrapper.INSTANCE.mc().theWorld != null) {
            this.freecamEnt = new EntityFakePlayer((World)Wrapper.INSTANCE.mc().theWorld, Wrapper.INSTANCE.player().getGameProfile());
            this.freecamEnt.setPosition(Wrapper.INSTANCE.player().posX, Wrapper.INSTANCE.player().posY, Wrapper.INSTANCE.player().posZ);
            this.freecamEnt.yOffset = Wrapper.INSTANCE.player().yOffset;
            this.freecamEnt.ySize = Wrapper.INSTANCE.player().ySize;
            this.freecamEnt.rotationPitch = Wrapper.INSTANCE.player().rotationPitch;
            this.freecamEnt.rotationYaw = Wrapper.INSTANCE.player().rotationYaw;
            this.freecamEnt.rotationYawHead = Wrapper.INSTANCE.player().rotationYawHead;
            Wrapper.INSTANCE.world().spawnEntityInWorld((Entity)this.freecamEnt);
        }
    }

    @Override
    public void onDisableMod() {
        if (this.freecamEnt != null && Wrapper.INSTANCE.world() != null) {
            Wrapper.INSTANCE.world().removeEntity((Entity)this.freecamEnt);
            this.freecamEnt = null;
        }
    }

    @Override
    public void onTicks() {
        
    }
    
    @Override
    public boolean onPacket(Object packet, PacketHandler.Side side) {
        if (side == PacketHandler.Side.OUT && (
                packet instanceof net.minecraft.network.play.client.C03PacketPlayer ||
                packet instanceof net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition ||
                packet instanceof net.minecraft.network.play.client.C03PacketPlayer.C05PacketPlayerLook ||
                packet instanceof net.minecraft.network.play.client.C03PacketPlayer.C06PacketPlayerPosLook))
            return false;
        return true;
    }
    
    @Override
    public boolean canOnOnStart() {
        return false;
    }
}

