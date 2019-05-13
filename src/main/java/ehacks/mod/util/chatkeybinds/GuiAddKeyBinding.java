package ehacks.mod.util.chatkeybinds;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.GuiYesNoCallback;

@SideOnly(Side.CLIENT)
public class GuiAddKeyBinding extends GuiScreen implements GuiYesNoCallback {

    private final GuiControls backScreen;

    public GuiAddKeyBinding(GuiControls backScreen) {
        this.backScreen = backScreen;
    }

    private GuiTextField command;

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    @Override
    public void initGui() {
        command = new GuiTextField(this.fontRendererObj, this.width / 2 - 99, this.height / 6 + 66, 198, 18);
        addButton = new GuiButton(200, this.width / 2 - 100, this.height / 6 + 144, "Add");
        this.buttonList.add(addButton);
    }

    private GuiButton addButton;

    @Override
    protected void mouseClicked(int x, int y, int btn) {
        super.mouseClicked(x, y, btn);
        this.command.mouseClicked(x, y, btn);
    }

    @Override
    public void keyTyped(char c, int i) {
        super.keyTyped(c, i);
        this.command.textboxKeyTyped(c, i);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.enabled) {
            if (button.id == 200) {
                ChatKeyBindingHandler.INSTANCE.keyBindings.add(new ChatKeyBinding(command.getText(), 0));
                this.mc.displayGuiScreen(new GuiControls(this.backScreen.parentScreen));
            }
        }
    }

    @Override
    public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
        this.drawBackground(0);
        this.drawCenteredString(this.fontRendererObj, "Chat command", this.width / 2, 15, 16777215);
        this.command.drawTextBox();
        this.addButton.enabled = !"".equals(this.command.getText());
        this.drawString(this.fontRendererObj, "Text", this.width / 2 - 100, this.height / 6 + 66 - 13, 16777215);
        super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
    }
}
