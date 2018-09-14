/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehacks.mod.modulesystem.classes;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import ehacks.api.module.ModStatus;
import ehacks.api.module.Module;
import ehacks.mod.util.InteropUtils;
import ehacks.mod.wrapper.Keybinds;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import java.lang.reflect.Field;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import org.lwjgl.input.Keyboard;

/**
 *
 * @author radioegor146
 */
public class Magnendo extends Module {

    public Magnendo() {
        super(ModuleCategory.EHACKS);
    }

    @Override
    public String getName() {
        return "Magnendo";
    }

    @Override
    public String getDescription() {
        return "Magnets all EntityItem to you on Numpad1";
    }

    private boolean prevState = false;

    @Override
    public void onTicks() {
        if (Keyboard.isKeyDown(Keybinds.magnet) && !prevState) {
            int cnt = 0;
            for (Object entObj : Wrapper.INSTANCE.world().loadedEntityList) {
                Entity ent = (Entity) entObj;
                if (ent instanceof EntityItem) {
                    cnt++;
                    tpEntity(ent, (int) Math.round(Wrapper.INSTANCE.player().lastTickPosX), (int) Math.round(Wrapper.INSTANCE.player().lastTickPosY), (int) Math.round(Wrapper.INSTANCE.player().lastTickPosZ));
                }
            }
            InteropUtils.log("Magneted " + cnt + " items", this);
        }
        prevState = Keyboard.isKeyDown(Keybinds.magnet);
    }

    @Override
    public ModStatus getModStatus() {
        try {
            Class.forName("crazypants.enderio.network.PacketHandler").getField("INSTANCE");
            Class.forName("crazypants.enderio.api.teleport.TravelSource").getField("BLOCK");
            Class.forName("crazypants.enderio.teleport.packet.PacketTravelEvent").getConstructor(Entity.class, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Boolean.TYPE, Class.forName("crazypants.enderio.api.teleport.TravelSource"));
            Class.forName("crazypants.enderio.teleport.packet.PacketTravelEvent").getDeclaredField("entityId");
            return ModStatus.WORKING;
        } catch (Exception e) {
            return ModStatus.NOTWORKING;
        }
    }

    public void tpEntity(Entity ent, int x, int y, int z) {
        try {
            SimpleNetworkWrapper snw = (SimpleNetworkWrapper) Class.forName("crazypants.enderio.network.PacketHandler").getField("INSTANCE").get(null);
            Object travelSource = Class.forName("crazypants.enderio.api.teleport.TravelSource").getField("BLOCK").get(null);
            Object packet = Class.forName("crazypants.enderio.teleport.packet.PacketTravelEvent").getConstructor(Entity.class, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Boolean.TYPE, Class.forName("crazypants.enderio.api.teleport.TravelSource")).newInstance(ent, x, y, z, 0, false, travelSource);
            Field f = Class.forName("crazypants.enderio.teleport.packet.PacketTravelEvent").getDeclaredField("entityId");
            f.setAccessible(true);
            f.set(packet, ent.getEntityId());
            snw.sendToServer((IMessage) packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getModName() {
        return "EnderIO";
    }
}
