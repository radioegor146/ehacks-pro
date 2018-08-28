/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehacks.mod.util;

/**
 *
 * @author radioegor146
 */
import ehacks.mod.wrapper.Wrapper;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.tileentity.TileEntityNote;
import net.minecraft.tileentity.TileEntityPiston;

public class Random {

    private final TileEntity[] tiles = {new TileEntityPiston(), new TileEntityNote(), new TileEntityMobSpawner(), new TileEntityCommandBlock(), new TileEntityBeacon()};
    private final Entity[] entityes = {new EntityBlaze(null), new EntityCreeper(null), new EntityGhast(null), new EntitySlime(null), new EntitySpider(null)};
    private final int randCoordCorr;

    public Random() {
        randCoordCorr = 15;
    }

    public int num() {
        return ThreadLocalRandom.current().nextInt();
    }

    public int num(int max) {
        return ThreadLocalRandom.current().nextInt(max);
    }

    public int num(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public String str() {
        return UUID.randomUUID().toString();
    }

    public boolean bool() {
        return ThreadLocalRandom.current().nextBoolean();
    }

    public NBTTagCompound nbt() {
        NBTTagCompound randNbt = new NBTTagCompound();
        randNbt.setString(str(), str());
        return randNbt;
    }

    public TileEntity tile() {
        return tiles[num(tiles.length)];
    }

    public ItemStack item() {
        ItemStack[] stacks = {new ItemStack(Items.diamond)};
        return stacks[num(stacks.length)];
    }

    public Entity ent() {
        return entityes[num(entityes.length)];
    }

    public int x() {
        int[] pos = new int[3];
        pos[0] = (int) Wrapper.INSTANCE.player().posX;
        pos[1] = (int) Wrapper.INSTANCE.player().posY;
        pos[2] = (int) Wrapper.INSTANCE.player().posZ;
        return num(pos[0] - randCoordCorr, pos[0] + randCoordCorr);
    }

    public int y() {
        int[] pos = new int[3];
        pos[0] = (int) Wrapper.INSTANCE.player().posX;
        pos[1] = (int) Wrapper.INSTANCE.player().posY;
        pos[2] = (int) Wrapper.INSTANCE.player().posZ;
        return num(pos[1] - randCoordCorr, pos[1] + randCoordCorr);
    }

    public int z() {
        int[] pos = new int[3];
        pos[0] = (int) Wrapper.INSTANCE.player().posX;
        pos[1] = (int) Wrapper.INSTANCE.player().posY;
        pos[2] = (int) Wrapper.INSTANCE.player().posZ;
        return num(pos[2] - randCoordCorr, pos[2] + randCoordCorr);
    }

}
