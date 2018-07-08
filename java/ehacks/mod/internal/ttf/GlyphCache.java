/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.GLAllocation
 *  org.lwjgl.opengl.GL11
 */
package ehacks.mod.internal.ttf;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.PrintStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import net.minecraft.client.renderer.GLAllocation;
import org.lwjgl.opengl.GL11;

public class GlyphCache {
    private static final int TEXTURE_WIDTH = 256;
    private static final int TEXTURE_HEIGHT = 256;
    private static final int STRING_WIDTH = 256;
    private static final int STRING_HEIGHT = 64;
    private static final int GLYPH_BORDER = 1;
    private static Color BACK_COLOR = new Color(255, 255, 255, 0);
    private int fontSize = 18;
    private boolean antiAliasEnabled = false;
    private BufferedImage stringImage;
    private Graphics2D stringGraphics;
    private final BufferedImage glyphCacheImage = new BufferedImage(256, 256, 2);
    private final Graphics2D glyphCacheGraphics = this.glyphCacheImage.createGraphics();
    private final FontRenderContext fontRenderContext = this.glyphCacheGraphics.getFontRenderContext();
    private final int[] imageData = new int[65536];
    private final IntBuffer imageBuffer = ByteBuffer.allocateDirect(262144).order(ByteOrder.BIG_ENDIAN).asIntBuffer();
    private final IntBuffer singleIntBuffer = GLAllocation.createDirectIntBuffer((int)1);
    private final List<Font> allFonts = Arrays.asList(GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts());
    private final List<Font> usedFonts = new ArrayList<Font>();
    private int textureName;
    private final LinkedHashMap<Font, Integer> fontCache = new LinkedHashMap();
    private final LinkedHashMap<Long, Entry> glyphCache = new LinkedHashMap();
    private int cachePosX = 1;
    private int cachePosY = 1;
    private int cacheLineHeight = 0;

    public GlyphCache() {
        this.glyphCacheGraphics.setBackground(BACK_COLOR);
        this.glyphCacheGraphics.setComposite(AlphaComposite.Src);
        this.allocateGlyphCacheTexture();
        this.allocateStringImage(256, 64);
        GraphicsEnvironment.getLocalGraphicsEnvironment().preferLocaleFonts();
        this.usedFonts.add(new Font("SansSerif", 0, 1));
    }

    void setDefaultFont(String name, int size, boolean antiAlias) {
        System.out.println("[NahuiClient] Loading Font:  \"" + name + "\"");
        this.usedFonts.clear();
        this.usedFonts.add(new Font(name, 0, 1));
        this.fontSize = size;
        this.antiAliasEnabled = antiAlias;
        this.setRenderingHints();
    }

    GlyphVector layoutGlyphVector(Font font, char[] text, int start, int limit, int layoutFlags) {
        if (!this.fontCache.containsKey(font)) {
            this.fontCache.put(font, this.fontCache.size());
        }
        return font.layoutGlyphVector(this.fontRenderContext, text, start, limit, layoutFlags);
    }

    Font lookupFont(char[] text, int start, int limit, int style) {
        Font font2;
        for (Font font3 : this.usedFonts) {
            if (font3.canDisplayUpTo(text, start, limit) == start) continue;
            return font3.deriveFont(style, this.fontSize);
        }
        for (Font font3 : this.allFonts) {
            if (font3.canDisplayUpTo(text, start, limit) == start) continue;
            System.out.println("[YouAlwaysWin] Loading Font: \"" + font3.getFontName() + "\"");
            this.usedFonts.add(font3);
            return font3.deriveFont(style, this.fontSize);
        }
        font2 = this.usedFonts.get(0);
        return font2.deriveFont(style, this.fontSize);
    }

    Entry lookupGlyph(Font font, int glyphCode) {
        long fontKey = (long)this.fontCache.get(font).intValue() << 32;
        return this.glyphCache.get(fontKey | (long)glyphCode);
    }

    void cacheGlyphs(Font font, char[] text, int start, int limit, int layoutFlags) {
        GlyphVector vector = this.layoutGlyphVector(font, text, start, limit, layoutFlags);
        Rectangle vectorBounds = null;
        long fontKey = (long)this.fontCache.get(font).intValue() << 32;
        int numGlyphs = vector.getNumGlyphs();
        Rectangle dirty = null;
        boolean vectorRendered = false;
        for (int index = 0; index < numGlyphs; ++index) {
            int glyphCode = vector.getGlyphCode(index);
            if (this.glyphCache.containsKey(fontKey | (long)glyphCode)) continue;
            if (!vectorRendered) {
                vectorRendered = true;
                for (int i = 0; i < numGlyphs; ++i) {
                    Point2D pos = vector.getGlyphPosition(i);
                    pos.setLocation(pos.getX() + (double)(2 * i), pos.getY());
                    vector.setGlyphPosition(i, pos);
                }
                vectorBounds = vector.getPixelBounds(this.fontRenderContext, 0.0f, 0.0f);
                if (this.stringImage == null || vectorBounds.width > this.stringImage.getWidth() || vectorBounds.height > this.stringImage.getHeight()) {
                    int width = Math.max(vectorBounds.width, this.stringImage.getWidth());
                    int height = Math.max(vectorBounds.height, this.stringImage.getHeight());
                    this.allocateStringImage(width, height);
                }
                this.stringGraphics.clearRect(0, 0, vectorBounds.width, vectorBounds.height);
                this.stringGraphics.drawGlyphVector(vector, - vectorBounds.x, - vectorBounds.y);
            }
            Rectangle rect = vector.getGlyphPixelBounds(index, null, - vectorBounds.x, - vectorBounds.y);
            if (this.cachePosX + rect.width + 1 > 256) {
                this.cachePosX = 1;
                this.cachePosY += this.cacheLineHeight + 1;
                this.cacheLineHeight = 0;
            }
            if (this.cachePosY + rect.height + 1 > 256) {
                this.updateTexture(dirty);
                dirty = null;
                this.allocateGlyphCacheTexture();
                this.cachePosX = 1;
                this.cachePosY = 1;
                this.cacheLineHeight = 0;
            }
            if (rect.height > this.cacheLineHeight) {
                this.cacheLineHeight = rect.height;
            }
            this.glyphCacheGraphics.drawImage(this.stringImage, this.cachePosX, this.cachePosY, this.cachePosX + rect.width, this.cachePosY + rect.height, rect.x, rect.y, rect.x + rect.width, rect.y + rect.height, null);
            rect.setLocation(this.cachePosX, this.cachePosY);
            Entry entry = new Entry();
            entry.textureName = this.textureName;
            entry.width = rect.width;
            entry.height = rect.height;
            entry.u1 = (float)rect.x / 256.0f;
            entry.v1 = (float)rect.y / 256.0f;
            entry.u2 = (float)(rect.x + rect.width) / 256.0f;
            entry.v2 = (float)(rect.y + rect.height) / 256.0f;
            this.glyphCache.put(fontKey | (long)glyphCode, entry);
            if (dirty == null) {
                dirty = new Rectangle(this.cachePosX, this.cachePosY, rect.width, rect.height);
            } else {
                dirty.add(rect);
            }
            this.cachePosX += rect.width + 1;
        }
        this.updateTexture(dirty);
    }

    private void updateTexture(Rectangle dirty) {
        if (dirty != null) {
            this.updateImageBuffer(dirty.x, dirty.y, dirty.width, dirty.height);
            GL11.glBindTexture((int)3553, (int)this.textureName);
            GL11.glTexSubImage2D((int)3553, (int)0, (int)dirty.x, (int)dirty.y, (int)dirty.width, (int)dirty.height, (int)6408, (int)5121, (IntBuffer)this.imageBuffer);
        }
    }

    private void allocateStringImage(int width, int height) {
        this.stringImage = new BufferedImage(width, height, 2);
        this.stringGraphics = this.stringImage.createGraphics();
        this.setRenderingHints();
        this.stringGraphics.setBackground(BACK_COLOR);
        this.stringGraphics.setPaint(Color.WHITE);
    }

    private void setRenderingHints() {
        this.stringGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, this.antiAliasEnabled ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF);
        this.stringGraphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, this.antiAliasEnabled ? RenderingHints.VALUE_TEXT_ANTIALIAS_ON : RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        this.stringGraphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
    }

    private void allocateGlyphCacheTexture() {
        this.glyphCacheGraphics.clearRect(0, 0, 256, 256);
        this.singleIntBuffer.clear();
        GL11.glGenTextures((IntBuffer)this.singleIntBuffer);
        this.textureName = this.singleIntBuffer.get(0);
        this.updateImageBuffer(0, 0, 256, 256);
        GL11.glBindTexture((int)3553, (int)this.textureName);
        GL11.glTexImage2D((int)3553, (int)0, (int)32828, (int)256, (int)256, (int)0, (int)6408, (int)5121, (IntBuffer)this.imageBuffer);
        GL11.glTexParameteri((int)3553, (int)10241, (int)9728);
        GL11.glTexParameteri((int)3553, (int)10240, (int)9728);
    }

    private void updateImageBuffer(int x, int y, int width, int height) {
        this.glyphCacheImage.getRGB(x, y, width, height, this.imageData, 0, width);
        for (int i = 0; i < width * height; ++i) {
            int color = this.imageData[i];
            this.imageData[i] = color << 8 | color >>> 24;
        }
        this.imageBuffer.clear();
        this.imageBuffer.put(this.imageData);
        this.imageBuffer.flip();
    }

    static class Entry {
        public int textureName;
        public int width;
        public int height;
        public float u1;
        public float v1;
        public float u2;
        public float v2;

        Entry() {
        }
    }

}

