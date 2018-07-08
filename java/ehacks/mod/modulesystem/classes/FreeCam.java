/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityClientPlayerMP
 *  net.minecraft.client.multiplayer.WorldClient
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.InventoryPlayer
 *  net.minecraft.util.AxisAlignedBB
 *  net.minecraft.util.MovementInput
 *  net.minecraft.world.World
 */
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
import ehacks.mod.util.FreeCamEntity;
import ehacks.mod.wrapper.ModuleCategories;
import ehacks.mod.wrapper.Wrapper;

public class FreeCam
extends Mod {
    public FreeCamEntity freecamEnt = null;
    private double posX;
    private double posY;
    private double posZ;

    public FreeCam() {
        super(ModuleCategories.PLAYER);
    }

    @Override
    public String getName() {
        return "FreeCam";
    }

    @Override
    public void onEnableMod() {
        if (Wrapper.INSTANCE.player() != null && Wrapper.INSTANCE.mc().theWorld != null) {
            this.posX = Wrapper.INSTANCE.player().posX;
            this.posY = Wrapper.INSTANCE.player().posY;
            this.posZ = Wrapper.INSTANCE.player().posZ;
            this.freecamEnt = new FreeCamEntity((World)Wrapper.INSTANCE.mc().theWorld, Wrapper.INSTANCE.player().getGameProfile());
            this.freecamEnt.setPosition(this.posX, Wrapper.INSTANCE.player().boundingBox.minY, this.posZ);
            Wrapper.INSTANCE.world().spawnEntityInWorld((Entity)this.freecamEnt);
        }
        Wrapper.INSTANCE.mc().renderViewEntity = this.freecamEnt;
    }

    @Override
    public void onDisableMod() {
        if (this.freecamEnt != null && Wrapper.INSTANCE.world() != null) {
            Wrapper.INSTANCE.world().removeEntity((Entity)this.freecamEnt);
            this.freecamEnt = null;
        }
        Wrapper.INSTANCE.mc().renderViewEntity = Wrapper.INSTANCE.player();
    }

    @Override
    public void onTicks() {
        Wrapper.INSTANCE.player().posX = this.posX;
        Wrapper.INSTANCE.player().posY = this.posY;
        Wrapper.INSTANCE.player().posZ = this.posZ;
        Wrapper.INSTANCE.player().motionX = 0.0;
        Wrapper.INSTANCE.player().motionY = 0.0;
        Wrapper.INSTANCE.player().motionZ = 0.0;
        this.freecamEnt.inventory = Wrapper.INSTANCE.player().inventory;
        this.freecamEnt.yOffset = Wrapper.INSTANCE.player().yOffset;
        this.freecamEnt.ySize = Wrapper.INSTANCE.player().ySize;
        this.freecamEnt.setMovementInput(Wrapper.INSTANCE.player().movementInput);
        this.freecamEnt.rotationPitch = Wrapper.INSTANCE.player().rotationPitch;
        this.freecamEnt.rotationYaw = Wrapper.INSTANCE.player().rotationYaw;
        this.freecamEnt.rotationYawHead = Wrapper.INSTANCE.player().rotationYawHead;
        this.freecamEnt.setSprinting(Wrapper.INSTANCE.player().isSprinting());
        if (Wrapper.INSTANCE.mc().renderViewEntity != this.freecamEnt) {
            Wrapper.INSTANCE.mc().renderViewEntity = this.freecamEnt;
        }
    }
}

