package ehacks.mod.util.nbtedit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class NBTTree {

    private final NBTTagCompound baseTag;
    private Node<NamedNBT> root;
    public static final NBTNodeSorter SORTER = new NBTNodeSorter();

    public NBTTree(NBTTagCompound tag) {
        this.baseTag = tag;
        this.construct();
    }

    public Node<NamedNBT> getRoot() {
        return this.root;
    }

    public boolean canDelete(Node<NamedNBT> node) {
        return node != this.root;
    }

    public boolean delete(Node<NamedNBT> node) {
        if (node == null || node == this.root) {
            return false;
        }
        return this.deleteNode(node, this.root);
    }

    private boolean deleteNode(Node<NamedNBT> toDelete, Node<NamedNBT> cur) {
        Iterator<Node<NamedNBT>> it = cur.getChildren().iterator();
        while (it.hasNext()) {
            Node<NamedNBT> child = it.next();
            if (child == toDelete) {
                it.remove();
                return true;
            }
            boolean flag = this.deleteNode(toDelete, child);
            if (!flag) {
                continue;
            }
            return true;
        }
        return false;
    }

    private void construct() {
        this.root = new Node<NamedNBT>(new NamedNBT("ROOT", (NBTBase) ((NBTTagCompound) this.baseTag.copy())));
        this.addChildrenToTree(this.root);
        this.sort(this.root);
    }

    public void sort(Node<NamedNBT> node) {
        Collections.sort(node.getChildren(), SORTER);
        for (Node<NamedNBT> c : node.getChildren()) {
            this.sort(c);
        }
    }

    public void addChildrenToTree(Node<NamedNBT> parent) {
        block3:
        {
            NBTBase tag;
            block2:
            {
                tag = parent.getObject().getNBT();
                if (!(tag instanceof NBTTagCompound)) {
                    break block2;
                }
                Map<String, NBTBase> map = NBTHelper.getMap((NBTTagCompound) tag);
                for (Map.Entry<String, NBTBase> entry : map.entrySet()) {
                    NBTBase base = entry.getValue();
                    Node<NamedNBT> child = new Node<NamedNBT>(parent, new NamedNBT(entry.getKey(), base));
                    parent.addChild(child);
                    this.addChildrenToTree(child);
                }
                break block3;
            }
            if (!(tag instanceof NBTTagList)) {
                break block3;
            }
            NBTTagList list = (NBTTagList) tag;
            for (int i = 0; i < list.tagCount(); ++i) {
                NBTBase base = NBTHelper.getTagAt(list, i);
                Node<NamedNBT> child = new Node<NamedNBT>(parent, new NamedNBT(base));
                parent.addChild(child);
                this.addChildrenToTree(child);
            }
        }
    }

    public NBTTagCompound toNBTTagCompound() {
        NBTTagCompound tag = new NBTTagCompound();
        this.addChildrenToTag(this.root, tag);
        return tag;
    }

    public void addChildrenToTag(Node<NamedNBT> parent, NBTTagCompound tag) {
        for (Node<NamedNBT> child : parent.getChildren()) {
            NBTBase base = child.getObject().getNBT();
            String name = child.getObject().getName();
            if (base instanceof NBTTagCompound) {
                NBTTagCompound newTag = new NBTTagCompound();
                this.addChildrenToTag(child, newTag);
                tag.setTag(name, (NBTBase) newTag);
                continue;
            }
            if (base instanceof NBTTagList) {
                NBTTagList list = new NBTTagList();
                this.addChildrenToList(child, list);
                tag.setTag(name, (NBTBase) list);
                continue;
            }
            tag.setTag(name, base.copy());
        }
    }

    public void addChildrenToList(Node<NamedNBT> parent, NBTTagList list) {
        for (Node<NamedNBT> child : parent.getChildren()) {
            NBTBase base = child.getObject().getNBT();
            if (base instanceof NBTTagCompound) {
                NBTTagCompound newTag = new NBTTagCompound();
                this.addChildrenToTag(child, newTag);
                list.appendTag((NBTBase) newTag);
                continue;
            }
            if (base instanceof NBTTagList) {
                NBTTagList newList = new NBTTagList();
                this.addChildrenToList(child, newList);
                list.appendTag((NBTBase) newList);
                continue;
            }
            list.appendTag(base.copy());
        }
    }

    public void print() {
        this.print(this.root, 0);
    }

    private void print(Node<NamedNBT> n, int i) {
        System.out.println(NBTTree.repeat("\t", i) + NBTStringHelper.getNBTName(n.getObject()));
        for (Node<NamedNBT> child : n.getChildren()) {
            this.print(child, i + 1);
        }
    }

    public List<String> toStrings() {
        ArrayList<String> s = new ArrayList<String>();
        this.toStrings(s, this.root, 0);
        return s;
    }

    private void toStrings(List<String> s, Node<NamedNBT> n, int i) {
        s.add(NBTTree.repeat("   ", i) + NBTStringHelper.getNBTName(n.getObject()));
        for (Node<NamedNBT> child : n.getChildren()) {
            this.toStrings(s, child, i + 1);
        }
    }

    public static String repeat(String c, int i) {
        StringBuilder b = new StringBuilder(i + 1);
        for (int j = 0; j < i; ++j) {
            b.append(c);
        }
        return b.toString();
    }
}
