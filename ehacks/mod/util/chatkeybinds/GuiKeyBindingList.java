package ehacks.mod.util.chatkeybinds;

import java.util.ArrayList;
import java.util.Arrays;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.EnumChatFormatting;
import org.apache.commons.lang3.ArrayUtils;

public class GuiKeyBindingList extends GuiListExtended {

    private final GuiControls parentScreen;
    private final Minecraft mc;
    private final GuiListExtended.IGuiListEntry[] listEntries;
    private int maxListLabelWidth = 0;

    public GuiKeyBindingList(GuiControls parentScreen, Minecraft mc) {
        super(mc, parentScreen.width, parentScreen.height, 63, parentScreen.height - 32, 20);
        this.parentScreen = parentScreen;
        this.mc = mc;
        ChatKeyBinding[] var3 = ArrayUtils.clone(ChatKeyBindingHandler.INSTANCE.keyBindings.toArray(new ChatKeyBinding[ChatKeyBindingHandler.INSTANCE.keyBindings.size()]));
        ArrayList<GuiListExtended.IGuiListEntry> arrayListEntries = new ArrayList<>();
        Arrays.sort(var3);

        for (int i = 0; i < var3.length; ++i) {
            if (mc.fontRenderer.getStringWidth(var3[i].getKeyDescription()) > this.maxListLabelWidth) {
                this.maxListLabelWidth = mc.fontRenderer.getStringWidth(var3[i].getKeyDescription());
            }
            arrayListEntries.add(new GuiKeyBindingList.KeyEntry(var3[i]));
        }

        listEntries = arrayListEntries.toArray(new GuiListExtended.IGuiListEntry[arrayListEntries.size()]);
    }

    @Override
    protected int getSize() {
        return this.listEntries.length;
    }

    /**
     * Gets the IGuiListEntry object for the given index
     */
    @Override
    public GuiListExtended.IGuiListEntry getListEntry(int index) {
        return this.listEntries[index];
    }

    @Override
    protected int getScrollBarX() {
        return super.getScrollBarX() + 15;
    }

    /**
     * Gets the width of the list
     */
    @Override
    public int getListWidth() {
        return super.getListWidth() + 32;
    }

    public class KeyEntry implements GuiListExtended.IGuiListEntry {

        private final ChatKeyBinding entryKeybinding;
        private final String keyDesctiption;
        private final GuiButton btnChangeKeyBinding;
        private final GuiButton btnRemove;

        private KeyEntry(ChatKeyBinding keybinding) {
            this.entryKeybinding = keybinding;
            this.keyDesctiption = keybinding.getKeyDescription();
            this.btnChangeKeyBinding = new GuiButton(0, 0, 0, 75, 18, keybinding.getKeyDescription());
            this.btnRemove = new GuiButton(0, 0, 0, 50, 18, "Remove");
        }

        @Override
        public void drawEntry(int index, int x, int y, int width, int height, Tessellator tesselator, int buttonX, int buttonY, boolean p_148279_9_) {
            boolean var10 = GuiKeyBindingList.this.parentScreen.currentKeyBinding == this.entryKeybinding;
            GuiKeyBindingList.this.mc.fontRenderer.drawString(this.keyDesctiption, x + 90 - GuiKeyBindingList.this.maxListLabelWidth, y + height / 2 - GuiKeyBindingList.this.mc.fontRenderer.FONT_HEIGHT / 2, 16777215);
            this.btnRemove.xPosition = x + 190;
            this.btnRemove.yPosition = y;
            this.btnRemove.drawButton(GuiKeyBindingList.this.mc, buttonX, buttonY);
            this.btnChangeKeyBinding.xPosition = x + 105;
            this.btnChangeKeyBinding.yPosition = y;
            this.btnChangeKeyBinding.displayString = GameSettings.getKeyDisplayString(this.entryKeybinding.getKeyCode());
            if (var10) {
                this.btnChangeKeyBinding.displayString = EnumChatFormatting.WHITE + "> " + EnumChatFormatting.YELLOW + this.btnChangeKeyBinding.displayString + EnumChatFormatting.WHITE + " <";
            }
            this.btnChangeKeyBinding.drawButton(GuiKeyBindingList.this.mc, buttonX, buttonY);
        }

        @Override
        public boolean mousePressed(int p_148278_1_, int x, int y, int p_148278_4_, int p_148278_5_, int p_148278_6_) {
            if (this.btnChangeKeyBinding.mousePressed(GuiKeyBindingList.this.mc, x, y)) {
                GuiKeyBindingList.this.parentScreen.currentKeyBinding = this.entryKeybinding;
                return true;
            } else if (this.btnRemove.mousePressed(GuiKeyBindingList.this.mc, x, y)) {
                ChatKeyBindingHandler.INSTANCE.keyBindings.remove(entryKeybinding);
                GuiKeyBindingList.this.mc.displayGuiScreen(new GuiControls(GuiKeyBindingList.this.parentScreen.parentScreen));
                return true;
            } else {
                return false;
            }
        }

        @Override
        public void mouseReleased(int p_148277_1_, int x, int y, int p_148277_4_, int p_148277_5_, int p_148277_6_) {
            this.btnChangeKeyBinding.mouseReleased(x, y);
            this.btnRemove.mouseReleased(x, y);
        }
    }
}
