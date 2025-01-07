package org.griffty.aparapiTests;

import org.griffty.Utility.Vector3;

import java.util.ArrayList;
import java.util.List;

public class RaySphereIntersection {
    public static List<Vector3> findRaySphereIntersection(double rayOriginX, double rayOriginY, double rayOriginZ,
                                                          double rayDirectionX, double rayDirectionY, double rayDirectionZ,
                                                          double sphereCenterX, double sphereCenterY, double sphereCenterZ,
                                                          double sphereRadius) {
        List<Vector3> intersectionPoints = new ArrayList<>();

        // Step 3: Normalize the ray direction vector
        double length = Math.sqrt(rayDirectionX * rayDirectionX + rayDirectionY * rayDirectionY + rayDirectionZ * rayDirectionZ);
        double normalizedDirectionX = rayDirectionX / length;
        double normalizedDirectionY = rayDirectionY / length;
        double normalizedDirectionZ = rayDirectionZ / length;

        // Step 4: Calculate the discriminant
        double discriminant = Math.pow((rayOriginX - sphereCenterX) * normalizedDirectionX +
                (rayOriginY - sphereCenterY) * normalizedDirectionY +
                (rayOriginZ - sphereCenterZ) * normalizedDirectionZ, 2) -
                ((Math.pow(rayOriginX - sphereCenterX, 2) + Math.pow(rayOriginY - sphereCenterY, 2) +
                        Math.pow(rayOriginZ - sphereCenterZ, 2)) - Math.pow(sphereRadius, 2));

        // Step 5: Check the discriminant value
        if (discriminant >= 0) {
            // Step 6: Calculate the intersection point(s)
            double t1 = -((rayOriginX - sphereCenterX) * normalizedDirectionX +
                    (rayOriginY - sphereCenterY) * normalizedDirectionY +
                    (rayOriginZ - sphereCenterZ) * normalizedDirectionZ) -
                    Math.sqrt(discriminant);

            double t2 = -((rayOriginX - sphereCenterX) * normalizedDirectionX +
                    (rayOriginY - sphereCenterY) * normalizedDirectionY +
                    (rayOriginZ - sphereCenterZ) * normalizedDirectionZ) +
                    Math.sqrt(discriminant);

            // Step 7: Analyze the intersection points
            if (t1 >= 0) {
                double intersectionX = rayOriginX + t1 * normalizedDirectionX;
                double intersectionY = rayOriginY + t1 * normalizedDirectionY;
                double intersectionZ = rayOriginZ + t1 * normalizedDirectionZ;
                intersectionPoints.add(new Vector3((float) intersectionX, (float) intersectionY, (float) intersectionZ));
            }
            if (t2 >= 0) {
                double intersectionX = rayOriginX + t2 * normalizedDirectionX;
                double intersectionY = rayOriginY + t2 * normalizedDirectionY;
                double intersectionZ = rayOriginZ + t2 * normalizedDirectionZ;
                intersectionPoints.add(new Vector3((float) intersectionX, (float) intersectionY, (float) intersectionZ));
            }
        }

        return intersectionPoints;
    }

    public static void main(String[] args) {
        double rayOriginX = 0.0;
        double rayOriginY = 0.0;
        double rayOriginZ = 0.0;
        double rayDirectionX = 1.0;
        double rayDirectionY = 0.0;
        double rayDirectionZ = 0.0;
        double sphereCenterX = 2.0;
        double sphereCenterY = 0.0;
        double sphereCenterZ = 0.0;
        double sphereRadius = 1.0;


    }
}
