package org.griffty.Objects;


import org.griffty.MainHandler;
import org.griffty.Objects.Transformation.KeyFrame;
import org.griffty.Objects.Transformation.TransformationSequence;
import org.griffty.Objects.Transformation.TransformationType;
import org.griffty.Utility.Transform;
import org.griffty.Utility.Vector3;

import java.util.ArrayList;

// All Vector3 can be changed to int[3] arrays if necessary due to optimisation issues

public abstract class WorldObject {
    private final int index;
    private boolean isActive;
    private final Vector3 initPosition;
    private final Vector3 initRotation;
    private final Vector3 initOrigin;
    private Transform transform;
//    private TransformationSequence transformationSequence;
//    private TransformationType transformationType = TransformationType.Apply;

    public WorldObject(int index, boolean isActive, Transform transform) {
        this.index = index;
        this.isActive = isActive;
        this.transform = transform;
        this.initPosition = transform.position;
        this.initRotation = transform.rotation;
        this.initOrigin = transform.origin;
//        transformationSequence = new TransformationSequence(this);
//        if (isActive)transformationSequence.start();
    }

    public WorldObject(int index, Transform transform) {
        this.index = index;
        this.isActive = true;
        this.transform = transform;
        this.initPosition = transform.position;
        this.initRotation = transform.rotation;
        this.initOrigin = transform.origin;
//        transformationSequence.start();
    }

    public WorldObject(int index) {
        this.index = index;
        this.isActive = true;
        this.transform = new Transform();
        this.initPosition = transform.position;
        this.initRotation = transform.rotation;
        this.initOrigin = transform.origin;
//        transformationSequence.start();
    }

//    public void applyTransformation() {
//        if (transformationSequence != null) {
//            switch (transformationType){
//                case Apply -> {
//                    Transform transform1 = transformationSequence.getNextTransformation();
//                    transform.set(transform1);
//                }
//                case Add -> transform.add(transformationSequence.getNextTransformation());
//            }
//        }
//    }
    public void setActive(boolean active){
        isActive = active;
//        if (transformationSequence != null){
//            if (active)transformationSequence.start();
//            else transformationSequence.stop();
//        }else{
//            if (MainHandler.debug) throw new RuntimeException("World object doesn't have transformation sequence");
//        }
    }

    private void rotate(Vector3 rotationVector){
        transform.rotation.add(rotationVector);
    }
    private void move(Vector3 movementVector){
        transform.position.add(movementVector);
    }
    private void moveOrigin(Vector3 originMovementVector){
        transform.origin.add(originMovementVector);
    }
    private void setRotation(Vector3 newRotation){
        transform.rotation = newRotation;
    }
    private void setPosition(Vector3 newPosition){
        transform.position = newPosition;
    }
    private void setOrigin(Vector3 newOrigin){
        transform.origin = newOrigin;
    }
    public Vector3 getPosition() {
        return transform.position;
    }
    public Vector3 getRotation() {
        return transform.rotation;
    }
    public Vector3 getOrigin() {
        return transform.origin;
    }
    public Vector3 getInitPosition() {
        return initPosition;
    }
    public Vector3 getInitRotation() {
        return initRotation;
    }
    public Vector3 getInitOrigin() {
        return initOrigin;
    }

    public int getIndex() {
        return index;
    }

//    public TransformationType getTransformationType() {
//        return transformationType;
//    }

//    public void setTransformationType(TransformationType transformationType) {
//        this.transformationType = transformationType;
//    }

    public boolean isActive() {
        return isActive;
    }

//    public ArrayList<KeyFrame> getKeyFrames(){
//        if (transformationSequence!=null){
//            return transformationSequence.getKeyFrames();
//        }else{
//            throw new RuntimeException("World object doesn't have transformation sequence");
//        }
//    }
//    public void setKeyFrames(ArrayList<KeyFrame> keyFrames){
//        if (transformationSequence!=null){
//            transformationSequence.setKeyFrames(keyFrames);
//        }else{
//            transformationSequence = new TransformationSequence(this, keyFrames);
//            if (MainHandler.debug) throw new RuntimeException("World object doesn't have transformation sequence");
//        }
//    }
}
