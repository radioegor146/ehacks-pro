package ehacks.mod.gui;

import ehacks.mod.api.Module;
import ehacks.mod.commands.ConsoleGui;
import ehacks.mod.config.ConfigurationManager;
import ehacks.mod.gui.element.SimpleWindow;
import ehacks.mod.gui.window.WindowBeuHacks;
import ehacks.mod.gui.window.WindowHub;
import ehacks.mod.util.GLUtils;
import ehacks.mod.wrapper.Wrapper;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.text.TextComponentString;

import java.util.ArrayList;

public class EHacksClickGui
        extends GuiScreen {

    public static int mainColor = GLUtils.getColor(255, 255, 255);
    public static Tooltip tooltip = null;
    public ArrayList<SimpleWindow> windows = new ArrayList<>();
    public ConsoleGui consoleGui = new ConsoleGui(Wrapper.INSTANCE.mc());
    public boolean canInputConsole = false;

    public EHacksClickGui() {
    }

    //TODO fix all of these being offscreen on scale = auto
    public void initWindows() {
        windows.add(new WindowBeuHacks());
        windows.add(new WindowHub());
    }

    @Override
    public void initGui() {
        super.initGui();
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
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

    @Override
    public void drawScreen(int x, int y, float f) {
        tooltip = null;
        super.drawScreen(x, y, f);
        windows.forEach((window) -> {
            window.draw(x, y);
        });
        if (tooltip != null) {
            this.drawHoveringText(this.fontRenderer.listFormattedStringToWidth(tooltip.text, 200), tooltip.x, tooltip.y, fontRenderer);
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
        consoleGui.printChatMessage(new TextComponentString(data.replace("&", "\u00a7").replace("\u00a7\u00a7", "&")));
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
    public void mouseReleased(int mouseX, int mouseY, int state) {
        for (SimpleWindow window : windows) {
            window.mouseReleased(mouseX, mouseY, state);
        }
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
