package ehacks.mod.commands;

import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@SideOnly(Side.CLIENT)
public class ConsoleGui extends Gui {

    private static final String __OBFID = "CL_00000669";
    private final Minecraft mc;
    /**
     * A list of messages previously sent through the chat GUI
     */
    private final List<String> sentMessages = new ArrayList<>();
    /**
     * Chat lines to be displayed in the chat box
     */
    private final List<ChatLine> chatLines = new ArrayList<>();
    private final List<ChatLine> drawnChatLines = new ArrayList<>();
    private int scrollPos;
    private boolean isScrolled;
    private double prevWidth = 0;

    public ConsoleGui(Minecraft p_i1022_1_) {
        this.mc = p_i1022_1_;
    }

    public static int calculateChatboxWidth(float p_146233_0_) {
        short short1 = 320;
        byte b0 = 40;
        return MathHelper.floor(p_146233_0_ * (short1 - b0) + b0);
    }

    public static int calculateChatboxHeight(float p_146243_0_) {
        short short1 = 180;
        byte b0 = 20;
        return MathHelper.floor(p_146243_0_ * (short1 - b0) + b0);
    }

    public void drawChat(int tickid) {
        if (prevWidth != this.mc.gameSettings.chatWidth) {
            prevWidth = this.mc.gameSettings.chatWidth;
            refreshChat();
        }
        int j = this.getLineCount();
        boolean flag = false;
        int k = 0;
        int l = this.drawnChatLines.size();
        float f = this.mc.gameSettings.chatOpacity * 0.9F + 0.1F;

        if (l > 0) {
            if (this.getChatOpen()) {
                flag = true;
            }

            float f1 = this.getChatScale();
            int i1 = MathHelper.ceil(this.getChatWidth() / f1);
            GL11.glPushMatrix();
            ScaledResolution get = new ScaledResolution(this.mc);
            GL11.glTranslatef(get.getScaledWidth() - 6 - getChatWidth(), get.getScaledHeight() - 28, 0.0F);
            GL11.glScalef(f1, f1, 1.0F);
            int j1;
            int k1;
            int i2;

            for (j1 = 0; j1 + this.scrollPos < this.drawnChatLines.size() && j1 < j; ++j1) {
                ChatLine chatline = this.drawnChatLines.get(j1 + this.scrollPos);

                if (chatline != null) {
                    k1 = tickid - chatline.getUpdatedCounter();

                    if (k1 < 200 || flag) {
                        double d0 = k1 / 200.0D;
                        d0 = 1.0D - d0;
                        d0 *= 10.0D;

                        if (d0 < 0.0D) {
                            d0 = 0.0D;
                        }

                        if (d0 > 1.0D) {
                            d0 = 1.0D;
                        }

                        d0 *= d0;
                        i2 = (int) (255.0D * d0);

                        if (flag) {
                            i2 = 255;
                        }

                        i2 = (int) (i2 * f);
                        ++k;

                        if (i2 > 3) {
                            byte b0 = 0;
                            int j2 = -j1 * 9;
                            drawRect(b0, j2 - 9, b0 + i1 + 4, j2, i2 / 2 << 24);
                            GL11.glEnable(GL11.GL_BLEND); // FORGE: BugFix MC-36812 Chat Opacity Broken in 1.7.x
                            String s = chatline.getChatComponent().getFormattedText();
                            this.mc.fontRenderer.drawStringWithShadow(s, b0, j2 - 8, 16777215 + (i2 << 24));
                            GL11.glDisable(GL11.GL_ALPHA_TEST);
                        }
                    }
                }
            }

            if (flag) {
                j1 = this.mc.fontRenderer.FONT_HEIGHT;
                GL11.glTranslatef(-3.0F, 0.0F, 0.0F);
                int k2 = l * j1 + l;
                k1 = k * j1 + k;
                int l2 = this.scrollPos * k1 / l;
                int l1 = k1 * k1 / k2;

                if (k2 != k1) {
                    i2 = l2 > 0 ? 170 : 96;
                    int i3 = this.isScrolled ? 13382451 : 3355562;
                    drawRect(0, -l2, 2, -l2 - l1, i3 + (i2 << 24));
                    drawRect(2, -l2, 1, -l2 - l1, 13421772 + (i2 << 24));
                }
            }

            GL11.glPopMatrix();
        }
    }

    /**
     * Clears the chat.
     */
    public void clearChatMessages() {
        this.drawnChatLines.clear();
        this.chatLines.clear();
        this.sentMessages.clear();
    }

    public void printChatMessage(ITextComponent p_146227_1_) {
        this.printChatMessageWithOptionalDeletion(p_146227_1_, 0);
    }

    /**
     * prints the ChatComponent to Chat. If the ID is not 0, deletes an existing
     * Chat Line of that ID from the GUI
     */
    public void printChatMessageWithOptionalDeletion(ITextComponent chatComponent, int chatLineId) {
        this.setChatLine(chatComponent, chatLineId, this.mc.ingameGUI.getUpdateCounter(), false);
    }

    private String func_146235_b(String p_146235_1_) {
        return Minecraft.getMinecraft().gameSettings.chatColours ? p_146235_1_ : TextFormatting.getTextWithoutFormattingCodes(p_146235_1_);
    }

    private void setChatLine(ITextComponent chatComponent, int chatLineId, int updateCounter, boolean displayOnly) {
        if (chatLineId != 0) {
            this.deleteChatLine(chatLineId);
        }

        int k = MathHelper.floor(this.getChatWidth() / this.getChatScale());
        int l = 0;
        TextComponentString chatcomponenttext = new TextComponentString("");
        ArrayList<ITextComponent> arraylist = Lists.newArrayList();
        @SuppressWarnings("unchecked")
        ArrayList<ITextComponent> arraylist1 = Lists.newArrayList(chatComponent);

        for (int i1 = 0; i1 < arraylist1.size(); ++i1) {
            ITextComponent ichatcomponent1 = arraylist1.get(i1);
            String s = this.func_146235_b(ichatcomponent1.getStyle().getFormattingCode() + ichatcomponent1.getUnformattedComponentText());
            int j1 = this.mc.fontRenderer.getStringWidth(s);
            TextComponentString chatcomponenttext1 = new TextComponentString(s);
            chatcomponenttext1.setStyle(ichatcomponent1.getStyle().createShallowCopy());
            boolean flag1 = false;

            if (l + j1 > k) {
                String s1 = this.mc.fontRenderer.trimStringToWidth(s, k - l, false);
                String s2 = s1.length() < s.length() ? s.substring(s1.length()) : null;

                if (s2 != null && s2.length() > 0) {
                    int k1 = s1.lastIndexOf(' ');

                    if (k1 >= 0 && this.mc.fontRenderer.getStringWidth(s.substring(0, k1)) > 0) {
                        s1 = s.substring(0, k1);
                        s2 = s.substring(k1);
                    }

                    char lastcolor = 'f';

                    for (int i = 0; i < s1.length(); i++) {
                        if (s1.charAt(i) == '\u00a7' && i != s1.length() - 1) {
                            lastcolor = s1.charAt(i + 1);
                        }
                    }

                    s2 = "\u00a7" + lastcolor + s2;

                    TextComponentString chatcomponenttext2 = new TextComponentString(s2);

                    chatcomponenttext2.setStyle(ichatcomponent1.getStyle().createShallowCopy());
                    arraylist1.add(i1 + 1, chatcomponenttext2);
                }

                j1 = this.mc.fontRenderer.getStringWidth(s1);
                chatcomponenttext1 = new TextComponentString(s1);
                chatcomponenttext1.setStyle(ichatcomponent1.getStyle().createShallowCopy());
                flag1 = true;
            }

            if (l + j1 <= k) {
                l += j1;
                chatcomponenttext.appendSibling(chatcomponenttext1);
            } else {
                flag1 = true;
            }

            if (flag1) {
                arraylist.add(chatcomponenttext);
                l = 0;
                chatcomponenttext = new TextComponentString("");
            }
        }

        arraylist.add(chatcomponenttext);
        boolean flag2 = this.getChatOpen();
        ITextComponent ichatcomponent2;

        for (Iterator iterator = arraylist.iterator(); iterator.hasNext(); this.drawnChatLines.add(0, new ChatLine(updateCounter, ichatcomponent2, chatLineId))) {
            ichatcomponent2 = (ITextComponent) iterator.next();

            if (flag2 && this.scrollPos > 0) {
                this.isScrolled = true;
                this.scroll(1);
            }
        }

        while (this.drawnChatLines.size() > 100) {
            this.drawnChatLines.remove(this.drawnChatLines.size() - 1);
        }

        if (!displayOnly) {
            this.chatLines.add(0, new ChatLine(updateCounter, chatComponent, chatLineId));

            while (this.chatLines.size() > 100) {
                this.chatLines.remove(this.chatLines.size() - 1);
            }
        }
    }

    public void refreshChat() {
        this.drawnChatLines.clear();
        this.resetScroll();

        for (int i = this.chatLines.size() - 1; i >= 0; --i) {
            ChatLine chatline = this.chatLines.get(i);
            this.setChatLine(chatline.getChatComponent(), chatline.getChatLineID(), chatline.getUpdatedCounter(), true);
        }
    }

    /**
     * Gets the list of messages previously sent through the chat GUI
     */
    public List<String> getSentMessages() {
        return Collections.unmodifiableList(this.sentMessages);
    }

    /**
     * Adds this string to the list of sent messages, for recall using the
     * up/down arrow keys
     */
    public void addToSentMessages(String message) {
        if (this.sentMessages.isEmpty() || !this.sentMessages.get(this.sentMessages.size() - 1).equals(message)) {
            this.sentMessages.add(message);
        }
    }

    /**
     * Resets the chat scroll (executed when the GUI is closed, among others)
     */
    public void resetScroll() {
        this.scrollPos = 0;
        this.isScrolled = false;
    }

    /**
     * Scrolls the chat by the given number of lines.
     */
    public void scroll(int amount) {
        this.scrollPos += amount;
        int j = this.drawnChatLines.size();

        if (this.scrollPos > j - this.getLineCount()) {
            this.scrollPos = j - this.getLineCount();
        }

        if (this.scrollPos <= 0) {
            this.scrollPos = 0;
            this.isScrolled = false;
        }
    }

    @SuppressWarnings("unchecked")
    public ITextComponent getChatComponent(int mouseX, int mouseY) {
        if (!this.getChatOpen()) {
            return null;
        } else {
            ScaledResolution scaledresolution = new ScaledResolution(this.mc);
            int k = scaledresolution.getScaleFactor();
            float f = this.getChatScale();
            int l = mouseX / k - 3;
            int i1 = mouseY / k - 27;
            l = MathHelper.floor(l / f);
            i1 = MathHelper.floor(i1 / f);

            if (l >= 0 && i1 >= 0) {
                int j1 = Math.min(this.getLineCount(), this.drawnChatLines.size());

                if (l <= MathHelper.floor(this.getChatWidth() / this.getChatScale()) && i1 < this.mc.fontRenderer.FONT_HEIGHT * j1 + j1) {
                    int k1 = i1 / this.mc.fontRenderer.FONT_HEIGHT + this.scrollPos;

                    if (k1 >= 0 && k1 < this.drawnChatLines.size()) {
                        ChatLine chatline = this.drawnChatLines.get(k1);
                        int l1 = 0;

                        for (Object iChatComponent : chatline.getChatComponent()) {
                            if (iChatComponent instanceof TextComponentString) {
                                l1 += this.mc.fontRenderer.getStringWidth(this.func_146235_b(((TextComponentString) iChatComponent).getText()));

                                if (l1 > l) {
                                    return (ITextComponent) iChatComponent;
                                }
                            }
                        }
                    }

                    return null;
                } else {
                    return null;
                }
            } else {
                return null;
            }
        }
    }

    /**
     * Returns true if the chat GUI is open
     */
    public boolean getChatOpen() {
        return this.mc.currentScreen instanceof ConsoleInputGui;
    }

    /**
     * finds and deletes a Chat line by ID
     */
    public void deleteChatLine(int id) {
        Iterator iterator = this.drawnChatLines.iterator();
        ChatLine chatline;

        while (iterator.hasNext()) {
            chatline = (ChatLine) iterator.next();

            if (chatline.getChatLineID() == id) {
                iterator.remove();
            }
        }

        iterator = this.chatLines.iterator();

        while (iterator.hasNext()) {
            chatline = (ChatLine) iterator.next();

            if (chatline.getChatLineID() == id) {
                iterator.remove();
                break;
            }
        }
    }

    public int getChatWidth() {
        return calculateChatboxWidth(this.mc.gameSettings.chatWidth);
    }

    public int getChatHeight() {
        return calculateChatboxHeight(this.getChatOpen() ? this.mc.gameSettings.chatHeightFocused : this.mc.gameSettings.chatHeightUnfocused);
    }

    public float getChatScale() {
        return this.mc.gameSettings.chatScale;
    }

    public int getLineCount() {
        return this.getChatHeight() / 9;
    }
}
