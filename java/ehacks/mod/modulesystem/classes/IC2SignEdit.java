/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityClientPlayerMP
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemFood
 *  net.minecraft.item.ItemStack
 */
package ehacks.mod.modulesystem.classes;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.ReflectionHelper;
import ehacks.api.module.Mod;
import ehacks.mod.gui.reeszrbteam.YouAlwaysWinClickGui;
import ehacks.mod.logger.ModLogger;
import ehacks.mod.main.Main;
import static ehacks.mod.modulesystem.classes.BlockDestroy.isActive;
import ehacks.mod.wrapper.Events;
import ehacks.mod.wrapper.ModuleCategories;
import ehacks.mod.wrapper.Wrapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import org.lwjgl.input.Mouse;

public class IC2SignEdit
extends Mod {
    public IC2SignEdit() {
        super(ModuleCategories.EHACKS);
    }

    @Override
    public String getName() {
        return "IC2 Sign Edit";
    }

    @Override
    public String getDescription() {
        return "IC2 Sign Edit";
    }
    
    @Override
    public void onTick() {
        
    }
    
    @Override
    public void onEnableMod() {
        try {
            Class.forName("shedar.mods.ic2.nuclearcontrol.network.message.PacketClientSensor").getConstructor();
        } catch (Exception ex) {
            this.off();
            YouAlwaysWinClickGui.log("[IC2 Sign Edit] \u0422\u0443\u0442 \u043d\u0435 \u0440\u0430\u0431\u043e\u0442\u0430\u0435\u0442");
        }
    }
    
    @Override
    public void onMouse(MouseEvent event) {
        try
        {
            MovingObjectPosition position = Wrapper.INSTANCE.mc().objectMouseOver;
            TileEntity entity = Minecraft.getMinecraft().theWorld.getTileEntity(position.blockX, position.blockY, position.blockZ);
            if (entity != null && Class.forName("shedar.mods.ic2.nuclearcontrol.tileentities.TileEntityInfoPanel").isInstance(entity) && Mouse.isButtonDown(1))
            {
                Object containerInfoPanel = Class.forName("shedar.mods.ic2.nuclearcontrol.containers.ContainerInfoPanel").getConstructor(new Class[] { EntityPlayer.class, Class.forName("shedar.mods.ic2.nuclearcontrol.tileentities.TileEntityInfoPanel") }).newInstance((EntityPlayer)Wrapper.INSTANCE.player(), entity);
                GuiContainer guiInfoPanel = (GuiContainer)Class.forName("shedar.mods.ic2.nuclearcontrol.gui.GuiInfoPanel").getConstructor(new Class[] { Container.class }).newInstance(containerInfoPanel);     
                Wrapper.INSTANCE.mc().displayGuiScreen((GuiScreen)guiInfoPanel);
                if (event.isCancelable())
                    event.setCanceled(true);
            }
            if (entity != null && Class.forName("shedar.mods.ic2.nuclearcontrol.tileentities.TileEntityThermo").isInstance(entity) && Mouse.isButtonDown(1))
            {
                GuiContainer guiInfoPanel = (GuiContainer)Class.forName("shedar.mods.ic2.nuclearcontrol.gui.GuiIC2Thermo").getConstructor(new Class[] { Class.forName("shedar.mods.ic2.nuclearcontrol.tileentities.TileEntityThermo") }).newInstance(entity);     
                Wrapper.INSTANCE.mc().displayGuiScreen((GuiScreen)guiInfoPanel);
                if (event.isCancelable())
                    event.setCanceled(true);
            }
            if (entity != null && Class.forName("shedar.mods.ic2.nuclearcontrol.tileentities.TileEntityRemoteThermo").isInstance(entity) && Mouse.isButtonDown(1))
            {
                Object containerInfoPanel = Class.forName("shedar.mods.ic2.nuclearcontrol.containers.ContainerRemoteThermo").getConstructor(new Class[] { EntityPlayer.class, Class.forName("shedar.mods.ic2.nuclearcontrol.tileentities.TileEntityRemoteThermo") }).newInstance((EntityPlayer)Wrapper.INSTANCE.player(), entity);
                GuiContainer guiInfoPanel = (GuiContainer)Class.forName("shedar.mods.ic2.nuclearcontrol.gui.GuiRemoteThermo").getConstructor(new Class[] { Container.class }).newInstance(containerInfoPanel);     
                Wrapper.INSTANCE.mc().displayGuiScreen((GuiScreen)guiInfoPanel);
                if (event.isCancelable())
                    event.setCanceled(true);
            }
            if (entity != null && Class.forName("shedar.mods.ic2.nuclearcontrol.tileentities.TileEntityRangeTrigger").isInstance(entity) && Mouse.isButtonDown(1))
            {
                Object containerInfoPanel = Class.forName("shedar.mods.ic2.nuclearcontrol.containers.ContainerRangeTrigger").getConstructor(new Class[] { EntityPlayer.class, Class.forName("shedar.mods.ic2.nuclearcontrol.tileentities.TileEntityRangeTrigger") }).newInstance((EntityPlayer)Wrapper.INSTANCE.player(), entity);
                GuiContainer guiInfoPanel = (GuiContainer)Class.forName("shedar.mods.ic2.nuclearcontrol.gui.GuiRangeTrigger").getConstructor(new Class[] { Container.class }).newInstance(containerInfoPanel);     
                Wrapper.INSTANCE.mc().displayGuiScreen((GuiScreen)guiInfoPanel);
                if (event.isCancelable())
                    event.setCanceled(true);
            }
            if (entity != null && Class.forName("shedar.mods.ic2.nuclearcontrol.tileentities.TileEntityIndustrialAlarm").isInstance(entity) && Mouse.isButtonDown(1))
            {
                GuiContainer guiInfoPanel = (GuiContainer)Class.forName("shedar.mods.ic2.nuclearcontrol.gui.GuiIndustrialAlarm").getConstructor(new Class[] { Class.forName("shedar.mods.ic2.nuclearcontrol.tileentities.TileEntityHowlerAlarm") }).newInstance(entity);     
                Wrapper.INSTANCE.mc().displayGuiScreen((GuiScreen)guiInfoPanel);
                if (event.isCancelable())
                    event.setCanceled(true);
            }
            if (entity != null && Class.forName("shedar.mods.ic2.nuclearcontrol.tileentities.TileEntityHowlerAlarm").isInstance(entity) && Mouse.isButtonDown(1))
            {
                GuiContainer guiInfoPanel = (GuiContainer)Class.forName("shedar.mods.ic2.nuclearcontrol.gui.GuiHowlerAlarm").getConstructor(new Class[] { Class.forName("shedar.mods.ic2.nuclearcontrol.tileentities.TileEntityHowlerAlarm") }).newInstance(entity);     
                Wrapper.INSTANCE.mc().displayGuiScreen((GuiScreen)guiInfoPanel);
                if (event.isCancelable())
                    event.setCanceled(true);
            }
            if (entity != null && Class.forName("shedar.mods.ic2.nuclearcontrol.tileentities.TileEntityEnergyCounter").isInstance(entity) && Mouse.isButtonDown(1))
            {
                Object containerInfoPanel = Class.forName("shedar.mods.ic2.nuclearcontrol.containers.ContainerEnergyCounter").getConstructor(new Class[] { EntityPlayer.class, Class.forName("shedar.mods.ic2.nuclearcontrol.tileentities.TileEntityEnergyCounter") }).newInstance((EntityPlayer)Wrapper.INSTANCE.player(), entity);
                GuiContainer guiInfoPanel = (GuiContainer)Class.forName("shedar.mods.ic2.nuclearcontrol.gui.GuiEnergyCounter").getConstructor(new Class[] { Container.class }).newInstance(containerInfoPanel);     
                Wrapper.INSTANCE.mc().displayGuiScreen((GuiScreen)guiInfoPanel);
                if (event.isCancelable())
                    event.setCanceled(true);
            }
            if (entity != null && Class.forName("shedar.mods.ic2.nuclearcontrol.tileentities.TileEntityEnergyCounter").isInstance(entity) && Mouse.isButtonDown(1))
            {
                Object containerInfoPanel = Class.forName("shedar.mods.ic2.nuclearcontrol.containers.ContainerEnergyCounter").getConstructor(new Class[] { EntityPlayer.class, Class.forName("shedar.mods.ic2.nuclearcontrol.tileentities.TileEntityEnergyCounter") }).newInstance((EntityPlayer)Wrapper.INSTANCE.player(), entity);
                GuiContainer guiInfoPanel = (GuiContainer)Class.forName("shedar.mods.ic2.nuclearcontrol.gui.GuiEnergyCounter").getConstructor(new Class[] { Container.class }).newInstance(containerInfoPanel);     
                Wrapper.INSTANCE.mc().displayGuiScreen((GuiScreen)guiInfoPanel);
                if (event.isCancelable())
                    event.setCanceled(true);
            }
            if (entity != null && Class.forName("shedar.mods.ic2.nuclearcontrol.tileentities.TileEntityAverageCounter").isInstance(entity) && Mouse.isButtonDown(1))
            {
                Object containerInfoPanel = Class.forName("shedar.mods.ic2.nuclearcontrol.containers.ContainerAverageCounter").getConstructor(new Class[] { EntityPlayer.class, Class.forName("shedar.mods.ic2.nuclearcontrol.tileentities.TileEntityAverageCounter") }).newInstance((EntityPlayer)Wrapper.INSTANCE.player(), entity);
                GuiContainer guiInfoPanel = (GuiContainer)Class.forName("shedar.mods.ic2.nuclearcontrol.gui.GuiAverageCounter").getConstructor(new Class[] { Container.class }).newInstance(containerInfoPanel);     
                Wrapper.INSTANCE.mc().displayGuiScreen((GuiScreen)guiInfoPanel);
                if (event.isCancelable())
                    event.setCanceled(true);
            }
            if (entity != null && Class.forName("shedar.mods.ic2.nuclearcontrol.tileentities.TileEntityAdvancedInfoPanel").isInstance(entity) && Mouse.isButtonDown(1))
            {
                Object containerInfoPanel = Class.forName("shedar.mods.ic2.nuclearcontrol.containers.ContainerAdvancedInfoPanel").getConstructor(new Class[] { EntityPlayer.class, Class.forName("shedar.mods.ic2.nuclearcontrol.tileentities.TileEntityAdvancedInfoPanel") }).newInstance((EntityPlayer)Wrapper.INSTANCE.player(), entity);
                GuiContainer guiInfoPanel = (GuiContainer)Class.forName("shedar.mods.ic2.nuclearcontrol.gui.GuiAdvancedInfoPanel").getConstructor(new Class[] { Container.class }).newInstance(containerInfoPanel);     
                Wrapper.INSTANCE.mc().displayGuiScreen((GuiScreen)guiInfoPanel);
                if (event.isCancelable())
                    event.setCanceled(true);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

