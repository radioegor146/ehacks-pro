/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  net.minecraft.client.entity.EntityOtherPlayerMP
 *  net.minecraft.util.MovementInput
 *  net.minecraft.world.World
 */
package ehacks.mod.util;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.util.MovementInput;
import net.minecraft.world.World;

public class FreeCamEntity
extends EntityOtherPlayerMP {
    private static MovementInput movementInput = null;

    public FreeCamEntity(World world, GameProfile gameProfile) {
        super(world, gameProfile);
    }

    public void setMovementInput(MovementInput movementInput) {
        FreeCamEntity.movementInput = movementInput;
        if (movementInput.jump && this.onGround) {
            super.jump();
        }
        super.moveEntityWithHeading(movementInput.moveStrafe, movementInput.moveForward);
    }

    public void moveEntity(double x, double y, double z) {
        this.onGround = true;
        super.moveEntity(x, y, z);
        this.onGround = true;
    }

    public boolean isSneaking() {
        return FreeCamEntity.movementInput.sneak;
    }

    public void onLivingUpdate() {
        super.onLivingUpdate();
        this.noClip = true;
        this.motionX = 0.0;
        this.motionY = 0.0;
        this.motionZ = 0.0;
        this.noClip = false;
    }
}

