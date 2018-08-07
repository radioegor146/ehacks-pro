/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehacks.mod.modulesystem.classes;

import ehacks.api.module.Mod;
import ehacks.api.module.ModStatus;
import ehacks.mod.commands.ItemSelectCommand;
import ehacks.mod.gui.reeszrbteam.YouAlwaysWinClickGui;
import ehacks.mod.wrapper.ModuleCategories;
import ehacks.mod.wrapper.Wrapper;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import org.lwjgl.input.Keyboard;

/**
 *
 * @author radioegor146
 */
public class NEISelect extends Mod {
    public NEISelect() {
        super(ModuleCategories.EHACKS);
    }

    @Override
    public String getName() {
        return "NEISelect";
    }

    @Override
    public String getDescription() {
        return "Select item from NEI";
    }
    
    @Override
    public void onEnableMod() {
        try {
            Class.forName("codechicken.nei.guihook.GuiContainerManager");
        } catch (Exception ex) {
            this.off();
            YouAlwaysWinClickGui.log("[NEISelect] Not working");
        }
    }
    
    @Override
    public ModStatus getModStatus() {
        try {
            Class.forName("codechicken.nei.guihook.GuiContainerManager");
            return ModStatus.WORKING;
        } catch (Exception e) {
            return ModStatus.NOTWORKING;
        }
    }
    
    private boolean prevState = false;
    
    @Override
    public void onTicks() {
        boolean newState = Keyboard.isKeyDown(Keyboard.KEY_TAB);
        if (newState && !prevState)
        {
            prevState = newState;
            try {
                GuiContainer container = Wrapper.INSTANCE.mc().currentScreen instanceof GuiContainer ? ((GuiContainer) Wrapper.INSTANCE.mc().currentScreen) : null;
                if (container == null)
                    return;
                Object checkItem = Class.forName("codechicken.nei.guihook.GuiContainerManager").getDeclaredMethod("getStackMouseOver", GuiContainer.class).invoke(null, container);
                if (checkItem instanceof ItemStack) {
                    ItemStack item = (ItemStack)checkItem;
                    int count = container.isShiftKeyDown() ? item.getMaxStackSize() : 1;
                    ItemSelectCommand.SelectedStack = item.copy().splitStack(count);
                }
                YouAlwaysWinClickGui.log("[NEISelect] ItemStack selected");
            } catch (Exception ex) {
                
            }
        }
        prevState = newState;
    }
    
    @Override
    public String getModName() {
        return "NEI";
    }
}
