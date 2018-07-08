/*
 * Decompiled with CFR 0_128.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.entity.RenderManager
 */
package ehacks.mod.internal;

import net.minecraft.client.renderer.entity.RenderManager;

public final class CEBlockCoord {
    private float x;
    private float y;
    private float z;

    public CEBlockCoord(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public CEBlockCoord(double x, double y, double z) {
        this.x = (float)x;
        this.y = (float)y;
        this.z = (float)z;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public float getZ() {
        return this.z;
    }

    public double getDeltaX() {
        return (double)this.getX() - RenderManager.renderPosX;
    }

    public double getDeltaY() {
        return (double)this.getY() - RenderManager.renderPosY;
    }

    public double getDeltaZ() {
        return (double)this.getZ() - RenderManager.renderPosZ;
    }

    public CEBlockCoord substract(CEBlockCoord old) {
        return new CEBlockCoord(this.getX() - old.getX(), this.getY() - old.getY(), this.getZ() - old.getZ());
    }

    public CEBlockCoord add(CEBlockCoord old) {
        return new CEBlockCoord(this.getX() + old.getX(), this.getY() + old.getY(), this.getZ() + old.getZ());
    }

    public CEBlockCoord devide(CEBlockCoord old) {
        return new CEBlockCoord(this.getX() / old.getX(), this.getY() / old.getY(), this.getZ() / old.getZ());
    }

    public CEBlockCoord multiply(CEBlockCoord old) {
        return new CEBlockCoord(this.getX() * old.getX(), this.getY() * old.getY(), this.getZ() * old.getZ());
    }

    public boolean equals(CEBlockCoord in) {
        if (in.getX() == this.getX() && in.getY() == this.getY() && in.getZ() == this.getZ()) {
            return true;
        }
        return false;
    }
}

