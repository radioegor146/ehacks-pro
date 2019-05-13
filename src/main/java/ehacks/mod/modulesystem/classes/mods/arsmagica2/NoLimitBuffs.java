package ehacks.mod.modulesystem.classes.mods.arsmagica2;

import ehacks.mod.api.ModStatus;
import ehacks.mod.api.Module;
import ehacks.mod.util.InteropUtils;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
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
    public void onModuleEnabled() {
        try {
            Class.forName("am2.blocks.tileentities.TileEntityInscriptionTable");
        } catch (Exception ex) {
            this.off();
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
                modCounts.entrySet().forEach((entry) -> {
                    entry.setValue(0);
                });
            }
        } catch (Exception e) {
            InteropUtils.log("&cError", this);
        }
    }

    @Override
    public String getModName() {
        return "Ars Magic";
    }
}
