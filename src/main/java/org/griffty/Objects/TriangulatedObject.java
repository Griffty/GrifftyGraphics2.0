package org.griffty.Objects;

import org.griffty.Utility.Transform;
import org.griffty.Utility.Triangle;

import java.util.ArrayList;

public class TriangulatedObject extends WorldObject{
    private ArrayList<Triangle> allTriangles;
    public TriangulatedObject(int index, boolean isActive, Transform transform, ArrayList<Triangle> allTriangles) {
        super(index, isActive, transform);
        this.allTriangles = allTriangles;
    }

    public TriangulatedObject(int index, Transform transform, ArrayList<Triangle> allTriangles) {
        super(index, transform);
        this.allTriangles = allTriangles;
    }

    public TriangulatedObject(int index, ArrayList<Triangle> allTriangles) {
        super(index);
        this.allTriangles = allTriangles;
    }

    public ArrayList<Triangle> getAllTriangles() {
        return allTriangles;
    }

    public void setAllTriangles(ArrayList<Triangle> allTriangles) {
        this.allTriangles = allTriangles;
    }
}
