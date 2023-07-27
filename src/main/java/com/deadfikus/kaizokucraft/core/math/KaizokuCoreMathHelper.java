package com.deadfikus.kaizokucraft.core.math;

import net.minecraft.util.math.vector.Vector3d;

public class KaizokuCoreMathHelper {


    private static double eps4 = 0.0001;
    private static final double eps4_1 = Math.sqrt(1 - 2 * eps4 * eps4);
    public static OrientedBoxDimensions getBoxDimensions(Vector3d direction, float width, float height, float thickness) {
        if (Math.abs(direction.x) < eps4 && Math.abs(direction.z) < eps4) { // aligned up or down (or error)
            if (Math.abs(direction.y) < eps4) { // error, return one by one box
                return OrientedBoxDimensions.cube();
            }
            direction = new Vector3d(eps4, direction.y > 0 ? eps4_1 : -eps4_1, eps4);
        }
        OrientedBoxDimensions dimensions = new OrientedBoxDimensions();
        if (direction.x != 0) {
            double vy = direction.y / direction.x;
            double vz = direction.z / direction.x;
            if (vy != 0) {
                dimensions.height = new Vector3d(1, -(vz*vz + 1)/vy, vz).normalize();
            } else {
                dimensions.height = new Vector3d(0, 1, 0);
            }
            if (vz != 0) {
                dimensions.width = new Vector3d(1, 0, -1/vz).normalize();
            } else {
                dimensions.width = new Vector3d(0, 0, 1);
            }
            dimensions.thickness = new Vector3d(1, vy, vz).normalize();
        } else {
            dimensions.thickness = new Vector3d(0, direction.y, 1).normalize();
            dimensions.width = new Vector3d(1, 0, 0);
            dimensions.height = new Vector3d(0, 1, -direction.y).normalize();
        }
        dimensions.width     = dimensions.width    .scale(width);
        dimensions.thickness = dimensions.thickness.scale(thickness);
        dimensions.height    = dimensions.height   .scale(height);

        return dimensions;
    }

}
