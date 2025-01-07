package org.griffty.aparapiTests;

public class RayTriangleIntersect {
    public static void main(String[] args) {
        float[] rayOrigin = {0, 0, 0};
        float[] rayDirection = {0.87157553f, 0, -1};

        float[][] triangleVertices = {
                {10, 10, -10},
                {10, -20, -30},
                {30, 15, -20}
        };

        System.out.println(rayIntersectsTriangle(rayOrigin, rayDirection, triangleVertices));
    }

    public static boolean rayIntersectsTriangle(float[] rayOrigin, float[] rayDirection, float[][] triangleVertices) {
        final float EPSILON = 0.000001f;

        float[] edge1, edge2, h, s, q;
        float a, f, u, v;

        edge1 = subtract(triangleVertices[1], triangleVertices[0]);
        edge2 = subtract(triangleVertices[2], triangleVertices[0]);

        h = crossProduct(rayDirection, edge2);
        a = dotProduct(edge1, h);

        if (a > -EPSILON && a < EPSILON)
            return false;    // This ray is parallel to this triangle.

        f = 1.0f / a;
        s = subtract(rayOrigin, triangleVertices[0]);
        u = f * dotProduct(s, h);

        if (u < 0.0 || u > 1.0)
            return false;

        q = crossProduct(s, edge1);
        v = f * dotProduct(rayDirection, q);

        if (v < 0.0 || u + v > 1.0)
            return false;

        // At this stage we can compute t to find out where the intersection point is on the line.
        float t = f * dotProduct(edge2, q);

        if (t > EPSILON) // ray intersection
            return true;
        else // This means that there is a line intersection but not a ray intersection.
            return false;
    }

    public static float[] subtract(float[] vec1, float[] vec2) {
        float[] result = new float[3];
        result[0] = vec1[0] - vec2[0];
        result[1] = vec1[1] - vec2[1];
        result[2] = vec1[2] - vec2[2];
        return result;
    }

    public static float dotProduct(float[] vec1, float[] vec2) {
        return vec1[0]*vec2[0] + vec1[1]*vec2[1] + vec1[2]*vec2[2];
    }

    public static float[] crossProduct(float[] vec1, float[] vec2) {
        float[] result = new float[3];
        result[0] = vec1[1]*vec2[2] - vec1[2]*vec2[1];
        result[1] = vec1[2]*vec2[0] - vec1[0]*vec2[2];
        result[2] = vec1[0]*vec2[1] - vec1[1]*vec2[0];
        return result;
    }
}
