package ehacks.api.module;

import ehacks.mod.wrapper.ModuleCategories;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public abstract class Mod implements Comparable {
    private String TPROT = "PRIORITY_1";
    
    protected String name = "unknown";
    protected String p = "ehacks:";
    protected String description = "unknown";
    protected int keybind = 0;
    protected boolean enabled;
    protected ModuleCategories category;

    public Mod(ModuleCategories category) {
        this.category = category;
    }

    public void setKeybinding(int key) {
        this.keybind = key;
    }

    public void setCategory(ModuleCategories category) {
        this.category = category;
    }

    public String getName() {
        return this.name;
    }

    public String getAlias() {
        return this.p + this.getName().toLowerCase().replaceAll(" ", "");
    }

    public int getKeybind() {
        return this.keybind;
    }

    public String getDescription() {
        return this.description;
    }

    public ModuleCategories getCategory() {
        return this.category;
    }

    public boolean isActive() {
        return this.enabled;
    }

    public void onAttackEntity() {
    }

    public void onPlayerUpdate() {
    }

    public void onWorldUpdate() {
    }

    public void onTick() {
    }

    public void onTicks() {
    }

    public void onWorldRender(RenderWorldLastEvent event) {
    }

    public void onEnableMod() {
    }

    public void onDisableMod() {
    }
    
    public void onMouse(MouseEvent event) {
    }
    
    public void onClick(PlayerInteractEvent event) {
    }
    
    public void onKeyBind() {
    }

    public void reset() {
        this.onEnableMod();
        this.onDisableMod();
    }

    public void on() {
        this.enabled = true;
        this.onEnableMod();
    }

    public void off() {
        this.enabled = false;
        this.onDisableMod();
    }

    public void toggle() {
        boolean bl = this.enabled = !this.enabled;
        if (this.isActive()) {
            this.onEnableMod();
        } else {
            this.onDisableMod();
        }
    }
    
    public ModStatus getModStatus() {
        return ModStatus.DEFAULT;
    }
    
    public String getModName() {
        return "Minecraft";
    }
    
    @Override
    public int compareTo(Object o) {
        if (o instanceof Mod)
        {
            int fc = this.getModName().compareTo(((Mod)o).getModName());
            if (fc == 0)
                return this.getName().compareTo(((Mod)o).getName());
            else
                return fc;
        }
        else
            return -1;
    }
    
    public boolean canOnOnStart() {
        return true;
    }
}

