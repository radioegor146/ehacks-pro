/*
 * Decompiled with CFR 0_128.
 */
package ehacks.mod.relationsystem;

import java.util.concurrent.CopyOnWriteArrayList;
import ehacks.mod.wrapper.Wrapper;

public class Friend {
    private static volatile Friend INSTANCE = new Friend();
    public static CopyOnWriteArrayList<String> friendList = new CopyOnWriteArrayList();

    public void addFriend(String string) {
        if (!friendList.contains(string)) {
            friendList.add(string);
            Wrapper.INSTANCE.addChatMessage("&9[&bCE Friend&9] &cPlayer &a" + string + " &chas &cbeen &cadded &cto &cyour &cfriends &clist.");
        } else {
            Wrapper.INSTANCE.addChatMessage("&9[&bCE Friend&9] &cPlayer &a" + string + " &cit &cis &calready &cyour &cfriend.");
        }
    }

    public void removeFriend(String string) {
        friendList.remove(string);
        Wrapper.INSTANCE.addChatMessage("&9[&bCE Friend&9] &cPlayer &a" + string + " &chas &cbeen &cremoved &cfrom &cyour &cfriends &clist.");
    }

    public boolean readFriend(String string) {
        if (friendList.contains(string)) {
            return true;
        }
        return false;
    }

    public static Friend instance() {
        return INSTANCE;
    }
}

