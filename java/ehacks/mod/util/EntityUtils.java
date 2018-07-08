/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 */
package ehacks.mod.util;

import ehacks.mod.util.Watcher;
import java.util.HashMap;
import java.util.UUID;
import net.minecraft.entity.Entity;

public class EntityUtils {
    private static HashMap<UUID, Watcher> events = new HashMap();

    public static Watcher getLastAffected(UUID id) {
        Watcher entity = events.get(id);
        if (entity != null) {
            return entity;
        }
        return null;
    }

    public static void setLastAffected(UUID id, Entity entity) {
        long now = System.currentTimeMillis();
        Watcher eventWatcher = new Watcher(entity, now);
        events.put(id, eventWatcher);
    }
}

