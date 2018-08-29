package ehacks.mod.modulesystem.classes;

import ehacks.api.module.ModStatus;
import ehacks.api.module.Module;
import ehacks.mod.gui.EHacksClickGui;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;

public class GalaxyTeleport
        extends Module {

    public GalaxyTeleport() {
        super(ModuleCategory.EHACKS);
    }

    @Override
    public String getName() {
        return "GalaxyTeleport";
    }

    @Override
    public String getDescription() {
        return "On click you can teleport to any planet in Galacticraft";
    }

    @Override
    public ModStatus getModStatus() {
        try {
            Class.forName("micdoodle8.mods.galacticraft.core.network.PacketSimple");
            return ModStatus.WORKING;
        } catch (Exception e) {
            return ModStatus.NOTWORKING;
        }
    }

    @Override
    public void onEnableMod() {
        try {
            Class.forName("micdoodle8.mods.galacticraft.core.network.PacketSimple");
            List<Object> objects = new ArrayList<Object>();
            objects.addAll(((Map<String, Object>) Class.forName("micdoodle8.mods.galacticraft.api.galaxies.GalaxyRegistry").getMethod("getRegisteredPlanets").invoke(null)).values());
            objects.addAll(((Map<String, Object>) Class.forName("micdoodle8.mods.galacticraft.api.galaxies.GalaxyRegistry").getMethod("getRegisteredMoons").invoke(null)).values());
            objects.addAll(((Map<String, Object>) Class.forName("micdoodle8.mods.galacticraft.api.galaxies.GalaxyRegistry").getMethod("getRegisteredSatellites").invoke(null)).values());
            Object screen = Class.forName("micdoodle8.mods.galacticraft.core.client.gui.screen.GuiCelestialSelection").getConstructor(Boolean.TYPE, List.class).newInstance(false, objects);
            Wrapper.INSTANCE.mc().displayGuiScreen((GuiScreen) screen);
            this.off();
        } catch (Exception ex) {
            this.off();
            ex.printStackTrace();
        }
    }

    @Override
    public void onDisableMod() {

    }

    @Override
    public void onTicks() {
        if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            Wrapper.INSTANCE.mc().displayGuiScreen(null);
        }
    }

    @Override
    public String getModName() {
        return "Galacticraft";
    }
}
