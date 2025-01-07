package org.griffty.aparapiTests;

import com.aparapi.Kernel;
import com.aparapi.Range;
import org.griffty.RunType;

import java.util.Date;
import java.util.Random;


public class TestSecond {
    boolean debug = false;
    private final Range range = Range.create2D(1920, 1080, 4, 4);
    private final Random rd = new Random();
    private final float[] ans = new float[range.getGlobalSize(0) * range.getGlobalSize(1)];
    TestSecond(RunType mode){
        long startT = new Date().getTime();
        if (mode == RunType.GPU){
            RunOnGPU();
        }else if (mode == RunType.CPU){
            RunOnCPU();
        }
        long endT = new Date().getTime();
        System.out.println("Full time: " + (endT - startT));
        debug();
    }

    private void RunOnCPU() {
        int width = range.getGlobalSize(0);
        int height = range.getGlobalSize(1);
        int i = 0;
        while (i < 100) {
            long startT = new Date().getTime();
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    float c = (float) (Math.sin(x * 0.357f) + Math.cos(y * 0.357f));
                    ans[y * 512 + x] = (float) (c / Math.tan(x / (y + 0.5f)) + Math.sqrt(Math.sin(y * 0.434f) - Math.cos(x * 0.245f)));
                }
            }
            long endT = new Date().getTime();
            System.out.println("Cycle time: " + (endT - startT));
            i++;
        }
    }

    private void RunOnGPU(){
        float[] ans = this.ans;
        Kernel kernel = new Kernel() {
            @Override
            public void run() {
                int x = getGlobalId(0);
                int y = getGlobalId(1);
                float c = sin(x * 0.357f) + cos(y * 0.357f);
                ans[y * 512 + x] = c / tan(x/(y+0.5f)) + sqrt(sin(y * 0.434f) - cos(x * 0.245f));
            }
        };
        int i = 0;
        while (i < 100) {
            long startT = new Date().getTime();
            kernel.execute(range);
            long endT = new Date().getTime();
            System.out.println("Cycle time: " + (endT - startT));
            i++;
        }
        if (!kernel.isRunningCL()) {
            System.out.println("Runned on CPU");
        }
    }

    private void debug() {
        if (debug) {

        }
    }
    public static void main(String[] args) {
        new TestSecond(RunType.CPU);
    }
}