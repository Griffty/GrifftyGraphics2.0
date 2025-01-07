package org.griffty;

import org.griffty.Objects.WorldObject;
import org.griffty.Utility.Resolution;
import org.griffty.Utility.ResolutionType;
import org.griffty.Utility.TimeHandler;

import java.awt.*;
import java.util.Scanner;

public class MainHandler extends Thread {
    public static boolean debug;
    private static final Resolution resolution = new Resolution(ResolutionType.r1600x900);
    private static final RenderTriangle render = new RenderTriangle();
    private static final RenderTriangleTest renderTriangleTest = new RenderTriangleTest();
    private boolean hasOpenedWindow = false;
    private static RunType renderType = RunType.GPU;
    private MainWindow window;

    MainHandler(){
        Scanner scanner = new Scanner(System.in);
        while (true){
            String s = scanner.nextLine();
            if (s.equals("s")){
                startMainCycle();
                break;
            }
            if (s.equals("d")){
                debug = !debug;
            }
            if (s.equals("c")){
                renderType = RunType.CPU;
            }
            if (s.equals("g")){
                renderType = RunType.GPU;
            }
            if (s.equals("r0")){
                resolution.setCurrentResolutionType(ResolutionType.r1920x1080);
            }
            if (s.equals("r1")){
                resolution.setCurrentResolutionType(ResolutionType.r1600x900);
            }
        }
    }

    private void startMainCycle(){
        initialize();
        while (true){
            move();
            draw();
            refresh();
        }
    }

    private void initialize() {
        start();
        while (!hasOpenedWindow){
            System.out.print("");
        }
        Editor.CreateObjects(2);
        for (WorldObject object : WorldObjectHandler.getAllWorldObjects()) {
            object.setActive(true);
        }
        render.setUpKernel();
    }

    private void move() {
        for (int i = 0; i < WorldObjectHandler.getAllWorldObjects().size(); i++){
            //WorldObjectHandler.getAllWorldObjects().get(i).applyTransformation();
        }
        render.updatePos();
    }
    private void draw() {
        if (!hasOpenedWindow) return;
        int[] currentFrame;
        if (renderType == RunType.GPU){
            currentFrame = renderTriangleTest.simpleGPURender();
        }else{
            currentFrame = render.simpleCPURender();
        }

        window.showFrame(currentFrame);
    }

    long startT = 0;
    private void refresh() {
        long endT = TimeHandler.getTimeInMillisSinceStartUp();

        if (startT != 0) {
            if (debug) System.out.println(endT - startT);
        }

        startT = TimeHandler.getTimeInMillisSinceStartUp();
    }
    public static Resolution getResolution(){
        return resolution;
    }
    public static RenderTriangle getRender(){
        return render;
    }
    public static void main(String[] args) {
        new MainHandler();
    }
    @Override
    public void run() {
        EventQueue.invokeLater(() -> {
            window = new MainWindow();
            hasOpenedWindow = true;
        });
    }
}