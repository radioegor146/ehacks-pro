/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  net.minecraft.client.entity.EntityClientPlayerMP
 */
package ehacks.mod.relationsystem;

import ehacks.mod.wrapper.Wrapper;
import com.mojang.authlib.GameProfile;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.client.entity.EntityClientPlayerMP;

public class Enemy {
    private static volatile Enemy INSTANCE = new Enemy();
    public CopyOnWriteArrayList<String> enemyList = new CopyOnWriteArrayList();

    public void addEnemy(String string) {
        if (!this.enemyList.contains(string)) {
            this.enemyList.add(string);
        }
    }

    public void removeEnemy(String string) {
        this.enemyList.remove(string);
    }

    public boolean readEnemy(String string) {
        if (Wrapper.INSTANCE.player().getGameProfile().getName() == string && this.enemyList.contains(string)) {
            return true;
        }
        return false;
    }

    public static Enemy instance() {
        return INSTANCE;
    }
}

