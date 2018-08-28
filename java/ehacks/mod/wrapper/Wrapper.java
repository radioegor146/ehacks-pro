package ehacks.mod.wrapper;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
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
        return Wrapper.INSTANCE.mc().thePlayer;
    }

    public WorldClient world() {
        return Wrapper.INSTANCE.mc().theWorld;
    }

    public GameSettings mcSettings() {
        return Wrapper.INSTANCE.mc().gameSettings;
    }

    public FontRenderer fontRenderer() {
        return Wrapper.INSTANCE.mc().fontRenderer;
    }

    public void addChatMessage(String tosend) {
        ChatComponentText chatcomponent = new ChatComponentText(tosend.replace("&", "\u00a7"));
        this.player().addChatMessage((IChatComponent) chatcomponent);
    }

    public void copy(String str) {
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(str), null);
    }

    public boolean classExists(String className) {
        try {
            Class.forName(className);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}
