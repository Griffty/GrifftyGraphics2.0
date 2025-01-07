package org.griffty.aparapiTests;

import com.aparapi.Kernel;
import com.aparapi.Range;
import org.griffty.RunType;

import java.util.Date;
import java.util.Random;

public class TestFirst {
    boolean debug = false;
    int kernels = 65536;
    int iterations = 1000;
    int number = kernels * iterations;
    int[] a = new int[number];
    int[] c = new int[number];
    private Random rd = new Random();
    TestFirst(RunType mode){
        for (int i = 0; i < number; i++){
            a[i] = (short) rd.nextInt(10);
        }

        for (int i = 0; i < number; i++){
            c[i] = 0;
        }

        if (mode == RunType.GPU){
            RunOnGPU();
        }else if (mode == RunType.CPU){
            RunOnCPU();
        }


        if (debug) {
            for (int i = 0; i < number; i++){
                System.out.println("c: " + c[i] + "; a: " + a[i]);
            }
        }

    }
    // a*b i: 10^4, k: 1024 = 10; i: 10^5, k: 1024 = 59; i: 10^5 * 4, k: 1024 = 200
    // a^2 i: 10^4, k: 1024 = 10; i: 10^5, k: 1024 = 45; i: 10^5 * 4, k: 1024 = 160
    // a^2 i: 10^3, k: 65536 = 790; i: 10^3 * 5, k: 65536 = 120
    private void RunOnCPU() {
        for (int i = 0; i < kernels; i++){
            for (int j = 0; j < iterations; j++){
                c[j + i * iterations] = a[j + i * iterations] * a[j + i * iterations];
            }
        }
    }
    // a*b i: 10^4, k: 1024 = 700; i: 10^5, k: 1024 = 915; i: 10^5 * 4, k: 1024 = 1700
    // a^2 i: 10^4, k: 1024 = 700; i: 10^5, k: 1024 = 840; i: 10^5 * 4, k: 1024 = 1450
    // a^2 i: 10^3, k: 65536 = 28; i: 10^3 * 5, k: 65536 = 1370
    private void RunOnGPU(){
        int iterations = this.iterations;
        int[] a = this.a;
        int[] c = this.c;
        long startT = new Date().getTime();
        Kernel kernel = new Kernel() {
            @Override
            public void run() {
                int id = getGlobalId();
                for (int i = 0; i < iterations; i++){
                    c[i + id * iterations] = a[i + id * iterations] * a[i + id * iterations];
                }
            }
        };
        long midT = new Date().getTime();
        kernel.execute(Range.create(kernels));
        long endT = new Date().getTime();
        if (!kernel.isRunningCL()) {
            System.out.println("Runned on CPU");
        }
        System.out.println("Full time: " + (endT - startT) + "\nTime to compile: " + (midT - startT) +"\nTime to execute: " + (endT - midT));
        System.out.println("Device: " + kernel.getTargetDevice().toString());
    }

    public static void main(String[] args) {
        new TestFirst(RunType.GPU);
    }

}
