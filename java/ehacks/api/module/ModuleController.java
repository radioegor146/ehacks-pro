package ehacks.api.module;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ModuleController {

    public static volatile ModuleController INSTANCE = new ModuleController();
    public List<Module> modules = new ArrayList();

    public void enable(Module modue) {
        this.modules.add(modue);
    }

    public void disable(Module module) {
        this.modules.remove(module);
    }

    public void sort() {
        Collections.sort(modules);
    }

    public Module call(Class clazz) {
        try {
            for (Module mod : this.modules) {
                if (mod.getClass() != clazz) {
                    continue;
                }
                return mod;
            }
        } catch (Exception exception) {
            throw new IllegalStateException("Why you use this for a non-module class?");
        }
        return null;
    }
}
