package ehacks.mod.gui.xraysettings;

import ehacks.mod.wrapper.Wrapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class XRayAddGui
        extends GuiScreen {

    String id;
    XRayGuiSlider colorR;
    XRayGuiSlider colorG;
    XRayGuiSlider colorB;
    XRayGuiSlider colorA;
    private GuiButton add;
    private GuiButton cancel;
    private GuiButton matterMeta;
    private GuiButton isEnabled;
    private int selectedIndex = -1;
    private int r = 128;
    private int g = 128;
    private int b = 128;
    private int a = 255;
    private boolean enabled = true;
    private boolean bmeta = false;
    private int meta;
    private int sliderpos;
    private GuiTextField searchbar;
    private ArrayList blocks = new ArrayList();

    public XRayAddGui(int r, int g, int b2, int a2, int meta, String id, boolean enabled, int index) {
        this();
        this.r = r;
        this.g = g;
        this.b = b2;
        this.a = a2;
        this.meta = meta;
        this.bmeta = meta != -1;
        this.id = id;
        this.enabled = enabled;
        this.selectedIndex = index;
    }

    public XRayAddGui() {
    }

    public XRayAddGui(XRayBlock xrayBlocks2, int index) {
        this(xrayBlocks2.r, xrayBlocks2.g, xrayBlocks2.b, xrayBlocks2.a, xrayBlocks2.meta, xrayBlocks2.id, xrayBlocks2.enabled, index);
    }

    @Override
    protected void actionPerformed(GuiButton par1GuiButton) {
        switch (par1GuiButton.id) {
            case 0:
                if (this.selectedIndex != -1) {
                    XRayBlock.blocks.remove(this.selectedIndex);
                }
                XRayBlock.blocks.add(new XRayBlock((int) (this.colorR.percent * 255.0f), (int) (this.colorG.percent * 255.0f), (int) (this.colorB.percent * 255.0f), (int) (this.colorA.percent * 255.0f), this.bmeta ? this.meta : -1, this.id, this.enabled));
                try {
                    XRayBlock.save();
                } catch (IOException var3) {
                    var3.printStackTrace();
                }
                Wrapper.INSTANCE.mc().displayGuiScreen((GuiScreen) new XRayGui());
                break;
            case 1:
                Wrapper.INSTANCE.mc().displayGuiScreen((GuiScreen) new XRayGui());
                break;
            case 6:
                this.enabled = !this.enabled;
                this.isEnabled.displayString = this.enabled ? "Enabled" : "Disabled";
                break;
            case 7:
                this.bmeta = !this.bmeta;
                this.matterMeta.displayString = this.bmeta ? "Meta-Check Enabled" : "Meta-Check Disabled";
                break;
            default:
                break;
        }
        super.actionPerformed(par1GuiButton);
    }

    @Override
    public void initGui() {
        super.initGui();
        this.add = new GuiButton(0, this.width / 2 - 42, this.height - 22, 40, 20, "Add");
        this.buttonList.add(this.add);
        this.cancel = new GuiButton(1, this.width / 2 + 42, this.height - 22, 40, 20, "Cancel");
        this.buttonList.add(this.cancel);
        this.colorR = new XRayGuiSlider(2, this.width - 160, this.height / 10 * 5, "Red-Value", (float) this.r / 255.0f);
        this.buttonList.add(this.colorR);
        this.colorG = new XRayGuiSlider(3, this.width - 160, this.height / 10 * 6, "Green-Value", (float) this.g / 255.0f);
        this.buttonList.add(this.colorG);
        this.colorB = new XRayGuiSlider(4, this.width - 160, this.height / 10 * 7, "Blue-Value", (float) this.b / 255.0f);
        this.buttonList.add(this.colorB);
        this.colorA = new XRayGuiSlider(5, this.width - 160, this.height / 10 * 8, "Alpha-Value", (float) this.a / 255.0f);
        this.buttonList.add(this.colorA);
        this.isEnabled = new GuiButton(6, this.width - 160, this.height / 10 * 4, 70, 20, this.enabled ? "Enabled" : "Disabled");
        this.buttonList.add(this.isEnabled);
        this.matterMeta = new GuiButton(7, this.width - 90, this.height / 10 * 4, 80, 20, this.bmeta ? "Meta-Check Enabled" : "Meta-Check Disabled");
        this.buttonList.add(this.matterMeta);
        Keyboard.enableRepeatEvents((boolean) true);
        this.searchbar = new GuiTextField(this.fontRendererObj, 60, 45, 120, this.fontRendererObj.FONT_HEIGHT);
        this.searchbar.setMaxStringLength(30);
        this.searchbar.setCanLoseFocus(false);
        this.searchbar.setFocused(true);
        this.searchbar.setTextColor(16777215);
        this.blocks.addAll(Block.blockRegistry.getKeys());
    }

    @Override
    public void drawScreen(int x, int y, float par3) {
        int si;
        super.drawScreen(x, y, par3);
        this.drawBackground(0);
        this.drawString(this.fontRendererObj, "Search for Blocks by their name ", 5, 10, 16777215);
        this.drawString(this.fontRendererObj, "or their ID and meta using @ (e.g. @12:0 or @12:1) ", 7, 20, 16777215);
        String text = this.searchbar.getText();
        if (text.startsWith("@")) {
            try {
                text = text.substring(1);
                String[] blockstodraw = text.split(":");
                int s = -1;
                if (blockstodraw.length > 1) {
                    s = Integer.parseInt(blockstodraw[1]);
                }
                if (blockstodraw.length > 0 && Block.blockRegistry.containsId(si = Integer.parseInt(blockstodraw[0]))) {
                    this.id = Block.blockRegistry.getNameForObject(Block.blockRegistry.getObjectById(si));
                    this.meta = s;
                    if (s != 1) {
                        this.bmeta = true;
                    }
                }
            } catch (Exception blockstodraw) {
                // empty catch block
            }
        }
        this.add.enabled = this.id != null;
        this.drawInfo();
        this.searchbar.drawTextBox();
        super.drawScreen(x, y, par3);
        ArrayList var13 = this.getItemStackList();
        int var14 = 9;
        for (si = 0; si < var13.size(); ++si) {
            int ni = si + this.sliderpos * var14;
            if (ni >= var13.size()) {
                continue;
            }
            ItemStack b2 = (ItemStack) var13.get(ni);
            if (si == var14 * 7) {
                break;
            }
            try {
                RenderHelper.enableGUIStandardItemLighting();
                XRayAddGui.drawRect((int) (10 + si % var14 * 20), (int) (60 + si / var14 * 20), (int) (10 + si % var14 * 20 + 16), (int) (60 + si / var14 * 20 + 16), (int) -2130706433);
                RenderHelper.disableStandardItemLighting();
                this.drawItem(b2, 10 + si % var14 * 20, 60 + si / var14 * 20, "");
            } catch (Exception exception) {
                // empty catch block
            }
        }
        RenderHelper.enableGUIStandardItemLighting();
        XRayAddGui.drawRect((int) (this.width / 3 * 2), (int) (this.height / 6), (int) (this.width - 30), (int) (this.height / 6 * 2), (int) ((((int) (this.colorA.percent * 255.0f) << 8 | (int) (this.colorR.percent * 255.0f)) << 8 | (int) (this.colorG.percent * 255.0f)) << 8 | (int) (this.colorB.percent * 255.0f)));
        GL11.glDisable((int) 2929);
        stringint var15 = this.getClickedBlock(x, y);
        if (var15 != null) {
            this.drawString(this.fontRendererObj, ((Block) Block.blockRegistry.getObject(var15.id)).getLocalizedName(), x - 5, y - 10, 16777215);
        }
        GL11.glEnable((int) 2929);
    }

    @Override
    public void updateScreen() {
        this.searchbar.updateCursorCounter();
    }

    private void drawInfo() {
        this.drawString(this.fontRendererObj, "Search", 15, 45, 16777215);
        this.drawString(this.fontRendererObj, this.id == null ? "No Block selected" : ((Block) Block.blockRegistry.getObject(this.id)).getLocalizedName(), this.width / 3 * 2 + 20, 20, 16777215);
    }

    private void drawItem(ItemStack itemstack, int x, int y, String name) {
        GL11.glColor3ub((byte) -1, (byte) -1, (byte) -1);
        GL11.glDisable((int) 2896);
        this.zLevel = 200.0f;
        GuiScreen.itemRender.zLevel = 200.0f;
        FontRenderer font = null;
        if (itemstack != null) {
            font = itemstack.getItem().getFontRenderer(itemstack);
        }
        if (font == null) {
            font = this.fontRendererObj;
        }
        GuiScreen.itemRender.renderItemAndEffectIntoGUI(font, this.mc.getTextureManager(), itemstack, x, y);
        GuiScreen.itemRender.renderItemOverlayIntoGUI(font, this.mc.getTextureManager(), itemstack, x, y, name);
        this.zLevel = 0.0f;
        GuiScreen.itemRender.zLevel = 0.0f;
        GL11.glEnable((int) 2896);
    }

    @Override
    protected void keyTyped(char par1, int par2) {
        this.searchbar.textboxKeyTyped(par1, par2);
        this.blocks.clear();
        Set s = Block.blockRegistry.getKeys();
        for (Object string : s) {
            String sb = this.searchbar.getText();
            Block b2 = (Block) Block.blockRegistry.getObject((String) string);
            if (!b2.getLocalizedName().toLowerCase().contains(sb.toLowerCase())) {
                continue;
            }
            this.blocks.add((String) string);
        }
        this.sliderpos = 0;
        super.keyTyped(par1, par2);
    }

    @Override
    protected void mouseClicked(int x, int y, int mouseButton) {
        stringint s;
        super.mouseClicked(x, y, mouseButton);
        this.searchbar.mouseClicked(x, y, mouseButton);
        if (mouseButton == 0 && (s = this.getClickedBlock(x, y)) != null) {
            this.id = s.id;
            this.meta = s.meta;
        }
    }

    @Override
    public void handleMouseInput() {
        super.handleMouseInput();
        int x = Mouse.getEventDWheel();
        ArrayList blockstodraw = this.getItemStackList();
        int xmax = blockstodraw.size() / 9;
        int xmin = 0;
        if (x < 0) {
            if (this.sliderpos < xmax) {
                ++this.sliderpos;
            }
        } else if (x > 0 && this.sliderpos > xmin) {
            --this.sliderpos;
        }
    }

    private ArrayList getItemStackList() {
        ArrayList blockstodraw = new ArrayList();
        for (int i = 0; i < this.blocks.size(); ++i) {
            Block b2 = (Block) Block.blockRegistry.getObject((String) this.blocks.get(i));
            b2.getSubBlocks(new ItemStack(b2).getItem(), (CreativeTabs) null, blockstodraw);
        }
        return blockstodraw;
    }

    private stringint getClickedBlock(int x, int y) {
        Block b2;
        int i;
        int index = 0;
        ArrayList z = new ArrayList();
        for (i = 0; i < this.blocks.size(); ++i) {
            b2 = (Block) Block.blockRegistry.getObject((String) this.blocks.get(i));
            b2.getSubBlocks(new ItemStack(b2).getItem(), (CreativeTabs) null, z);
        }
        for (i = 0; i < this.blocks.size(); ++i) {
            b2 = (Block) Block.blockRegistry.getObject((String) this.blocks.get(i));
            ArrayList blockstodraw = new ArrayList();
            b2.getSubBlocks(new ItemStack(b2).getItem(), (CreativeTabs) null, blockstodraw);
            for (int j = 0; j < blockstodraw.size(); ++j) {
                int var14;
                if ((index + j) % 9 > 9 || (index + j - this.sliderpos * 9) / 9 > 7 || index + j - this.sliderpos * 9 < 0) {
                    continue;
                }
                int c = (index + j) % 9;
                int v = (index + j - this.sliderpos * 9) / 9;
                if (x <= 10 + c * 20 || x >= 26 + c * 20 || y <= v * 20 + 60 || y >= v * 20 + 76) {
                    continue;
                }
                boolean smeta = false;
                try {
                    var14 = ((ItemStack) blockstodraw.get(j)).getItemDamage();
                } catch (Exception var13) {
                    var14 = -1;
                }
                return new stringint((String) this.blocks.get(i), var14);
            }
            index += blockstodraw.size();
        }
        return null;
    }

    private class stringint {

        public int meta;
        public String id;

        public stringint(String id, int meta) {
            this.id = id;
            this.meta = meta;
        }
    }

}
