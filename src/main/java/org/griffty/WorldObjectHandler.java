package org.griffty;

import org.griffty.Objects.Sphere;
import org.griffty.Objects.TriangulatedObject;
import org.griffty.Objects.WorldObject;
import org.griffty.Objects.WorldObjectType;
import org.griffty.Utility.Transform;
import org.griffty.Utility.Triangle;
import org.griffty.Utility.Vector3;

import java.util.ArrayList;

public class WorldObjectHandler {
    private static int maxIndex = 0;
    private static final ArrayList<WorldObject> allWorldObjects = new ArrayList<>();
    public static ArrayList<WorldObject> getAllWorldObjects(){
        return allWorldObjects;
    }
    public static WorldObject addNewWorldObject(WorldObject object){
        allWorldObjects.add(object);
        return object;
    }
    public static WorldObject addNewWorldObject(WorldObjectType type, String args, boolean isActive){
        int index = maxIndex;
        maxIndex++;
        Vector3 pos = new Vector3();
        Vector3 rot = new Vector3();
        Vector3 org = new Vector3();
        float rad = 1;
        ArrayList<Triangle> tri = new ArrayList<>();
        for (int i = 0; i < args.length(); i++){
            if (args.charAt(i) == '-' && args.charAt(i+1) == '-'){
                int nextArg = getIndexOfNextArg(i+5, args);
                switch (args.substring(i+2, i+5)){
                    case "pos" -> {
                        pos = Vector3.parseVector3(args.substring(i + 6, nextArg));
                    }
                    case "rot" -> {
                        rot = Vector3.parseVector3(args.substring(i + 6, nextArg));
                    }
                    case "org" -> {
                        org = Vector3.parseVector3(args.substring(i + 6, nextArg));
                    }
                    case "rad" -> {
                        rad = Float.parseFloat(args.substring(i + 6, nextArg));
                    }
                    case "tri" ->{
                        tri = Triangle.parseMultipleTriangles(args.substring(i + 6, nextArg));
                    }
                }
                i = nextArg;
            }
        }
        if (MainHandler.debug) {
            System.out.println("pos: " + pos + " rot: " + rot + " org: " + org + " rad: " + rad + " tri" + tri);
        }
        WorldObject worldObject;
        switch (type){
            case Sphere -> {
                worldObject = new Sphere(index, isActive, new Transform(pos, rot, org), rad);
            }
            case Triangulated -> {
                worldObject = new TriangulatedObject(index, isActive, new Transform(pos, rot, org),tri);
            }
            default -> throw new IllegalStateException("Unexpected value: " + type);
        }
        allWorldObjects.add(worldObject);
        return worldObject;
    }

    private static int getIndexOfNextArg(int i, String args) {
        for (; i < args.length(); i++){
            if (args.charAt(i) == '-' && args.charAt(i+1) == '-'){
                return i - 1;
            }
        }
        return args.length();
    }
    public static int getTrianglesNumber(){
        int n = 0;
        for (int i = 0; i < allWorldObjects.size(); i++){
            n += ((TriangulatedObject)allWorldObjects.get(i)).getAllTriangles().size();
        }
        return n;
    }
}
