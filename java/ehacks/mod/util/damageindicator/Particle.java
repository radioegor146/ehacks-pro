package ehacks.mod.util.damageindicator;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ehacks.mod.wrapper.Events;
import ehacks.mod.wrapper.Wrapper;
import java.awt.Color;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

@SideOnly(value = Side.CLIENT)
public class Particle
        extends EntityFX {

    private String critical = "Critical!";
    public boolean criticalhit = false;
    public static DynamicTexture texID;
    public int Damage;
    public int curTexID;
    boolean heal = false;
    boolean grow = true;
    float ul;
    float ur;
    float vl;
    float vr;
    float locX;
    float locY;
    float locZ;
    float lastPar2;
    float red;
    float green;
    float blue;
    float alpha;
    public boolean shouldOnTop = false;
    public static boolean isOptifinePresent;

    public Particle(World par1World, double par2, double par4, double par6, double par8, double par10, double par12) {
        this(par1World, par2, par4, par6, par8, par10, par12, 0);
        this.criticalhit = true;
        this.particleGravity = -0.05f;
    }

    public Particle(World par1World, double par2, double par4, double par6, double par8, double par10, double par12, int damage) {
        super(par1World, par2, par4, par6, par8, par10, par12);
        this.Damage = damage;
        this.setSize(0.2f, 0.2f);
        this.yOffset = this.height * 1.1f;
        this.setPosition(par2, par4, par6);
        this.motionX = par8;
        this.motionY = par10;
        this.motionZ = par12;
        float var15 = MathHelper.sqrt_double((double) (this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ));
        this.motionX = this.motionX / (double) var15 * 0.12;
        this.motionY = this.motionY / (double) var15 * 0.12;
        this.motionZ = this.motionZ / (double) var15 * 0.12;
        this.particleTextureJitterX = 1.5f;
        this.particleTextureJitterY = 1.5f;
        this.particleGravity = 0.8f;
        this.particleScale = 3f;
        this.particleMaxAge = 12;
        this.particleAge = 0;
        if (this.Damage < 0) {
            this.heal = true;
            this.Damage = Math.abs(this.Damage);
        }
        try {
            int baseColor = this.heal ? 65280 : 16755200;
            this.red = (float) (baseColor >> 16 & 255) / 255.0f;
            this.green = (float) (baseColor >> 8 & 255) / 255.0f;
            this.blue = (float) (baseColor & 255) / 255.0f;
            this.alpha = 0.9947f;
            this.ul = ((float) this.Damage - (float) MathHelper.floor_float((float) ((float) this.Damage / 16.0f)) * 16.0f) % 16.0f / 16.0f;
            this.ur = this.ul + 0.0624375f;
            this.vl = (float) MathHelper.floor_float((float) ((float) this.Damage / 16.0f)) * 16.0f / 16.0f / 16.0f;
            this.vr = this.vl + 0.0624375f;
        } catch (Throwable ex) {
            // empty catch block
        }
    }

    @SideOnly(value = Side.CLIENT)
    @Override
    public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7) {
        if (!Events.cheatEnabled) {
            return;
        }
        this.rotationYaw = -Wrapper.INSTANCE.player().rotationYaw;
        this.rotationPitch = Wrapper.INSTANCE.player().rotationPitch;
        float size = 0.1f * this.particleScale;
        try {
            this.locX = (float) (this.prevPosX + (this.posX - this.prevPosX) * (double) par2 - interpPosX);
            this.locY = (float) (this.prevPosY + (this.posY - this.prevPosY) * (double) par2 - interpPosY);
            this.locZ = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * (double) par2 - interpPosZ);
            par3 *= size;
            par4 *= size;
            par5 *= size;
            par6 *= size;
            par7 *= size;
        } catch (Throwable ex) {
            // empty catch block
        }
        GL11.glPushMatrix();
        if (this.shouldOnTop) {
            GL11.glDepthFunc((int) 519);
        } else {
            GL11.glDepthFunc((int) 515);
        }
        GL11.glTranslatef((float) this.locX, (float) this.locY, (float) this.locZ);
        GL11.glRotatef((float) this.rotationYaw, (float) 0.0f, (float) 1.0f, (float) 0.0f);
        GL11.glRotatef((float) this.rotationPitch, (float) 1.0f, (float) 0.0f, (float) 0.0f);
        GL11.glScalef((float) -1.0f, (float) -1.0f, (float) 1.0f);
        GL11.glScaled((double) ((double) this.particleScale * 0.008), (double) ((double) this.particleScale * 0.008), (double) ((double) this.particleScale * 0.008));
        if (this.criticalhit) {
            GL11.glScaled((double) 0.5, (double) 0.5, (double) 0.5);
        }
        FontRenderer fontRenderer = Wrapper.INSTANCE.fontRenderer();
        OpenGlHelper.setLightmapTextureCoords((int) OpenGlHelper.lightmapTexUnit, (float) 240.0f, (float) 0.003662109f);
        GL11.glEnable((int) 3553);
        GL11.glDisable((int) 3042);
        GL11.glDepthMask((boolean) true);
        GL11.glColor4f((float) 1.0f, (float) 1.0f, (float) 1.0f, (float) 1.0f);
        GL11.glEnable((int) 3553);
        GL11.glEnable((int) 2929);
        GL11.glDisable((int) 2896);
        GL11.glBlendFunc((int) 770, (int) 771);
        GL11.glEnable((int) 3042);
        GL11.glEnable((int) 3008);
        GL11.glColor4f((float) 1.0f, (float) 1.0f, (float) 1.0f, (float) 1.0f);
        if (this.criticalhit) {
            fontRenderer.drawString(this.critical, -MathHelper.floor_float((float) ((float) fontRenderer.getStringWidth(this.critical) / 2.0f)) + 1, -MathHelper.floor_float((float) ((float) fontRenderer.FONT_HEIGHT / 2.0f)) + 1, 0);
            fontRenderer.drawString(this.critical, -MathHelper.floor_float((float) ((float) fontRenderer.getStringWidth(this.critical) / 2.0f)), -MathHelper.floor_float((float) ((float) fontRenderer.FONT_HEIGHT / 2.0f)), -7600622);
        } else if (!this.criticalhit) {
            int color = this.heal ? 65280 : 16755200;
            Color c_Color = new Color(color);
            c_Color = new Color((float) c_Color.getRed() / 5.0f / 255.0f, (float) c_Color.getGreen() / 5.0f / 255.0f, (float) c_Color.getBlue() / 5.0f / 255.0f);
            fontRenderer.drawString(String.valueOf(this.Damage), -MathHelper.floor_float((float) ((float) fontRenderer.getStringWidth("" + this.Damage + "") / 2.0f)) + 1, -MathHelper.floor_float((float) ((float) fontRenderer.FONT_HEIGHT / 2.0f)) + 1, c_Color.getRGB());
            fontRenderer.drawString(String.valueOf(this.Damage), -MathHelper.floor_float((float) ((float) fontRenderer.getStringWidth("" + this.Damage + "") / 2.0f)), -MathHelper.floor_float((float) ((float) fontRenderer.FONT_HEIGHT / 2.0f)), color);
        }
        GL11.glColor4f((float) 1.0f, (float) 1.0f, (float) 1.0f, (float) 1.0f);
        GL11.glDepthFunc((int) 515);
        GL11.glPopMatrix();
        if (this.grow) {
            this.particleScale *= 1.08f;
            if ((double) this.particleScale > 9.0) {
                this.grow = false;
            }
        } else {
            this.particleScale *= 0.96f;
        }
    }

    @Override
    public int getFXLayer() {
        return 3;
    }

    static {
        isOptifinePresent = false;
    }
}
