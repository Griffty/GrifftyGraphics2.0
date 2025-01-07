package org.griffty.Utility;

public class Ray {
    public Vector3 origin;
    public Vector3 direction;
    Ray(){
        origin = new Vector3();
        direction = new Vector3(0,1,0);
    }
    Ray(Vector3 origin, Vector3 direction){
        this.direction = direction;
        this.origin = origin;
    }
}
