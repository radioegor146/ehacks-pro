/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.NBTBase
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.nbt.NBTTagList
 */
package ehacks.mod.util.nbtedit;

import java.util.Comparator;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class NBTNodeSorter
implements Comparator<Node<NamedNBT>> {
    @Override
    public int compare(Node<NamedNBT> a, Node<NamedNBT> b) {
        NBTBase n1 = a.getObject().getNBT();
        NBTBase n2 = b.getObject().getNBT();
        String s1 = a.getObject().getName();
        String s2 = b.getObject().getName();
        if (n1 instanceof NBTTagCompound || n1 instanceof NBTTagList) {
            if (n2 instanceof NBTTagCompound || n2 instanceof NBTTagList) {
                int dif = n1.getId() - n2.getId();
                return dif == 0 ? s1.compareTo(s2) : dif;
            }
            return 1;
        }
        if (n2 instanceof NBTTagCompound || n2 instanceof NBTTagList) {
            return -1;
        }
        int dif = n1.getId() - n2.getId();
        return dif == 0 ? s1.compareTo(s2) : dif;
    }
}

