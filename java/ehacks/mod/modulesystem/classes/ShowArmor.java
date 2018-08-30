package ehacks.mod.modulesystem.classes;

import ehacks.api.module.Module;
import ehacks.mod.wrapper.ModuleCategory;
import ehacks.mod.wrapper.Wrapper;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class ShowArmor
        extends Module {

    public ShowArmor() {
        super(ModuleCategory.RENDER);
    }

    @Override
    public String getName() {
        return "ShowArmor";
    }

    @Override
    public String getDescription() {
        return "Allows you to see armor of player or info of item";
    }

    @Override
    public void onGameOverlay(RenderGameOverlayEvent.Text event) {
        if (event.type == RenderGameOverlayEvent.ElementType.TEXT) {
            Entity entityHit = getMouseOver(event.partialTicks, 5000, false);
            if (entityHit != null && entityHit instanceof EntityPlayer) {
                EntityPlayer entity = (EntityPlayer) entityHit;
                int t = 0;
                for (int i = 3; i >= 0; i--) {
                    if (entity.inventory.armorInventory[i] == null) {
                        continue;
                    }
                    drawItemStack(entity.inventory.armorInventory[i], 4 + 8, 30 + t);
                    t += Math.max(drawHoveringText(getItemToolTip(entity.inventory.armorInventory[i]), 4 + 16 + 4 + 4, 34 + t, Wrapper.INSTANCE.fontRenderer()), 16) + 10;
                }
                if (entity.inventory.getCurrentItem() != null) {
                    drawItemStack(entity.inventory.getCurrentItem(), 4 + 8, 30 + t);
                    t += Math.max(drawHoveringText(getItemToolTip(entity.inventory.getCurrentItem()), 4 + 16 + 4 + 4, 34 + t, Wrapper.INSTANCE.fontRenderer()), 16) + 10;
                }
                return;
            }
            if (entityHit != null && entityHit instanceof EntityLiving) {
                EntityLiving entity = (EntityLiving) entityHit;
                int t = 0;
                for (int i = 4; i >= 0; i--) {
                    if (entity.getEquipmentInSlot(i) == null) {
                        continue;
                    }
                    drawItemStack(entity.getEquipmentInSlot(i), 4 + 8, 30 + t);
                    t += Math.max(drawHoveringText(getItemToolTip(entity.getEquipmentInSlot(i)), 4 + 16 + 4 + 4, 34 + t, Wrapper.INSTANCE.fontRenderer()), 16) + 10;
                }
                return;
            }
            if (entityHit != null && entityHit instanceof EntityItem && ((EntityItem) entityHit).getEntityItem() != null) {
                EntityItem entity = (EntityItem) entityHit;
                int t = 0;
                drawItemStack(entity.getEntityItem(), 4 + 8, 30 + t);
                t += Math.max(drawHoveringText(getItemToolTip(entity.getEntityItem()), 4 + 16 + 4 + 4, 34 + t, Wrapper.INSTANCE.fontRenderer()), 16) + 10;
            }
        }
    }

    public Entity getMouseOver(float partialTicks, double distance, boolean canBeCollidedWith) {
        Minecraft mc = Wrapper.INSTANCE.mc();
        Entity pointedEntity = null;
        MovingObjectPosition rayTrace = null;

        if (mc.renderViewEntity != null) {
            if (mc.theWorld != null) {
                Vec3 positionVec = mc.renderViewEntity.getPosition(partialTicks);
                Vec3 lookVec = mc.renderViewEntity.getLook(partialTicks);
                Vec3 posDistVec = positionVec.addVector(lookVec.xCoord * distance, lookVec.yCoord * distance, lookVec.zCoord * distance);
                double boxExpand = 1.0F;
                List<Entity> entities = mc.theWorld.getEntitiesWithinAABBExcludingEntity(mc.renderViewEntity, mc.renderViewEntity.boundingBox.addCoord(lookVec.xCoord * distance, lookVec.yCoord * distance, lookVec.zCoord * distance).expand(boxExpand, boxExpand, boxExpand));
                double mincalc = Double.MAX_VALUE;
                for (int i = 0; i < entities.size(); i++) {
                    Entity entity = entities.get(i);
                    if (!canBeCollidedWith || entity.canBeCollidedWith()) {
                        double borderSize = entity.getCollisionBorderSize();
                        AxisAlignedBB expEntityBox = entity.boundingBox.expand(borderSize, borderSize, borderSize);
                        MovingObjectPosition calculateInterceptPos = expEntityBox.calculateIntercept(positionVec, posDistVec);
                        if (calculateInterceptPos != null) {
                            double calcInterceptPosDist = positionVec.distanceTo(calculateInterceptPos.hitVec);
                            if (mincalc > calcInterceptPosDist)
                            {
                                mincalc = calcInterceptPosDist;
                                pointedEntity = entity;
                            }
                        }
                    }
                }
                if (pointedEntity != null) {
                    return pointedEntity;
                }
            }
        }

        return null;
    }

    private void drawPotionEffects(EntityLiving entity) {
        int i = 100;
        int j = 10;
        boolean flag = true;
        Collection collection = entity.getActivePotionEffects();
        if (!collection.isEmpty()) {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glDisable(GL11.GL_LIGHTING);
            int k = 33;

            if (collection.size() > 5) {
                k = 132 / (collection.size() - 1);
            }

            for (Iterator iterator = entity.getActivePotionEffects().iterator(); iterator.hasNext(); j += k) {
                PotionEffect potioneffect = (PotionEffect) iterator.next();
                Potion potion = Potion.potionTypes[potioneffect.getPotionID()];
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                Wrapper.INSTANCE.mc().getTextureManager().bindTexture(new ResourceLocation("textures/gui/container/inventory.png"));
                this.drawTexturedModalRect(i, j, 0, 166, 140, 32);

                if (potion.hasStatusIcon()) {
                    int l = potion.getStatusIconIndex();
                    this.drawTexturedModalRect(i + 6, j + 7, 0 + l % 8 * 18, 198 + l / 8 * 18, 18, 18);
                }

                potion.renderInventoryEffect(i, j, potioneffect, Wrapper.INSTANCE.mc());
                String s1 = I18n.format(potion.getName(), new Object[0]);

                s1 += " " + potioneffect.getAmplifier();

                Wrapper.INSTANCE.fontRenderer().drawStringWithShadow(s1, i + 10 + 18, j + 6, 16777215);
                String s = Potion.getDurationString(potioneffect);
                Wrapper.INSTANCE.fontRenderer().drawStringWithShadow(s, i + 10 + 18, j + 6 + 10, 8355711);
            }
        }
    }

    private void drawTexturedModalRect(int p_73729_1_, int p_73729_2_, int p_73729_3_, int p_73729_4_, int p_73729_5_, int p_73729_6_) {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((double) (p_73729_1_ + 0), (double) (p_73729_2_ + p_73729_6_), (double) 300f, (double) ((float) (p_73729_3_ + 0) * f), (double) ((float) (p_73729_4_ + p_73729_6_) * f1));
        tessellator.addVertexWithUV((double) (p_73729_1_ + p_73729_5_), (double) (p_73729_2_ + p_73729_6_), (double) 300f, (double) ((float) (p_73729_3_ + p_73729_5_) * f), (double) ((float) (p_73729_4_ + p_73729_6_) * f1));
        tessellator.addVertexWithUV((double) (p_73729_1_ + p_73729_5_), (double) (p_73729_2_ + 0), (double) 300f, (double) ((float) (p_73729_3_ + p_73729_5_) * f), (double) ((float) (p_73729_4_ + 0) * f1));
        tessellator.addVertexWithUV((double) (p_73729_1_ + 0), (double) (p_73729_2_ + 0), (double) 300f, (double) ((float) (p_73729_3_ + 0) * f), (double) ((float) (p_73729_4_ + 0) * f1));
        tessellator.draw();
    }

    private List<String> getItemToolTip(ItemStack itemStack) {
        if (itemStack == null) {
            return new ArrayList();
        }
        List<String> list = itemStack.getTooltip(Wrapper.INSTANCE.player(), true);

        for (int i = 0; i < list.size(); ++i) {
            if (i == 0) {
                list.set(i, itemStack.getRarity().rarityColor + (String) list.get(i));
            } else {
                list.set(i, "\u00a77" + (String) list.get(i));
            }
        }

        return list;
    }

    private int drawHoveringText(List text, int x, int y, FontRenderer font) {
        int t = 0;
        if (!text.isEmpty()) {
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            int k = 0;
            Iterator iterator = text.iterator();

            while (iterator.hasNext()) {
                String s = (String) iterator.next();
                int l = font.getStringWidth(s);

                if (l > k) {
                    k = l;
                }
            }

            int j2 = x;
            int k2 = y;
            int i1 = 8;

            if (text.size() > 1) {
                i1 += 2 + (text.size() - 1) * 10;
            }

            t = i1;

            RenderItem.getInstance().zLevel = 300.0F;
            int j1 = -267386864;
            this.drawGradientRect(j2 - 3, k2 - 4, j2 + k + 3, k2 - 3, j1, j1);
            this.drawGradientRect(j2 - 3, k2 + i1 + 3, j2 + k + 3, k2 + i1 + 4, j1, j1);
            this.drawGradientRect(j2 - 3, k2 - 3, j2 + k + 3, k2 + i1 + 3, j1, j1);
            this.drawGradientRect(j2 - 4, k2 - 3, j2 - 3, k2 + i1 + 3, j1, j1);
            this.drawGradientRect(j2 + k + 3, k2 - 3, j2 + k + 4, k2 + i1 + 3, j1, j1);
            int k1 = 1347420415;
            int l1 = (k1 & 16711422) >> 1 | k1 & -16777216;
            this.drawGradientRect(j2 - 3, k2 - 3 + 1, j2 - 3 + 1, k2 + i1 + 3 - 1, k1, l1);
            this.drawGradientRect(j2 + k + 2, k2 - 3 + 1, j2 + k + 3, k2 + i1 + 3 - 1, k1, l1);
            this.drawGradientRect(j2 - 3, k2 - 3, j2 + k + 3, k2 - 3 + 1, k1, k1);
            this.drawGradientRect(j2 - 3, k2 + i1 + 2, j2 + k + 3, k2 + i1 + 3, l1, l1);

            for (int i2 = 0; i2 < text.size(); ++i2) {
                String s1 = (String) text.get(i2);
                font.drawStringWithShadow(s1, j2, k2, -1);

                if (i2 == 0) {
                    k2 += 2;
                }

                k2 += 10;
            }

            RenderItem.getInstance().zLevel = 0.0F;
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        }
        return t;
    }

    private void drawGradientRect(int p_73733_1_, int p_73733_2_, int p_73733_3_, int p_73733_4_, int p_73733_5_, int p_73733_6_) {
        float f = (float) (p_73733_5_ >> 24 & 255) / 255.0F;
        float f1 = (float) (p_73733_5_ >> 16 & 255) / 255.0F;
        float f2 = (float) (p_73733_5_ >> 8 & 255) / 255.0F;
        float f3 = (float) (p_73733_5_ & 255) / 255.0F;
        float f4 = (float) (p_73733_6_ >> 24 & 255) / 255.0F;
        float f5 = (float) (p_73733_6_ >> 16 & 255) / 255.0F;
        float f6 = (float) (p_73733_6_ >> 8 & 255) / 255.0F;
        float f7 = (float) (p_73733_6_ & 255) / 255.0F;
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.setColorRGBA_F(f1, f2, f3, f);
        tessellator.addVertex((double) p_73733_3_, (double) p_73733_2_, (double) 300f);
        tessellator.addVertex((double) p_73733_1_, (double) p_73733_2_, (double) 300f);
        tessellator.setColorRGBA_F(f5, f6, f7, f4);
        tessellator.addVertex((double) p_73733_1_, (double) p_73733_4_, (double) 300f);
        tessellator.addVertex((double) p_73733_3_, (double) p_73733_4_, (double) 300f);
        tessellator.draw();
        GL11.glShadeModel(GL11.GL_FLAT);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }

    private void drawItemStack(ItemStack itemStack, int x, int y) {
        if (itemStack == null) {
            return;
        }
        RenderHelper.enableGUIStandardItemLighting();
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        RenderItem.getInstance().renderItemAndEffectIntoGUI(Wrapper.INSTANCE.mc().fontRenderer, Wrapper.INSTANCE.mc().renderEngine, itemStack, x - 8, y);
        RenderItem.getInstance().renderItemOverlayIntoGUI(Wrapper.INSTANCE.mc().fontRenderer, Wrapper.INSTANCE.mc().renderEngine, itemStack, x - 8, y);
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        RenderHelper.disableStandardItemLighting();
    }

    @Override
    public boolean canOnOnStart() {
        return true;
    }
}
