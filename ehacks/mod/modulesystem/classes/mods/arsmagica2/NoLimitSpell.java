package ehacks.mod.modulesystem.classes.mods.arsmagica2;

import ehacks.mod.api.ModStatus;
import ehacks.mod.api.Module;
import ehacks.mod.util.InteropUtils;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

public class NoLimitSpell
        extends Module {

    public NoLimitSpell() {
        super(ModuleCategory.EHACKS);
    }

    @Override
    public String getName() {
        return "NoLimitSpell";
    }

    @Override
    public String getDescription() {
        return "Allows you to put a any modifier to spell";
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

    static void setFinalStatic(Field field, Object to, Integer[] newValue) throws Exception {
        field.setAccessible(true);

        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

        field.set(to, newValue);
    }

    static Integer[] getIntArray(ArrayList<Integer> arrayList) {
        return arrayList.toArray(new Integer[arrayList.size()]);
    }

    @Override
    public void onTicks() {
        try {
            Object currentScreen = Wrapper.INSTANCE.mc().currentScreen;
            if (currentScreen != null && Class.forName("am2.guis.GuiInscriptionTable").isInstance(currentScreen)) {
                setFinalStatic(Class.forName("am2.guis.GuiInscriptionTable").getDeclaredField("knownShapes"), currentScreen, getIntArray((ArrayList<Integer>) Class.forName("am2.spell.SkillManager").getMethod("getAllShapes").invoke(Class.forName("am2.spell.SkillManager").getField("instance").get(null))));
                setFinalStatic(Class.forName("am2.guis.GuiInscriptionTable").getDeclaredField("knownComponents"), currentScreen, getIntArray((ArrayList<Integer>) Class.forName("am2.spell.SkillManager").getMethod("getAllComponents").invoke(Class.forName("am2.spell.SkillManager").getField("instance").get(null))));
                setFinalStatic(Class.forName("am2.guis.GuiInscriptionTable").getDeclaredField("knownModifiers"), currentScreen, getIntArray((ArrayList<Integer>) Class.forName("am2.spell.SkillManager").getMethod("getAllModifiers").invoke(Class.forName("am2.spell.SkillManager").getField("instance").get(null))));
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
