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

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.ByteBufUtils;
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
import ehacks.api.module.ModStatus;
import ehacks.mod.gui.EHacksClickGui;
import ehacks.mod.modulesystem.classes.AimBot;
import ehacks.mod.modulesystem.classes.AutoBlock;
import ehacks.mod.modulesystem.classes.Criticals;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.util.ArrayList;
import java.util.Map;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import java.util.Random;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;

public class GalaxyTeleport
extends Mod {
    public GalaxyTeleport() {
        super(ModuleCategory.EHACKS);
    }

    @Override
    public String getName() {
        return "GalaxyTeleport";
    }
    
    @Override
    public String getDescription() {
        return "On click you can teleport to any planet in Galacticraft";
    }
    
    @Override
    public ModStatus getModStatus() {
        try {
            Class.forName("micdoodle8.mods.galacticraft.core.network.PacketSimple");
            return ModStatus.WORKING;
        } catch (Exception e) {
            return ModStatus.NOTWORKING;
        }
    }
    
    @Override
    public void onEnableMod() {
        try {
            Class.forName("micdoodle8.mods.galacticraft.core.network.PacketSimple");
            List<Object> objects = new ArrayList<Object>();
            objects.addAll(((Map<String, Object>)Class.forName("micdoodle8.mods.galacticraft.api.galaxies.GalaxyRegistry").getMethod("getRegisteredPlanets").invoke(null)).values());
            objects.addAll(((Map<String, Object>)Class.forName("micdoodle8.mods.galacticraft.api.galaxies.GalaxyRegistry").getMethod("getRegisteredMoons").invoke(null)).values());
            objects.addAll(((Map<String, Object>)Class.forName("micdoodle8.mods.galacticraft.api.galaxies.GalaxyRegistry").getMethod("getRegisteredSatellites").invoke(null)).values());
            Object screen = Class.forName("micdoodle8.mods.galacticraft.core.client.gui.screen.GuiCelestialSelection").getConstructor(Boolean.TYPE, List.class).newInstance(false, objects);
            Wrapper.INSTANCE.mc().displayGuiScreen((GuiScreen)screen);
            this.off(); 
        }
        catch (Exception ex) {
            this.off();
            ex.printStackTrace();
            EHacksClickGui.log("[GalaxyTeleport] Not working");
        }
    }

    @Override
    public void onDisableMod() {
        
    }

    @Override
    public void onTicks() {
        if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
        {
            Wrapper.INSTANCE.mc().displayGuiScreen(null);
        }
    }
    
    @Override
    public String getModName() {
        return "Galacticraft";
    }
}

