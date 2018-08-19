package ehacks.mod.util.ehackscfg;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ehacks.mod.main.Main;
import static ehacks.mod.main.Main.INSTANCE;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.audio.SoundEventAccessorComposite;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.gui.stream.GuiStreamOptions;
import net.minecraft.client.gui.stream.GuiStreamUnavailable;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.stream.IStream;

@SideOnly(Side.CLIENT)
public class GuiModIdConfig extends GuiScreen implements GuiYesNoCallback
{
    private final GuiScreen backScreen;

    public GuiModIdConfig(GuiScreen backScreen)
    {
        this.backScreen = backScreen;
    }

    private GuiTextField modId;
    private GuiTextField modVersion;
    
    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    @Override
    public void initGui()
    {
        modId = new GuiTextField(this.fontRendererObj, this.width / 2 - 99, this.height / 6 + 66, 198, 18);
        modId.setText(Main.modId);
        modVersion = new GuiTextField(this.fontRendererObj, this.width / 2 - 99, this.height / 6 + 66 + 36, 198, 18);
        modVersion.setText(Main.modVersion);
        saveButton = new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168, "Save");
        this.buttonList.add(saveButton);
    }
    
    private GuiButton saveButton;

    @Override
    protected void mouseClicked(int x, int y, int btn)  {
        super.mouseClicked(x, y, btn);
        this.modId.mouseClicked(x, y, btn);
        this.modVersion.mouseClicked(x, y, btn);
    }
    
    @Override
    public void keyTyped(char c, int i) {
        super.keyTyped(c, i);
        this.modId.textboxKeyTyped(c, i);
        this.modVersion.textboxKeyTyped(c, i);
    }
    
    @Override
    protected void actionPerformed(GuiButton button)
    {
        if (button.enabled)
        {
            if (button.id == 200)
            {
                Main.modId = modId.getText();
                Main.modVersion = modVersion.getText();
                Main.applyModChanges();
                this.mc.displayGuiScreen(this.backScreen);
            }
        }
    }

    @Override
    public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_)
    {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, "ModID configuration", this.width / 2, 15, 16777215);
        this.modId.drawTextBox();
        this.modVersion.drawTextBox();
        boolean nameOk = true;
        for (ModContainer container : Loader.instance().getActiveModList()) {
            if ((container.getModId() == null ? this.modId.getText() == null : container.getModId().equals(this.modId.getText())) && container.getMod() != INSTANCE)
            {
                nameOk = false;
                break;
            }
        }
        this.saveButton.enabled = "".equals(this.modId.getText()) || nameOk;
        this.drawString(this.fontRendererObj, "Mod ID (empty - no mod)", this.width / 2 - 100, this.height / 6 + 66 - 13, 16777215);
        this.drawString(this.fontRendererObj, "Mod Version", this.width / 2 - 100, this.height / 6 + 66 + 36 - 13, 16777215);
        super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
    }
}