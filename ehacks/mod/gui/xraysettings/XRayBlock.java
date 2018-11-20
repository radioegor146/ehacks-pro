package ehacks.mod.gui.xraysettings;

import ehacks.mod.config.ConfigurationManager;
import java.util.ArrayList;
import net.minecraft.block.Block;

public class XRayBlock {

    public static ArrayList<XRayBlock> blocks = new ArrayList<>();
    public int r;
    public int g;
    public int b;
    public int a;
    public int meta;
    public String id = "";
    public boolean enabled = true;

    public XRayBlock() {
    }

    public XRayBlock(int r, int g, int b2, int a2, int meta, String id, boolean enabled) {
        this.r = r;
        this.g = g;
        this.b = b2;
        this.a = a2;
        this.id = id;
        this.meta = meta;
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "" + this.r + " " + this.g + " " + this.b + " " + this.a + " " + this.meta + " " + this.id + " " + this.enabled;
    }

    public static XRayBlock fromString(String s) {
        XRayBlock result = new XRayBlock();
        String[] info = s.split(" ");
        result.r = Integer.parseInt(info[0]);
        result.g = Integer.parseInt(info[1]);
        result.b = Integer.parseInt(info[2]);
        result.a = Integer.parseInt(info[3]);
        result.meta = Integer.parseInt(info[4]);
        result.id = info[5];
        result.enabled = Boolean.parseBoolean(info[6]);
        return result;
    }

    public static void setStandardList() {
        ArrayList<XRayBlock> block = new ArrayList<>();
        block.add(new XRayBlock(0, 0, 128, 200, -1, "minecraft:lapis_ore", true));
        block.add(new XRayBlock(255, 0, 0, 200, -1, "minecraft:redstone_ore", true));
        block.add(new XRayBlock(255, 255, 0, 200, -1, "minecraft:gold_ore", true));
        block.add(new XRayBlock(0, 255, 0, 200, -1, "minecraft:emerald_ore", true));
        block.add(new XRayBlock(0, 191, 255, 200, -1, "minecraft:diamond_ore", true));
        blocks = block;
        ConfigurationManager.instance().saveConfigs();
    }

    public static void removeInvalidBlocks() {
        for (int i = 0; i < blocks.size(); ++i) {
            XRayBlock block = blocks.get(i);
            if (Block.blockRegistry.containsKey(block.id)) {
                continue;
            }
            blocks.remove(block);
        }
    }

    public static void init() {
        XRayBlock.removeInvalidBlocks();
        if (blocks.isEmpty()) {
            XRayBlock.setStandardList();
        }
    }
}
