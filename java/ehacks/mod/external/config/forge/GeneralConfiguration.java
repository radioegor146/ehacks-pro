package ehacks.mod.external.config.forge;

import java.io.File;
import net.minecraftforge.common.config.Configuration;
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
        this.configuration.addCustomCategoryComment("general", "General EHacks values. This change when you use commands but you can change them here too");
        this.apply();
        this.configuration.save();
    }

    private void apply() {

    }

    public static GeneralConfiguration instance() {
        return INSTANCE;
    }
}

