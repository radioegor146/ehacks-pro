package ehacks.mod.gui.reeszrbteam;

import java.util.ArrayList;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ChatAllowedCharacters;
import org.lwjgl.input.Keyboard;
import ehacks.mod.external.config.manual.ModuleStateConfiguration;
import ehacks.mod.external.config.manual.SaveableGuiState;
import ehacks.mod.gui.reeszrbteam.element.YAWWindow;
import ehacks.mod.gui.reeszrbteam.window.WindowActives;
import ehacks.mod.gui.reeszrbteam.window.WindowCheckVanish;
import ehacks.mod.gui.reeszrbteam.window.WindowCombat;
import ehacks.mod.gui.reeszrbteam.window.WindowEHacks;
import ehacks.mod.gui.reeszrbteam.window.WindowHub;
import ehacks.mod.gui.reeszrbteam.window.WindowInfo;
import ehacks.mod.gui.reeszrbteam.window.WindowMinigames;
import ehacks.mod.gui.reeszrbteam.window.WindowNoCheatPlus;
import ehacks.mod.gui.reeszrbteam.window.WindowPlayer;
import ehacks.mod.gui.reeszrbteam.window.WindowPlayerIds;
import ehacks.mod.gui.reeszrbteam.window.WindowRadar;
import ehacks.mod.gui.reeszrbteam.window.WindowRender;
import ehacks.mod.util.GLUtils;
import ehacks.mod.wrapper.Wrapper;
import java.util.ArrayDeque;
import java.util.concurrent.atomic.AtomicBoolean;
import net.minecraft.client.gui.ScaledResolution;

public class YouAlwaysWinClickGui
extends GuiScreen {
    public static ArrayList<YAWWindow> windows = new ArrayList();
    public static ArrayList<YAWWindow> unFocusedWindows = new ArrayList();
    public static boolean isDark = false;
    public static ArrayList OrgName = new ArrayList();
    public static ArrayList NameP = new ArrayList();
    private int updateCounter = 0;
    private static final char[] allowedCharacters = ChatAllowedCharacters.allowedCharacters;
    private boolean var6 = false;
    public YAWWindow guiHub = new WindowHub();

    public YouAlwaysWinClickGui() {
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

    public static void sendPanelToFront(YAWWindow window) {
        if (windows.contains(window)) {
            int panelIndex = windows.indexOf(window);
            windows.remove(panelIndex);
            windows.add(windows.size(), window);
        }
    }

    public static YAWWindow getFocusedPanel() {
        return windows.get(windows.size() - 1);
    }

    public void drawScreen(int x, int y, float f) {
        super.drawScreen(x, y, f);
        for (YAWWindow window : windows) {
            window.draw(x, y);
        }
        drawLog();
    }
    
    
    public static ArrayDeque<Tuple<String, Integer>> logData = new ArrayDeque<Tuple<String, Integer>>();
    
    public static void drawLog() {
        int ti = logData.size();
        for (Tuple<String, Integer> log : logData)
        {
            ScaledResolution get = new ScaledResolution(Wrapper.INSTANCE.mc(), Wrapper.INSTANCE.mc().displayWidth, Wrapper.INSTANCE.mc().displayHeight);
            int twidth = Wrapper.INSTANCE.mc().displayWidth / get.getScaleFactor();
            int theight = Wrapper.INSTANCE.mc().displayHeight / get.getScaleFactor();
            if (log.y < 160)
                Wrapper.INSTANCE.fontRenderer().drawString(log.x, twidth - Wrapper.INSTANCE.fontRenderer().getStringWidth(log.x) - 20, theight - 10 * ti - 20, GLUtils.getColor(0, 255, 255));   
            else
                Wrapper.INSTANCE.fontRenderer().drawString(log.x, twidth - Wrapper.INSTANCE.fontRenderer().getStringWidth(log.x) - 20, theight - 10 * ti - 20, GLUtils.getColor((int)((160 - (log.y - 160)) / 160.0 * 255.0), 0, 255, 255));
            ti--;
            log.y++;
        }
        while (!logData.isEmpty() && logData.peek().y == 80)
            logData.poll();
    }

    public static void log(String data)
    {
        YouAlwaysWinClickGui.logData.add(new Tuple<String, Integer>(data, 0));
    }
    
    public void updateScreen() {
        ++this.updateCounter;
        super.updateScreen();
    }

    public void mouseClicked(int x, int y, int button) {
        try {
            for (YAWWindow window : windows) {
                window.mouseClicked(x, y, button);
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    protected void mouseMovedOrUp(int p_146286_1_, int p_146286_2_, int p_146286_3_) {
        for (YAWWindow window : windows) {
            window.mouseMovedOrUp(p_146286_1_, p_146286_2_, p_146286_3_);
        }
    }

    public boolean doesGuiPauseGame() {
        return false;
    }
}

