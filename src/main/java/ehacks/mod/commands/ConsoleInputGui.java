package ehacks.mod.commands;

import com.google.common.collect.Sets;
import ehacks.mod.modulesystem.handler.EHacksGui;
import net.minecraft.client.gui.GuiConfirmOpenLink;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@SideOnly(Side.CLIENT)
public class ConsoleInputGui extends GuiScreen implements GuiYesNoCallback {

    private static final Set field_152175_f = Sets.newHashSet("http", "https");
    private static final Logger logger = LogManager.getLogger();
    private static final String __OBFID = "CL_00000682";
    /**
     * Chat entry field
     */
    protected GuiTextField inputField;
    private String field_146410_g = "";
    /**
     * keeps position of which chat message you will select when you press up,
     * (does not increase for duplicated messages sent immediately after each
     * other)
     */
    private int sentHistoryCursor = -1;
    private boolean field_146417_i;
    private boolean field_146414_r;
    private int field_146413_s;
    private List<String> field_146412_t = new ArrayList<>();
    /**
     * used to pass around the URI to various dialogues and to the host os
     */
    private URI clickedURI;
    /**
     * is the text that appears when you press the chat key and the input box
     * appears pre-filled
     */
    private String defaultInputFieldText = "";

    public ConsoleInputGui() {
    }

    public ConsoleInputGui(String p_i1024_1_) {
        this.defaultInputFieldText = p_i1024_1_;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.sentHistoryCursor = EHacksGui.clickGui.consoleGui.getSentMessages().size();
        this.inputField = new GuiTextField(1, this.fontRenderer, 4, this.height - 12, this.width - 4, 12);
        this.inputField.setMaxStringLength(100);
        this.inputField.setEnableBackgroundDrawing(false);
        this.inputField.setFocused(true);
        this.inputField.setText(this.defaultInputFieldText);
        this.inputField.setCanLoseFocus(false);
    }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat
     * events
     */
    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
        EHacksGui.clickGui.consoleGui.resetScroll();
    }

    /**
     * Called from the main game loop to update the screen.
     */
    @Override
    public void updateScreen() {
        this.inputField.updateCursorCounter();
    }

    /**
     * Fired when a key is typed. This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e).
     */
    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        if (!EHacksGui.clickGui.canInputConsole) {
            return;
        }
        this.field_146414_r = false;

        if (keyCode == 15) {
            this.getTabComplete();
        } else {
            this.field_146417_i = false;
        }

        if (keyCode == 1) {
            this.mc.displayGuiScreen(null);
        } else if (keyCode != 28 && keyCode != 156) {
            switch (keyCode) {
                case 200:
                    this.getSentHistory(-1);
                    break;
                case 208:
                    this.getSentHistory(1);
                    break;
                case 201:
                    EHacksGui.clickGui.consoleGui.scroll(EHacksGui.clickGui.consoleGui.getLineCount() - 1);
                    break;
                case 209:
                    EHacksGui.clickGui.consoleGui.scroll(-EHacksGui.clickGui.consoleGui.getLineCount() + 1);
                    break;
                default:
                    this.inputField.textboxKeyTyped(typedChar, keyCode);
                    break;
            }
        } else {
            String s = this.inputField.getText().trim();

            if (s.length() > 0) {
                this.sendMessage(s);
            }

            this.mc.displayGuiScreen(null);
        }
    }

    public void sendMessage(String message) {
        CommandManager.INSTANCE.processString(message);
        EHacksGui.clickGui.consoleGui.addToSentMessages(message);
    }

    /**
     * Handles mouse input.
     */
    @Override
    public void handleMouseInput() {
        try {
            super.handleMouseInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int i = Mouse.getEventDWheel();

        if (i != 0) {
            if (i > 1) {
                i = 1;
            }

            if (i < -1) {
                i = -1;
            }

            if (!isShiftKeyDown()) {
                i *= 7;
            }

            EHacksGui.clickGui.consoleGui.scroll(i);
        }
    }

    /**
     * Called when the mouse is clicked.
     */
    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0 && this.mc.gameSettings.chatLinks) {
            ITextComponent ichatcomponent = EHacksGui.clickGui.consoleGui.getChatComponent(Mouse.getX(), Mouse.getY());

            if (ichatcomponent != null) {
                ClickEvent clickevent = ichatcomponent.getStyle().getClickEvent();

                if (clickevent != null) {
                    if (isShiftKeyDown()) {
                        this.inputField.writeText(ichatcomponent.getUnformattedComponentText());
                    } else {
                        URI uri;

                        if (null == clickevent.getAction()) {
                            logger.error("Don\'t know how to handle " + clickevent);
                        } else {
                            switch (clickevent.getAction()) {
                                case OPEN_URL:
                                    try {
                                        uri = new URI(clickevent.getValue());

                                        if (!field_152175_f.contains(uri.getScheme().toLowerCase())) {
                                            throw new URISyntaxException(clickevent.getValue(), "Unsupported protocol: " + uri.getScheme().toLowerCase());
                                        }

                                        if (this.mc.gameSettings.chatLinksPrompt) {
                                            this.clickedURI = uri;
                                            this.mc.displayGuiScreen(new GuiConfirmOpenLink(this, clickevent.getValue(), 0, false));
                                        } else {
                                            this.openLink(uri);
                                        }
                                    } catch (URISyntaxException urisyntaxexception) {
                                        logger.error("Can\'t open url for " + clickevent, urisyntaxexception);
                                    }
                                    break;
                                case OPEN_FILE:
                                    uri = (new File(clickevent.getValue())).toURI();
                                    this.openLink(uri);
                                    break;
                                case SUGGEST_COMMAND:
                                    this.inputField.setText(clickevent.getValue());
                                    break;
                                case RUN_COMMAND:
                                    this.sendMessage(clickevent.getValue());
                                    break;
                                default:
                                    logger.error("Don\'t know how to handle " + clickevent);
                                    break;
                            }
                        }
                    }

                    return;
                }
            }
        }

        this.inputField.mouseClicked(mouseX, mouseY, mouseButton);
        try {
            super.mouseClicked(mouseX, mouseY, mouseButton);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void confirmClicked(boolean p_73878_1_, int p_73878_2_) {
        if (p_73878_2_ == 0) {
            if (p_73878_1_) {
                this.openLink(this.clickedURI);
            }

            this.clickedURI = null;
            this.mc.displayGuiScreen(this);
        }
    }

    @SuppressWarnings("unchecked")
    private void openLink(URI link) {
        try {
            Class oclass = Class.forName("java.awt.Desktop");
            Object object = oclass.getMethod("getDesktop").invoke(null);
            oclass.getMethod("browse", URI.class).invoke(object, link);
        } catch (Throwable throwable) {
            logger.error("Couldn\'t open link", throwable);
        }
    }

    public void getTabComplete() {
        String s1;

        if (this.field_146417_i) {
            this.inputField.deleteFromCursor(this.inputField.getNthWordFromPosWS(-1, this.inputField.getCursorPosition(), false) - this.inputField.getCursorPosition());

            if (this.field_146413_s >= this.field_146412_t.size()) {
                this.field_146413_s = 0;
            }
        } else {
            int i = this.inputField.getNthWordFromPosWS(-1, this.inputField.getCursorPosition(), false);
            this.field_146412_t.clear();
            this.field_146413_s = 0;
            String s = this.inputField.getText().substring(i).toLowerCase();
            s1 = this.inputField.getText().substring(0, this.inputField.getCursorPosition());
            this.getAutoComplete(s1, s);

            if (this.field_146412_t.isEmpty()) {
                return;
            }

            this.field_146417_i = true;
            this.inputField.deleteFromCursor(i - this.inputField.getCursorPosition());
        }

        if (this.field_146412_t.size() > 1) {
            StringBuilder stringbuilder = new StringBuilder();

            for (Iterator iterator = this.field_146412_t.iterator(); iterator.hasNext(); stringbuilder.append(s1)) {
                s1 = (String) iterator.next();

                if (stringbuilder.length() > 0) {
                    stringbuilder.append(", ");
                }
            }

            EHacksGui.clickGui.consoleGui.printChatMessageWithOptionalDeletion(new TextComponentString(stringbuilder.toString()), 1);
        }

        this.inputField.writeText(TextFormatting.getTextWithoutFormattingCodes(this.field_146412_t.get(this.field_146413_s++)));
    }

    private void getAutoComplete(String p_146405_1_, String p_146405_2_) {
        if (p_146405_1_.length() >= 1) {
            this.field_146414_r = true;
            this.func_146406_a(CommandManager.INSTANCE.autoComplete(p_146405_1_));
        }
    }

    /**
     * input is relative and is applied directly to the sentHistoryCursor so -1
     * is the previous message, 1 is the next message from the current cursor
     * position
     */
    public void getSentHistory(int p_146402_1_) {
        int j = this.sentHistoryCursor + p_146402_1_;
        int k = EHacksGui.clickGui.consoleGui.getSentMessages().size();

        if (j < 0) {
            j = 0;
        }

        if (j > k) {
            j = k;
        }

        if (j != this.sentHistoryCursor) {
            if (j == k) {
                this.sentHistoryCursor = k;
                this.inputField.setText(this.field_146410_g);
            } else {
                if (this.sentHistoryCursor == k) {
                    this.field_146410_g = this.inputField.getText();
                }

                this.inputField.setText(EHacksGui.clickGui.consoleGui.getSentMessages().get(j));
                this.sentHistoryCursor = j;
            }
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    @SuppressWarnings("unchecked")
    @Override
    public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
        drawRect(2, this.height - 14, this.width - 2, this.height - 2, Integer.MIN_VALUE);
        if (this.inputField.getText().length() == 0) {
            this.inputField.setText("/");
        }
        if (this.inputField.getText().charAt(0) != '/') {
            this.inputField.setText("/" + this.inputField.getText());
        }
        this.inputField.drawTextBox();
        ITextComponent ichatcomponent = EHacksGui.clickGui.consoleGui.getChatComponent(Mouse.getX(), Mouse.getY());

        if (ichatcomponent != null && ichatcomponent.getStyle().getHoverEvent() != null) {
            HoverEvent hoverevent = ichatcomponent.getStyle().getHoverEvent();

            if (null != hoverevent.getAction()) {
                switch (hoverevent.getAction()) {
                    case SHOW_ITEM:
                        ItemStack itemstack = null;
                        try {
                            NBTBase nbtbase = JsonToNBT.getTagFromJson(hoverevent.getValue().getUnformattedText());

                            if (nbtbase instanceof NBTTagCompound) {
                                itemstack = null; //ItemStack.loadItemStackFromNBT((NBTTagCompound) nbtbase); НЕ смог найти замену..
                            }
                        } catch (NBTException ignored) {
                        }
                        if (itemstack != null) {
                            this.renderToolTip(itemstack, p_73863_1_, p_73863_2_);
                        } else {
                            this.drawCenteredString(fontRenderer, TextFormatting.RED + "Invalid Item!", p_73863_1_, p_73863_2_, 0xFFFFFF);
                        }
                        break;
                    case SHOW_TEXT:
                        // Пока неизвестно, что сюда вписать.
                        //this.(Splitter.on("\n").splitToList(hoverevent.getValue().getFormattedText()), p_73863_1_, p_73863_2_);
                        break;
                    default:
                        break;
                }
            }

            GL11.glDisable(GL11.GL_LIGHTING);
        }

        super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
    }

    public void func_146406_a(String[] p_146406_1_) {
        if (this.field_146414_r) {
            this.field_146417_i = false;
            this.field_146412_t.clear();

            for (String s : p_146406_1_) {
                if (s.length() > 0) {
                    this.field_146412_t.add(s);
                }
            }

            String s1 = this.inputField.getText().substring(this.inputField.getNthWordFromPosWS(-1, this.inputField.getCursorPosition(), false));
            String s2 = StringUtils.getCommonPrefix(p_146406_1_);

            if (s2.length() > 0 && !s1.equalsIgnoreCase(s2)) {
                this.inputField.deleteFromCursor(this.inputField.getNthWordFromPosWS(-1, this.inputField.getCursorPosition(), false) - this.inputField.getCursorPosition());
                this.inputField.writeText(s2);
            } else if (this.field_146412_t.size() > 0) {
                this.field_146417_i = true;
                this.getTabComplete();
            }
        }
    }

    /**
     * Returns true if this GUI should pause the game when it is displayed in
     * single-player
     */
    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
