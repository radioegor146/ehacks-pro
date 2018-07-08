/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraftforge.common.config.Configuration
 *  net.minecraftforge.common.config.Property
 */
package ehacks.mod.external.config.forge;

import java.io.File;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import ehacks.mod.commands.ACommandAuraRange;
import ehacks.mod.commands.ACommandSpeedValue;
import ehacks.mod.commands.ACommandStepHeight;
import ehacks.mod.wrapper.Wrapper;

public class GeneralConfiguration {
    private static volatile GeneralConfiguration INSTANCE = new GeneralConfiguration();
    public Configuration configuration;
    public float flySpeedValue;
    public float stepHeightValue;
    public float speedValue;
    public float aurarange;
    public float nukerRange;
    public float nukerSpeed;
    public int bfrValue;

    public GeneralConfiguration() {
        File path = new File(Wrapper.INSTANCE.mc().mcDataDir, "/config/ehacks/genconfig.cfg");
        this.configuration = new Configuration(path);
        this.configuration.load();
        this.configuration.addCustomCategoryComment("general", "General Cheating Essentials values. This change when you use commands but you can change them here too");
        Property stepHeight = this.configuration.get("general", "stepHeight", (double)ACommandStepHeight.STEP_HEIGHT_VALUE);
        Property speed = this.configuration.get("general", "speedValue", (double)ACommandSpeedValue.SPEED_VALUE);
        Property AuraRange = this.configuration.get("general", "AuraRange", (double)ACommandAuraRange.aurarange);
        stepHeight.comment = "Player step height when <Step> module is enabled.";
        speed.comment = "Player speed when <Speed> module is enabled.";
        AuraRange.comment = "KillAura fight range when <KillAura/MobAura/Forcefield> module is enabled.";
        this.stepHeightValue = (float)stepHeight.getDouble((double)ACommandStepHeight.STEP_HEIGHT_VALUE);
        this.speedValue = (float)speed.getDouble((double)ACommandSpeedValue.SPEED_VALUE);
        this.aurarange = (float)AuraRange.getDouble((double)ACommandAuraRange.aurarange);
        this.apply();
        this.configuration.save();
    }

    private void apply() {
        ACommandStepHeight.STEP_HEIGHT_VALUE = this.stepHeightValue;
        ACommandSpeedValue.SPEED_VALUE = this.speedValue;
        ACommandAuraRange.aurarange = this.aurarange;
    }

    public static GeneralConfiguration instance() {
        return INSTANCE;
    }
}

