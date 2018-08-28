package ehacks.mod.modulesystem.classes;

import ehacks.api.module.ModStatus;
import ehacks.api.module.Module;
import ehacks.mod.gui.EHacksClickGui;
import ehacks.mod.gui.window.WindowPlayerIds;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import java.lang.reflect.Method;
import java.util.List;
import net.minecraft.entity.Entity;

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
    public void onEnableMod() {
        try {
            Class.forName("micdoodle8.mods.galacticraft.core.network.PacketSimple").getConstructor();
        } catch (Exception ex) {
            this.off();
            EHacksClickGui.log("[NoLimitFire] Not working");
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
            List<Entity> players = (List<Entity>) (WindowPlayerIds.useIt ? (Object) WindowPlayerIds.getPlayers() : Wrapper.INSTANCE.world().loadedEntityList);
            for (Object o : players) {
                if (((Entity) o).getEntityId() != Wrapper.INSTANCE.player().getEntityId()) {
                    fireEntity(((Entity) o).getEntityId());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fireEntity(int entityId) {
        try {
            Object packetPipeLine = Class.forName("micdoodle8.mods.galacticraft.core.GalacticraftCore").getField("packetPipeline").get(null);
            Method sendMethod = packetPipeLine.getClass().getMethod("sendToServer", new Class[]{Class.forName("micdoodle8.mods.galacticraft.core.network.IPacket")});
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
