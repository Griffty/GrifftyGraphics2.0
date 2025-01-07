package org.griffty;

import com.aparapi.Range;
import org.griffty.Utility.Vector3;

public class RenderTriangleTest {
    float[] xRScreenD = new float[MainHandler.getResolution().getCurrentResolutionSize().width * MainHandler.getResolution().getCurrentResolutionSize().height];
    float[] yRScreenD = new float[MainHandler.getResolution().getCurrentResolutionSize().width * MainHandler.getResolution().getCurrentResolutionSize().height];

    private double[] direction;
    int[] frame = new int[MainHandler.getResolution().getCurrentResolutionSize().width * MainHandler.getResolution().getCurrentResolutionSize().height];
    public int[] simpleGPURender() {
        int fov = 130;
        float width = MainHandler.getResolution().getCurrentResolutionSize().width;
        float height = MainHandler.getResolution().getCurrentResolutionSize().height;
        int w = (int) width;
        for (int xPix = 0; xPix < width; xPix++){
            for (int yPix = 0; yPix < height; yPix++){
                float x = (2 * xPix) / width - 1;
                float y = 1 - (2 * yPix) / height;

                x *= (width / height);

                y *= Math.tan(fov / 2);
                xRScreenD[ (xPix + yPix * 1600)] = x;
                yRScreenD[ (xPix + yPix * 1600)] = y;
            }
        }

        double[] direction = new double[(int) (3*width*height)];
        int i = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++){
                Vector3 dir = Vector3.normalize(new Vector3(xRScreenD[x + y * 1600], yRScreenD[x + y * 1600], -0.9f));
                direction[i] = dir.x;
                i++;
                direction[i] = dir.y;
                i++;
                direction[i] = dir.z;
                i++;
            }
        }
        this.direction = direction;
        double[] origin = new double[3]; // x - left/right; y - up/down, z - closer/further
        double[][] triangle = {
                {-5, 1, -4},
                {10, 10, -10},
                {20, -20, -20}
        };

        for (int x = 0; x < 1600; x++){
            for (int y = 0; y < 900; y++) {
                if (rayIntersectsTriangle(origin, new double[]{direction[(x + y * 1600) * 3], direction[(x + y * 1600) * 3 + 1], direction[(x + y * 1600) * 3 + 2]}, triangle)){
                    frame[x + y * 1600] = 500;
                }else{
                    frame[x + y * 1600] = 0;
                }
            }
        }

        return frame;
    }
    public static boolean rayIntersectsTriangle(double[] rayOrigin, double[] rayDirection, double[][] triangleVertices) {
        final double EPSILON = 0.000001;

        double[] edge1, edge2, h, s, q;
        double a, f, u, v;

        edge1 = subtract(triangleVertices[1], triangleVertices[0]);
        edge2 = subtract(triangleVertices[2], triangleVertices[0]);

        h = crossProduct(rayDirection, edge2);
        a = dotProduct(edge1, h);

        if (a > -EPSILON && a < EPSILON)
            return false;

        f = 1.0 / a;
        s = subtract(rayOrigin, triangleVertices[0]);
        u = f * dotProduct(s, h);

        if (u < 0.0 || u > 1.0)
            return false;

        q = crossProduct(s, edge1);
        v = f * dotProduct(rayDirection, q);

        if (v < 0.0 || u + v > 1.0)
            return false;

        double t = f * dotProduct(edge2, q);

        if (t > EPSILON)
            return true;
        else
            return false;
    }

    public static double[] subtract(double[] vec1, double[] vec2) {
        double[] result = new double[3];
        result[0] = vec1[0] - vec2[0];
        result[1] = vec1[1] - vec2[1];
        result[2] = vec1[2] - vec2[2];
        return result;
    }

    public static double dotProduct(double[] vec1, double[] vec2) {
        return vec1[0]*vec2[0] + vec1[1]*vec2[1] + vec1[2]*vec2[2];
    }

    public static double[] crossProduct(double[] vec1, double[] vec2) {
        double[] result = new double[3];
        result[0] = vec1[1]*vec2[2] - vec1[2]*vec2[1];
        result[1] = vec1[2]*vec2[0] - vec1[0]*vec2[2];
        result[2] = vec1[0]*vec2[1] - vec1[1]*vec2[0];
        return result;
    }
}
