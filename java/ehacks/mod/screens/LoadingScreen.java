/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.GuiScreen
 *  org.lwjgl.opengl.GL11
 */
package ehacks.mod.screens;

import ehacks.mod.wrapper.Wrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.opengl.GL11;

public class LoadingScreen
extends GuiScreen {
    public static boolean main;
    public static boolean module;
    public static boolean gui;
    public static boolean config;
    public static boolean last;
    public static boolean commands;

    public void drawScreen(int par1, int par2, float par3) {
        GL11.glPushMatrix();
        GL11.glClear((int)16384);
        GL11.glDisable((int)2896);
        GL11.glDisable((int)2912);
        GL11.glClearColor((float)5.0f, (float)5.0f, (float)5.0f, (float)1.0f);
        GL11.glTranslated((double)0.0, (double)5.0, (double)0.0);
        GL11.glScalef((float)3.5f, (float)3.5f, (float)3.5f);
        Wrapper.INSTANCE.mc().fontRenderer.drawString("Wait while we init EHacks for you...", 0, 0, 268435455);
        GL11.glScalef((float)2.0f, (float)2.0f, (float)2.0f);
        if (main) {
            Wrapper.INSTANCE.mc().fontRenderer.drawString("Loading main mod...", 0, 2, 268435455);
        }
        if (module) {
            Wrapper.INSTANCE.mc().fontRenderer.drawString("Loading modules...", 0, 2, 268435455);
        }
        if (gui) {
            Wrapper.INSTANCE.mc().fontRenderer.drawString("Loading Gui...", 0, 2, 268435455);
        }
        if (config) {
            Wrapper.INSTANCE.mc().fontRenderer.drawString("Preparing config...", 0, 2, 268435455);
        }
        if (last) {
            Wrapper.INSTANCE.mc().fontRenderer.drawString("Completed.", 0, 2, 268435455);
        }
        GL11.glPopMatrix();
    }
}

