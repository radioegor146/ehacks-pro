package ehacks.mod.modulesystem.classes;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovementInput;
import net.minecraft.world.World;
import ehacks.api.module.Mod;
import ehacks.mod.util.EntityFakePlayer;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.PacketHandler.Side;
import ehacks.mod.packetlogger.PacketHandler;
import ehacks.mod.wrapper.Wrapper;
import javax.swing.JFrame;
import ehacks.mod.packetlogger.Gui;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PacketLogger
extends Mod {
    private Gui gui;
    private PacketHandler handler;
    
    public PacketLogger() {
        super(ModuleCategory.EHACKS);
        gui = new Gui();
        handler = new PacketHandler(gui);
    }

    @Override
    public String getName() {
        return "PacketLogger";
    }
    
    @Override
    public String getDescription() {
        return "Allows you to see all out- and incoming packets";
    }

    @Override
    public void onEnableMod() {
        gui.setVisible(true);
    }

    @Override
    public void onDisableMod() {
        gui.setVisible(false);
    }
    
    @Override
    public boolean onPacket(Object packet, Side side) {
        try {
            if (side == Side.IN && !handler.handlePacket(packet, null, PacketHandler.inBlackList))
                return false;
            if (side == Side.OUT && !handler.handlePacket(packet, null, PacketHandler.outBlackList))
                return false;
            handler.handlePacket(packet, side, PacketHandler.logBlackList);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return true;
    }
    
    @Override
    public boolean canOnOnStart() {
        return false;
    }
}

