package ehacks.mod.gui;

import ehacks.mod.external.config.ConfigurationManager;
import ehacks.mod.gui.element.SimpleWindow;
import ehacks.mod.gui.window.*;
import ehacks.mod.util.GLUtils;
import ehacks.mod.wrapper.Wrapper;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ChatAllowedCharacters;
import org.lwjgl.input.Keyboard;

public class EHacksClickGui
        extends GuiScreen {

    public static ArrayList<SimpleWindow> windows = new ArrayList();
    public static boolean isDark = false;
    public static ArrayList OrgName = new ArrayList();
    public static ArrayList NameP = new ArrayList();
    private int updateCounter = 0;
    private static final char[] allowedCharacters = ChatAllowedCharacters.allowedCharacters;
    private final boolean var6 = false;

    public EHacksClickGui() {
        this.initWindows();
    }

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
        windows.add(new WindowFakeImportConfig());
        windows.add(new WindowFakeExportConfig());
        windows.add(new WindowHub());
    }

    @Override
    public void initGui() {
        super.initGui();
        Keyboard.enableRepeatEvents((boolean) true);
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        Keyboard.enableRepeatEvents((boolean) false);
        ConfigurationManager.instance().saveConfigs();
    }

    public static void sendPanelToFront(SimpleWindow window) {
        if (windows.contains(window)) {
            int panelIndex = windows.indexOf(window);
            windows.remove(panelIndex);
            windows.add(windows.size(), window);
        }
    }

    public static SimpleWindow getFocusedPanel() {
        return windows.get(windows.size() - 1);
    }

    public static Tooltip tooltip = null;

    @Override
    public void drawScreen(int x, int y, float f) {
        tooltip = null;
        super.drawScreen(x, y, f);
        for (SimpleWindow window : windows) {
            window.draw(x, y);
        }
        if (tooltip != null) {
            this.drawHoveringText(this.fontRendererObj.listFormattedStringToWidth(tooltip.text, 200), tooltip.x, tooltip.y, fontRendererObj);
        }
    }

    public static ArrayDeque<Tuple<String, Integer>> logData = new ArrayDeque<Tuple<String, Integer>>();

    public static void drawLog() {
        int ti = 0;
        for (int i = logData.size() - 1; i >= 0; i--) {
            Tuple<String, Integer> log = (Tuple<String, Integer>) logData.toArray()[i];
            ScaledResolution get = new ScaledResolution(Wrapper.INSTANCE.mc(), Wrapper.INSTANCE.mc().displayWidth, Wrapper.INSTANCE.mc().displayHeight);
            int twidth = Wrapper.INSTANCE.mc().displayWidth / get.getScaleFactor();
            int theight = Wrapper.INSTANCE.mc().displayHeight / get.getScaleFactor();
            List<String> strings = Wrapper.INSTANCE.fontRenderer().listFormattedStringToWidth(log.x, 324);
            if (log.y < 320) {
                for (int j = 0; j < strings.size(); j++) {
                    if (ti + j < 20) {
                        GLUtils.drawRect(twidth - 326, theight - 9 * ti - 9 * j - 28 - 9 + 1, twidth - 2, theight - 9 * ti - 9 * j - 28 + 1, GLUtils.getColor(128, 0, 0, 0));
                        Wrapper.INSTANCE.fontRenderer().drawStringWithShadow(strings.get(strings.size() - j - 1).replaceAll("[\u0000-\u001f]", ""), twidth - 326, theight - 9 * ti - 9 * j - 28 - 9 + 2, GLUtils.getColor(0, 255, 255));
                    }
                }
            } else {
                for (int j = 0; j < strings.size(); j++) {
                    if (ti + j < 20) {
                        GLUtils.drawRect(twidth - 326, theight - 9 * ti - 9 * j - 28 - 9 + 1, twidth - 2, theight - 9 * ti - 9 * j - 28 + 1, GLUtils.getColor(Math.max(0, (int) ((80 - (log.y - 320)) / 80.0 * 128.0)), 0, 0, 0));
                        Wrapper.INSTANCE.fontRenderer().drawStringWithShadow(strings.get(strings.size() - j - 1).replaceAll("[\u0000-\u001f]", ""), twidth - 326, theight - 9 * ti - 9 * j - 28 - 9 + 2, GLUtils.getColor(Math.max(0, (int) ((80 - (log.y - 320)) / 80.0 * 255.0)), 0, 255, 255));
                    }
                }
            }
            ti += strings.size();
            log.y++;
        }
        while (!logData.isEmpty() && logData.peek().y == 390) {
            logData.poll();
        }
    }

    public static void log(String data) {
        EHacksClickGui.logData.add(new Tuple<String, Integer>(data, 0));
    }

    @Override
    public void updateScreen() {
        ++this.updateCounter;
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
        for (SimpleWindow window : windows) {
            window.mouseMovedOrUp(p_146286_1_, p_146286_2_, p_146286_3_);
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
