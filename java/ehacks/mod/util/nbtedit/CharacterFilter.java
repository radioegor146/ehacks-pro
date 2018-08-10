/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.ChatAllowedCharacters
 */
package ehacks.mod.util.nbtedit;

import net.minecraft.util.ChatAllowedCharacters;

public class CharacterFilter {
    public static String filerAllowedCharacters(String str, boolean section) {
        StringBuilder sb = new StringBuilder();
        for (char c : str.toCharArray()) {
            if (!ChatAllowedCharacters.isAllowedCharacter((char)c) && (!section || c != '\u00a7' && c != '\n')) continue;
            sb.append(c);
        }
        return sb.toString();
    }
}

