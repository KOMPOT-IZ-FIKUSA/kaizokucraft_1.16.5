package com.deadfikus.kaizokucraft.core.math;

import net.minecraft.util.math.vector.Vector3d;

public class KaizokuCoreMathHelper {


    private static double eps4 = 0.0001;
    private static final double eps4_1 = Math.sqrt(1 - 2 * eps4 * eps4);
    public static OrientedBoxDimensions getBoxDimensions(Vector3d direction, float width, float height, float thickness) {
        if (direction.x < eps4 && direction.z < eps4) { // aligned up or down (or error)
            if (direction.y < eps4) { // error, return one by one box
                return OrientedBoxDimensions.cube();
            }
            direction = new Vector3d(eps4, direction.y > 0 ? eps4_1 : -eps4_1, eps4);
        }
        OrientedBoxDimensions dimensions = new OrientedBoxDimensions();
        double a = -direction.y / (direction.z * direction.z + direction.x * direction.x);
        dimensions.height = new Vector3d(a * direction.x, 1, a * direction.y).normalize().scale(height);
        dimensions.width = new Vector3d(direction.z, 0, -direction.x).normalize().scale(width);
        dimensions.thickness = new Vector3d(direction.x, direction.y, direction.z).normalize().scale(thickness);
        return dimensions;
    }

}
