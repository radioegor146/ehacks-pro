package ehacks.mod.modulesystem.classes;

import ehacks.api.module.ModStatus;
import ehacks.api.module.Module;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.PacketHandler.Side;
import ehacks.mod.wrapper.Wrapper;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.event.MouseEvent;
import org.lwjgl.input.Mouse;

public class IC2SignEdit
        extends Module {

    public IC2SignEdit() {
        super(ModuleCategory.EHACKS);
    }

    @Override
    public String getName() {
        return "IC2SignEdit";
    }

    @Override
    public String getDescription() {
        return "Allows you to edit configs of blocks from IC2 Nuclear Control mod with right click";
    }

    @Override
    public void onEnableMod() {
        try {
            Class.forName("shedar.mods.ic2.nuclearcontrol.network.message.PacketClientSensor").getConstructor();
        } catch (Exception ex) {
            this.off();
        }
    }

    @Override
    public ModStatus getModStatus() {
        try {
            Class.forName("shedar.mods.ic2.nuclearcontrol.network.message.PacketClientSensor");
            return ModStatus.WORKING;
        } catch (Exception e) {
            return ModStatus.NOTWORKING;
        }
    }

    private boolean fakeGuiOpened = false;

    @Override
    public void onMouse(MouseEvent event) {
        try {
            MovingObjectPosition position = Wrapper.INSTANCE.mc().objectMouseOver;
            TileEntity entity = Wrapper.INSTANCE.world().getTileEntity(position.blockX, position.blockY, position.blockZ);
            if (entity != null && Class.forName("shedar.mods.ic2.nuclearcontrol.tileentities.TileEntityInfoPanel").isInstance(entity) && Mouse.isButtonDown(1)) {
                Object containerInfoPanel = Class.forName("shedar.mods.ic2.nuclearcontrol.containers.ContainerInfoPanel").getConstructor(new Class[]{EntityPlayer.class, Class.forName("shedar.mods.ic2.nuclearcontrol.tileentities.TileEntityInfoPanel")}).newInstance((EntityPlayer) Wrapper.INSTANCE.player(), entity);
                GuiContainer guiInfoPanel = (GuiContainer) Class.forName("shedar.mods.ic2.nuclearcontrol.gui.GuiInfoPanel").getConstructor(new Class[]{Container.class}).newInstance(containerInfoPanel);
                fakeGuiOpened = true;
                Wrapper.INSTANCE.mc().displayGuiScreen((GuiScreen) guiInfoPanel);
                if (event.isCancelable()) {
                    event.setCanceled(true);
                }
            }
            if (entity != null && Class.forName("shedar.mods.ic2.nuclearcontrol.tileentities.TileEntityThermo").isInstance(entity) && Mouse.isButtonDown(1)) {
                GuiContainer guiInfoPanel = (GuiContainer) Class.forName("shedar.mods.ic2.nuclearcontrol.gui.GuiIC2Thermo").getConstructor(new Class[]{Class.forName("shedar.mods.ic2.nuclearcontrol.tileentities.TileEntityThermo")}).newInstance(entity);
                fakeGuiOpened = true;
                Wrapper.INSTANCE.mc().displayGuiScreen((GuiScreen) guiInfoPanel);
                if (event.isCancelable()) {
                    event.setCanceled(true);
                }
            }
            if (entity != null && Class.forName("shedar.mods.ic2.nuclearcontrol.tileentities.TileEntityRemoteThermo").isInstance(entity) && Mouse.isButtonDown(1)) {
                Object containerInfoPanel = Class.forName("shedar.mods.ic2.nuclearcontrol.containers.ContainerRemoteThermo").getConstructor(new Class[]{EntityPlayer.class, Class.forName("shedar.mods.ic2.nuclearcontrol.tileentities.TileEntityRemoteThermo")}).newInstance((EntityPlayer) Wrapper.INSTANCE.player(), entity);
                GuiContainer guiInfoPanel = (GuiContainer) Class.forName("shedar.mods.ic2.nuclearcontrol.gui.GuiRemoteThermo").getConstructor(new Class[]{Container.class}).newInstance(containerInfoPanel);
                fakeGuiOpened = true;
                Wrapper.INSTANCE.mc().displayGuiScreen((GuiScreen) guiInfoPanel);
                if (event.isCancelable()) {
                    event.setCanceled(true);
                }
            }
            if (entity != null && Class.forName("shedar.mods.ic2.nuclearcontrol.tileentities.TileEntityRangeTrigger").isInstance(entity) && Mouse.isButtonDown(1)) {
                Object containerInfoPanel = Class.forName("shedar.mods.ic2.nuclearcontrol.containers.ContainerRangeTrigger").getConstructor(new Class[]{EntityPlayer.class, Class.forName("shedar.mods.ic2.nuclearcontrol.tileentities.TileEntityRangeTrigger")}).newInstance((EntityPlayer) Wrapper.INSTANCE.player(), entity);
                GuiContainer guiInfoPanel = (GuiContainer) Class.forName("shedar.mods.ic2.nuclearcontrol.gui.GuiRangeTrigger").getConstructor(new Class[]{Container.class}).newInstance(containerInfoPanel);
                fakeGuiOpened = true;
                Wrapper.INSTANCE.mc().displayGuiScreen((GuiScreen) guiInfoPanel);
                if (event.isCancelable()) {
                    event.setCanceled(true);
                }
            }
            if (entity != null && Class.forName("shedar.mods.ic2.nuclearcontrol.tileentities.TileEntityIndustrialAlarm").isInstance(entity) && Mouse.isButtonDown(1)) {
                GuiContainer guiInfoPanel = (GuiContainer) Class.forName("shedar.mods.ic2.nuclearcontrol.gui.GuiIndustrialAlarm").getConstructor(new Class[]{Class.forName("shedar.mods.ic2.nuclearcontrol.tileentities.TileEntityHowlerAlarm")}).newInstance(entity);
                fakeGuiOpened = true;
                Wrapper.INSTANCE.mc().displayGuiScreen((GuiScreen) guiInfoPanel);
                if (event.isCancelable()) {
                    event.setCanceled(true);
                }
            }
            if (entity != null && Class.forName("shedar.mods.ic2.nuclearcontrol.tileentities.TileEntityHowlerAlarm").isInstance(entity) && Mouse.isButtonDown(1)) {
                GuiContainer guiInfoPanel = (GuiContainer) Class.forName("shedar.mods.ic2.nuclearcontrol.gui.GuiHowlerAlarm").getConstructor(new Class[]{Class.forName("shedar.mods.ic2.nuclearcontrol.tileentities.TileEntityHowlerAlarm")}).newInstance(entity);
                fakeGuiOpened = true;
                Wrapper.INSTANCE.mc().displayGuiScreen((GuiScreen) guiInfoPanel);
                if (event.isCancelable()) {
                    event.setCanceled(true);
                }
            }
            if (entity != null && Class.forName("shedar.mods.ic2.nuclearcontrol.tileentities.TileEntityEnergyCounter").isInstance(entity) && Mouse.isButtonDown(1)) {
                Object containerInfoPanel = Class.forName("shedar.mods.ic2.nuclearcontrol.containers.ContainerEnergyCounter").getConstructor(new Class[]{EntityPlayer.class, Class.forName("shedar.mods.ic2.nuclearcontrol.tileentities.TileEntityEnergyCounter")}).newInstance((EntityPlayer) Wrapper.INSTANCE.player(), entity);
                GuiContainer guiInfoPanel = (GuiContainer) Class.forName("shedar.mods.ic2.nuclearcontrol.gui.GuiEnergyCounter").getConstructor(new Class[]{Container.class}).newInstance(containerInfoPanel);
                fakeGuiOpened = true;
                Wrapper.INSTANCE.mc().displayGuiScreen((GuiScreen) guiInfoPanel);
                if (event.isCancelable()) {
                    event.setCanceled(true);
                }
            }
            if (entity != null && Class.forName("shedar.mods.ic2.nuclearcontrol.tileentities.TileEntityEnergyCounter").isInstance(entity) && Mouse.isButtonDown(1)) {
                Object containerInfoPanel = Class.forName("shedar.mods.ic2.nuclearcontrol.containers.ContainerEnergyCounter").getConstructor(new Class[]{EntityPlayer.class, Class.forName("shedar.mods.ic2.nuclearcontrol.tileentities.TileEntityEnergyCounter")}).newInstance((EntityPlayer) Wrapper.INSTANCE.player(), entity);
                GuiContainer guiInfoPanel = (GuiContainer) Class.forName("shedar.mods.ic2.nuclearcontrol.gui.GuiEnergyCounter").getConstructor(new Class[]{Container.class}).newInstance(containerInfoPanel);
                fakeGuiOpened = true;
                Wrapper.INSTANCE.mc().displayGuiScreen((GuiScreen) guiInfoPanel);
                if (event.isCancelable()) {
                    event.setCanceled(true);
                }
            }
            if (entity != null && Class.forName("shedar.mods.ic2.nuclearcontrol.tileentities.TileEntityAverageCounter").isInstance(entity) && Mouse.isButtonDown(1)) {
                Object containerInfoPanel = Class.forName("shedar.mods.ic2.nuclearcontrol.containers.ContainerAverageCounter").getConstructor(new Class[]{EntityPlayer.class, Class.forName("shedar.mods.ic2.nuclearcontrol.tileentities.TileEntityAverageCounter")}).newInstance((EntityPlayer) Wrapper.INSTANCE.player(), entity);
                GuiContainer guiInfoPanel = (GuiContainer) Class.forName("shedar.mods.ic2.nuclearcontrol.gui.GuiAverageCounter").getConstructor(new Class[]{Container.class}).newInstance(containerInfoPanel);
                Wrapper.INSTANCE.mc().displayGuiScreen((GuiScreen) guiInfoPanel);
                fakeGuiOpened = true;
                Wrapper.INSTANCE.mc().displayGuiScreen((GuiScreen) guiInfoPanel);
                if (event.isCancelable()) {
                    event.setCanceled(true);
                }
            }
            if (entity != null && Class.forName("shedar.mods.ic2.nuclearcontrol.tileentities.TileEntityAdvancedInfoPanel").isInstance(entity) && Mouse.isButtonDown(1)) {
                Object containerInfoPanel = Class.forName("shedar.mods.ic2.nuclearcontrol.containers.ContainerAdvancedInfoPanel").getConstructor(new Class[]{EntityPlayer.class, Class.forName("shedar.mods.ic2.nuclearcontrol.tileentities.TileEntityAdvancedInfoPanel")}).newInstance((EntityPlayer) Wrapper.INSTANCE.player(), entity);
                GuiContainer guiInfoPanel = (GuiContainer) Class.forName("shedar.mods.ic2.nuclearcontrol.gui.GuiAdvancedInfoPanel").getConstructor(new Class[]{Container.class}).newInstance(containerInfoPanel);
                Wrapper.INSTANCE.mc().displayGuiScreen((GuiScreen) guiInfoPanel);
                fakeGuiOpened = true;
                Wrapper.INSTANCE.mc().displayGuiScreen((GuiScreen) guiInfoPanel);
                if (event.isCancelable()) {
                    event.setCanceled(true);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getModName() {
        return "NucControl";
    }

    @Override
    public void onTicks() {
        if (Wrapper.INSTANCE.mc().currentScreen == null) {
            fakeGuiOpened = false;
        }
    }

    @Override
    public boolean onPacket(Object packet, Side side) {
        return false;
    }
    
    @Override
    public boolean canOnOnStart() {
        return true;
    }
}
