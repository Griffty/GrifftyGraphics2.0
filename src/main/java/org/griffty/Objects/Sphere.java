package org.griffty.Objects;

import org.griffty.Utility.Transform;
import org.griffty.Utility.Vector3;

public class Sphere extends WorldObject{
    private float radius;
    public Sphere(int index, boolean isActive, Transform transform, float radius){
        super(index, isActive, transform);
        this.radius = radius;
    }
    public Sphere(int index, Transform transform, float radius){
        super(index, transform);
        this.radius = radius;
    }
    public Sphere(int index, float radius){
        super(index);
        this.radius = radius;
    }

    public float getRadius() {
        return radius;
    }
}
