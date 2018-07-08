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
import ehacks.mod.commands.ACommandAuraRange;
import ehacks.mod.gui.reeszrbteam.YouAlwaysWinClickGui;
import ehacks.mod.modulesystem.classes.AimBot;
import ehacks.mod.modulesystem.classes.AutoBlock;
import ehacks.mod.modulesystem.classes.Criticals;
import ehacks.mod.relationsystem.Friend;
import ehacks.mod.wrapper.ModuleCategories;
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
        super(ModuleCategories.EHACKS);
    }

    @Override
    public String getName() {
        return "GalaxyTeleport";
    }

    @Override
    public void onEnableMod() {
        try {
            Class.forName("micdoodle8.mods.galacticraft.core.network.PacketSimple").getConstructor();
            List<Object> objects = new ArrayList<Object>();
            objects.addAll(((Map<String, Object>)Class.forName("micdoodle8.mods.galacticraft.api.galaxies.GalaxyRegistry").getMethod("getRegisteredPlanets").invoke(null)).values());
            objects.addAll(((Map<String, Object>)Class.forName("micdoodle8.mods.galacticraft.api.galaxies.GalaxyRegistry").getMethod("getRegisteredMoons").invoke(null)).values());
            objects.addAll(((Map<String, Object>)Class.forName("micdoodle8.mods.galacticraft.api.galaxies.GalaxyRegistry").getMethod("getRegisteredSatellites").invoke(null)).values());
            Object screen = Class.forName("micdoodle8.mods.galacticraft.core.client.gui.screen.GuiCelestialSelection").getConstructor(Boolean.TYPE, List.class).newInstance(false, objects);
            Wrapper.INSTANCE.mc().displayGuiScreen((GuiScreen)screen);
            /*
            ByteBuf buf = Unpooled.buffer();
            buf.writeByte(0);
            buf.writeInt(1);
            ByteBufUtils.writeUTF8String(buf, "moon.moon");
            C17PacketCustomPayload packet = new C17PacketCustomPayload("GalacticraftCore", buf);
            Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(packet);*/
            this.off(); 
        }
        catch (Exception ex) {
            this.off();
            ex.printStackTrace();
            YouAlwaysWinClickGui.log("[GalaxyTeleport] \u0422\u0443\u0442 \u043d\u0435 \u0440\u0430\u0431\u043e\u0442\u0430\u0435\u0442");
        }
    }

    @Override
    public void onDisableMod() {
        
    }

    @Override
    public void onTicks() {
        if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
        {
            
        }
    }
}

