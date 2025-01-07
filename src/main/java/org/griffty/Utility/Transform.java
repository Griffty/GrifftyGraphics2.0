package org.griffty.Utility;

public class Transform {
    public Vector3 position;
    public Vector3 rotation;
    public Vector3 origin;
    public Transform(Vector3 position, Vector3 rotation, Vector3 origin) {
        this.position = position;
        this.rotation = rotation;
        this.origin = origin;
    }
    public Transform() {
        this.position = new Vector3();
        this.rotation = new Vector3();
        this.origin = new Vector3();
    }

    public void set(Transform nextTransformation) {
        this.position = nextTransformation.position;
        this.rotation = nextTransformation.rotation;
        this.origin = nextTransformation.origin;
    }
    public void add(Transform nextTransformation){
        this.position.add(nextTransformation.position);
        this.rotation.add(nextTransformation.rotation);
        this.origin.add(nextTransformation.origin);
    }
    public void subtract(Transform nextTransformation){
        this.position.subtract(nextTransformation.position);
        this.rotation.subtract(nextTransformation.rotation);
        this.origin.subtract(nextTransformation.origin);
    }
}