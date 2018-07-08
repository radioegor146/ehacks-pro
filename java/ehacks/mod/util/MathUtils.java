/*
 * Decompiled with CFR 0_128.
 */
package ehacks.mod.util;

import ehacks.mod.internal.CEBlockCoord;
import java.util.Arrays;

public class MathUtils {
    public static CEBlockCoord nearestBlock(CEBlockCoord of, CEBlockCoord[] in, int size) {
        CEBlockCoord closest = null;
        CEBlockCoord newCoord = in[0].substract(of);
        int distanceX = (int)Math.abs(newCoord.getX());
        int distanceY = (int)Math.abs(newCoord.getY());
        int distanceZ = (int)Math.abs(newCoord.getZ());
        for (int c = 0; c < size; ++c) {
            CEBlockCoord newCoord2 = in[c].substract(of);
            int cdistanceX = (int)Math.abs(newCoord2.getX());
            int cdistanceY = (int)Math.abs(newCoord2.getY());
            int cdistanceZ = (int)Math.abs(newCoord2.getZ());
            if (cdistanceX >= distanceX || cdistanceY >= distanceY || cdistanceZ >= distanceZ) continue;
            closest = newCoord2;
            distanceX = cdistanceX;
            distanceY = cdistanceY;
            distanceZ = cdistanceZ;
        }
        return closest;
    }

    public static Integer nearInclusive(double[] array, double value) {
        Integer i = null;
        int idx = MathUtils.binarySearch(array, value);
        if (idx < 0) {
            if ((idx = - idx - 1) != 0 && idx < array.length) {
                double d1;
                double d0 = Math.abs(array[idx - 1] - value);
                i = d0 <= (d1 = Math.abs(array[idx] - value)) ? idx - 1 : idx;
            }
        } else {
            i = idx;
        }
        return i;
    }

    public static int binarySearch(double[] a, double key) {
        int index = -1;
        index = a[0] < a[1] ? Arrays.binarySearch(a, key) : MathUtils.binarySearch(a, key, 0, a.length - 1);
        return index;
    }

    private static int binarySearch(double[] a, double key, int low, int high) {
        while (low <= high) {
            int cmp = 0;
            int mid = (low + high) / 2;
            double midVal = a[mid];
            if (midVal > key) {
                cmp = -1;
            } else if (midVal < key) {
                cmp = 1;
            } else {
                long keyBits;
                long midBits = Double.doubleToLongBits(midVal);
                int n = midBits == (keyBits = Double.doubleToLongBits(key)) ? 0 : (cmp = midBits < keyBits ? -1 : 1);
            }
            if (cmp < 0) {
                low = mid + 1;
                continue;
            }
            if (cmp > 0) {
                high = mid - 1;
                continue;
            }
            return mid;
        }
        return - low + 1;
    }
}

