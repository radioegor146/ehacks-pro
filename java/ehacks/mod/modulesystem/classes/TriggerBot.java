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
 *  net.minecraft.util.MovingObjectPosition
 *  net.minecraft.world.World
 */
package ehacks.mod.modulesystem.classes;

import java.util.Random;
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
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import ehacks.api.module.Mod;
import ehacks.mod.modulesystem.classes.AutoBlock;
import ehacks.mod.modulesystem.classes.Criticals;
import ehacks.mod.relationsystem.Friend;
import ehacks.mod.wrapper.ModuleCategories;
import ehacks.mod.wrapper.Wrapper;

public class TriggerBot
extends Mod {
    public static boolean isActive = false;
    private static Random rand = new Random();
    private static long lastMS;

    public TriggerBot() {
        super(ModuleCategories.COMBAT);
    }

    @Override
    public String getName() {
        return "TriggerBot";
    }

    @Override
    public String getDescription() {
        return "TriggerBot";
    }

    @Override
    public void onEnableMod() {
        isActive = true;
    }

    @Override
    public void onDisableMod() {
        isActive = false;
    }

    private static long getCurrentMS() {
        return System.nanoTime() / 1000000L;
    }

    private static boolean hasReached(long milliseconds) {
        return TriggerBot.getCurrentMS() - lastMS >= milliseconds;
    }

    @Override
    public void reset() {
        lastMS = TriggerBot.getCurrentMS();
    }

    private boolean isValidTarget(Entity e) {
        return (!Friend.instance().readFriend(e.getCommandSenderName()) || !(e instanceof EntityPlayer)) && e instanceof EntityLivingBase;
    }

    @Override
    public void onTicks() {
        try {
            if (!Friend.instance().readFriend(Wrapper.INSTANCE.mc().objectMouseOver.entityHit.getCommandSenderName()) && Wrapper.INSTANCE.mc().objectMouseOver != null && Wrapper.INSTANCE.mc().objectMouseOver.entityHit != null && this.isValidTarget(Wrapper.INSTANCE.mc().objectMouseOver.entityHit) && TriggerBot.hasReached(150 + rand.nextInt(100))) {
                if (Criticals.isActive && !Wrapper.INSTANCE.player().isInWater() && !Wrapper.INSTANCE.player().isInsideOfMaterial(Material.lava) && !Wrapper.INSTANCE.player().isInsideOfMaterial(Material.web) && Wrapper.INSTANCE.player().onGround) {
                    Wrapper.INSTANCE.player().motionY = 0.1;
                    Wrapper.INSTANCE.player().fallDistance = 0.1f;
                    Wrapper.INSTANCE.player().onGround = false;
                }
                if (AutoBlock.isActive && Wrapper.INSTANCE.player().getCurrentEquippedItem() != null && Wrapper.INSTANCE.player().getCurrentEquippedItem().getItem() instanceof ItemSword) {
                    ItemStack lel = Wrapper.INSTANCE.player().getCurrentEquippedItem();
                    lel.useItemRightClick((World)Wrapper.INSTANCE.world(), (EntityPlayer)Wrapper.INSTANCE.player());
                }
                Wrapper.INSTANCE.player().swingItem();
                Wrapper.INSTANCE.mc().playerController.attackEntity((EntityPlayer)Wrapper.INSTANCE.player(), Wrapper.INSTANCE.mc().objectMouseOver.entityHit);
                this.reset();
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

