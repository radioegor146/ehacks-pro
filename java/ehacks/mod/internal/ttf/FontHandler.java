/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.renderer.texture.TextureManager
 *  net.minecraft.client.settings.GameSettings
 *  net.minecraft.util.ResourceLocation
 */
package ehacks.mod.internal.ttf;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.ResourceLocation;

public class FontHandler {
    private static volatile FontHandler instance;
    private boolean globalTTF = true;
    public static String fontName;
    public static int fontSize;

    public static FontHandler getInstance() {
        if (instance == null) {
            instance = new FontHandler();
        }
        return instance;
    }

    public String getFontName() {
        return fontName;
    }

    public void setFontName(String fontName) {
        FontHandler.fontName = fontName;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        FontHandler.fontSize = fontSize;
    }

    public boolean isGlobalTTF() {
        return this.globalTTF;
    }

    public void setGlobalTTF(boolean globalTTF) {
        this.globalTTF = globalTTF;
    }

    public void resetGlobalTTF(boolean status) {
        this.globalTTF = status;
        this.initializeFontRenderer();
    }

    public void initializeFontRenderer() {
        Minecraft mc = Minecraft.getMinecraft();
        mc.fontRenderer = new FontRenderer(mc.gameSettings, new ResourceLocation("textures/font/ascii.png"), mc.renderEngine, false);
        mc.standardGalacticFontRenderer = new FontRenderer(mc.gameSettings, new ResourceLocation("textures/font/ascii.png"), mc.renderEngine, false);
    }

    static {
        fontName = "Segoe UI";
        fontSize = 16;
    }
}

