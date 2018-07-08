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
 *  net.minecraft.entity.EntityLiving
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
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;
import ehacks.api.module.Mod;
import ehacks.mod.commands.ACommandAuraRange;
import ehacks.mod.modulesystem.classes.AimBot;
import ehacks.mod.modulesystem.classes.AutoBlock;
import ehacks.mod.modulesystem.classes.Criticals;
import ehacks.mod.wrapper.ModuleCategories;
import ehacks.mod.wrapper.Wrapper;

public class MobAura
extends Mod {
    public static boolean isActive = false;
    private long currentMS = 0L;
    private long lastMS = -1L;

    public MobAura() {
        super(ModuleCategories.COMBAT);
    }

    @Override
    public String getName() {
        return "MobAura";
    }

    @Override
    public void onEnableMod() {
        isActive = true;
    }

    @Override
    public void onDisableMod() {
        isActive = false;
    }

    private boolean hasDelayRun(long time) {
        return this.currentMS - this.lastMS >= time;
    }

    @Override
    public void onTicks() {
        block6 : {
            try {
                this.currentMS = System.nanoTime() / 980000L;
                if (!this.hasDelayRun(133L)) break block6;
                for (int i = 0; i < Wrapper.INSTANCE.mc().theWorld.loadedEntityList.size(); ++i) {
                    Entity e = (Entity)Wrapper.INSTANCE.mc().theWorld.getLoadedEntityList().get(i);
                    if (e == Wrapper.INSTANCE.player() || e.isDead || Wrapper.INSTANCE.player().getDistanceToEntity(e) >= ACommandAuraRange.aurarange || !(e instanceof EntityLiving)) continue;
                    if (AutoBlock.isActive && Wrapper.INSTANCE.player().getCurrentEquippedItem() != null && Wrapper.INSTANCE.player().getCurrentEquippedItem().getItem() instanceof ItemSword) {
                        ItemStack lel = Wrapper.INSTANCE.player().getCurrentEquippedItem();
                        lel.useItemRightClick((World)Wrapper.INSTANCE.world(), (EntityPlayer)Wrapper.INSTANCE.player());
                    }
                    if (Criticals.isActive && !Wrapper.INSTANCE.player().isInWater() && !Wrapper.INSTANCE.player().isInsideOfMaterial(Material.lava) && !Wrapper.INSTANCE.player().isInsideOfMaterial(Material.web) && Wrapper.INSTANCE.player().onGround) {
                        Wrapper.INSTANCE.player().motionY = 0.1000000014901161;
                        Wrapper.INSTANCE.player().fallDistance = 0.1f;
                        Wrapper.INSTANCE.player().onGround = false;
                    }
                    if (AimBot.isActive) {
                        AimBot.faceEntity(e);
                    }
                    Wrapper.INSTANCE.player().setSprinting(false);
                    Wrapper.INSTANCE.player().swingItem();
                    Wrapper.INSTANCE.mc().playerController.attackEntity((EntityPlayer)Wrapper.INSTANCE.player(), e);
                    this.lastMS = System.nanoTime() / 980000L;
                    break;
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

