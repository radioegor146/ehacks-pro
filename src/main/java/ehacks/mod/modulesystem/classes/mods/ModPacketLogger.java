package ehacks.mod.modulesystem.classes.mods;

import ehacks.mod.api.ModStatus;
import ehacks.mod.api.Module;
import ehacks.mod.util.InteropUtils;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.PacketHandler;

public class ModPacketLogger extends Module {
    public ModPacketLogger() {
        super(ModuleCategory.EHACKS);
    }

    @Override
    public String getName() {
        return "ModPacketLogger";
    }

    @Override
    public String getDescription() {
        return "Sends info about incoming and outcoming packets from mods.";
    }

    @Override
    public ModStatus getModStatus() {
        return ModStatus.WORKING;
    }

    @Override
    public String getModName() {
        return "ModPacketLogger";
    }

    @Override
    public boolean canOnOnStart() {
        return true;
    }

    @Override
    public boolean onPacket(Object packet, PacketHandler.Side side) {
        String name = packet.getClass().getName();

        if (name.startsWith("net.minecraft")) {
            return true;
        }

        InteropUtils.log(name, "Packet" + side.toString());

        return true;
    }
}