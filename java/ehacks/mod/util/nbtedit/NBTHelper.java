/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.ReflectionHelper
 *  net.minecraft.nbt.CompressedStreamTools
 *  net.minecraft.nbt.NBTBase
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.nbt.NBTTagList
 */
package ehacks.mod.util.nbtedit;

import cpw.mods.fml.relauncher.ReflectionHelper;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class NBTHelper {
    public static NBTTagCompound nbtRead(DataInputStream in) throws IOException {
        return CompressedStreamTools.read((DataInputStream)in);
    }

    public static void nbtWrite(NBTTagCompound compound, DataOutput out) throws IOException {
        CompressedStreamTools.write((NBTTagCompound)compound, (DataOutput)out);
    }

    public static Map<String, NBTBase> getMap(NBTTagCompound tag) {
        return (Map)ReflectionHelper.getPrivateValue(NBTTagCompound.class, tag, (int)1);
    }

    public static NBTBase getTagAt(NBTTagList tag, int index) {
        List list = (List)ReflectionHelper.getPrivateValue(NBTTagList.class, tag, (int)0);
        return (NBTBase)list.get(index);
    }
}

