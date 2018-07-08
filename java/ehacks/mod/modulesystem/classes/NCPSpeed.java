/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityClientPlayerMP
 *  net.minecraft.client.multiplayer.WorldClient
 *  net.minecraft.client.settings.GameSettings
 *  net.minecraft.client.settings.KeyBinding
 *  net.minecraft.entity.Entity
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemFood
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.AxisAlignedBB
 *  net.minecraft.util.Timer
 */
package ehacks.mod.modulesystem.classes;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Timer;
import ehacks.api.module.Mod;
import ehacks.mod.util.TimerUtils;
import ehacks.mod.wrapper.ModuleCategories;
import ehacks.mod.wrapper.Wrapper;

public class NCPSpeed
extends Mod {
    private double motionSpeed = 3.14;
    private float timerSpeed = 1.1f;
    private boolean iceSpeed = true;
    private float ground = 0.0f;
    private int motionDelay;
    private boolean canStep;

    public NCPSpeed() {
        super(ModuleCategories.NOCHEATPLUS);
    }

    @Override
    public String getName() {
        return "Speed";
    }

    @Override
    public void onTicks() {
        boolean using = Wrapper.INSTANCE.player().isUsingItem() && Wrapper.INSTANCE.player().getCurrentEquippedItem().getItem() instanceof ItemFood;
        double speed = this.motionSpeed;
        double slow = 1.458;
        double offset = 4.7;
        float timer = Float.valueOf(this.timerSpeed).floatValue();
        boolean shouldOffset = true;
        this.canStep = false;
        for (Object o : Wrapper.INSTANCE.world().getCollidingBoundingBoxes((Entity)Wrapper.INSTANCE.player(), Wrapper.INSTANCE.player().boundingBox.copy().offset(Wrapper.INSTANCE.player().motionX / offset, 0.0, Wrapper.INSTANCE.player().motionZ / offset))) {
            if (o == null || !(o instanceof AxisAlignedBB)) continue;
            shouldOffset = false;
            break;
        }
        if (Wrapper.INSTANCE.mc().gameSettings.keyBindForward.isPressed() || Wrapper.INSTANCE.mc().gameSettings.keyBindBack.isPressed() || Wrapper.INSTANCE.mc().gameSettings.keyBindLeft.isPressed() || Wrapper.INSTANCE.mc().gameSettings.keyBindRight.isPressed()) {
            if (Wrapper.INSTANCE.player().onGround && this.ground < 1.0f) {
                this.ground += 0.2f;
            }
            if (!Wrapper.INSTANCE.player().onGround) {
                this.ground = 0.0f;
            }
            if (this.ground == 1.0f) {
                if (!Wrapper.INSTANCE.player().isSprinting()) {
                    offset += 0.8;
                }
                if (Wrapper.INSTANCE.player().moveStrafing != 0.0f) {
                    speed -= 0.1;
                    offset += 0.5;
                }
                if (Wrapper.INSTANCE.player().isInWater()) {
                    speed -= 0.1;
                }
                ++this.motionDelay;
                if (this.motionDelay == 1) {
                    TimerUtils.getTimer().timerSpeed = timer;
                    Wrapper.INSTANCE.player().motionX *= speed;
                    Wrapper.INSTANCE.player().motionZ *= speed;
                    this.canStep = false;
                } else if (this.motionDelay == 2) {
                    Wrapper.INSTANCE.player().motionX /= slow;
                    Wrapper.INSTANCE.player().motionZ /= slow;
                    this.canStep = true;
                } else if (this.motionDelay == 3) {
                    if ((double)timer > 1.05) {
                        TimerUtils.getTimer().timerSpeed = 1.05f;
                    }
                    this.canStep = true;
                } else if (this.motionDelay == 4) {
                    if (shouldOffset) {
                        Wrapper.INSTANCE.player().setPosition(Wrapper.INSTANCE.player().posX + Wrapper.INSTANCE.player().motionX / offset, Wrapper.INSTANCE.player().posY, Wrapper.INSTANCE.player().posZ + Wrapper.INSTANCE.player().motionZ / offset);
                        this.canStep = false;
                    }
                    this.motionDelay = 0;
                }
            }
        }
    }
}

