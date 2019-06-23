package ehacks.mod.util;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.util.MovementInput;
import net.minecraft.world.World;

public class EntityFakePlayer
        extends EntityOtherPlayerMP {

    private static MovementInput movementInput = null;

    public EntityFakePlayer(World world, GameProfile gameProfile) {
        super(world, gameProfile);
    }

    public void setMovementInput(MovementInput movementInput) {
        EntityFakePlayer.movementInput = movementInput;
        if (movementInput.jump && this.onGround) {
            super.jump();
        }
        super.moveRelative(movementInput.moveStrafe, 0, movementInput.moveForward, 0);
    }

    @Override
    public boolean isSneaking() {
        return false;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        this.noClip = true;
        this.motionX = 0.0;
        this.motionY = 0.0;
        this.motionZ = 0.0;
        this.noClip = false;
    }
}
