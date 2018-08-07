package ehacks.mod.main;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.relauncher.ReflectionHelper;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.opengl.GL11;
import ehacks.mod.external.config.forge.GeneralConfiguration;
import ehacks.mod.external.config.manual.ConfigurationManager;
import ehacks.mod.gui.xraysettings.XRayBlock;
import ehacks.mod.modulesystem.classes.XRay;
import ehacks.mod.modulesystem.handler.ModuleManagement;
import ehacks.mod.util.FontRendererUtils;
import ehacks.mod.util.Mappings;
import ehacks.mod.util.TimerUtils;
import ehacks.mod.wrapper.Events;
import ehacks.mod.wrapper.Wrapper;
import java.lang.reflect.Field;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraftforge.client.ClientCommandHandler;

@Mod(modid="EHacks", name="EHacks", version="2.0.7")
public class Main {
    @Mod.Instance(value="EHacks")
    public static Main INSTANCE;
    public static final String version = "2.0.7";
    
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        try
        {
            INSTANCE = this;
            ModuleManagement.instance();
            if (Loader.instance().activeModContainer() == null)
            {
                Field controller = Class.forName("cpw.mods.fml.common.Loader").getDeclaredField("modController");
                controller.setAccessible(true);
                Object loadController = controller.get(Loader.instance());
                Field container = Class.forName("cpw.mods.fml.common.LoadController").getDeclaredField("activeContainer");
                container.setAccessible(true);
                container.set(loadController, Loader.instance().getMinecraftModContainer());
            }
            FMLCommonHandler.instance().bus().register((Object)new Events());
            MinecraftForge.EVENT_BUS.register((Object)new Events());
            Wrapper.INSTANCE.mc().fontRenderer = new FontRendererUtils(Wrapper.INSTANCE.mc().gameSettings, new ResourceLocation("textures/font/ascii.png"), Wrapper.INSTANCE.mc().renderEngine, false);
            if (Wrapper.INSTANCE.mcSettings().language != null) {
                Wrapper.INSTANCE.fontRenderer().setUnicodeFlag(Wrapper.INSTANCE.mc().func_152349_b());
                Wrapper.INSTANCE.fontRenderer().setBidiFlag(Wrapper.INSTANCE.mcSettings().forceUnicodeFont);
            }
            try {
                for (Method m : SimpleReloadableResourceManager.class.getDeclaredMethods()) {
                    if (!m.getName().equals(Mappings.registerReloadListener)) continue;
                    m.invoke((Object)Wrapper.INSTANCE.mc().getResourceManager(), new Object[]{Wrapper.INSTANCE.mc().fontRenderer});
                }
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
            ReflectionHelper.setPrivateValue(Minecraft.class, Wrapper.INSTANCE.mc(), (Object)((Object)new TimerUtils(20.0f)), (String[])new String[]{Mappings.timer});
            GeneralConfiguration.instance();
            ConfigurationManager.instance();
            FMLCommonHandler.instance().bus().register((Object)this);
            MinecraftForge.EVENT_BUS.register((Object)this);
            XRayBlock.init();
            XRay.displayListid = GL11.glGenLists((int)5) + 3;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public Main(int sig) 
    {
        if (sig == 1337)
            init(null);
    }
    
    public Main()
    {
        
    }
}

