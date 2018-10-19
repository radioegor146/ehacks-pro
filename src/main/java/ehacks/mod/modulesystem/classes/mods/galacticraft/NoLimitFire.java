package ehacks.mod.modulesystem.classes.mods.galacticraft;

import ehacks.mod.api.ModStatus;
import ehacks.mod.api.Module;
import ehacks.mod.gui.window.WindowPlayerIds;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import java.lang.reflect.Method;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class NoLimitFire
        extends Module {

    public NoLimitFire() {
        super(ModuleCategory.EHACKS);
    }

    @Override
    public String getName() {
        return "NoLimitFire";
    }

    @Override
    public String getDescription() {
        return "Fires all players around you";
    }

    @Override
    public void onModuleEnabled() {
        try {
            Class.forName("micdoodle8.mods.galacticraft.core.network.PacketSimple").getConstructor();
        } catch (Exception ex) {
            this.off();
        }
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
    public void onTicks() {
        try {
            List<EntityPlayer> players = (WindowPlayerIds.useIt ? (List<EntityPlayer>)WindowPlayerIds.getPlayers() : (List<EntityPlayer>)Wrapper.INSTANCE.world().loadedEntityList);
            players.stream().filter((o) -> (((Entity) o).getEntityId() != Wrapper.INSTANCE.player().getEntityId())).forEachOrdered((o) -> {
                fireEntity(((Entity) o).getEntityId());
            });
        } catch (Exception e) {
        }
    }

    public void fireEntity(int entityId) {
        try {
            Object packetPipeLine = Class.forName("micdoodle8.mods.galacticraft.core.GalacticraftCore").getField("packetPipeline").get(null);
            Method sendMethod = packetPipeLine.getClass().getMethod("sendToServer", Class.forName("micdoodle8.mods.galacticraft.core.network.IPacket"));
            Object packetObj = Class.forName("micdoodle8.mods.galacticraft.core.network.PacketSimple").getConstructor(new Class[]{Class.forName("micdoodle8.mods.galacticraft.core.network.PacketSimple$EnumSimplePacket"), Object[].class}).newInstance(Class.forName("micdoodle8.mods.galacticraft.core.network.PacketSimple$EnumSimplePacket").getMethod("valueOf", String.class).invoke(null, "S_SET_ENTITY_FIRE"), new Object[]{entityId});
            sendMethod.invoke(packetPipeLine, packetObj);
        } catch (Exception ex) {

        }
    }

    @Override
    public String getModName() {
        return "Galacticraft";
    }
}
