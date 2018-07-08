/*
 * Decompiled with CFR 0_128.
 */
package ehacks.mod.util;

import java.util.Collection;
import java.util.HashSet;

public class Utils {
    public static void removeDupes(Collection list) {
        HashSet set = new HashSet(list);
        list.clear();
        list.addAll(set);
    }
}

