package ehacks.mod.util;

import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.client.Minecraft;

public class Mappings {

    public static String timer = Mappings.isMCP() ? "timer" : "field_71428_T";
    public static String isInWeb = Mappings.isMCP() ? "isInWeb" : "field_70134_J";
    public static String registerReloadListener = Mappings.isMCP() ? "registerReloadListener" : "func_110542_a";
    public static String chunkListing = Mappings.isMCP() ? "chunkListing" : "field_73237_c";
    public static String currentSlot = Mappings.isMCP() ? "theSlot" : "field_75186_f";
    public static String isMouseOverSlot = Mappings.isMCP() ? "isMouseOverSlot" : "func_146981_a";
    public static String splashText = Mappings.isMCP() ? "splashText" : "field_73975_c";

    public static boolean isMCP() {
        try {
            return ReflectionHelper.findField(Minecraft.class, (String[]) new String[]{"theMinecraft"}) != null;
        } catch (Exception ex) {
            return false;
        }
    }
}
