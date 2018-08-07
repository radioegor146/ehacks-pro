package ehacks.mod.util;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.ResourceLocation;
import ehacks.mod.modulesystem.classes.NameProtect;
import ehacks.mod.wrapper.Wrapper;

public class FontRendererUtils
extends FontRenderer {
    public FontRendererUtils(GameSettings p_i1035_1_, ResourceLocation p_i1035_2_, TextureManager p_i1035_3_, boolean p_i1035_4_) {
        super(p_i1035_1_, p_i1035_2_, p_i1035_3_, p_i1035_4_);
    }

    public int drawString(String p_85187_1_, int p_85187_2_, int p_85187_3_, int p_85187_4_, boolean p_85187_5_) {
        return super.drawString(p_85187_1_.replace(Wrapper.INSTANCE.mc().getSession().getUsername(), NameProtect.isActive ? "NameProtected" : Wrapper.INSTANCE.mc().getSession().getUsername()), p_85187_2_, p_85187_3_, p_85187_4_, p_85187_5_);
    }
}

