package ehacks.mod.modulesystem.classes;

import ehacks.api.module.Module;
import ehacks.mod.util.diffgui.EnchantmentsRegistry;
import ehacks.mod.util.diffgui.PotionsRegistry;
import ehacks.mod.wrapper.ModuleCategory;

public class DiffRegistry
        extends Module {

    private PotionsRegistry potionsGui;
    private EnchantmentsRegistry enchantmentsGui;

    public DiffRegistry() {
        super(ModuleCategory.EHACKS);
    }

    @Override
    public String getName() {
        return "DiffRegistry";
    }

    @Override
    public String getDescription() {
        return "Get all registries";
    }

    @Override
    public void onEnableMod() {
        potionsGui = new PotionsRegistry();
        potionsGui.setVisible(true);
        enchantmentsGui = new EnchantmentsRegistry();
        enchantmentsGui.setVisible(true);
    }

    @Override
    public void onDisableMod() {
        potionsGui.setVisible(false);
        enchantmentsGui.setVisible(false);
    }
}
