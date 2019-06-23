package ehacks.mod.util.ehackscfg;

import ehacks.mod.main.Main;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.IOException;

import static ehacks.mod.main.Main.INSTANCE;

@SideOnly(Side.CLIENT)
public class GuiModIdConfig extends GuiScreen implements GuiYesNoCallback {

    private final GuiScreen backScreen;
    private GuiTextField modId;
    private GuiTextField modVersion;
    private GuiButton saveButton;

    public GuiModIdConfig(GuiScreen backScreen) {
        this.backScreen = backScreen;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    @Override
    public void initGui() {
        modId = new GuiTextField(1, this.fontRenderer, this.width / 2 - 99, this.height / 6 + 66, 198, 18);
        modId.setText(Main.modId);
        modVersion = new GuiTextField(2, this.fontRenderer, this.width / 2 - 99, this.height / 6 + 66 + 36, 198, 18);
        modVersion.setText(Main.modVersion);
        saveButton = new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168, "Save");
        this.buttonList.add(saveButton);
    }

    @Override
    protected void mouseClicked(int x, int y, int btn) {
        try {
            super.mouseClicked(x, y, btn);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.modId.mouseClicked(x, y, btn);
        this.modVersion.mouseClicked(x, y, btn);
    }

    @Override
    public void keyTyped(char c, int i) {
        try {
            super.keyTyped(c, i);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.modId.textboxKeyTyped(c, i);
        this.modVersion.textboxKeyTyped(c, i);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.enabled) {
            if (button.id == 200) {
                Main.modId = modId.getText();
                Main.modVersion = modVersion.getText();
                Main.applyModChanges();
                this.mc.displayGuiScreen(this.backScreen);
            }
        }
    }

    @Override
    public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, "ModID configuration", this.width / 2, 15, 16777215);
        this.modId.drawTextBox();
        this.modVersion.drawTextBox();
        boolean nameOk = true;
        for (ModContainer container : Loader.instance().getActiveModList()) {
            if ((container.getModId() == null ? this.modId.getText() == null : container.getModId().equals(this.modId.getText())) && container.getMod() != INSTANCE) {
                nameOk = false;
                break;
            }
        }
        this.saveButton.enabled = "".equals(this.modId.getText()) || nameOk;
        this.drawString(this.fontRenderer, "Mod ID (empty - no mod)", this.width / 2 - 100, this.height / 6 + 66 - 13, 16777215);
        this.drawString(this.fontRenderer, "Mod Version", this.width / 2 - 100, this.height / 6 + 66 + 36 - 13, 16777215);
        super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
    }
}
