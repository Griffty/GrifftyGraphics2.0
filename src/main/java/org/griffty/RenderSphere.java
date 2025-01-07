package org.griffty;

import com.aparapi.Kernel;
import com.aparapi.Range;
import org.griffty.Objects.Sphere;

public class RenderSphere {
    private float[] xSpherePos;
    private float[] ySpherePos;
    private float[] zSpherePos;
    private float[] SphereRad;
    private Kernel renderKernel;
    private byte[] frame;
    public void setUpKernel(){
        byte[] frame = new byte[MainHandler.getResolution().getCurrentResolutionSize().width * MainHandler.getResolution().getCurrentResolutionSize().height];
        float[] xSpherePos = new float[WorldObjectHandler.getAllWorldObjects().size()];
        float[] ySpherePos = new float[WorldObjectHandler.getAllWorldObjects().size()];
        float[] zSpherePos = new float[WorldObjectHandler.getAllWorldObjects().size()];
        float[] SphereRad = new float[WorldObjectHandler.getAllWorldObjects().size()];
        this.xSpherePos = xSpherePos;
        this.ySpherePos = ySpherePos;
        this.zSpherePos = zSpherePos;
        this.SphereRad = SphereRad;
        this.frame = frame;
        updateValInKernel(xSpherePos, ySpherePos, zSpherePos, SphereRad);
        int width = MainHandler.getResolution().getCurrentResolutionSize().width;
        renderKernel = new Kernel() {
            @Override
            public void run() {
                int x = getGlobalId(0);
                int y = getGlobalId(1);
                frame[x + y * width] = 0;
                for (int i = 0; i < SphereRad.length; i++){
                    if (isInsideSphere(xSpherePos[i], ySpherePos[i], zSpherePos[i], SphereRad[i], x, y, zSpherePos[i])){
                        frame[x + y * width] = (byte) 1000;
                    }else if (frame[x + y * width] == 0){
                        frame[x + y * width] = (byte) 0;
                    }
                }
            }
            public boolean isInsideSphere(float sX, float sY, float sZ, float sR, float pX, float pY, float pZ){
                return sqrt(pow(pX - sX, 2) + pow(pY - sY, 2) + pow(pZ - sZ, 2)) < sR;
            }
        };
        renderKernel.setExplicit(true);
    }
    public void updatePos(){
        updateValInKernel(xSpherePos, ySpherePos, zSpherePos, SphereRad);
    }

    private void updateValInKernel(float[] xSpherePos, float[] ySpherePos, float[] zSpherePos, float[] sphereRad) {
        for (int i = 0; i < WorldObjectHandler.getAllWorldObjects().size(); i++) {
            xSpherePos[i] = WorldObjectHandler.getAllWorldObjects().get(i).getPosition().x;
            ySpherePos[i] = WorldObjectHandler.getAllWorldObjects().get(i).getPosition().y;
            zSpherePos[i] = WorldObjectHandler.getAllWorldObjects().get(i).getPosition().z;
            sphereRad[i] = ((Sphere) WorldObjectHandler.getAllWorldObjects().get(i)).getRadius();
        }
    }

    public byte[] simpleGPURender() {
        renderKernel.put(xSpherePos).put(ySpherePos).put(zSpherePos).put(SphereRad).put(frame).execute(Range.create2D(MainHandler.getResolution().getCurrentResolutionSize().width, MainHandler.getResolution().getCurrentResolutionSize().height, 4, 4));
        renderKernel.get(frame);
        return frame;
    }

    public byte[] simpleCPURender() {
        throw new UnsupportedOperationException("Have not been implemented yet");
    }
}
