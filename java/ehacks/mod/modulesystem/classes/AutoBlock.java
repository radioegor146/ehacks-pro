/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityClientPlayerMP
 *  net.minecraft.client.multiplayer.WorldClient
 *  net.minecraft.client.settings.GameSettings
 *  net.minecraft.client.settings.KeyBinding
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.ItemSword
 *  net.minecraft.world.World
 */
package ehacks.mod.modulesystem.classes;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;
import ehacks.api.module.Mod;
import ehacks.mod.modulesystem.classes.Forcefield;
import ehacks.mod.modulesystem.classes.KillAura;
import ehacks.mod.modulesystem.classes.MobAura;
import ehacks.mod.modulesystem.classes.ProphuntAura;
import ehacks.mod.modulesystem.classes.TriggerBot;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;

public class AutoBlock
extends Mod {
    public static boolean isActive = false;

    public AutoBlock() {
        super(ModuleCategory.COMBAT);
    }

    @Override
    public String getName() {
        return "AutoBlock";
    }

    @Override
    public String getDescription() {
        return "Your sword will be blocking damage while using it";
    }

    @Override
    public void onEnableMod() {
        isActive = true;
    }

    @Override
    public void onDisableMod() {
        isActive = false;
    }

    @Override
    public void onTicks() {
        if (!(KillAura.isActive || MobAura.isActive || ProphuntAura.isActive || Forcefield.isActive || TriggerBot.isActive || !Wrapper.INSTANCE.mc().gameSettings.keyBindAttack.getIsKeyPressed() || Wrapper.INSTANCE.player().getCurrentEquippedItem() == null || !(Wrapper.INSTANCE.player().getCurrentEquippedItem().getItem() instanceof ItemSword))) {
            ItemStack lel = Wrapper.INSTANCE.player().getCurrentEquippedItem();
            lel.useItemRightClick((World)Wrapper.INSTANCE.world(), (EntityPlayer)Wrapper.INSTANCE.player());
        }
    }
}

