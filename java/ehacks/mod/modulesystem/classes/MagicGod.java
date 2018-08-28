package ehacks.mod.modulesystem.classes;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.relauncher.ReflectionHelper;
import ehacks.api.module.ModStatus;
import ehacks.api.module.Module;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.util.ArrayList;
import net.minecraft.network.play.client.C17PacketCustomPayload;

public class MagicGod extends Module {

    public MagicGod() {
        super(ModuleCategory.EHACKS);
    }

    private Object getPrivateValue(String className, String fieldName, Object from) throws Exception {
        return ReflectionHelper.findField(Class.forName(className), fieldName).get(from);
    }

    @Override
    public void onEnableMod() {
        try {
            ArrayList aspects = (ArrayList)Class.forName("thaumcraft.api.aspects.Aspect").getMethod("getCompoundAspects").invoke(null);
            for (Object aspect : aspects) {
                Object aspect1 = ((Object[])Class.forName("thaumcraft.api.aspects.Aspect").getMethod("getComponents").invoke(aspect))[0];
                Object aspect2 = ((Object[])Class.forName("thaumcraft.api.aspects.Aspect").getMethod("getComponents").invoke(aspect))[1];
                String a1 = (String)Class.forName("thaumcraft.api.aspects.Aspect").getMethod("getTag").invoke(aspect1);
                String a2 = (String)Class.forName("thaumcraft.api.aspects.Aspect").getMethod("getTag").invoke(aspect2);
                doGive(a1, a2);
            }
            this.off();
        } catch (Exception e) {

        }
    }

    @Override
    public ModStatus getModStatus() {
        try {
            Class.forName("thaumcraft.api.aspects.Aspect");
            return ModStatus.WORKING;
        } catch (Exception e) {
            return ModStatus.NOTWORKING;
        }
    }

    @Override
    public String getModName() {
        return "Thaumcraft";
    }

    @Override
    public String getName() {
        return "MagicGod";
    }

    @Override
    public String getDescription() {
        return "Automatically gives all avaible thaumcraft compound aspects";
    }

    private void doGive(String a1, String a2) {
        ByteBuf buf = Unpooled.buffer(0);
	buf.writeByte(13);
	buf.writeInt(Wrapper.INSTANCE.player().dimension);
	buf.writeInt(Wrapper.INSTANCE.player().getEntityId());
	buf.writeInt(0);
	buf.writeInt(0);
	buf.writeInt(0);
	ByteBufUtils.writeUTF8String(buf, a1);
	ByteBufUtils.writeUTF8String(buf, a2);
	buf.writeBoolean(true);
	buf.writeBoolean(true);
	C17PacketCustomPayload packet = new C17PacketCustomPayload("thaumcraft", buf);
        Wrapper.INSTANCE.player().sendQueue.addToSendQueue(packet);
    }
}
