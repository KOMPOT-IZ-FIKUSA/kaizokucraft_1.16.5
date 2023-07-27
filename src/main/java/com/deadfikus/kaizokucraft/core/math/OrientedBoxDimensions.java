package com.deadfikus.kaizokucraft.core.math;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.Objects;

public class OrientedBoxDimensions implements INBTSerializable<CompoundNBT> {
    public Vector3d width;
    public Vector3d height;
    public Vector3d thickness;

    private int hash;
    private Vector3d[] verticesResult;

    public static OrientedBoxDimensions cube() {
        return new OrientedBoxDimensions(new Vector3d(0, 0, 1), new Vector3d(0, 1, 0), new Vector3d(1, 0, 0));
    }

    public OrientedBoxDimensions() {

    }

    private boolean update() {
        int hashCode = Objects.hash(width, height, thickness);
        if (hashCode != hash) {
            hash = hashCode;
            verticesResult = getVertices_();
            return false;
        }
        return true;
    }

    public OrientedBoxDimensions(Vector3d width, Vector3d height, Vector3d thickness) {
        this.width = width;
        this.height = height;
        this.thickness = thickness;
    }

    public double[] arrayX() {
        return new double[]{width.x, height.x, thickness.x};
    }

    public double[] arrayZ() {
        return new double[]{width.z, height.z, thickness.z};
    }

    public double[] arrayY() {
        return new double[]{width.y, height.y, thickness.y};
    }

    public double getAxisAlignedMaxWidth() {
        return Math.max(Math.abs(width.x) + Math.abs(height.x) + Math.abs(thickness.x), Math.abs(width.z) + Math.abs(height.z) + Math.abs(thickness.z));
    }

    public double getAxisAlignedHeight() {
        return Math.abs(width.y) + Math.abs(height.y) + Math.abs(thickness.y);
    }

    @Override
    public String toString() {
        return String.format("OrientedBox[%s, %s, %s]", width, height, thickness);
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT res = new CompoundNBT();
        res.putDouble("wx", width.x);
        res.putDouble("wy", width.y);
        res.putDouble("wz", width.z);
        res.putDouble("hx", height.x);
        res.putDouble("hy", height.y);
        res.putDouble("hz", height.z);
        res.putDouble("tx", thickness.x);
        res.putDouble("ty", thickness.y);
        res.putDouble("tz", thickness.z);
        return res;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        this.width = new Vector3d(nbt.getDouble("wx"), nbt.getDouble("wy"), nbt.getDouble("wz"));
        this.height = new Vector3d(nbt.getDouble("hx"), nbt.getDouble("hy"), nbt.getDouble("hz"));
        this.thickness = new Vector3d(nbt.getDouble("tx"), nbt.getDouble("ty"), nbt.getDouble("tz"));
    }

    public Vector3d[] getVertices() {
        update();
        return verticesResult;
    }

    private Vector3d[] getVertices_() {
        double wx = width.x / 2;
        double wy = width.y / 2;
        double wz = width.z / 2;
        double hx = height.x / 2;
        double hy = height.y / 2;
        double hz = height.z / 2;
        double tx = thickness.x / 2;
        double ty = thickness.y / 2;
        double tz = thickness.z / 2;
        Vector3d[] res = new Vector3d[8];
        res[0] = new Vector3d(wx + hx + tx, wy + hy + ty, wz + hz + tz); //
        res[1] = new Vector3d(wx + hx - tx, wy + hy - ty, wz + hz - tz); //
        res[2] = new Vector3d(wx - hx + tx, wy - hy + ty, wz - hz + tz);
        res[3] = new Vector3d(wx - hx - tx, wy - hy - ty, wz - hz - tz);
        res[4] = new Vector3d(-wx + hx + tx, -wy + hy + ty, -wz + hz + tz); //
        res[5] = new Vector3d(-wx + hx - tx, -wy + hy - ty, -wz + hz - tz); //
        res[6] = new Vector3d(-wx - hx + tx, -wy - hy + ty, -wz - hz + tz);
        res[7] = new Vector3d(-wx - hx - tx, -wy - hy - ty, -wz - hz - tz);
        return res;
    }

    /**
     * Contains indices of outputs of getVertices for faces: front, back, top, bottom, left, right.
     */
    public static int[][] facesVerticesIndices = new int[][]{{7, 5, 1, 3}, {2, 0, 4, 6}, {5, 4, 0, 1}, {6, 7, 3, 2}, {6, 4, 5, 7}, {3, 1, 0, 2}};

}
