package ehacks.mod.modulesystem.classes.mods.bibliocraft;

import ehacks.debugme.Debug;
import ehacks.mod.api.ModStatus;
import ehacks.mod.api.Module;
import ehacks.mod.modulesystem.classes.mods.bibliocraft.OnlineCraft.CraftingGuiInventoryContainer;
import ehacks.mod.util.GLUtils;
import ehacks.mod.util.InteropUtils;
import ehacks.mod.util.MinecraftGuiUtils;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.IIcon;
import org.lwjgl.opengl.GL11;

public class OnlineCraft
        extends Module {

    public OnlineCraft() {
        super(ModuleCategory.EHACKS);
    }

    @Override
    public String getName() {
        return "OnlineCraft";
    }

    @Override
    public String getDescription() {
        return "Allows you to craft without crafting table\nUsage:\n  Numpad0 - perform give";
    }

    @Override
    public void onModuleEnabled() {
        try {
            Class.forName("jds.bibliocraft.BiblioCraft");
            Wrapper.INSTANCE.mc().displayGuiScreen(new CraftingGuiInventory(this));
            this.off();
        } catch (Exception ex) {
            this.off();
        }
    }

    @Override
    public ModStatus getModStatus() {
        try {
            Class.forName("jds.bibliocraft.BiblioCraft");
            return ModStatus.WORKING;
        } catch (Exception e) {
            return ModStatus.NOTWORKING;
        }
    }

    private final boolean prevState = false;

    private NBTTagCompound nbtItem(ItemStack item, int slot) {
        NBTTagCompound itemTag = new NBTTagCompound();
        itemTag.setByte("Count", (byte) item.stackSize);
        itemTag.setByte("Slot", (byte) slot);
        itemTag.setShort("id", (short) Item.getIdFromItem(item.getItem()));
        itemTag.setShort("Damage", (short) item.getItemDamage());
        if (item.hasTagCompound()) {
            itemTag.setTag("tag", item.getTagCompound());
        }
        return itemTag;
    }

    @Override
    public String getModName() {
        return "BiblioCraft";
    }

    public class CraftingGuiInventoryContainer extends Container {

        public InventoryCrafting craftMatrix = new InventoryCrafting(this, 2, 2);
        public IInventory craftResult = new InventoryCraftResult();

        public CraftingGuiInventoryContainer(EntityPlayer basePlayer) {
            this.addSlotToContainer(new SlotCrafting(basePlayer, this.craftMatrix, this.craftResult, 0, -2000, -2000));
            for (int i = 0; i < 4; ++i) {
                this.addSlotToContainer(new Slot(this.craftMatrix, i, -2000, -2000));
            }
            for (int i = 0; i < 4; ++i) {
                final int k = i;
                this.addSlotToContainer(new Slot(basePlayer.inventory, basePlayer.inventory.getSizeInventory() - 1 - i, 8, 8 + i * 18) {
                    @Override
                    public int getSlotStackLimit() {
                        return 1;
                    }

                    @Override
                    public boolean isItemValid(ItemStack p_75214_1_) {
                        if (p_75214_1_ == null) {
                            return false;
                        }
                        return p_75214_1_.getItem().isValidArmor(p_75214_1_, k, Wrapper.INSTANCE.player());
                    }

                    @Override
                    public IIcon getBackgroundIconIndex() {
                        return ItemArmor.func_94602_b(k);
                    }
                });
            }
            for (int i = 0; i < 3; ++i) {
                for (int j = 0; j < 9; ++j) {
                    this.addSlotToContainer(new Slot(basePlayer.inventory, j + (i + 1) * 9, 8 + j * 18, 84 + i * 18));
                }
            }
            for (int i = 0; i < 9; ++i) {
                this.addSlotToContainer(new Slot(basePlayer.inventory, i, 8 + i * 18, 142));
            }
            this.onCraftMatrixChanged(this.craftMatrix);
        }

        @Override
        public boolean canInteractWith(EntityPlayer p_75145_1_) {
            return true;
        }
    }

    public class CraftingGuiInventory extends GuiContainer {

        private final ItemStack[] stacks = new ItemStack[10];

        private GuiButton craftButton;

        private final Module hackModule;

        public CraftingGuiInventory(Module hackModule) {
            super(new CraftingGuiInventoryContainer(Wrapper.INSTANCE.player()));
            this.hackModule = hackModule;
        }

        @Override
        public void initGui() {
            super.initGui();
            craftButton = new GuiButton(100, this.guiLeft + 106, this.guiTop + 50, 50, 20, "Craft");
            craftButton.enabled = false;
            this.buttonList.add(craftButton);
        }

        @Override
        protected void handleMouseClick(Slot slot, int slotId, int buttonId, int modeId) {
            if (slotId < 45) {
                super.handleMouseClick(slot, slotId, buttonId, modeId);
            } else {
                Wrapper.INSTANCE.player().openContainer.slotClick(slotId, buttonId, modeId, Wrapper.INSTANCE.player());
            }
        }

        @Override
        protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            int k = this.guiLeft;
            int l = this.guiTop;
            MinecraftGuiUtils.drawBack(k, l, this.xSize, this.ySize);
            k--;
            l--;
            for (int i = 0; i < 4; ++i) {
                MinecraftGuiUtils.drawSlotBack(k + 8, l + 8 + i * 18);
            }
            for (int i = 0; i < 3; ++i) {
                for (int j = 0; j < 9; ++j) {
                    MinecraftGuiUtils.drawSlotBack(k + 8 + j * 18, l + 84 + i * 18);
                }
            }
            for (int i = 0; i < 9; ++i) {
                MinecraftGuiUtils.drawSlotBack(k + 8 + i * 18, l + 142);
            }
            for (int i = 0; i < 3; ++i) {
                for (int j = 0; j < 3; ++j) {
                    MinecraftGuiUtils.drawSlotBack(k + 41 + i * 18, l + 17 + j * 18);
                }
            }
            GLUtils.drawRect(k + 102, l + 43 - 15, k + 102 + 14, l + 43 + 3 - 15, GLUtils.getColor(139, 139, 139));
            for (int i = 0; i < 8; i++) {
                GLUtils.drawRect(k + 102 + 14 + i, l + 43 + 3 - 9 + i - 15, k + 102 + 15 + i, l + 43 + 4 + 5 - i - 15, GLUtils.getColor(139, 139, 139));
            }
            MinecraftGuiUtils.drawSlotBack(k + 131, l + 17, 26, 26);
        }

        private void drawItemStack(ItemStack p_146982_1_, int p_146982_2_, int p_146982_3_, String p_146982_4_) {
            GL11.glTranslatef(0.0F, 0.0F, 32.0F);
            itemRender.zLevel = 200.0F;
            FontRenderer font = null;
            if (p_146982_1_ != null) {
                font = p_146982_1_.getItem().getFontRenderer(p_146982_1_);
            }
            if (font == null) {
                font = fontRendererObj;
            }
            itemRender.renderItemAndEffectIntoGUI(font, this.mc.getTextureManager(), p_146982_1_, p_146982_2_, p_146982_3_);
            itemRender.renderItemOverlayIntoGUI(font, this.mc.getTextureManager(), p_146982_1_, p_146982_2_, p_146982_3_, p_146982_4_);
            this.zLevel = 0.0F;
            itemRender.zLevel = 0.0F;
        }

        @Override
        public void drawScreen(int x, int y, float partialTicks) {
            craftButton.enabled = stacks[9] != null;
            super.drawScreen(x, y, partialTicks);
            GL11.glPushMatrix();
            RenderHelper.enableGUIStandardItemLighting();
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), stacks[i + j * 3], this.guiLeft + 41 + i * 18, this.guiTop + 17 + j * 18);
                    itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), stacks[i + j * 3], this.guiLeft + 41 + i * 18, this.guiTop + 17 + j * 18, "");
                }
            }
            if (stacks[9] != null) {
                itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), stacks[9], this.guiLeft + 135, this.guiTop + 21);
                itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), stacks[9], this.guiLeft + 135, this.guiTop + 21, stacks[9].stackSize > 1 ? String.valueOf(stacks[9].stackSize) : "");
            }
            RenderHelper.enableStandardItemLighting();
            GL11.glPopMatrix();
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            ItemStack itemStack = null;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (this.guiLeft + 40 + i * 18 <= x && this.guiTop + 16 + j * 18 <= y && this.guiLeft + 40 + i * 18 + 18 > x && this.guiTop + 16 + j * 18 + 18 > y) {
                        GL11.glColorMask(true, true, true, false);
                        this.drawGradientRect(this.guiLeft + 40 + i * 18 + 1, this.guiTop + 16 + j * 18 + 1, this.guiLeft + 40 + i * 18 + 16 + 1, this.guiTop + 16 + j * 18 + 16 + 1, -2130706433, -2130706433);
                        GL11.glColorMask(true, true, true, true);
                        itemStack = stacks[i + j * 3];
                    }
                }
            }
            craftButton.drawButton(mc, x, y);
            if (itemStack != null && Wrapper.INSTANCE.player().inventory.getItemStack() == null) {
                this.renderToolTip(itemStack, x, y);
            }
            ItemStack itemstack = Wrapper.INSTANCE.player().inventory.getItemStack();
            if (itemstack != null) {
                String s = "";
                if (itemstack.stackSize > 1) {
                    s = "" + itemstack.stackSize;
                }
                GL11.glPushMatrix();
                RenderHelper.enableGUIStandardItemLighting();
                this.drawItemStack(itemstack, x - 8, y - 8, s);
                GL11.glPopMatrix();
            }
        }

        @Override
        public void mouseClicked(int x, int y, int button) {
            if (button == 0) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (this.guiLeft + 40 + i * 18 <= x && this.guiTop + 16 + j * 18 <= y && this.guiLeft + 40 + i * 18 + 18 > x && this.guiTop + 16 + j * 18 + 18 > y) {
                            stacks[i + j * 3] = Wrapper.INSTANCE.player().inventory.getItemStack();
                            InventoryCrafting crafting = new InventoryCrafting(new CraftingGuiInventoryContainer(mc.thePlayer), 3, 3);
                            for (int ii = 0; ii < 3; ii++) {
                                for (int jj = 0; jj < 3; jj++) {
                                    crafting.setInventorySlotContents(ii + jj * 3, stacks[ii + jj * 3]);
                                }
                            }
                            stacks[9] = CraftingManager.getInstance().findMatchingRecipe(crafting, mc.theWorld);
                            return;
                        }
                    }
                }
            }
            if (button == 1) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (this.guiLeft + 40 + i * 18 <= x && this.guiTop + 16 + j * 18 <= y && this.guiLeft + 40 + i * 18 + 18 > x && this.guiTop + 16 + j * 18 + 18 > y) {
                            stacks[i + j * 3] = null;
                            InventoryCrafting crafting = new InventoryCrafting(new CraftingGuiInventoryContainer(mc.thePlayer), 3, 3);
                            for (int ii = 0; ii < 3; ii++) {
                                for (int jj = 0; jj < 3; jj++) {
                                    crafting.setInventorySlotContents(ii + jj * 3, stacks[ii + jj * 3]);
                                }
                            }
                            stacks[9] = CraftingManager.getInstance().findMatchingRecipe(crafting, mc.theWorld);
                            return;
                        }
                    }
                }
            }
            super.mouseClicked(x, y, button);
        }

        @Override
        public void actionPerformed(GuiButton button) {
            if (button == craftButton) {
                try {
                    ItemStack book = new ItemStack((Item) (Class.forName("jds.bibliocraft.items.ItemLoader").getField("bookRecipe").get(null)));
                    book.setTagCompound(new NBTTagCompound());
                    NBTTagList tList = new NBTTagList();
                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 3; j++) {
                            if (stacks[i + j * 3] != null) {
                                NBTTagCompound itemTag = new NBTTagCompound();
                                itemTag.setByte("Slot", (byte) (i + j * 3));
                                stacks[i + j * 3].writeToNBT(itemTag);
                                tList.appendTag(itemTag);
                            }
                        }
                    }
                    book.setTagInfo("grid", tList);
                    NBTTagCompound itemTag = new NBTTagCompound();
                    stacks[9].writeToNBT(itemTag);
                    book.setTagInfo("result", itemTag);
                    Debug.sendProxyPacket("BiblioRecipeCraft", new Object[]{book});
                    InteropUtils.log("Crafted", hackModule);
                } catch (Exception e) {
                    InteropUtils.log("Can't craft", hackModule);
                }
            }
        }
    }
}
