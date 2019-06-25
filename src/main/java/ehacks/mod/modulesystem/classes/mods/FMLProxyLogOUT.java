package ehacks.mod.modulesystem.classes.mods;

import ehacks.mod.api.ModStatus;
import ehacks.mod.api.Module;
import ehacks.mod.util.InteropUtils;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.PacketHandler;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;

public class FMLProxyLogOUT extends Module {
    public FMLProxyLogOUT() {
        super(ModuleCategory.BEUHACKS);
    }

    @Override
    public String getName() {
        return "FMLProxyLogOUT";
    }

    @Override
    public String getDescription() {
        return "Sends info about outcoming packets.";
    }

    @Override
    public ModStatus getModStatus() {
        return ModStatus.WORKING;
    }

    @Override
    public String getModName() {
        return "";
    }

    @Override
    public boolean canOnOnStart() {
        return true;
    }

    @Override
    public boolean onPacket(Object packet, PacketHandler.Side side) {
        if (side != PacketHandler.Side.OUT) {
            return true;
        }

        if (packet instanceof FMLProxyPacket) {
            FMLProxyPacket fmlPacket = (FMLProxyPacket) packet;

            InteropUtils.log(String.format("Packet from channel %s", fmlPacket.channel()), "Packet" + side.toString());
        }
        return true;
    }
}