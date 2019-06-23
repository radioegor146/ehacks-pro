package ehacks.mod.wrapper;

import ehacks.mod.api.Module;
import ehacks.mod.api.ModuleController;
import ehacks.mod.commands.ConsoleInputGui;
import ehacks.mod.config.ConfigurationManager;
import ehacks.mod.main.Main;
import ehacks.mod.modulesystem.classes.keybinds.HideCheatKeybind;
import ehacks.mod.modulesystem.classes.keybinds.OpenConsoleKeybind;
import ehacks.mod.modulesystem.handler.EHacksGui;
import ehacks.mod.util.GLUtils;
import ehacks.mod.util.InteropUtils;
import ehacks.mod.util.Mappings;
import ehacks.mod.util.UltimateLogger;
import ehacks.mod.util.chatkeybinds.ChatKeyBindingHandler;
import ehacks.mod.util.ehackscfg.GuiMainConfig;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.HashSet;

public class Events {

    public static boolean cheatEnabled = true;
    private final boolean[] keyStates;
    private final HashSet<Integer> pressedKeys = new HashSet<>();
    public boolean prevState = false;
    public boolean prevCState = false;
    FontRenderer fontRender;
    private boolean ready = false;

    public Events() {
        this.fontRender = Wrapper.INSTANCE.fontRenderer();
        this.keyStates = new boolean[256];
    }

    public static Color rainbowEffect_Text(long offset, float fade) {
        float hue = (System.nanoTime() + offset) / 1.0E10f % 1.0f;
        long color = Long.parseLong(Integer.toHexString(Color.HSBtoRGB(hue, 1.0f, 1.0f)), 16);
        Color c = new Color((int) color);
        return new Color(c.getRed() / 255.0f * fade, c.getGreen() / 255.0f * fade, c.getBlue() / 255.0f * fade, c.getAlpha() / 255.0f);
    }

    public boolean onPacket(Object packet, PacketHandler.Side side) {
        if (!cheatEnabled) {
            return true;
        }
        boolean ok = true;
        ok = ModuleController.INSTANCE.modules.stream().filter((mod) -> !(!mod.isActive() || Wrapper.INSTANCE.world() == null)).map((mod) -> mod.onPacket(packet, side)).reduce(ok, (accumulator, _item) -> accumulator & _item);
        return ok;
    }

    @SubscribeEvent
    public void onPlayerJoinedServer(FMLNetworkEvent.ClientConnectedToServerEvent event) {
        UltimateLogger.INSTANCE.sendServerConnectInfo();
    }

    @SubscribeEvent
    public void onTicks(TickEvent.ClientTickEvent event) {
        EHacksGui.clickGui.canInputConsole = Wrapper.INSTANCE.mc().currentScreen instanceof ConsoleInputGui;
        boolean nowState = InteropUtils.isKeyDown(HideCheatKeybind.getKey());
        if (!prevState && nowState) {
            cheatEnabled = !cheatEnabled;
        }
        prevState = nowState;
        Wrapper.INSTANCE.mcSettings().viewBobbing = true;
        if (!cheatEnabled) {
            return;
        }
        boolean nowCState = InteropUtils.isKeyDown(OpenConsoleKeybind.getKey());
        if (!prevCState && nowCState && (Wrapper.INSTANCE.mc().currentScreen == null)) {
            Wrapper.INSTANCE.mc().displayGuiScreen(new ConsoleInputGui("/"));
        }
        prevCState = nowCState;
        Wrapper.INSTANCE.mcSettings().viewBobbing = false;
        if (Wrapper.INSTANCE.player() != null) {
            if (!ready) {
                new PacketHandler(this);
                ready = true;
            }
        } else {
            ready = false;
        }
        try {
            Wrapper.INSTANCE.world().loadedEntityList.stream().filter((entity) -> (entity instanceof EntityPlayer)).map((entity) -> (EntityPlayer) entity).forEachOrdered((ep1) -> {
                EntityPlayer ep = (EntityPlayer) ep1;
            });
        } catch (Exception e) {

        }
        for (Module mod : ModuleController.INSTANCE.modules) {
            if (mod.isActive() && Wrapper.INSTANCE.world() != null) {
                mod.onTicks();
            }
            if (Wrapper.INSTANCE.world() == null || !this.checkAndSaveKeyState(mod.getKeybind())) {
                continue;
            }
            if (InteropUtils.isKeyDown(mod.getKeybind())) {
                mod.toggle();
            }
        }
        for (int key : pressedKeys) {
            this.keyStates[key] = !this.keyStates[key];
        }
        pressedKeys.clear();
        if (Wrapper.INSTANCE.mc().currentScreen == null) {
            ChatKeyBindingHandler.INSTANCE.handle();
        }
    }

    @SubscribeEvent
    public void onRenderWorld(RenderWorldLastEvent event) {
        if (!cheatEnabled) {
            return;
        }
        ModuleController.INSTANCE.modules.stream().filter((mod) -> !(!mod.isActive() || Wrapper.INSTANCE.world() == null)).forEachOrdered((mod) -> {
            mod.onWorldRender(event);
        });
    }

    @SubscribeEvent
    public void onMouse(MouseEvent event) {
        if (!cheatEnabled) {
            return;
        }
        ModuleController.INSTANCE.modules.stream().filter((mod) -> !(!mod.isActive() || Wrapper.INSTANCE.world() == null)).forEachOrdered((mod) -> {
            mod.onMouse(event);
        });
    }

    @SubscribeEvent
    public void onLiving(LivingEvent.LivingUpdateEvent event) {
        if (!cheatEnabled) {
            return;
        }
        ModuleController.INSTANCE.modules.stream().filter((mod) -> !(!mod.isActive() || Wrapper.INSTANCE.world() == null)).forEachOrdered((mod) -> {
            mod.onLiving(event);
        });
    }

    @SubscribeEvent
    public void onGameOverlay(RenderGameOverlayEvent.Text event) {
        if (!cheatEnabled) {
            return;
        }
        GLUtils.hasClearedDepth = false;
        ModuleController.INSTANCE.modules.stream().filter((mod) -> !(!mod.isActive() || Wrapper.INSTANCE.world() == null)).forEachOrdered((mod) -> {
            mod.onGameOverlay(event);
        });
        if (Wrapper.INSTANCE.mc().currentScreen == null) {
            int x2 = 8;
            int y2 = 7;
            GL11.glPushMatrix();
            GL11.glScalef(1f, 1f, 1f);
            String Copyright1 = "EHacks Pro v" + Main.version;
            String Copyright2 = "by radioegor146";
            ScaledResolution get = new ScaledResolution(Wrapper.INSTANCE.mc());
            this.fontRender.drawString(Copyright1, 2, 2, Events.rainbowEffect_Text(9999999L, 1.0f).getRGB());
            this.fontRender.drawStringWithShadow(Copyright2, get.getScaledWidth() - 2 - this.fontRender.getStringWidth(Copyright2), get.getScaledHeight() - this.fontRender.FONT_HEIGHT - 2, GLUtils.getColor(255, 255, 255));
            GL11.glPopMatrix();
        }
        EHacksGui.clickGui.drawBack();
    }

    @SubscribeEvent
    public void onAttack(AttackEntityEvent event) {
        if (!cheatEnabled) {
            return;
        }
        if (event.getTarget() instanceof EntityPlayer) {
            EntityPlayer e = (EntityPlayer) event.getTarget();
        }
    }

    public boolean checkAndSaveKeyState(int key) {
        if (Wrapper.INSTANCE.mc().currentScreen != null) {
            return false;
        }
        if (InteropUtils.isKeyDown(key) != this.keyStates[key]) {
            pressedKeys.add(key);
            return true;
        }
        return false;
    }

    @SubscribeEvent
    public void onGuiScreenDraw(GuiScreenEvent.DrawScreenEvent.Pre event) {
        if (event.getGui() instanceof GuiMainMenu) {
            GuiMainMenu mainMenu = (GuiMainMenu) event.getGui();
            ReflectionHelper.setPrivateValue(GuiMainMenu.class, mainMenu, "Fucked by radioegor146", Mappings.splashText);
        }
    }

    @SubscribeEvent
    public void onGuiScreenInit(GuiScreenEvent.InitGuiEvent event) {
        if (Wrapper.INSTANCE.player() == null) {
            Wrapper.INSTANCE.mc().getSession();
            ConfigurationManager.instance().initConfigs();
            event.getButtonList().add(new GuiButton(1337, 0, 0, 100, 20, "EHacks"));
        }
    }

    @SubscribeEvent
    public void onGuiScreenAction(GuiScreenEvent.ActionPerformedEvent event) {
        if (event.getButton().id == 1337) {
            Wrapper.INSTANCE.mc().displayGuiScreen(new GuiMainConfig(event.getGui()));
        }
    }
}
