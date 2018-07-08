/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.Tessellator
 *  org.lwjgl.opengl.GL11
 */
package ehacks.mod.internal.ttf;

import ehacks.mod.internal.ttf.GlyphCache;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.Point2D;
import java.lang.ref.WeakReference;
import java.text.Bidi;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.WeakHashMap;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.opengl.GL11;

public class StringCache {
    private static final int BASELINE_OFFSET = 7;
    private static final int UNDERLINE_OFFSET = 1;
    private static final int UNDERLINE_THICKNESS = 2;
    private static final int STRIKETHROUGH_OFFSET = -6;
    private static final int STRIKETHROUGH_THICKNESS = 2;
    private final GlyphCache glyphCache = new GlyphCache();
    private final int[] colorTable;
    private final WeakHashMap<Key, Entry> stringCache = new WeakHashMap();
    private final WeakHashMap<String, Key> weakRefCache = new WeakHashMap();
    private final Key lookupKey = new Key();
    private final Glyph[][] digitGlyphs = new Glyph[4][];
    private boolean digitGlyphsReady = false;
    private boolean antiAliasEnabled = false;
    private final Thread mainThread = Thread.currentThread();

    public StringCache(int[] colors) {
        this.colorTable = colors;
        this.cacheDightGlyphs();
    }

    public void setDefaultFont(String name, int size, boolean antiAlias) {
        this.glyphCache.setDefaultFont(name, size, antiAlias);
        this.antiAliasEnabled = antiAlias;
        this.weakRefCache.clear();
        this.stringCache.clear();
        this.cacheDightGlyphs();
    }

    private void cacheDightGlyphs() {
        this.digitGlyphsReady = false;
        this.digitGlyphs[0] = this.cacheString((String)"0123456789").glyphs;
        this.digitGlyphs[1] = this.cacheString((String)"???l0123456789").glyphs;
        this.digitGlyphs[2] = this.cacheString((String)"???o0123456789").glyphs;
        this.digitGlyphs[3] = this.cacheString((String)"???l???o0123456789").glyphs;
        this.digitGlyphsReady = true;
    }

    public int renderString(String str, float startX, float startY, int initialColor, boolean shadowFlag) {
        if (str == null || str.isEmpty()) {
            return 0;
        }
        Entry entry = this.cacheString(str);
        startY += 7.0f;
        int color = initialColor;
        int boundTextureName = 0;
        GL11.glColor3f((float)(color >> 16 & 255), (float)(color >> 8 & 255), (float)(color & 255));
        if (this.antiAliasEnabled) {
            GL11.glEnable((int)3042);
            GL11.glBlendFunc((int)770, (int)771);
        }
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.setColorRGBA(color >> 16 & 255, color >> 8 & 255, color & 255, color >> 24 & 255);
        byte fontStyle = 0;
        int colorIndex = 0;
        for (int glyphIndex = 0; glyphIndex < entry.glyphs.length; ++glyphIndex) {
            while (colorIndex < entry.colors.length && entry.glyphs[glyphIndex].stringIndex >= entry.colors[colorIndex].stringIndex) {
                color = this.applyColorCode(entry.colors[colorIndex].colorCode, initialColor, shadowFlag);
                fontStyle = entry.colors[colorIndex].fontStyle;
                ++colorIndex;
            }
            Glyph glyph = entry.glyphs[glyphIndex];
            GlyphCache.Entry texture = glyph.texture;
            int glyphX = glyph.x;
            char c = str.charAt(glyph.stringIndex);
            if (c >= '0' && c <= '9') {
                int oldWidth = texture.width;
                texture = this.digitGlyphs[fontStyle][c - 48].texture;
                int newWidth = texture.width;
                glyphX += oldWidth - newWidth >> 1;
            }
            if (boundTextureName != texture.textureName) {
                tessellator.draw();
                tessellator.startDrawingQuads();
                tessellator.setColorRGBA(color >> 16 & 255, color >> 8 & 255, color & 255, color >> 24 & 255);
                GL11.glBindTexture((int)3553, (int)texture.textureName);
                boundTextureName = texture.textureName;
            }
            float x1 = startX + (float)glyphX / 2.0f;
            float x2 = startX + (float)(glyphX + texture.width) / 2.0f;
            float y1 = startY + (float)glyph.y / 2.0f;
            float y2 = startY + (float)(glyph.y + texture.height) / 2.0f;
            tessellator.addVertexWithUV((double)x1, (double)y1, 0.0, (double)texture.u1, (double)texture.v1);
            tessellator.addVertexWithUV((double)x1, (double)y2, 0.0, (double)texture.u1, (double)texture.v2);
            tessellator.addVertexWithUV((double)x2, (double)y2, 0.0, (double)texture.u2, (double)texture.v2);
            tessellator.addVertexWithUV((double)x2, (double)y1, 0.0, (double)texture.u2, (double)texture.v1);
        }
        tessellator.draw();
        if (entry.specialRender) {
            int renderStyle = 0;
            color = initialColor;
            GL11.glDisable((int)3553);
            tessellator.startDrawingQuads();
            tessellator.setColorRGBA(color >> 16 & 255, color >> 8 & 255, color & 255, color >> 24 & 255);
            int colorIndex2 = 0;
            for (int glyphIndex = 0; glyphIndex < entry.glyphs.length; ++glyphIndex) {
                float y1;
                float y2;
                float x2;
                while (colorIndex2 < entry.colors.length && entry.glyphs[glyphIndex].stringIndex >= entry.colors[colorIndex2].stringIndex) {
                    color = this.applyColorCode(entry.colors[colorIndex2].colorCode, initialColor, shadowFlag);
                    renderStyle = entry.colors[colorIndex2].renderStyle;
                    ++colorIndex2;
                }
                Glyph glyph = entry.glyphs[glyphIndex];
                int glyphSpace = glyph.advance - glyph.texture.width;
                if (renderStyle != 0 && true) {
                    float x1 = startX + (float)(glyph.x - glyphSpace) / 2.0f;
                    x2 = startX + (float)(glyph.x + glyph.advance) / 2.0f;
                    y1 = startY + 0.5f;
                    y2 = startY + 1.5f;
                    tessellator.addVertex((double)x1, (double)y1, 0.0);
                    tessellator.addVertex((double)x1, (double)y2, 0.0);
                    tessellator.addVertex((double)x2, (double)y2, 0.0);
                    tessellator.addVertex((double)x2, (double)y1, 0.0);
                }
                if ((renderStyle & 2) == 0) continue;
                float x1 = startX + (float)(glyph.x - glyphSpace) / 2.0f;
                x2 = startX + (float)(glyph.x + glyph.advance) / 2.0f;
                y1 = startY + -3.0f;
                y2 = startY + -2.0f;
                tessellator.addVertex((double)x1, (double)y1, 0.0);
                tessellator.addVertex((double)x1, (double)y2, 0.0);
                tessellator.addVertex((double)x2, (double)y2, 0.0);
                tessellator.addVertex((double)x2, (double)y1, 0.0);
            }
            tessellator.draw();
            GL11.glEnable((int)3553);
        }
        if (this.antiAliasEnabled) {
            GL11.glDisable((int)3042);
        }
        return entry.advance / 2;
    }

    public int getStringWidth(String str) {
        if (str == null || str.isEmpty()) {
            return 0;
        }
        Entry entry = this.cacheString(str);
        return entry.advance / 2;
    }

    public int getStringHeight() {
        Entry entry = this.cacheString("|");
        Glyph glyph = entry.glyphs[0];
        return glyph.texture.height / 2 - 1;
    }

    private int sizeString(String str, int width, boolean breakAtSpaces) {
        int index;
        if (str == null || str.isEmpty()) {
            return 0;
        }
        width += width;
        Glyph[] glyphs = this.cacheString((String)str).glyphs;
        int wsIndex = -1;
        int advance = 0;
        for (index = 0; index < glyphs.length && advance <= width; advance += glyphs[index].advance, ++index) {
            if (!breakAtSpaces) continue;
            char c = str.charAt(glyphs[index].stringIndex);
            if (c == ' ') {
                wsIndex = index;
                continue;
            }
            if (c != '\n') continue;
            wsIndex = index;
            break;
        }
        if (index < glyphs.length && wsIndex != -1 && wsIndex < index) {
            index = wsIndex;
        }
        return index < glyphs.length ? glyphs[index].stringIndex : str.length();
    }

    public int sizeStringToWidth(String str, int width) {
        return this.sizeString(str, width, true);
    }

    public String trimStringToWidth(String str, int width, boolean reverse) {
        int length = this.sizeString(str, width, false);
        str = str.substring(0, length);
        if (reverse) {
            str = new StringBuilder(str).reverse().toString();
        }
        return str;
    }

    private int applyColorCode(int colorCode, int color, boolean shadowFlag) {
        if (colorCode != -1) {
            colorCode = shadowFlag ? colorCode + 16 : colorCode;
            color = this.colorTable[colorCode] & 16777215 | color & -16777216;
        }
        Tessellator.instance.setColorRGBA(color >> 16 & 255, color >> 8 & 255, color & 255, color >> 24 & 255);
        return color;
    }

    private Entry cacheString(String str) {
        Entry entry = null;
        if (this.mainThread == Thread.currentThread()) {
            this.lookupKey.str = str;
            entry = this.stringCache.get(this.lookupKey);
        }
        if (entry == null) {
            char[] text = str.toCharArray();
            entry = new Entry();
            int length = this.stripColorCodes(entry, str, text);
            ArrayList<Glyph> glyphList = new ArrayList<Glyph>();
            entry.advance = this.layoutBidiString(glyphList, text, 0, length, entry.colors);
            entry.glyphs = new Glyph[glyphList.size()];
            entry.glyphs = glyphList.toArray(entry.glyphs);
            Arrays.sort(entry.glyphs);
            int colorIndex = 0;
            int shift = 0;
            Glyph[] arr$ = entry.glyphs;
            int len$ = arr$.length;
            for (int i$ = 0; i$ < len$; ++i$) {
                Glyph glyph2;
                Glyph glyph = glyph2 = arr$[i$];
                while (colorIndex < entry.colors.length && glyph.stringIndex + shift >= entry.colors[colorIndex].stringIndex) {
                    shift += 2;
                    ++colorIndex;
                }
                glyph.stringIndex += shift;
            }
            if (this.mainThread == Thread.currentThread()) {
                Key key = new Key();
                key.str = new String(str);
                entry.keyRef = new WeakReference<Key>(key);
                this.stringCache.put(key, entry);
            }
        }
        if (this.mainThread == Thread.currentThread()) {
            Key oldKey = entry.keyRef.get();
            if (oldKey != null) {
                this.weakRefCache.put(str, oldKey);
            }
            this.lookupKey.str = null;
        }
        return entry;
    }

    private int stripColorCodes(Entry cacheEntry, String str, char[] text) {
        int next;
        ArrayList<ColorCode> colorList = new ArrayList<ColorCode>();
        int start = 0;
        int shift = 0;
        int fontStyle = 0;
        int renderStyle = 0;
        int colorCode = -1;
        while ((next = str.indexOf(167, start)) != -1 && next + 1 < str.length()) {
            System.arraycopy(text, next - shift + 2, text, next - shift, text.length - next - 2);
            int code = "0123456789abcdefklmnor".indexOf(Character.toLowerCase(str.charAt(next + 1)));
            switch (code) {
                case 16: {
                    break;
                }
                case 17: {
                    fontStyle = (byte)(fontStyle | 1);
                    break;
                }
                case 18: {
                    renderStyle = (byte)(renderStyle | 2);
                    cacheEntry.specialRender = true;
                    break;
                }
                case 19: {
                    renderStyle = (byte)(renderStyle | 1);
                    cacheEntry.specialRender = true;
                    break;
                }
                case 20: {
                    fontStyle = (byte)(fontStyle | 2);
                    break;
                }
                case 21: {
                    fontStyle = 0;
                    renderStyle = 0;
                    colorCode = -1;
                    break;
                }
                default: {
                    if (code < 0 || code > 15) break;
                    colorCode = (byte)code;
                    fontStyle = 0;
                    renderStyle = 0;
                }
            }
            ColorCode entry = new ColorCode();
            entry.stringIndex = next;
            entry.stripIndex = next - shift;
            entry.colorCode = (byte)colorCode;
            entry.fontStyle = (byte)fontStyle;
            entry.renderStyle = (byte)renderStyle;
            colorList.add(entry);
            start = next + 2;
            shift += 2;
        }
        cacheEntry.colors = new ColorCode[colorList.size()];
        cacheEntry.colors = colorList.toArray(cacheEntry.colors);
        return text.length - shift;
    }

    private int layoutBidiString(List<Glyph> glyphList, char[] text, int start, int limit, ColorCode[] colors) {
        int advance = 0;
        if (Bidi.requiresBidi(text, start, limit)) {
            Bidi bidi = new Bidi(text, start, null, 0, limit - start, -2);
            if (bidi.isRightToLeft()) {
                return this.layoutStyle(glyphList, text, start, limit, 1, advance, colors);
            }
            int runCount = bidi.getRunCount();
            byte[] levels = new byte[runCount];
            Object[] ranges = new Integer[runCount];
            for (int index = 0; index < runCount; ++index) {
                levels[index] = (byte)bidi.getRunLevel(index);
                ranges[index] = new Integer(index);
            }
            Bidi.reorderVisually(levels, 0, ranges, 0, runCount);
            for (int visualIndex = 0; visualIndex < runCount; ++visualIndex) {
                int logicalIndex = (Integer)ranges[visualIndex];
                int layoutFlag = (bidi.getRunLevel(logicalIndex) & 1) == 1 ? 1 : 0;
                advance = this.layoutStyle(glyphList, text, start + bidi.getRunStart(logicalIndex), start + bidi.getRunLimit(logicalIndex), layoutFlag, advance, colors);
            }
            return advance;
        }
        return this.layoutStyle(glyphList, text, start, limit, 0, advance, colors);
    }

    private int layoutStyle(List<Glyph> glyphList, char[] text, int start, int limit, int layoutFlags, int advance, ColorCode[] colors) {
        byte currentFontStyle = 0;
        int colorIndex = Arrays.binarySearch(colors, (Object)start);
        if (colorIndex < 0) {
            colorIndex = - colorIndex - 2;
        }
        while (start < limit) {
            int next = limit;
            while (colorIndex >= 0 && colorIndex < colors.length - 1 && colors[colorIndex].stripIndex == colors[colorIndex + 1].stripIndex) {
                ++colorIndex;
            }
            if (colorIndex >= 0 && colorIndex < colors.length) {
                currentFontStyle = colors[colorIndex].fontStyle;
            }
            while (++colorIndex < colors.length) {
                if (colors[colorIndex].fontStyle == currentFontStyle) continue;
                next = colors[colorIndex].stripIndex;
                break;
            }
            advance = this.layoutString(glyphList, text, start, next, layoutFlags, advance, currentFontStyle);
            start = next;
        }
        return advance;
    }

    private int layoutString(List<Glyph> glyphList, char[] text, int start, int limit, int layoutFlags, int advance, int style) {
        if (this.digitGlyphsReady) {
            for (int index = start; index < limit; ++index) {
                if (text[index] < '0' || text[index] > '9') continue;
                text[index] = 48;
            }
        }
        while (start < limit) {
            Font font = this.glyphCache.lookupFont(text, start, limit, style);
            int next = font.canDisplayUpTo(text, start, limit);
            if (next == -1) {
                next = limit;
            }
            if (next == start) {
                ++next;
            }
            advance = this.layoutFont(glyphList, text, start, next, layoutFlags, advance, font);
            start = next;
        }
        return advance;
    }

    private int layoutFont(List<Glyph> glyphList, char[] text, int start, int limit, int layoutFlags, int advance, Font font) {
        if (this.mainThread == Thread.currentThread()) {
            this.glyphCache.cacheGlyphs(font, text, start, limit, layoutFlags);
        }
        GlyphVector vector = this.glyphCache.layoutGlyphVector(font, text, start, limit, layoutFlags);
        Glyph glyph = null;
        int numGlyphs = vector.getNumGlyphs();
        for (int index = 0; index < numGlyphs; ++index) {
            Point position = vector.getGlyphPixelBounds(index, null, advance, 0.0f).getLocation();
            if (glyph != null) {
                glyph.advance = position.x - glyph.x;
            }
            glyph = new Glyph();
            glyph.stringIndex = start + vector.getGlyphCharIndex(index);
            glyph.texture = this.glyphCache.lookupGlyph(font, vector.getGlyphCode(index));
            glyph.x = position.x;
            glyph.y = position.y;
            glyphList.add(glyph);
        }
        advance += (int)vector.getGlyphPosition(numGlyphs).getX();
        if (glyph != null) {
            glyph.advance = advance - glyph.x;
        }
        return advance;
    }

    private static class Glyph
    implements Comparable<Glyph> {
        public int stringIndex;
        public GlyphCache.Entry texture;
        public int x;
        public int y;
        public int advance;

        private Glyph() {
        }

        @Override
        public int compareTo(Glyph o) {
            return this.stringIndex == o.stringIndex ? 0 : (this.stringIndex < o.stringIndex ? -1 : 1);
        }
    }

    private static class ColorCode
    implements Comparable<Integer> {
        public static final byte UNDERLINE = 1;
        public static final byte STRIKETHROUGH = 2;
        public int stringIndex;
        public int stripIndex;
        public byte colorCode;
        public byte fontStyle;
        public byte renderStyle;

        private ColorCode() {
        }

        @Override
        public int compareTo(Integer i) {
            return this.stringIndex == i ? 0 : (this.stringIndex < i ? -1 : 1);
        }
    }

    private static class Entry {
        public WeakReference<Key> keyRef;
        public int advance;
        public Glyph[] glyphs;
        public ColorCode[] colors;
        public boolean specialRender;

        private Entry() {
        }
    }

    private static class Key {
        public String str;

        private Key() {
        }

        public int hashCode() {
            int code = 0;
            int length = this.str.length();
            boolean colorCode = false;
            for (int index = 0; index < length; ++index) {
                int c = this.str.charAt(index);
                if (c >= 48 && c <= 57 && !colorCode) {
                    c = 48;
                }
                code = code * 31 + c;
                colorCode = c == 167;
            }
            return code;
        }

        public boolean equals(Object o) {
            if (o == null) {
                return false;
            }
            String other = o.toString();
            int length = this.str.length();
            if (length != other.length()) {
                return false;
            }
            boolean colorCode = false;
            for (int index = 0; index < length; ++index) {
                char c2;
                char c1 = this.str.charAt(index);
                if (c1 != (c2 = other.charAt(index)) && (c1 < '0' || c1 > '9' || c2 < '0' || c2 > '9' || colorCode)) {
                    return false;
                }
                colorCode = c1 == '\u00a7';
            }
            return true;
        }

        public String toString() {
            return this.str;
        }
    }

}

