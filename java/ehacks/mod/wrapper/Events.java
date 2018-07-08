/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.eventhandler.SubscribeEvent
 *  cpw.mods.fml.common.gameevent.TickEvent
 *  cpw.mods.fml.common.gameevent.TickEvent$ClientTickEvent
 *  cpw.mods.fml.common.gameevent.TickEvent$PlayerTickEvent
 *  cpw.mods.fml.common.gameevent.TickEvent$ServerTickEvent
 *  cpw.mods.fml.common.gameevent.TickEvent$WorldTickEvent
 *  net.minecraft.block.Block
 *  net.minecraft.block.material.Material
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityClientPlayerMP
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.multiplayer.WorldClient
 *  net.minecraft.client.settings.GameSettings
 *  net.minecraft.client.settings.KeyBinding
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.player.InventoryPlayer
 *  net.minecraft.inventory.Container
 *  net.minecraft.inventory.Slot
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.MovingObjectPosition
 *  net.minecraft.util.MovingObjectPosition$MovingObjectType
 *  net.minecraftforge.client.event.RenderGameOverlayEvent
 *  net.minecraftforge.client.event.RenderGameOverlayEvent$Text
 *  net.minecraftforge.client.event.RenderWorldLastEvent
 *  net.minecraftforge.event.entity.player.AttackEntityEvent
 *  net.minecraftforge.event.entity.player.PlayerInteractEvent
 *  net.minecraftforge.event.entity.player.PlayerInteractEvent$Action
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.opengl.GL11
 */
package ehacks.mod.wrapper;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import java.awt.Color;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import ehacks.api.module.APICEMod;
import ehacks.api.module.Mod;
import ehacks.mod.gui.reeszrbteam.Tuple;
import ehacks.mod.gui.reeszrbteam.YouAlwaysWinClickGui;
import ehacks.mod.gui.reeszrbteam.element.YAWWindow;
import ehacks.mod.gui.reeszrbteam.window.WindowPlayerIds;
import ehacks.mod.logger.ModLogger;
import ehacks.mod.main.Main;
import ehacks.mod.modulesystem.classes.AutoTool;
import ehacks.mod.modulesystem.classes.BlockDestroy;
import ehacks.mod.modulesystem.classes.Criticals;
import ehacks.mod.modulesystem.classes.Forcefield;
import ehacks.mod.modulesystem.classes.KillAura;
import ehacks.mod.modulesystem.classes.MobAura;
import ehacks.mod.modulesystem.classes.PrivateNuker;
import ehacks.mod.modulesystem.classes.ProphuntAura;
import ehacks.mod.modulesystem.classes.SeeHealth;
import ehacks.mod.modulesystem.classes.TriggerBot;
import ehacks.mod.wrapper.Wrapper;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;

public class Events {
    public static Block selectedBlock = null;
    public static String itemToGive = null;
    public static boolean itemGiveEnabled = false;
    public static int enchant = 0;
    FontRenderer fontRender;
    private boolean[] keyStates;

    public Events() {
        this.fontRender = Wrapper.INSTANCE.mc().fontRenderer;
        this.keyStates = new boolean[256];
        Main.INSTANCE.logger.info("Forge events initialization.");
    }

    @SubscribeEvent
    public void onTick(TickEvent.ServerTickEvent event) {
        for (Mod mod : APICEMod.INSTANCE.mods) {
            if (!mod.isActive() || Wrapper.INSTANCE.world() == null) continue;
            mod.onTick();
        }
    }

    @SubscribeEvent
    public void onTicks(TickEvent.ClientTickEvent event) {
        try
        {
            for (Object entity : Wrapper.INSTANCE.world().loadedEntityList)
            {
                if (entity instanceof EntityPlayer) {
                    EntityPlayer ep = (EntityPlayer)entity;
                    if (WindowPlayerIds.players.containsKey(ep.getCommandSenderName()))
                    {
                        WindowPlayerIds.players.remove(ep.getCommandSenderName());
                        WindowPlayerIds.players.put(ep.getCommandSenderName(), new Tuple<Integer, EntityPlayer>(0, ep));
                    }
                    else
                        WindowPlayerIds.players.put(ep.getCommandSenderName(), new Tuple<Integer, EntityPlayer>(0, ep));
                }
            }
        }
        catch (Exception e)
        {

        }
        for (Mod mod : APICEMod.INSTANCE.mods) {
            if (mod.isActive() && Wrapper.INSTANCE.world() != null) {
                mod.onTicks();
            }
            if (!this.checkAndSaveKeyState(mod.getKeybind()) || Wrapper.INSTANCE.world() == null) continue;
            mod.toggle();
            break;
        }
    }

    @SubscribeEvent
    public void onPlayerUpdate(TickEvent.PlayerTickEvent event) {
        for (Mod mod : APICEMod.INSTANCE.mods) {
            if (!mod.isActive() || Wrapper.INSTANCE.world() == null) continue;
            mod.onPlayerUpdate();
        }
    }

    @SubscribeEvent
    public void onWorldUpdate(TickEvent.WorldTickEvent event) {
        for (Mod mod : APICEMod.INSTANCE.mods) {
            if (!mod.isActive() || Wrapper.INSTANCE.world() == null) continue;
            mod.onWorldUpdate();
        }
    }

    @SubscribeEvent
    public void onRenderWorld(RenderWorldLastEvent event) {
        for (Mod mod : APICEMod.INSTANCE.mods) {
            if (!mod.isActive() || Wrapper.INSTANCE.world() == null) continue;
            mod.onWorldRender();
        }
    }
    
    @SubscribeEvent
    public void onMouse(MouseEvent event) {
        for (Mod mod : APICEMod.INSTANCE.mods) {
            if (!mod.isActive() || Wrapper.INSTANCE.world() == null) continue;
            mod.onMouse(event);
        }
    }

    @SubscribeEvent
    public void onClick(PlayerInteractEvent event) {
        for (Mod mod : APICEMod.INSTANCE.mods) {
            if (!mod.isActive() || Wrapper.INSTANCE.world() == null) continue;
            mod.onClick(event);
        }
    }

    @SubscribeEvent
    public void onGameOverlay(RenderGameOverlayEvent.Text event) {
        if (Wrapper.INSTANCE.mc().currentScreen == null) {
            int x2 = 8;
            int y2 = 7;
            GL11.glPushMatrix();
            String Copyright1 = "EHacks for Neuter";
            String Copyright2 = "Powered by CheatingEssentials [Qmaks edit]";
            String Copyright3 = "[ForgeFuck and radioegor146 edition]";
            GL11.glScalef((float)1f, (float)1f, (float)1f);
            this.fontRender.drawString(Copyright1, 2,  2, Events.rainbowEffect_Text(9999999L, 1.0f).getRGB());
            this.fontRender.drawString(Copyright2, 2, 12, Events.rainbowEffect_Text(9999999L, 1.0f).getRGB());
            this.fontRender.drawString(Copyright3, 2, 22, Events.rainbowEffect_Text(9999999L, 1.0f).getRGB());
            GL11.glPopMatrix();
        }
        for (YAWWindow windows : YouAlwaysWinClickGui.windows) {
            if (!windows.isPinned()) continue;
            windows.draw(0, 0);
        }
    }

    public static Color rainbowEffect_Text(long offset, float fade) {
        float hue = (float)(System.nanoTime() + offset) / 1.0E10f % 1.0f;
        long color = Long.parseLong(Integer.toHexString(Color.HSBtoRGB(hue, 1.0f, 1.0f)), 16);
        Color c = new Color((int)color);
        return new Color((float)c.getRed() / 255.0f * fade, (float)c.getGreen() / 255.0f * fade, (float)c.getBlue() / 255.0f * fade, (float)c.getAlpha() / 255.0f);
    }

    @SubscribeEvent
    public void onAttack(AttackEntityEvent event) {
        if (!(KillAura.isActive || MobAura.isActive || ProphuntAura.isActive || Forcefield.isActive || TriggerBot.isActive || !Criticals.isActive || Wrapper.INSTANCE.player().isInWater() || Wrapper.INSTANCE.player().isInsideOfMaterial(Material.lava) || Wrapper.INSTANCE.player().isInsideOfMaterial(Material.web) || !Wrapper.INSTANCE.player().onGround || !Wrapper.INSTANCE.mc().gameSettings.keyBindAttack.getIsKeyPressed() || Wrapper.INSTANCE.mc().objectMouseOver == null || Wrapper.INSTANCE.mc().objectMouseOver.typeOfHit != MovingObjectPosition.MovingObjectType.ENTITY)) {
            event.setCanceled(true);
            Wrapper.INSTANCE.player().motionY = 0.1000000014901161;
            Wrapper.INSTANCE.player().fallDistance = 0.1f;
            Wrapper.INSTANCE.player().onGround = false;
            event.setCanceled(false);
        }
        if (event.target instanceof EntityPlayer) {
            EntityPlayer e = (EntityPlayer)event.target;
            if (SeeHealth.isActive) {
                Wrapper.INSTANCE.addChatMessage("&9[&bEhacks Console&9] &fPlayer health &e" + e.getCommandSenderName() + "&f: &e" + e.getHealth());
            }
        }
    }

    public boolean checkAndSaveKeyState(int key) {
        if (Wrapper.INSTANCE.mc().currentScreen != null) {
            return false;
        }
        if (Keyboard.isKeyDown((int)key) != this.keyStates[key]) {
            this.keyStates[key] = !this.keyStates[key];
            return this.keyStates[key];
        }
        return false;
    }
}

