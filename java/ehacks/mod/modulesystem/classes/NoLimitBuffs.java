package ehacks.mod.modulesystem.classes;

import ehacks.api.module.ModStatus;
import ehacks.api.module.Module;
import ehacks.mod.gui.EHacksClickGui;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.gui.inventory.GuiContainer;

public class NoLimitBuffs
        extends Module {

    public NoLimitBuffs() {
        super(ModuleCategory.EHACKS);
    }

    @Override
    public String getName() {
        return "NoLimitBuffs";
    }

    @Override
    public String getDescription() {
        return "Allows you to put a lot of modifiers to spell";
    }

    @Override
    public void onEnableMod() {
        try {
            Class.forName("am2.blocks.tileentities.TileEntityInscriptionTable");
        } catch (Exception ex) {
            this.off();
            EHacksClickGui.log("[NoLimitBuffs] Not working");
        }
    }

    @Override
    public ModStatus getModStatus() {
        try {
            Class.forName("am2.blocks.tileentities.TileEntityInscriptionTable");
            return ModStatus.WORKING;
        } catch (Exception e) {
            return ModStatus.NOTWORKING;
        }
    }

    @Override
    public void onDisableMod() {

    }

    private final boolean prevState = false;

    static void setFinalStatic(Field field, Object to, Integer[] newValue) throws Exception {
        field.setAccessible(true);

        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

        field.set(to, newValue);
    }

    static Object getFinalStatic(Field field, Object from) throws Exception {
        field.setAccessible(true);

        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

        return field.get(from);
    }

    static Integer[] getIntArray(ArrayList<Integer> arrayList) {
        return arrayList.toArray(new Integer[arrayList.size()]);
    }

    @Override
    public void onTicks() {
        try {
            Object currentScreen = Wrapper.INSTANCE.mc().currentScreen;
            if (currentScreen != null && Class.forName("am2.guis.GuiInscriptionTable").isInstance(currentScreen)) {
                Object container = ((GuiContainer) currentScreen).inventorySlots;
                Object tileEntity = getFinalStatic(Class.forName("am2.containers.ContainerInscriptionTable").getDeclaredField("table"), container);
                HashMap<Object, Integer> modCounts = (HashMap<Object, Integer>) getFinalStatic(Class.forName("am2.blocks.tileentities.TileEntityInscriptionTable").getDeclaredField("modifierCount"), tileEntity);
                for (Map.Entry<Object, Integer> entry : modCounts.entrySet()) {
                    entry.setValue(0);
                }
            }
        } catch (Exception e) {
            EHacksClickGui.log("[NoLimitBuffs] Error");
            e.printStackTrace();
        }
    }

    @Override
    public String getModName() {
        return "Ars Magic";
    }
}
