package ehacks.mod.util.nbtedit;

import com.google.common.base.Strings;
import net.minecraft.nbt.*;

public class NBTStringHelper {

    public static final char SECTION_SIGN = '\u00a7';

    public static String getNBTName(NamedNBT namedNBT) {
        String name = namedNBT.getName();
        NBTBase obj = namedNBT.getNBT();
        String s = NBTStringHelper.toString(obj);
        return Strings.isNullOrEmpty(name) ? "" + s : name + ": " + s;
    }

    public static String getNBTNameSpecial(NamedNBT namedNBT) {
        String name = namedNBT.getName();
        NBTBase obj = namedNBT.getNBT();
        String s = NBTStringHelper.toString(obj);
        return Strings.isNullOrEmpty(name) ? "" + s : name + ": " + s + '\u00a7' + 'r';
    }

    public static NBTBase newTag(byte type) {
        switch (type) {
            case 1: {
                return new NBTTagCompound();
            }
            case 2: {
                return new NBTTagByte((byte) 0);
            }
            case 3: {
                return new NBTTagShort();
            }
            case 4: {
                return new NBTTagInt(0);
            }
            case 5: {
                return new NBTTagLong(0L);
            }
            case 6: {
                return new NBTTagFloat(0.0f);
            }
            case 7: {
                return new NBTTagDouble(0.0);
            }
            case 8: {
                return new NBTTagString("");
            }
            case 9: {
                return new NBTTagList();
            }
            case 10: {
                return new NBTTagByteArray(new byte[0]);
            }
            case 11: {
                return new NBTTagIntArray(new int[0]);
            }
        }
        return null;
    }

    public static String toString(NBTBase base) {
        switch (GuiNBTNode.NBT_ICON_MAPPING[base.getId() - 1]) {
            case 0: {
                return "(TagCompound)";
            }
            case 1: {
                return "" + ((NBTTagByte) base).getByte();
            }
            case 2: {
                return "" + ((NBTTagShort) base).getShort();
            }
            case 3: {
                return "" + ((NBTTagInt) base).getInt();
            }
            case 4: {
                return "" + ((NBTTagLong) base).getLong();
            }
            case 5: {
                return "" + ((NBTTagFloat) base).getFloat();
            }
            case 6: {
                return "" + ((NBTTagDouble) base).getDouble();
            }
            case 7: {
                return ((NBTTagString) base).getString();
            }
            case 8: {
                return "(TagList)";
            }
            case 9: {
                return base.toString();
            }
            case 10: {
                return base.toString();
            }
        }
        return "?";
    }

    public static String getButtonName(byte id) {
        switch (id) {
            case 1: {
                return "Compound";
            }
            case 2: {
                return "Byte";
            }
            case 3: {
                return "Short";
            }
            case 4: {
                return "Int";
            }
            case 5: {
                return "Long";
            }
            case 6: {
                return "Float";
            }
            case 7: {
                return "Double";
            }
            case 8: {
                return "String";
            }
            case 9: {
                return "List";
            }
            case 10: {
                return "Byte[]";
            }
            case 11: {
                return "Int[]";
            }
            case 12: {
                return "Edit";
            }
            case 13: {
                return "Delete";
            }
            case 14: {
                return "Copy";
            }
            case 15: {
                return "Cut";
            }
            case 16: {
                return "Paste";
            }
        }
        return "Unknown";
    }
}
