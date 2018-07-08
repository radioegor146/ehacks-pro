/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 */
package ehacks.mod.util;

import java.util.UUID;
import net.minecraft.entity.Entity;

public class Watcher {
    private final Entity entity;
    private final long time;
    public static int delay;

    public Watcher(Entity entity, long time) {
        this.entity = entity;
        this.time = time;
        delay = 250;
    }

    public boolean matches(Entity other, long now) {
        return other.getUniqueID() == this.entity.getUniqueID() && this.time > now - (long)delay && other.getClass() == this.entity.getClass();
    }
}

