/*
 * Decompiled with CFR 0_128.
 */
package ehacks.api.module;

import ehacks.api.module.APICEMod;
import ehacks.mod.gui.reeszrbteam.element.YAWWindow;
import ehacks.mod.logger.ModLogger;
import ehacks.mod.main.Main;
import ehacks.mod.wrapper.ModuleCategories;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public abstract class Mod {
    protected String name = "unknown";
    protected String p = "ehacks:";
    protected String description = "unknown";
    protected int keybind = 0;
    protected boolean enabled;
    protected ModuleCategories category;
    public YAWWindow window = null;

    public Mod(ModuleCategories category) {
        this.category = category;
        Main.INSTANCE.logger.info("Loaded: " + this.getName() + " (" + this.getAlias() + ") Category: " + (Object)((Object)this.getCategory()));
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

    public void onWorldRender() {
    }

    public void onEnableMod() {
    }

    public void onDisableMod() {
    }
    
    public void onMouse(MouseEvent event) {
    }
    
    public void onClick(PlayerInteractEvent event) {
    }

    public void reset() {
        this.onEnableMod();
        this.onDisableMod();
    }

    public void on() {
        this.enabled = true;
        this.onEnableMod();
        if (this.window != null)
            this.window.setOpen(true);
    }

    public void off() {
        this.enabled = false;
        this.onDisableMod();
        if (this.window != null)
            this.window.setOpen(false);
    }

    public void toggle() {
        boolean bl = this.enabled = !this.enabled;
        if (this.isActive()) {
            this.onEnableMod();
            if (this.window != null)
                this.window.setOpen(true);
        } else {
            this.onDisableMod();
            if (this.window != null)
                this.window.setOpen(false);
        }
    }
}

