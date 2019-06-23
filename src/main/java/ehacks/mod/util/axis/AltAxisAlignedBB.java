package ehacks.mod.util.axis;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

public class AltAxisAlignedBB {

    private static final ThreadLocal theAABBLocalPool = new AltAABBLocalPool();
    public double minX;
    public double minY;
    public double minZ;
    public double maxX;
    public double maxY;
    public double maxZ;

    public AltAxisAlignedBB(double par1, double par3, double par5, double par7, double par9, double par11) {
        this.minX = par1;
        this.minY = par3;
        this.minZ = par5;
        this.maxX = par7;
        this.maxY = par9;
        this.maxZ = par11;
    }

    public static AltAxisAlignedBB getBoundingBox(double par0, double par2, double par4, double par6, double par8, double par10) {
        return new AltAxisAlignedBB(par0, par2, par4, par6, par8, par10);
    }

    public static AxisAlignedBB getAABBPool() {
        return (AxisAlignedBB) theAABBLocalPool.get();
    }

    public AltAxisAlignedBB setBounds(double par1, double par3, double par5, double par7, double par9, double par11) {
        this.minX = par1;
        this.minY = par3;
        this.minZ = par5;
        this.maxX = par7;
        this.maxY = par9;
        this.maxZ = par11;
        return this;
    }

    public AxisAlignedBB addCoord(double par1, double par3, double par5) {
        double var7 = this.minX;
        double var9 = this.minY;
        double var11 = this.minZ;
        double var13 = this.maxX;
        double var15 = this.maxY;
        double var17 = this.maxZ;
        if (par1 < 0.0) {
            var7 += par1;
        }
        if (par1 > 0.0) {
            var13 += par1;
        }
        if (par3 < 0.0) {
            var9 += par3;
        }
        if (par3 > 0.0) {
            var15 += par3;
        }
        if (par5 < 0.0) {
            var11 += par5;
        }
        if (par5 > 0.0) {
            var17 += par5;
        }
        AltAxisAlignedBB.getAABBPool();
        return new AxisAlignedBB(var7, var9, var11, var13, var15, var17);
    }

    public AxisAlignedBB expand(double par1, double par3, double par5) {
        double var7 = this.minX - par1;
        double var9 = this.minY - par3;
        double var11 = this.minZ - par5;
        double var13 = this.maxX + par1;
        double var15 = this.maxY + par3;
        double var17 = this.maxZ + par5;
        AltAxisAlignedBB.getAABBPool();
        return new AxisAlignedBB(var7, var9, var11, var13, var15, var17);
    }

    public AxisAlignedBB func_111270_a(AltAxisAlignedBB par1AxisAlignedBB) {
        double var2 = Math.min(this.minX, par1AxisAlignedBB.minX);
        double var4 = Math.min(this.minY, par1AxisAlignedBB.minY);
        double var6 = Math.min(this.minZ, par1AxisAlignedBB.minZ);
        double var8 = Math.max(this.maxX, par1AxisAlignedBB.maxX);
        double var10 = Math.max(this.maxY, par1AxisAlignedBB.maxY);
        double var12 = Math.max(this.maxZ, par1AxisAlignedBB.maxZ);
        AltAxisAlignedBB.getAABBPool();
        return new AxisAlignedBB(var2, var4, var6, var8, var10, var12);
    }

    public AxisAlignedBB getOffsetBoundingBox(double par1, double par3, double par5) {
        AltAxisAlignedBB.getAABBPool();
        return new AxisAlignedBB((this.minX + par1), (this.minY + par3), (this.minZ + par5), (this.maxX + par1), (this.maxY + par3), (this.maxZ + par5));
    }

    public double calculateXOffset(AltAxisAlignedBB par1AxisAlignedBB, double par2) {
        if (par1AxisAlignedBB.maxY > this.minY && par1AxisAlignedBB.minY < this.maxY) {
            if (par1AxisAlignedBB.maxZ > this.minZ && par1AxisAlignedBB.minZ < this.maxZ) {
                double var4;
                if (par2 > 0.0 && par1AxisAlignedBB.maxX <= this.minX && (var4 = this.minX - par1AxisAlignedBB.maxX) < par2) {
                    par2 = var4;
                }
                if (par2 < 0.0 && par1AxisAlignedBB.minX >= this.maxX && (var4 = this.maxX - par1AxisAlignedBB.minX) > par2) {
                    par2 = var4;
                }
                return par2;
            }
            return par2;
        }
        return par2;
    }

    public double calculateYOffset(AltAxisAlignedBB par1AxisAlignedBB, double par2) {
        if (par1AxisAlignedBB.maxX > this.minX && par1AxisAlignedBB.minX < this.maxX) {
            if (par1AxisAlignedBB.maxZ > this.minZ && par1AxisAlignedBB.minZ < this.maxZ) {
                double var4;
                if (par2 > 0.0 && par1AxisAlignedBB.maxY <= this.minY && (var4 = this.minY - par1AxisAlignedBB.maxY) < par2) {
                    par2 = var4;
                }
                if (par2 < 0.0 && par1AxisAlignedBB.minY >= this.maxY && (var4 = this.maxY - par1AxisAlignedBB.minY) > par2) {
                    par2 = var4;
                }
                return par2;
            }
            return par2;
        }
        return par2;
    }

    public double calculateZOffset(AltAxisAlignedBB par1AxisAlignedBB, double par2) {
        if (par1AxisAlignedBB.maxX > this.minX && par1AxisAlignedBB.minX < this.maxX) {
            if (par1AxisAlignedBB.maxY > this.minY && par1AxisAlignedBB.minY < this.maxY) {
                double var4;
                if (par2 > 0.0 && par1AxisAlignedBB.maxZ <= this.minZ && (var4 = this.minZ - par1AxisAlignedBB.maxZ) < par2) {
                    par2 = var4;
                }
                if (par2 < 0.0 && par1AxisAlignedBB.minZ >= this.maxZ && (var4 = this.maxZ - par1AxisAlignedBB.minZ) > par2) {
                    par2 = var4;
                }
                return par2;
            }
            return par2;
        }
        return par2;
    }

    public boolean intersectsWith(AltAxisAlignedBB par1AxisAlignedBB) {
        return (par1AxisAlignedBB.maxX > this.minX && par1AxisAlignedBB.minX < this.maxX) && ((par1AxisAlignedBB.maxY > this.minY && par1AxisAlignedBB.minY < this.maxY) && (par1AxisAlignedBB.maxZ > this.minZ && par1AxisAlignedBB.minZ < this.maxZ));
    }

    public AltAxisAlignedBB offset(double par1, double par3, double par5) {
        this.minX += par1;
        this.minY += par3;
        this.minZ += par5;
        this.maxX += par1;
        this.maxY += par3;
        this.maxZ += par5;
        return this;
    }

    public boolean isVecInside(Vec3d par1Vec3) {
        return (par1Vec3.x > this.minX && par1Vec3.x < this.maxX) && ((par1Vec3.y > this.minY && par1Vec3.y < this.maxY) && (par1Vec3.z > this.minZ && par1Vec3.z < this.maxZ));
    }

    public double getAverageEdgeLength() {
        double var1 = this.maxX - this.minX;
        double var3 = this.maxY - this.minY;
        double var5 = this.maxZ - this.minZ;
        return (var1 + var3 + var5) / 3.0;
    }

    public AxisAlignedBB contract(double par1, double par3, double par5) {
        double var7 = this.minX + par1;
        double var9 = this.minY + par3;
        double var11 = this.minZ + par5;
        double var13 = this.maxX - par1;
        double var15 = this.maxY - par3;
        double var17 = this.maxZ - par5;
        AltAxisAlignedBB.getAABBPool();
        return new AxisAlignedBB(var7, var9, var11, var13, var15, var17);
    }

    public AxisAlignedBB copy() {
        AltAxisAlignedBB.getAABBPool();
        return new AxisAlignedBB(this.minX, this.minY, this.minZ, this.maxX, this.maxY, this.maxZ);
    }

    public RayTraceResult calculateIntercept(Vec3d par1Vec3, Vec3d par2Vec3) {
        Vec3d var3 = par1Vec3.getIntermediateWithXValue(par2Vec3, this.minX);
        Vec3d var4 = par1Vec3.getIntermediateWithXValue(par2Vec3, this.maxX);
        Vec3d var5 = par1Vec3.getIntermediateWithYValue(par2Vec3, this.minY);
        Vec3d var6 = par1Vec3.getIntermediateWithYValue(par2Vec3, this.maxY);
        Vec3d var7 = par1Vec3.getIntermediateWithZValue(par2Vec3, this.minZ);
        Vec3d var8 = par1Vec3.getIntermediateWithZValue(par2Vec3, this.maxZ);
        if (!this.isVecInYZ(var3)) {
            var3 = null;
        }
        if (!this.isVecInYZ(var4)) {
            var4 = null;
        }
        if (!this.isVecInXZ(var5)) {
            var5 = null;
        }
        if (!this.isVecInXZ(var6)) {
            var6 = null;
        }
        if (!this.isVecInXY(var7)) {
            var7 = null;
        }
        if (!this.isVecInXY(var8)) {
            var8 = null;
        }
        Vec3d var9 = null;
        if (var3 != null) {
            var9 = var3;
        }
        if (var4 != null && (var9 == null || par1Vec3.squareDistanceTo(var4) < par1Vec3.squareDistanceTo(var9))) {
            var9 = var4;
        }
        if (var5 != null && (var9 == null || par1Vec3.squareDistanceTo(var5) < par1Vec3.squareDistanceTo(var9))) {
            var9 = var5;
        }
        if (var6 != null && (var9 == null || par1Vec3.squareDistanceTo(var6) < par1Vec3.squareDistanceTo(var9))) {
            var9 = var6;
        }
        if (var7 != null && (var9 == null || par1Vec3.squareDistanceTo(var7) < par1Vec3.squareDistanceTo(var9))) {
            var9 = var7;
        }
        if (var8 != null && (var9 == null || par1Vec3.squareDistanceTo(var8) < par1Vec3.squareDistanceTo(var9))) {
            var9 = var8;
        }
        if (var9 == null) {
            return null;
        }
        int var10 = -1;
        if (var9 == var3) {
            var10 = 4;
        }
        if (var9 == var4) {
            var10 = 5;
        }
        if (var9 == var5) {
            var10 = 0;
        }
        if (var9 == var6) {
            var10 = 1;
        }
        if (var9 == var7) {
            var10 = 2;
        }
        if (var9 == var8) {
            var10 = 3;
        }
        return new RayTraceResult(new Vec3d(0, 0, 0), EnumFacing.DOWN); // Написал чисто на шару
    }

    private boolean isVecInYZ(Vec3d par1Vec3) {
        return par1Vec3 != null && (par1Vec3.y >= this.minY && par1Vec3.y <= this.maxY && par1Vec3.z >= this.minZ && par1Vec3.z <= this.maxZ);
    }

    private boolean isVecInXZ(Vec3d par1Vec3) {
        return par1Vec3 != null && (par1Vec3.x >= this.minX && par1Vec3.x <= this.maxX && par1Vec3.z >= this.minZ && par1Vec3.z <= this.maxZ);
    }

    private boolean isVecInXY(Vec3d par1Vec3) {
        return par1Vec3 != null && (par1Vec3.x >= this.minX && par1Vec3.x <= this.maxX && par1Vec3.y >= this.minY && par1Vec3.y <= this.maxY);
    }

    public void setBB(AltAxisAlignedBB par1AxisAlignedBB) {
        this.minX = par1AxisAlignedBB.minX;
        this.minY = par1AxisAlignedBB.minY;
        this.minZ = par1AxisAlignedBB.minZ;
        this.maxX = par1AxisAlignedBB.maxX;
        this.maxY = par1AxisAlignedBB.maxY;
        this.maxZ = par1AxisAlignedBB.maxZ;
    }

    @Override
    public String toString() {
        return "box[" + this.minX + ", " + this.minY + ", " + this.minZ + " -> " + this.maxX + ", " + this.maxY + ", " + this.maxZ + "]";
    }
}
