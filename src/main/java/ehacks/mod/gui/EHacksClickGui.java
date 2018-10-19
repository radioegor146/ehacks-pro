package ehacks.mod.gui;

import ehacks.mod.api.Module;
import ehacks.mod.commands.ConsoleGui;
import ehacks.mod.config.ConfigurationManager;
import ehacks.mod.gui.element.SimpleWindow;
import ehacks.mod.gui.window.*;
import ehacks.mod.util.GLUtils;
import ehacks.mod.wrapper.Wrapper;
import java.util.ArrayList;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ChatComponentText;
import org.lwjgl.input.Keyboard;

public class EHacksClickGui
        extends GuiScreen {

    public ArrayList<SimpleWindow> windows = new ArrayList<>();
    public ConsoleGui consoleGui = new ConsoleGui(Wrapper.INSTANCE.mc());
    public boolean canInputConsole = false;
    public static int mainColor = GLUtils.getColor(255, 255, 255);

    public EHacksClickGui() {
    }

    //TODO fix all of these being offscreen on scale = auto
    public void initWindows() {
        windows.add(new WindowPlayer());
        windows.add(new WindowCombat());
        windows.add(new WindowRender());
        windows.add(new WindowMinigames());
        windows.add(new WindowNoCheatPlus());
        windows.add(new WindowEHacks());
        windows.add(new WindowInfo());
        windows.add(new WindowRadar());
        windows.add(new WindowActives());
        windows.add(new WindowPlayerIds());
        windows.add(new WindowCheckVanish());
        windows.add(new WindowFakeKeybindings());
        windows.add(new WindowFakeImportConfig());
        windows.add(new WindowFakeExportConfig());
        windows.add(new WindowHub());
    }

    @Override
    public void initGui() {
        super.initGui();
        Keyboard.enableRepeatEvents(true);
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        Keyboard.enableRepeatEvents(false);
        ConfigurationManager.instance().saveConfigs();
    }

    public void sendPanelToFront(SimpleWindow window) {
        if (windows.contains(window)) {
            windows.remove(window);
            windows.add(windows.size(), window);
        }
    }

    public SimpleWindow getFocusedPanel() {
        return windows.get(windows.size() - 1);
    }

    public static Tooltip tooltip = null;

    @Override
    public void drawScreen(int x, int y, float f) {
        tooltip = null;
        super.drawScreen(x, y, f);
        windows.forEach((window) -> {
            window.draw(x, y);
        });
        if (tooltip != null) {
            this.drawHoveringText(this.fontRendererObj.listFormattedStringToWidth(tooltip.text, 200), tooltip.x, tooltip.y, fontRendererObj);
        }
    }

    public void log(String data, Object from) {
        if (from != null) {
            if (from instanceof Module) {
                data = "&7[&f" + ((Module) from).getName() + "&7] &e" + data;
            } else if (from instanceof String) {
                data = "&7[&f" + from + "&7] &e" + data;
            }
        }
        consoleGui.printChatMessage(new ChatComponentText(data.replace("&", "\u00a7").replace("\u00a7\u00a7", "&")));
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
    }

    @Override
    public void mouseClicked(int x, int y, int button) {
        if (button != 0) {
            return;
        }
        try {
            for (int i = windows.size() - 1; i >= 0; i--) {
                if (windows.get(i).mouseClicked(x, y, button)) {
                    break;
                }
            }
        } catch (Exception exception) {
            // empty catch block
        }
    }

    @Override
    protected void mouseMovedOrUp(int p_146286_1_, int p_146286_2_, int p_146286_3_) {
        windows.forEach((window) -> {
            window.mouseMovedOrUp(p_146286_1_, p_146286_2_, p_146286_3_);
        });
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    public void drawBack() {
        if ((!(Wrapper.INSTANCE.mc().currentScreen instanceof EHacksClickGui))) {
            windows.stream().filter((windows) -> !(!windows.isPinned())).forEachOrdered((windows) -> {
                windows.draw(0, 0);
            });
        }
        consoleGui.drawChat(Wrapper.INSTANCE.mc().ingameGUI.getUpdateCounter());
    }
}
