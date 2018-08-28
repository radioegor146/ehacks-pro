package ehacks.mod.logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModLogger {

    private final Logger log = LogManager.getLogger();
    private final String pre = "[EHacks] ";

    public ModLogger(String s) {
    }

    public void info(String s) {
        this.log.info(this.pre + s);
    }

    public void warning(String s) {
        this.log.warn(this.pre + s);
    }
}
