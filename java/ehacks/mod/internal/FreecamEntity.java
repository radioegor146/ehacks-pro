/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  net.minecraft.client.entity.EntityOtherPlayerMP
 *  net.minecraft.util.MovementInput
 *  net.minecraft.world.World
 */
package ehacks.mod.internal;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.util.MovementInput;
import net.minecraft.world.World;

public class FreecamEntity
extends EntityOtherPlayerMP {
    public MovementInput movementInput = null;
    public boolean flyMode = false;

    public FreecamEntity(World par1World, GameProfile par2Str) {
        super(par1World, par2Str);
    }

    public void setMovementInput(MovementInput par1MovementInput) {
        this.movementInput = par1MovementInput;
        if (par1MovementInput.jump && !this.flyMode && this.onGround) {
            this.jump();
        }
        this.moveEntityWithHeading(par1MovementInput.moveStrafe, par1MovementInput.moveForward);
    }

    public void moveEntity(double x, double y, double z) {
        super.moveEntity(x, y, z);
    }

    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (this.flyMode) {
            this.noClip = true;
            this.motionX = 0.0;
            this.motionY = 0.0;
            this.motionZ = 0.0;
            this.setAIMoveSpeed(0.6f);
            this.jumpMovementFactor = 0.6f;
            if (this.movementInput != null) {
                this.motionX = 0.0;
                this.motionY = 0.0;
                this.motionZ = 0.0;
                this.jumpMovementFactor *= 3.0f;
                if (this.movementInput.jump) {
                    this.motionY += 1.0;
                }
                if (this.movementInput.sneak) {
                    this.motionY -= 1.0;
                }
            }
        } else {
            if (!this.isSprinting()) {
                this.setAIMoveSpeed(0.1f);
                this.jumpMovementFactor = 0.02f;
            }
            this.noClip = false;
        }
    }
}

