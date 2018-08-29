package ehacks.mod.modulesystem.classes;

import ehacks.api.module.ModStatus;
import ehacks.api.module.Module;
import ehacks.mod.gui.EHacksClickGui;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import java.lang.reflect.Method;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.event.MouseEvent;
import org.lwjgl.input.Mouse;

public class SpaceFire
        extends Module {

    public SpaceFire() {
        super(ModuleCategory.EHACKS);
    }

    @Override
    public String getName() {
        return "Space Fire";
    }

    @Override
    public String getDescription() {
        return "Fires entities";
    }

    @Override
    public void onEnableMod() {
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
    public void onMouse(MouseEvent event) {
        try {
            MovingObjectPosition position = Wrapper.INSTANCE.mc().objectMouseOver;
            if (position.entityHit != null && position.entityHit instanceof EntityLivingBase && Mouse.isButtonDown(0)) {
                Object packetPipeLine = Class.forName("micdoodle8.mods.galacticraft.core.GalacticraftCore").getField("packetPipeline").get(null);
                Method sendMethod = packetPipeLine.getClass().getMethod("sendToServer", new Class[]{Class.forName("micdoodle8.mods.galacticraft.core.network.IPacket")});
                Object packetObj = Class.forName("micdoodle8.mods.galacticraft.core.network.PacketSimple").getConstructor(new Class[]{Class.forName("micdoodle8.mods.galacticraft.core.network.PacketSimple$EnumSimplePacket"), Object[].class}).newInstance(Class.forName("micdoodle8.mods.galacticraft.core.network.PacketSimple$EnumSimplePacket").getMethod("valueOf", String.class).invoke(null, "S_SET_ENTITY_FIRE"), new Object[]{position.entityHit.getEntityId()});
                sendMethod.invoke(packetPipeLine, packetObj);
                if (event.isCancelable()) {
                    event.setCanceled(true);
                }
            }
        } catch (Exception e) {

        }
    }

    @Override
    public String getModName() {
        return "Galacticraft";
    }
}
