package org.griffty;

import org.griffty.Objects.Transformation.KeyFrame;
import org.griffty.Objects.Transformation.TransformationType;
import org.griffty.Objects.WorldObject;
import org.griffty.Objects.WorldObjectType;
import org.griffty.Utility.Transform;
import org.griffty.Utility.Vector3;

import java.util.ArrayList;

public class Editor {

    public static void CreateObjects(int templateIndex) {
        switch (templateIndex){
            case 1 -> Template1();

            case 2 -> Template2();
        }
    }
    private static void Template1(){
        WorldObject obj2 = WorldObjectHandler.addNewWorldObject(WorldObjectType.Sphere, "--pos 200,160,0 --rad 40 --rot 0,0,0", true);
//        obj2.setTransformationType(TransformationType.Add);
//        obj2.setKeyFrames(new ArrayList<>(){
//            {
//                add(new KeyFrame(new Transform(new Vector3(9, 0, 0), new Vector3(), new Vector3()), 1950));
//                add(new KeyFrame(new Transform(new Vector3(-9, 0, 0), new Vector3(), new Vector3()), 3900));
//            }
//        });

        WorldObject obj1 = WorldObjectHandler.addNewWorldObject(WorldObjectType.Sphere, "--pos 200,750,0 --rad 40 --rot 0,0,0", true);
//        obj1.setKeyFrames(new ArrayList<>(){
//            {
//                add(new KeyFrame(new Transform(new Vector3(200, 750, 0), new Vector3(), new Vector3(100, 500, 0)), 300));
//                add(new KeyFrame(new Transform(new Vector3(300, 750, 0), new Vector3(), new Vector3(1000, 500, 0)), 600));
//                add(new KeyFrame(new Transform(new Vector3(400, 750, 0), new Vector3(), new Vector3(1000, 500, 0)), 900));
//                add(new KeyFrame(new Transform(new Vector3(500, 750, 0), new Vector3(), new Vector3(1000, 500, 0)), 1200));
//                add(new KeyFrame(new Transform(new Vector3(600, 750, 0), new Vector3(), new Vector3(1000, 500, 0)), 1500));
//                add(new KeyFrame(new Transform(new Vector3(700, 750, 0), new Vector3(), new Vector3(1000, 500, 0)), 1800));
//                add(new KeyFrame(new Transform(new Vector3(800, 750, 0), new Vector3(), new Vector3(1000, 500, 0)), 2100));
//                add(new KeyFrame(new Transform(new Vector3(900, 750, 0), new Vector3(), new Vector3(1000, 500, 0)), 2400));
//                add(new KeyFrame(new Transform(new Vector3(1000, 750, 0), new Vector3(), new Vector3(1000, 500, 0)), 2700));
//                add(new KeyFrame(new Transform(new Vector3(1100, 750, 0), new Vector3(), new Vector3(1000, 500, 0)), 3000));
//                add(new KeyFrame(new Transform(new Vector3(1200, 750, 0), new Vector3(), new Vector3(1000, 500, 0)), 3300));
//                add(new KeyFrame(new Transform(new Vector3(1300, 750, 0), new Vector3(), new Vector3(1000, 500, 0)), 3600));
//                add(new KeyFrame(new Transform(new Vector3(1400, 750, 0), new Vector3(), new Vector3(1000, 500, 0)), 3900));
//            }
//        });
    }
    private static void Template2(){
        WorldObject obj1 = WorldObjectHandler.addNewWorldObject(WorldObjectType.Triangulated, "--pos 150,160,0 --rot 0,0,0 --tri 25,10,-10:35,0,-30:12,40,-20", true);
    }
}
