package ehacks.mod.util.ehackscfg;

import ehacks.mod.main.Main;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiWorldSelection;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiMainConfig extends GuiScreen implements GuiYesNoCallback {

    private final GuiScreen backScreen;

    public GuiMainConfig(GuiScreen backScreen) {
        this.backScreen = backScreen;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    @Override
    public void initGui() {
        GuiButton modidbutton = new GuiButton(100, this.width / 2 - 100, this.height / 6 + 72 - 6, "ModID");
        if (Main.isInjected) {
            modidbutton.enabled = false;
        }
        this.buttonList.add(modidbutton);
        GuiButton reauthbutton = new GuiButton(101, this.width / 2 - 100, this.height / 6 + 96 - 6, "Reauthorization");
        reauthbutton.enabled = false;
        this.buttonList.add(reauthbutton);
        this.buttonList.add(new GuiButton(201, this.width / 2 - 100, this.height / 6 + 120 - 6, "Open singleplayer"));
        this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 192, "Done"));
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.enabled) {
            if (button.id == 200) {
                this.mc.displayGuiScreen(this.backScreen);
            }
            if (button.id == 201) {
                this.mc.displayGuiScreen(new GuiWorldSelection(this));
            }
            if (button.id == 100) {
                this.mc.displayGuiScreen(new GuiModIdConfig(this));
            }
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    @Override
    public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, "EHacks config and utils", this.width / 2, 15, 16777215);
        super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
    }
}
