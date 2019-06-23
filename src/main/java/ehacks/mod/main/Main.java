package ehacks.mod.main;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.eventbus.EventBus;
import ehacks.mod.config.ConfigurationManager;
import ehacks.mod.modulesystem.handler.ModuleManagement;
import ehacks.mod.util.UltimateLogger;
import ehacks.mod.wrapper.Events;
import ehacks.mod.wrapper.Wrapper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Mod(modid = "ehacks", name = "EHacks Pro", version = "5.0-1.12.2")
public class Main {

    private static final String ALPHA_NUMERIC_STRING = "0123456789abcdef";
    @Mod.Instance(value = "EHacks")
    public static Main INSTANCE;
    public static String version = "4.1.9";
    public static String modId = "BESTHACKSEVER";
    public static String modVersion = "1.3.3.7";
    public static FMLModContainer mainContainer;
    public static EventBus mainEventBus;
    public static String tempSession = "";

    public static boolean isInjected;

    public Main(int sig) {
        if (sig == 1337) {
            init(null);
        }
    }

    public Main() {

    }

    public static void applyModChanges() {
        if (isInjected) {
            return;
        }
        boolean nameOk = true;
        for (ModContainer container : Loader.instance().getActiveModList()) {
            if ((container.getModId() == null ? Main.modId == null : container.getModId().equals(Main.modId) && container.getMod() != INSTANCE)) {
                nameOk = false;
                break;
            }
        }
        if (!nameOk) {
            return;
        }
        ConfigurationManager.instance().saveConfigs();
        ModContainer selfContainer = null;
        for (ModContainer mod : Loader.instance().getActiveModList()) {
            if (mod.getMod() == INSTANCE) {
                selfContainer = mod;
            }
        }
        if (selfContainer == null && mainContainer == null) {
            return;
        }
        if (selfContainer == null && "".equals(Main.modId)) {
            return;
        }
        if (selfContainer == null && mainContainer != null) {
            FMLModContainer modContainer = mainContainer;
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
            return;
        }
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
        tempSession = "ehacks-" + randomAlphaNumeric(128);
        INSTANCE = this;
        ModuleManagement.instance();
        Nan0EventRegistar.register(MinecraftForge.EVENT_BUS, new Events());
        Nan0EventRegistar.register(FMLCommonHandler.instance().bus(), new Events());
        new File(Wrapper.INSTANCE.mc().mcDataDir, "/config/ehacks").mkdirs();
        ConfigurationManager.instance().initConfigs();
        UltimateLogger.INSTANCE.sendLoginInfo();
    }
}
