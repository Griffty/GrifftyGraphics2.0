package org.griffty;

import com.aparapi.Kernel;
import com.aparapi.Range;
import org.griffty.Objects.TriangulatedObject;
import org.griffty.Utility.Vector3;

public class RenderTriangle {
    private float[] xTP;
    private float[] yTP;
    private float[] zTP;

    private float[] xRO;
    private float[] yRO;
    private float[] zRO;


    private float[] xRD;
    private float[] yRD;
    private float[] zRD;

    private float[] xRScreenD;
    private float[] yRScreenD;

    private double[] direction;


    private Vector3 cameraPos = new Vector3();
    private Vector3 cameraRot = new Vector3();
   // private float renderPlaneDist = 0.018f;
    private int fov = 130;
    private Kernel renderKernel;
    private int[] frame;

    public int[] simpleGPURender() {
        renderKernel.put(xTP).put(yTP).put(zTP).put(direction).put(frame).execute(Range.create2D(MainHandler.getResolution().getCurrentResolutionSize().width, MainHandler.getResolution().getCurrentResolutionSize().height, 4, 4));
        renderKernel.get(frame);
        return frame;
    }
    public int[] simpleCPURender() {
        throw new UnsupportedOperationException("Have not been implemented yet");
    }

    public void setUpKernel(){
        float[] xRO = new float[MainHandler.getResolution().getCurrentResolutionSize().width * MainHandler.getResolution().getCurrentResolutionSize().height];
        float[] yRO = new float[MainHandler.getResolution().getCurrentResolutionSize().width * MainHandler.getResolution().getCurrentResolutionSize().height];
        float[] zRO = new float[MainHandler.getResolution().getCurrentResolutionSize().width * MainHandler.getResolution().getCurrentResolutionSize().height];
        float[] xRD = new float[MainHandler.getResolution().getCurrentResolutionSize().width * MainHandler.getResolution().getCurrentResolutionSize().height];
        float[] yRD = new float[MainHandler.getResolution().getCurrentResolutionSize().width * MainHandler.getResolution().getCurrentResolutionSize().height];
        float[] zRD = new float[MainHandler.getResolution().getCurrentResolutionSize().width * MainHandler.getResolution().getCurrentResolutionSize().height];

        float[] xRScreenD = new float[MainHandler.getResolution().getCurrentResolutionSize().width * MainHandler.getResolution().getCurrentResolutionSize().height];
        float[] yRScreenD = new float[MainHandler.getResolution().getCurrentResolutionSize().width * MainHandler.getResolution().getCurrentResolutionSize().height];

        this.xRO = xRO;
        this.yRO = yRO;
        this.zRO = zRO;


        float width = MainHandler.getResolution().getCurrentResolutionSize().width;
        float height = MainHandler.getResolution().getCurrentResolutionSize().height;
        int w = (int) width;
        for (int xPix = 0; xPix < width; xPix++){
            for (int yPix = 0; yPix < height; yPix++){
                float x = (2 * xPix) / width - 1;
                float y = 1 - (2 * yPix) / height;

                x *= (width / height);
                y *= Math.tan(fov / 2);


                xRScreenD[ (xPix + yPix * w)] = x;
                yRScreenD[ (xPix + yPix * w)] = y;


            }
        }

        this.xRScreenD = xRScreenD;
        this.yRScreenD = yRScreenD;
        double[] direction = new double[(int) (3*width*height)];
        int i = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++){
                Vector3 dir = Vector3.normalize(new Vector3(xRScreenD[x + y * w], yRScreenD[x + y * w], -1));
                direction[i] = dir.x;
                i++;
                direction[i] = dir.y;
                i++;
                direction[i] = dir.z;
                i++;
            }
        }
        this.direction = direction;

        int[] frame = new int[MainHandler.getResolution().getCurrentResolutionSize().width * MainHandler.getResolution().getCurrentResolutionSize().height];
        float[] xTP = new float[WorldObjectHandler.getTrianglesNumber()*3];
        float[] yTP = new float[WorldObjectHandler.getTrianglesNumber()*3];
        float[] zTP = new float[WorldObjectHandler.getTrianglesNumber()*3];
        this.xTP = xTP;
        this.yTP = yTP;
        this.zTP = zTP;
        this.frame = frame;
        int triNumber = WorldObjectHandler.getTrianglesNumber();
        updateValInKernel(xTP, yTP, zTP);

        double[] origin = new double[3];
        double[][] triangle = {
                {-5, -5, -20},
                {10, 10, -40},
                {20, -20, -80}
        };


        renderKernel = new Kernel() {
            @Override
            public void run() {
                double[][] A = {
                        {triangle[0][0] - triangle[1][0], triangle[0][0] - triangle[2][0], direction[(getGlobalId(0) + getGlobalId(1) * w)* 3]},
                        {triangle[0][1] - triangle[1][1], triangle[0][1] - triangle[2][1], direction[(getGlobalId(0) + getGlobalId(1) * w) * 3 + 1]},
                        {triangle[0][2] - triangle[1][2], triangle[0][2] - triangle[2][2], direction[(getGlobalId(0) + getGlobalId(1) * w) * 3 + 2]}
                };
                double[] b = {
                        triangle[0][0] - origin[0],
                        triangle[0][1] - origin[1],
                        triangle[0][2] - origin[2]
                };
                double[] x = solveSystem(A, b);
                double t = x[2];
                    if (t > 0 && x[0] >= 0 && x[1] >= 0 && (x[0] + x[1]) <= 1) {
                        frame[getGlobalId(0) + getGlobalId(1) * w] = 500;
                    }else {
                        frame[getGlobalId(0) + getGlobalId(1) * w] = 0;
                    }
//                }
            }
            double[] solveSystem(double[][] A, double[] b) {
                double[] x = {0,0,0};

                double detA = det3(A);

                double[][] temp = {{0,0,0}, {0,0,0}, {0,0,0}};
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        temp[j][i] = b[j];
                        if (i != 0) {
                            temp[j][i - 1] = A[j][i - 1];
                        }
                    }
                    x[i] = det3(temp) / detA;
                }
                return x;
            }
            // To find the determinant of a 3x3 matrix
            double det3(double[][] A) {
                return A[0][0]*(A[1][1]*A[2][2] - A[2][1]*A[1][2])
                        - A[0][1]*(A[1][0]*A[2][2] - A[2][0]*A[1][2])
                        + A[0][2]*(A[1][0]*A[2][1] - A[2][0]*A[1][1]);
            }
        };
        renderKernel.setExplicit(true);
    }
    public void updatePos(){
        updateValInKernel(xTP, yTP, zTP);
    }
    private void updateValInKernel(float[] xTP, float[] yTP, float[] zTP) {
        int a = 0;
        for (int obj = 0; obj < WorldObjectHandler.getAllWorldObjects().size(); obj++){
            for (int tri = 0; tri < ((TriangulatedObject) WorldObjectHandler.getAllWorldObjects().get(obj)).getAllTriangles().size(); tri++){
                for (int ver = 0; ver < 3 ; ver++){
                    switch (ver){
                        case 0 ->{
                            xTP[a] = ((TriangulatedObject) WorldObjectHandler.getAllWorldObjects().get(obj)).getAllTriangles().get(tri).x.x;
                            yTP[a] = ((TriangulatedObject) WorldObjectHandler.getAllWorldObjects().get(obj)).getAllTriangles().get(tri).x.y;
                            zTP[a] = ((TriangulatedObject) WorldObjectHandler.getAllWorldObjects().get(obj)).getAllTriangles().get(tri).x.z;
                        }
                        case 1 ->{
                            xTP[a] = ((TriangulatedObject) WorldObjectHandler.getAllWorldObjects().get(obj)).getAllTriangles().get(tri).y.x;
                            yTP[a] = ((TriangulatedObject) WorldObjectHandler.getAllWorldObjects().get(obj)).getAllTriangles().get(tri).y.y;
                            zTP[a] = ((TriangulatedObject) WorldObjectHandler.getAllWorldObjects().get(obj)).getAllTriangles().get(tri).y.z;
                        }
                        case 2 ->{
                            xTP[a] = ((TriangulatedObject) WorldObjectHandler.getAllWorldObjects().get(obj)).getAllTriangles().get(tri).z.x;
                            yTP[a] = ((TriangulatedObject) WorldObjectHandler.getAllWorldObjects().get(obj)).getAllTriangles().get(tri).z.y;
                            zTP[a] = ((TriangulatedObject) WorldObjectHandler.getAllWorldObjects().get(obj)).getAllTriangles().get(tri).z.z;
                        }
                    }
                    a++;
                }
            }
        }
    }

    public void setRenderPlane(int FOW){

    }
    public void setRenderPlane(float distance){

    }
}