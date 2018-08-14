package ehacks.mod.gui;

import java.util.ArrayList;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ChatAllowedCharacters;
import org.lwjgl.input.Keyboard;
import ehacks.mod.external.config.manual.ModuleStateConfiguration;
import ehacks.mod.external.config.manual.SaveableGuiState;
import ehacks.mod.gui.element.ModWindow;
import ehacks.mod.gui.element.SimpleWindow;
import ehacks.mod.gui.window.WindowActives;
import ehacks.mod.gui.window.WindowCheckVanish;
import ehacks.mod.gui.window.WindowCombat;
import ehacks.mod.gui.window.WindowEHacks;
import ehacks.mod.gui.window.WindowHub;
import ehacks.mod.gui.window.WindowInfo;
import ehacks.mod.gui.window.WindowMinigames;
import ehacks.mod.gui.window.WindowNoCheatPlus;
import ehacks.mod.gui.window.WindowPlayer;
import ehacks.mod.gui.window.WindowPlayerIds;
import ehacks.mod.gui.window.WindowRadar;
import ehacks.mod.gui.window.WindowRender;
import ehacks.mod.util.GLUtils;
import ehacks.mod.wrapper.Wrapper;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import net.minecraft.client.gui.ScaledResolution;

public class EHacksClickGui
extends GuiScreen {
    public static ArrayList<SimpleWindow> windows = new ArrayList();
    public static ArrayList<SimpleWindow> unFocusedWindows = new ArrayList();
    public static boolean isDark = false;
    public static ArrayList OrgName = new ArrayList();
    public static ArrayList NameP = new ArrayList();
    private int updateCounter = 0;
    private static final char[] allowedCharacters = ChatAllowedCharacters.allowedCharacters;
    private boolean var6 = false;
    public SimpleWindow guiHub = new WindowHub();

    public EHacksClickGui() {
        this.initWindows();
    }
    
    public void initWindows() {
        new WindowPlayer().init();
        new WindowCombat().init();
        new WindowRender().init();
        new WindowMinigames().init();
        new WindowNoCheatPlus().init();
        new WindowEHacks().init();
        new WindowInfo();
        new WindowRadar();
        new WindowActives();
        new WindowPlayerIds();
        new WindowCheckVanish();
    }

    public void initGui() {
        super.initGui();
        SaveableGuiState.instance().read();
        Keyboard.enableRepeatEvents((boolean)true);
        this.guiHub.setOpen(true);
    }

    public void onGuiClosed() {
        super.onGuiClosed();
        Keyboard.enableRepeatEvents((boolean)false);
        ModuleStateConfiguration.instance().writeToFile();
        SaveableGuiState.instance().writeToFile();
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
        for (int i = logData.size() - 1; i >= 0; i--)
        {
            Tuple<String, Integer> log = (Tuple<String, Integer>)logData.toArray()[i];
            ScaledResolution get = new ScaledResolution(Wrapper.INSTANCE.mc(), Wrapper.INSTANCE.mc().displayWidth, Wrapper.INSTANCE.mc().displayHeight);
            int twidth = Wrapper.INSTANCE.mc().displayWidth / get.getScaleFactor();
            int theight = Wrapper.INSTANCE.mc().displayHeight / get.getScaleFactor();
            List<String> strings = Wrapper.INSTANCE.fontRenderer().listFormattedStringToWidth(log.x, 324);
            if (log.y < 320) {
                for (int j = 0; j < strings.size(); j++)
                    if (ti + j < 20)
                    {
                        //GLUtils.drawRect(twidth - 326, theight - 9 * ti - 9 * j - 28 - 9, twidth - 324, theight - 9 * ti - 9 * j - 28, GLUtils.getColor(128, 0, 0, 0));
                        Wrapper.INSTANCE.fontRenderer().drawStringWithShadow(strings.get(strings.size() - j - 1).replaceAll("[\u0000-\u001f]", ""), twidth - 326, theight - 9 * ti - 9 * j - 28 - 9, GLUtils.getColor(0, 255, 255));   
                    }
            } else {
                for (int j = 0; j < strings.size(); j++)
                    if (ti + j < 20)
                    {
                        //GLUtils.drawRect(twidth - 326, theight - 9 * ti - 9 * j - 28 - 9, twidth - 324, theight - 9 * ti - 9 * j - 28, GLUtils.getColor(Math.max(0, (int)((80 - (log.y - 320)) / 80.0 * 128.0)), 0, 0, 0));
                        Wrapper.INSTANCE.fontRenderer().drawStringWithShadow(strings.get(strings.size() - j - 1).replaceAll("[\u0000-\u001f]", ""), twidth - 326, theight - 9 * ti - 9 * j - 28 - 9, GLUtils.getColor(Math.max(0, (int)((80 - (log.y - 320)) / 80.0 * 255.0)), 0, 255, 255));
                    }
            }
            ti += strings.size();
            log.y++;
        }
        while (!logData.isEmpty() && logData.peek().y == 390)
            logData.poll();
    }

    public static void log(String data)
    {
        EHacksClickGui.logData.add(new Tuple<String, Integer>(data, 0));
    }
    
    public void updateScreen() {
        ++this.updateCounter;
        super.updateScreen();
    }

    public void mouseClicked(int x, int y, int button) {
        try {
            for (int i = windows.size() - 1; i >= 0; i--)
                if (windows.get(i).mouseClicked(x, y, button))
                    break;
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    protected void mouseMovedOrUp(int p_146286_1_, int p_146286_2_, int p_146286_3_) {
        for (SimpleWindow window : windows) {
            window.mouseMovedOrUp(p_146286_1_, p_146286_2_, p_146286_3_);
        }
    }

    public boolean doesGuiPauseGame() {
        return false;
    }
}

