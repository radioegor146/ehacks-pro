package ehacks.mod.modulesystem.classes;

import ehacks.api.module.Module;
import ehacks.mod.util.GLUtils;
import ehacks.mod.util.InteropUtils;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import java.util.ArrayList;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.MouseEvent;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class ShowContainer
        extends Module {

    public ShowContainer() {
        super(ModuleCategory.EHACKS);
    }

    @Override
    public String getName() {
        return "ShowContainer";
    }

    @Override
    public String getDescription() {
        return "Fake destroyer";
    }

    private boolean prevState = false;

    @Override
    public void onMouse(MouseEvent event) {
        try {
            boolean nowState = Mouse.isButtonDown(1);
            MovingObjectPosition position = Wrapper.INSTANCE.mc().objectMouseOver;
            if (position.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && !prevState && nowState) {
                TileEntity tileEntity = Wrapper.INSTANCE.world().getTileEntity(position.blockX, position.blockY, position.blockZ);
                if (tileEntity != null && tileEntity instanceof IInventory) {
                    ItemStack[] stacks = new ItemStack[0];
                    Wrapper.INSTANCE.mc().displayGuiScreen(new ShowContainerGui(new ShowContainerContainer(stacks, tileEntity.getClass().getSimpleName())));
                    if (event.isCancelable()) {
                        event.setCanceled(true);
                    }
                } else {
                    InteropUtils.log("Not a container", this);
                }
            }
            prevState = nowState;
        } catch (Exception e) {

        }
    }

    private class ShowContainerGui extends GuiContainer {

        private final ShowContainerContainer container;
        private GuiButton buttonLeft;
        private GuiButton buttonRight;

        public ShowContainerGui(ShowContainerContainer container) {
            super(container);
            this.container = container;
            this.xSize = 256;
            this.ySize = 256;

        }

        @Override
        protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
            Wrapper.INSTANCE.mc().renderEngine.bindTexture(new ResourceLocation("ehacks", "textures/gui/container.png"));
            GL11.glColor4f((float) 1.0f, (float) 1.0f, (float) 1.0f, (float) 1.0f);
            GL11.glEnable((int) 3042);
            int startX = (this.width - this.xSize) / 2;
            int startY = (this.height - this.ySize) / 2;
            this.drawTexturedModalRect(startX, startY, 0, 0, this.xSize, this.ySize);
            GL11.glDisable((int) 3042);
            int x = 0;
            int y = 0;
            for (int i = 0; i < 143; i++) {
                if (i >= container.slots.get(container.currentPage).size()) {
                    GLUtils.drawRect(startX + 11 + x * 18, startY + 17 + y * 18, startX + 11 + x * 18 + 18, startY + 17 + y * 18 + 18, GLUtils.getColor(198, 198, 198));
                }
                x++;
                y += x / 13;
                x %= 13;
            }
        }

        @Override
        protected void drawGuiContainerForegroundLayer(int p1, int p2) {
            Wrapper.INSTANCE.fontRenderer().drawString(container.containerName + " - " + String.valueOf(container.inventorySlots.size()) + " slots", 12, 6, GLUtils.getColor(64, 64, 64));
            Wrapper.INSTANCE.fontRenderer().drawString("Page " + String.valueOf(container.currentPage + 1), 128 - Wrapper.INSTANCE.fontRenderer().getStringWidth("Page " + String.valueOf(container.currentPage + 1)) / 2, 230, GLUtils.getColor(64, 64, 64));
        }

        @Override
        public void initGui() {
            super.initGui();
            int startX = (this.width - this.xSize) / 2;
            int startY = (this.height - this.ySize) / 2;
            buttonLeft = new GuiButton(1, startX + 20, startY + 224, 20, 20, "<");
            buttonLeft.enabled = container.currentPage > 0;
            this.buttonList.add(buttonLeft);
            buttonRight = new GuiButton(2, startX + 216, startY + 224, 20, 20, ">");
            buttonRight.enabled = container.currentPage < (container.slots.size() - 1);
            this.buttonList.add(buttonRight);
        }

        @Override
        protected void actionPerformed(GuiButton b) {
            if (b.id == 1) {
                container.setPage(container.currentPage - 1);
                buttonLeft.enabled = container.currentPage > 0;
                buttonRight.enabled = container.currentPage < (container.slots.size() - 1);
            }
            if (b.id == 2) {
                container.setPage(container.currentPage + 1);
                buttonLeft.enabled = container.currentPage > 0;
                buttonRight.enabled = container.currentPage < (container.slots.size() - 1);
            }
        }

        @Override
        protected void handleMouseClick(Slot p_146984_1_, int p_146984_2_, int p_146984_3_, int p_146984_4_) {

        }

    }

    private class ShowContainerContainer extends Container {

        public int currentPage = 0;

        public ItemStack[] inventory;

        public ArrayList<ArrayList<Slot>> slots = new ArrayList();

        public String containerName = "";

        public ShowContainerContainer(ItemStack[] inventory, String containerName) {
            this.containerName = containerName;
            this.inventory = inventory;
            int x = 0;
            int y = 0;
            int page = 0;
            for (int i = 0; i < inventory.length; i++) {
                if (slots.size() == page) {
                    slots.add(new ArrayList());
                }
                Slot slot = new ShowContainerSlot(inventory[i], i, page == currentPage ? 12 + x * 18 : -2000, page == currentPage ? 18 + y * 18 : -2000);
                slots.get(page).add(slot);
                this.addSlotToContainer(slot);
                this.putStackInSlot(i, inventory[i]);
                x++;
                y += x / 13;
                x %= 13;
                page += y / 11;
                y %= 11;
            }
            if (slots.isEmpty()) {
                slots.add(new ArrayList());
            }
        }

        @Override
        public void putStackInSlot(int p_75141_1_, ItemStack p_75141_2_) {
        }

        public void setPage(int pageId) {
            if (pageId < 0 || pageId >= slots.size()) {
                return;
            }
            for (Slot s : slots.get(currentPage)) {
                s.xDisplayPosition = -2000;
                s.yDisplayPosition = -2000;
            }
            currentPage = pageId;
            int x = 0;
            int y = 0;
            for (int i = 0; i < slots.get(currentPage).size(); i++) {
                slots.get(currentPage).get(i).xDisplayPosition = 12 + x * 18;
                slots.get(currentPage).get(i).yDisplayPosition = 18 + y * 18;
                x++;
                y += x / 13;
                x %= 13;
            }
        }

        @Override
        public boolean canInteractWith(EntityPlayer p_75145_1_) {
            return true;
        }

    }

    private class ShowContainerSlot extends Slot {

        private final ItemStack is;

        public ShowContainerSlot(ItemStack is, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_) {
            super(null, p_i1824_2_, p_i1824_3_, p_i1824_4_);
            this.is = is;
        }

        public boolean isValidItem(ItemStack is) {
            return true;
        }

        @Override
        public ItemStack getStack() {
            return is;
        }

        @Override
        public void putStack(ItemStack p_75215_1_) {

        }

        @Override
        public void onSlotChanged() {

        }

        @Override
        public int getSlotStackLimit() {
            return is.getMaxStackSize();
        }

        /**
         * Decrease the size of the stack in slot (first int arg) by the amount
         * of the second int arg. Returns the new stack.
         */
        @Override
        public ItemStack decrStackSize(int p_75209_1_) {
            return is;
        }

        @Override
        public boolean isSlotInInventory(IInventory p_75217_1_, int p_75217_2_) {
            return true;
        }
    }
}
