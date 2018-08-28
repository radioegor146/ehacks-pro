package ehacks.mod.gui.xraysettings;

import ehacks.mod.modulesystem.classes.XRay;
import ehacks.mod.wrapper.Wrapper;
import java.io.IOException;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class XRayGui
        extends GuiScreen {

    XRayBlockSlot slot;
    GuiButton add;
    GuiButton del;
    GuiButton edit;
    GuiButton exit;
    FontRenderer render;

    @Override
    public void initGui() {
        super.initGui();
        this.render = this.fontRendererObj;
        this.slot = new XRayBlockSlot(Wrapper.INSTANCE.mc(), this.width, this.height, 25, this.height - 25, 20, this);
        this.add = new GuiButton(0, this.width / 9, this.height - 22, 70, 20, "Add Block");
        this.del = new GuiButton(1, this.width / 9 * 3, this.height - 22, 70, 20, "Delete Block");
        this.del.enabled = false;
        this.edit = new GuiButton(2, this.width / 9 * 5, this.height - 22, 70, 20, "Edit Block");
        this.edit.enabled = false;
        this.exit = new GuiButton(3, this.width / 9 * 7, this.height - 22, 70, 20, "Exit");
        this.buttonList.add(this.add);
        this.buttonList.add(this.del);
        this.buttonList.add(this.edit);
        this.buttonList.add(this.exit);
    }

    @Override
    public void drawScreen(int par1, int par2, float par3) {
        this.slot.drawScreen(par1, par2, par3);
        super.drawScreen(par1, par2, par3);
        if (this.slot.selectedIndex != -1) {
            this.del.enabled = true;
            this.edit.enabled = true;
        } else {
            this.del.enabled = false;
            this.edit.enabled = false;
        }
    }

    @Override
    protected void actionPerformed(GuiButton par1GuiButton) {
        switch (par1GuiButton.id) {
            case 0: {
                Wrapper.INSTANCE.mc().displayGuiScreen((GuiScreen) new XRayAddGui());
                break;
            }
            case 1: {
                XRayBlock.blocks.remove(this.slot.selectedIndex);
                this.slot.selectedIndex = -1;
                try {
                    XRayBlock.save();
                } catch (IOException var3) {
                    var3.printStackTrace();
                }
                break;
            }
            case 2: {
                Wrapper.INSTANCE.mc().displayGuiScreen((GuiScreen) new XRayAddGui((XRayBlock) XRayBlock.blocks.get(this.slot.selectedIndex), this.slot.selectedIndex));
                break;
            }
            case 3: {
                Wrapper.INSTANCE.mc().displayGuiScreen((GuiScreen) null);
                XRay.cooldownTicks = 0;
                break;
            }
            default: {
                this.slot.actionPerformed(par1GuiButton);
            }
        }
    }
}
