package ehacks.mod.main;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.eventbus.EventBus;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.relauncher.ReflectionHelper;
import ehacks.mod.external.config.ConfigurationManager;
import ehacks.mod.gui.xraysettings.XRayBlock;
import ehacks.mod.modulesystem.classes.XRay;
import ehacks.mod.modulesystem.handler.ModuleManagement;
import ehacks.mod.util.Mappings;
import ehacks.mod.util.TimerUtils;
import ehacks.mod.util.UltimateLogger;
import ehacks.mod.wrapper.Events;
import ehacks.mod.wrapper.Wrapper;
import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.opengl.GL11;

@Mod(modid = "EHacks", name = "EHacks", version = "3.2.4")
public class Main {

    @Mod.Instance(value = "EHacks")
    public static Main INSTANCE;

    public static boolean isInjected;

    public static final String REAL_VERSION = "3.2.4";
    public static String tempSession = "";

    public static String modId = "BESTHACKSEVER";
    public static String modVersion = "1.3.3.7";

    public static FMLModContainer mainContainer;
    public static EventBus mainEventBus;

    public static void applyModChanges() {
        if (isInjected) {
            System.err.println("Mod is injected");
            return;
        }
        boolean nameOk = true;
        for (ModContainer container : Loader.instance().getActiveModList()) {
            if ((container.getModId() == null ? Main.modId == null : container.getModId().equals(Main.modId) && container.getMod() != INSTANCE)) {
                nameOk = false;
                break;
            }
        }
        ConfigurationManager.instance().saveConfigs();
        ModContainer selfContainer = null;
        for (ModContainer mod : Loader.instance().getActiveModList()) {
            if (mod.getMod() == INSTANCE) {
                selfContainer = mod;
            }
        }
        if (selfContainer == null && mainContainer == null) {
            System.err.println("ModContainer not found!!! Check forge!!!");
            return;
        }
        if (selfContainer == null && "".equals(Main.modId)) {
            System.err.println("ModContainer already deleted");
            return;
        }
        if (selfContainer == null && mainContainer != null) {
            FMLModContainer modContainer = mainContainer;
            System.out.println("ModContainer not found, but mainContainer != null: " + mainContainer.toString());
            LoadController loadController = ((LoadController) ReflectionHelper.getPrivateValue(Loader.class, Loader.instance(), "modController"));
            ImmutableMap<String, EventBus> eventBusMap = ((ImmutableMap<String, EventBus>) ReflectionHelper.getPrivateValue(LoadController.class, loadController, "eventChannels"));
            HashMap<String, EventBus> eventBusHashMap = Maps.newHashMap(eventBusMap);
            ReflectionHelper.setPrivateValue(FMLModContainer.class, modContainer, Main.modVersion, "internalVersion");
            ((Map<String, Object>) ReflectionHelper.getPrivateValue(FMLModContainer.class, modContainer, "descriptor")).put("modid", Main.modId);
            ((Map<String, Object>) ReflectionHelper.getPrivateValue(FMLModContainer.class, modContainer, "descriptor")).put("name", Main.modId);
            ((Map<String, Object>) ReflectionHelper.getPrivateValue(FMLModContainer.class, modContainer, "descriptor")).put("version", Main.modVersion);
            ((ModMetadata) ReflectionHelper.getPrivateValue(FMLModContainer.class, modContainer, "modMetadata")).modId = Main.modId;
            ((ModMetadata) ReflectionHelper.getPrivateValue(FMLModContainer.class, modContainer, "modMetadata")).name = Main.modId;
            ((ModMetadata) ReflectionHelper.getPrivateValue(FMLModContainer.class, modContainer, "modMetadata")).version = Main.modVersion;
            eventBusHashMap.put(modContainer.getModId(), mainEventBus);
            ReflectionHelper.setPrivateValue(LoadController.class, loadController, ImmutableMap.copyOf(eventBusHashMap), "eventChannels");
            Loader.instance().getActiveModList().add(modContainer);
            return;
        }
        if (!(selfContainer instanceof FMLModContainer)) {
            System.err.println("ModContainer is not instanceof FMLModContainer!!! Check forge!!!");
            return;
        }
        System.out.println("ModContainer found: " + selfContainer.toString());
        FMLModContainer modContainer = (FMLModContainer) selfContainer;
        mainContainer = modContainer;
        LoadController loadController = ((LoadController) ReflectionHelper.getPrivateValue(Loader.class, Loader.instance(), "modController"));
        ImmutableMap<String, EventBus> eventBusMap = ((ImmutableMap<String, EventBus>) ReflectionHelper.getPrivateValue(LoadController.class, loadController, "eventChannels"));
        HashMap<String, EventBus> eventBusHashMap = Maps.newHashMap(eventBusMap);
        EventBus modEventBus = eventBusHashMap.get(modContainer.getModId());
        mainEventBus = modEventBus;
        eventBusHashMap.remove(modContainer.getModId());
        if ("".equals(Main.modId)) {
            ReflectionHelper.setPrivateValue(LoadController.class, loadController, ImmutableMap.copyOf(eventBusHashMap), "eventChannels");
            Loader.instance().getActiveModList().remove(modContainer);
            return;
        }
        ReflectionHelper.setPrivateValue(FMLModContainer.class, modContainer, Main.modVersion, "internalVersion");
        ((Map<String, Object>) ReflectionHelper.getPrivateValue(FMLModContainer.class, modContainer, "descriptor")).put("modid", Main.modId);
        ((Map<String, Object>) ReflectionHelper.getPrivateValue(FMLModContainer.class, modContainer, "descriptor")).put("name", Main.modId);
        ((Map<String, Object>) ReflectionHelper.getPrivateValue(FMLModContainer.class, modContainer, "descriptor")).put("version", Main.modVersion);
        ((ModMetadata) ReflectionHelper.getPrivateValue(FMLModContainer.class, modContainer, "modMetadata")).modId = Main.modId;
        ((ModMetadata) ReflectionHelper.getPrivateValue(FMLModContainer.class, modContainer, "modMetadata")).name = Main.modId;
        ((ModMetadata) ReflectionHelper.getPrivateValue(FMLModContainer.class, modContainer, "modMetadata")).version = Main.modVersion;
        eventBusHashMap.put(modContainer.getModId(), modEventBus);
        ReflectionHelper.setPrivateValue(LoadController.class, loadController, ImmutableMap.copyOf(eventBusHashMap), "eventChannels");
    }

    private static final String ALPHA_NUMERIC_STRING = "0123456789abcdef";

    public static String randomAlphaNumeric(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        if (event == null) {
            isInjected = true;
        }
        try {
            tempSession = randomAlphaNumeric(128);
            if (Loader.instance().activeModContainer() == null) {
                Field controller = Class.forName("cpw.mods.fml.common.Loader").getDeclaredField("modController");
                controller.setAccessible(true);
                Object loadController = controller.get(Loader.instance());
                Field container = Class.forName("cpw.mods.fml.common.LoadController").getDeclaredField("activeContainer");
                container.setAccessible(true);
                container.set(loadController, Loader.instance().getMinecraftModContainer());
            }
            INSTANCE = this;
            ModuleManagement.instance();
            FMLCommonHandler.instance().bus().register((Object) new Events());
            MinecraftForge.EVENT_BUS.register((Object) new Events());
            /*Wrapper.INSTANCE.mc().fontRenderer = new FontRendererUtils(Wrapper.INSTANCE.mcSettings(), new ResourceLocation("textures/font/ascii.png"), Wrapper.INSTANCE.mc().renderEngine, false);
            if (Wrapper.INSTANCE.mcSettings().language != null) {
                Wrapper.INSTANCE.fontRenderer().setUnicodeFlag(Wrapper.INSTANCE.mc().func_152349_b());
                Wrapper.INSTANCE.fontRenderer().setBidiFlag(Wrapper.INSTANCE.mcSettings().forceUnicodeFont);
            }
            try {
                for (Method m : SimpleReloadableResourceManager.class.getDeclaredMethods()) {
                    if (!m.getName().equals(Mappings.registerReloadListener)) {
                        continue;
                    }
                    m.invoke((Object) Wrapper.INSTANCE.mc().getResourceManager(), new Object[]{Wrapper.INSTANCE.fontRenderer()});
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }*/
            ReflectionHelper.setPrivateValue(Minecraft.class, Wrapper.INSTANCE.mc(), (Object) ((Object) new TimerUtils(20.0f)), (String[]) new String[]{Mappings.timer});
            new File(Wrapper.INSTANCE.mc().mcDataDir, "/config/ehacks").mkdirs();
            FMLCommonHandler.instance().bus().register((Object)this);
            MinecraftForge.EVENT_BUS.register((Object)this);
            XRayBlock.init();
            XRay.displayListid = GL11.glGenLists((int) 5) + 3;
            ConfigurationManager.instance().initConfigs();
            UltimateLogger.INSTANCE.sendLoginInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Main(int sig) {
        if (sig == 1337) {
            init(null);
        }
    }

    public Main() {

    }
}
