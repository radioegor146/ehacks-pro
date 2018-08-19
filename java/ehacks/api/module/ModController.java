package ehacks.api.module;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ModController {
    public static volatile ModController INSTANCE = new ModController();
    public List<Module> mods = new ArrayList();

    public void enable(Module mod) {
        this.mods.add(mod);
    }

    public void disable(Module mod) {
        this.mods.remove(mod);
    }
    
    public void sort() {
        Collections.sort(mods);
    }

    public Module call(Class clazz) {
        try {
            for (Module mod : this.mods) {
                if (mod.getClass() != clazz) continue;
                return mod;
            }
        }
        catch (Exception exception) {
            throw new IllegalStateException("Why you use this for a non-module class?");
        }
        return null;
    }
}

