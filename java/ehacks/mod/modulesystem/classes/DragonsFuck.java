package ehacks.mod.modulesystem.classes;

import ehacks.api.module.ModStatus;
import ehacks.api.module.Module;
import ehacks.mod.gui.EHacksClickGui;
import ehacks.mod.util.InteropUtils;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.event.MouseEvent;
import org.lwjgl.input.Mouse;

public class DragonsFuck
        extends Module {

    public DragonsFuck() {
        super(ModuleCategory.EHACKS);
    }

    @Override
    public String getName() {
        return "DragonsFuck";
    }

    @Override
    public String getDescription() {
        return "You can open Dragon's radio block on right click";
    }

    @Override
    public void onEnableMod() {
        try {
            Class.forName("eu.thesociety.DragonbornSR.DragonsRadioMod.Block.TileEntity.TileEntityRadio");
            Class.forName("eu.thesociety.DragonbornSR.DragonsRadioMod.Block.Gui.NGuiRadio");
        } catch (Exception ex) {
            this.off();
        }
    }

    @Override
    public ModStatus getModStatus() {
        try {
            Class.forName("eu.thesociety.DragonbornSR.DragonsRadioMod.Block.TileEntity.TileEntityRadio");
            Class.forName("eu.thesociety.DragonbornSR.DragonsRadioMod.Block.Gui.NGuiRadio");
            return ModStatus.WORKING;
        } catch (Exception e) {
            return ModStatus.NOTWORKING;
        }
    }

    @Override
    public void onDisableMod() {

    }

    @Override
    public void onMouse(MouseEvent event) {
        MovingObjectPosition mop = Wrapper.INSTANCE.mc().objectMouseOver;
        if (mop.sideHit == -1) {
            return;
        }
        if (Mouse.isButtonDown(1)) {
            TileEntity entity = Wrapper.INSTANCE.world().getTileEntity(mop.blockX, mop.blockY, mop.blockZ);
            try {
                if (mop.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && entity != null && Class.forName("eu.thesociety.DragonbornSR.DragonsRadioMod.Block.TileEntity.TileEntityRadio").isInstance(entity)) {
                    Wrapper.INSTANCE.mc().displayGuiScreen((GuiScreen) Class.forName("eu.thesociety.DragonbornSR.DragonsRadioMod.Block.Gui.NGuiRadio").getConstructor(Class.forName("eu.thesociety.DragonbornSR.DragonsRadioMod.Block.TileEntity.TileEntityRadio")).newInstance(entity));
                    InteropUtils.log("Gui opened", this);
                    if (event.isCancelable()) {
                        event.setCanceled(true);
                    }
                }
            } catch (Exception ex) {
                InteropUtils.log("&cError", this);
                ex.printStackTrace();
            }
        }
    }

    @Override
    public String getModName() {
        return "Dragon's radio";
    }
}
