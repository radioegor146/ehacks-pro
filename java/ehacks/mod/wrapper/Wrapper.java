/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityClientPlayerMP
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.multiplayer.WorldClient
 *  net.minecraft.client.settings.GameSettings
 *  net.minecraft.util.ChatComponentText
 *  net.minecraft.util.IChatComponent
 */
package ehacks.mod.wrapper;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

public class Wrapper {
    public static volatile Wrapper INSTANCE = new Wrapper();

    public Minecraft mc() {
        return Minecraft.getMinecraft();
    }

    public EntityClientPlayerMP player() {
        return Minecraft.getMinecraft().thePlayer;
    }

    public WorldClient world() {
        return Minecraft.getMinecraft().theWorld;
    }

    public GameSettings mcSettings() {
        return Minecraft.getMinecraft().gameSettings;
    }

    public FontRenderer fontRenderer() {
        return Minecraft.getMinecraft().fontRenderer;
    }

    public void addChatMessage(String tosend) {
        ChatComponentText chatcomponent = new ChatComponentText(tosend.replace("&", "\u00a7"));
        this.player().addChatMessage((IChatComponent)chatcomponent);
    }

    public void copy(String str) {
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(str), null);
    }

    public boolean classExists(String className) {
        try {
            Class.forName(className);
            return true;
        }
        catch (ClassNotFoundException e) {
            return false;
        }
    }
}

