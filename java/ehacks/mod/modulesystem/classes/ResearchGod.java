package ehacks.mod.modulesystem.classes;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.relauncher.ReflectionHelper;
import ehacks.api.module.ModStatus;
import ehacks.api.module.Module;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.network.play.client.C17PacketCustomPayload;

public class ResearchGod extends Module {

    public ResearchGod() {
        super(ModuleCategory.EHACKS);
    }

    private Object getPrivateValue(String className, String fieldName, Object from) throws Exception {
        return ReflectionHelper.findField(Class.forName(className), fieldName).get(from);
    }

    @Override
    public void onEnableMod() {
        try {

        } catch (Exception e) {

        }
    }

    @Override
    public ModStatus getModStatus() {
        try {
            Class.forName("thaumcraft.api.research.ResearchCategories");
            Class.forName("thaumcraft.api.research.ResearchCategoryList");
            Class.forName("thaumcraft.api.research.ResearchItem");
            Class.forName("thaumcraft.common.lib.research.ResearchManager");
            Class.forName("thaumcraft.api.aspects.AspectList");
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
        return "ResearchGod";
    }

    @Override
    public String getDescription() {
        return "Automatically researches all avaible thaumcraft researches";
    }

    public int tickId = -1;

    @Override
    public void onTicks() {
        tickId++;
        if (tickId % 40 != 0) {
            return;
        }
        tickId = 0;
        try {
            Field f = Class.forName("thaumcraft.client.gui.GuiResearchPopup").getDeclaredField("theResearch");
            f.setAccessible(true);
            ((ArrayList)f.get(Class.forName("thaumcraft.client.lib.ClientTickEventsFML").getField("researchPopup").get(null))).clear();
            LinkedHashMap<String, Object> researchCategories
                    = (LinkedHashMap<String, Object>) getPrivateValue("thaumcraft.api.research.ResearchCategories", "researchCategories", null);
            for (Object listObj : researchCategories.values()) {
                Map<String, Object> research
                        = (Map<String, Object>) getPrivateValue("thaumcraft.api.research.ResearchCategoryList", "research", listObj);
                for (Object item : research.values()) {
                    String[] parents = (String[]) getPrivateValue("thaumcraft.api.research.ResearchItem", "parents", item);
                    String[] parentsHidden = (String[]) getPrivateValue("thaumcraft.api.research.ResearchItem", "parentsHidden", item);
                    String key = (String) getPrivateValue("thaumcraft.api.research.ResearchItem", "key", item);
                    if (!isResearchComplete(key)) {
                        boolean doIt = true;
                        if (parents != null) {
                            for (String parent : parents) {
                                if (!isResearchComplete(parent)) {
                                    doIt = false;
                                    break;
                                }
                            }
                        }
                        if (!doIt) {
                            continue;
                        }
                        if (parentsHidden != null) {
                            for (String parent : parentsHidden) {
                                if (!isResearchComplete(parent)) {
                                    doIt = false;
                                    break;
                                }
                            }
                        }
                        if (!doIt) {
                            continue;
                        }
                        for (Object aObj : getAspects(item)) {
                            if (aObj == null) {
                                doIt = false;
                                break;
                            }
                        }
                        if (!doIt) {
                            continue;
                        }
                        doResearch(key);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isResearchComplete(String researchId) throws Exception {
        return (Boolean) Class.forName("thaumcraft.common.lib.research.ResearchManager").getMethod("isResearchComplete", String.class, String.class).invoke(null, Wrapper.INSTANCE.player().getCommandSenderName(), researchId);
    }

    private Object[] getAspects(Object item) throws Exception {
        Object aspectListObj = getPrivateValue("thaumcraft.api.research.ResearchItem", "tags", item);
        return (Object[]) Class.forName("thaumcraft.api.aspects.AspectList").getMethod("getAspects").invoke(aspectListObj);
    }

    private void doResearch(String researchId) {
        ByteBuf buf = Unpooled.buffer(0);
        buf.writeByte(14);
        ByteBufUtils.writeUTF8String(buf, researchId);
        buf.writeInt(Wrapper.INSTANCE.player().dimension);
        ByteBufUtils.writeUTF8String(buf, Wrapper.INSTANCE.player().getCommandSenderName());
        buf.writeByte(0);
        C17PacketCustomPayload packet = new C17PacketCustomPayload("thaumcraft", buf);
        Wrapper.INSTANCE.player().sendQueue.addToSendQueue(packet);
    }
}
