/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.material.Material
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityClientPlayerMP
 *  net.minecraft.client.multiplayer.PlayerControllerMP
 *  net.minecraft.client.multiplayer.WorldClient
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.ItemSword
 *  net.minecraft.world.World
 */
package ehacks.mod.modulesystem.classes;

import java.util.List;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;
import ehacks.api.module.Mod;
import ehacks.mod.modulesystem.classes.AimBot;
import ehacks.mod.modulesystem.classes.AutoBlock;
import ehacks.mod.modulesystem.classes.Criticals;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;

public class Forcefield
extends Mod {
    public static boolean isActive = false;

    public Forcefield() {
        super(ModuleCategory.COMBAT);
    }

    @Override
    public String getName() {
        return "Forcefield";
    }

    @Override
    public void onEnableMod() {
        isActive = true;
    }

    @Override
    public void onDisableMod() {
        isActive = false;
    }

    private void hitEntity(Entity e, boolean block, boolean criticals, boolean aimbot, boolean auto) {
        if (block && Wrapper.INSTANCE.player().getCurrentEquippedItem().getItem() instanceof ItemSword) {
            ItemStack lel = Wrapper.INSTANCE.player().getCurrentEquippedItem();
            lel.useItemRightClick((World)Wrapper.INSTANCE.world(), (EntityPlayer)Wrapper.INSTANCE.player());
        }
        if (criticals && !Wrapper.INSTANCE.player().isInWater() && !Wrapper.INSTANCE.player().isInsideOfMaterial(Material.lava) && !Wrapper.INSTANCE.player().isInsideOfMaterial(Material.web) && Wrapper.INSTANCE.player().onGround) {
            Wrapper.INSTANCE.player().motionY = 0.1000000014901161;
            Wrapper.INSTANCE.player().fallDistance = 0.1f;
            Wrapper.INSTANCE.player().onGround = false;
        }
        if (aimbot) {
            AimBot.faceEntity(e);
        }
        Wrapper.INSTANCE.mc().playerController.attackEntity((EntityPlayer)Wrapper.INSTANCE.player(), e);
        Wrapper.INSTANCE.player().swingItem();
    }

    private float getDistanceToEntity(Entity from, Entity to) {
        return from.getDistanceToEntity(to);
    }

    private boolean isWithinRange(float range, Entity e) {
        return this.getDistanceToEntity(e, (Entity)Wrapper.INSTANCE.player()) <= range;
    }

    @Override
    public void onTicks() {
        try {
            for (Object o : Wrapper.INSTANCE.world().loadedEntityList) {
                EntityLivingBase entity = null;
                if (o instanceof EntityLivingBase) {
                    entity = (EntityLivingBase)o;
                }
                if (entity == null || !this.isWithinRange(6, (Entity)entity) || entity.isDead || entity == Wrapper.INSTANCE.player()) continue;
                this.hitEntity((Entity)entity, AutoBlock.isActive, Criticals.isActive, AimBot.isActive, true);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

