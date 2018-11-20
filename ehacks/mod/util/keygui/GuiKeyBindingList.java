package ehacks.mod.util.keygui;

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
        ModuleKeyBinding[] var3 = ArrayUtils.clone(parentScreen.keyBindings);
        ArrayList<GuiListExtended.IGuiListEntry> arrayListEntries = new ArrayList<>();
        Arrays.sort(var3);
        int var4 = 0;
        String var5 = null;
        ModuleKeyBinding[] var6 = var3;
        int var7 = var3.length;

        for (int var8 = 0; var8 < var7; ++var8) {
            ModuleKeyBinding var9 = var6[var8];
            String var10 = var9.getKeyCategory();

            if (!var10.equals(var5)) {
                var5 = var10;
                arrayListEntries.add(new GuiKeyBindingList.CategoryEntry(var10));
            }

            int var11 = mc.fontRenderer.getStringWidth(var9.getKeyDescription());

            if (var11 > this.maxListLabelWidth) {
                this.maxListLabelWidth = var11;
            }

            arrayListEntries.add(new GuiKeyBindingList.KeyEntry(var9));
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

    public class CategoryEntry implements GuiListExtended.IGuiListEntry {

        private final String labelText;
        private final int labelWidth;

        public CategoryEntry(String name) {
            this.labelText = name;
            this.labelWidth = GuiKeyBindingList.this.mc.fontRenderer.getStringWidth(this.labelText);
        }

        @Override
        public void drawEntry(int p_148279_1_, int p_148279_2_, int p_148279_3_, int p_148279_4_, int p_148279_5_, Tessellator p_148279_6_, int p_148279_7_, int p_148279_8_, boolean p_148279_9_) {
            GuiKeyBindingList.this.mc.fontRenderer.drawString(this.labelText, GuiKeyBindingList.this.mc.currentScreen.width / 2 - this.labelWidth / 2, p_148279_3_ + p_148279_5_ - GuiKeyBindingList.this.mc.fontRenderer.FONT_HEIGHT - 1, 16777215);
        }

        @Override
        public boolean mousePressed(int p_148278_1_, int p_148278_2_, int p_148278_3_, int p_148278_4_, int p_148278_5_, int p_148278_6_) {
            return false;
        }

        @Override
        public void mouseReleased(int p_148277_1_, int p_148277_2_, int p_148277_3_, int p_148277_4_, int p_148277_5_, int p_148277_6_) {
        }
    }

    public class KeyEntry implements GuiListExtended.IGuiListEntry {

        private final ModuleKeyBinding entryKeybinding;
        private final String keyDesctiption;
        private final GuiButton btnChangeKeyBinding;
        private final GuiButton btnReset;

        private KeyEntry(ModuleKeyBinding keybinding) {
            this.entryKeybinding = keybinding;
            this.keyDesctiption = keybinding.getKeyDescription();
            this.btnChangeKeyBinding = new GuiButton(0, 0, 0, 75, 18, keybinding.getKeyDescription());
            this.btnReset = new GuiButton(0, 0, 0, 50, 18, "Reset");
        }

        @Override
        public void drawEntry(int index, int x, int y, int width, int height, Tessellator tesselator, int buttonX, int buttonY, boolean p_148279_9_) {
            boolean var10 = GuiKeyBindingList.this.parentScreen.currentKeyBinding == this.entryKeybinding;
            GuiKeyBindingList.this.mc.fontRenderer.drawString(this.keyDesctiption, x + 90 - GuiKeyBindingList.this.maxListLabelWidth, y + height / 2 - GuiKeyBindingList.this.mc.fontRenderer.FONT_HEIGHT / 2, 16777215);
            this.btnReset.xPosition = x + 190;
            this.btnReset.yPosition = y;
            this.btnReset.enabled = this.entryKeybinding.getKeyCode() != this.entryKeybinding.getKeyCodeDefault();
            this.btnReset.drawButton(GuiKeyBindingList.this.mc, buttonX, buttonY);
            this.btnChangeKeyBinding.xPosition = x + 105;
            this.btnChangeKeyBinding.yPosition = y;
            this.btnChangeKeyBinding.displayString = GameSettings.getKeyDisplayString(this.entryKeybinding.getKeyCode());
            boolean var11 = false;

            if (this.entryKeybinding.getKeyCode() != 0) {
                ModuleKeyBinding[] var12 = GuiKeyBindingList.this.parentScreen.keyBindings;
                int var13 = var12.length;

                for (int var14 = 0; var14 < var13; ++var14) {
                    ModuleKeyBinding var15 = var12[var14];

                    if (var15 != this.entryKeybinding && var15.getKeyCode() == this.entryKeybinding.getKeyCode()) {
                        var11 = true;
                        break;
                    }
                }
            }

            if (var10) {
                this.btnChangeKeyBinding.displayString = EnumChatFormatting.WHITE + "> " + EnumChatFormatting.YELLOW + this.btnChangeKeyBinding.displayString + EnumChatFormatting.WHITE + " <";
            } else if (var11) {
                this.btnChangeKeyBinding.displayString = EnumChatFormatting.RED + this.btnChangeKeyBinding.displayString;
            }

            this.btnChangeKeyBinding.drawButton(GuiKeyBindingList.this.mc, buttonX, buttonY);
        }

        @Override
        public boolean mousePressed(int p_148278_1_, int x, int y, int p_148278_4_, int p_148278_5_, int p_148278_6_) {
            if (this.btnChangeKeyBinding.mousePressed(GuiKeyBindingList.this.mc, x, y)) {
                GuiKeyBindingList.this.parentScreen.currentKeyBinding = this.entryKeybinding;
                return true;
            } else if (this.btnReset.mousePressed(GuiKeyBindingList.this.mc, x, y)) {
                GuiKeyBindingList.this.parentScreen.setKeyBinding(this.entryKeybinding, this.entryKeybinding.getKeyCodeDefault());
                return true;
            } else {
                return false;
            }
        }

        @Override
        public void mouseReleased(int p_148277_1_, int x, int y, int p_148277_4_, int p_148277_5_, int p_148277_6_) {
            this.btnChangeKeyBinding.mouseReleased(x, y);
            this.btnReset.mouseReleased(x, y);
        }
    }
}
