package org.griffty.Objects.Transformation;

import org.griffty.Objects.WorldObject;
import org.griffty.Utility.TimeHandler;
import org.griffty.Utility.Transform;

import java.util.ArrayList;

public class TransformationSequence {
    private final WorldObject parentObject;
    private final TimeHandler timer = new TimeHandler();
    private ArrayList<KeyFrame> keyFrames = new ArrayList<>();
    private int currentKeyFrame = 0;
    private int currentTime = 0;
    private int totalTime = 0;
    public TransformationSequence(WorldObject object){
        parentObject = object;
    }
    public TransformationSequence(WorldObject object, ArrayList<KeyFrame> keyFrames){
        parentObject = object;
        this.keyFrames = keyFrames;
    }
    public void start() {
        if (!keyFrames.isEmpty()){
            totalTime = getTotalTime();
            timer.startTimer();
        }
    }
    public void stop() {
        timer.stopTimer();
    }
    public Transform getNextTransformation() {

        currentTime = timer.getTimeInMillis();
        if (currentTime > totalTime) {
            timer.addStartTime(totalTime);
            currentTime = timer.getTimeInMillis();
            currentKeyFrame = 0;
        }
        if (currentTime > totalTime) throw new RuntimeException("too long period between getNextTransformation() call");
        int keyframeTime = getKeyFrames().get(currentKeyFrame).time();

        while (currentTime > keyframeTime){
            currentKeyFrame ++;
            keyframeTime = getKeyFrames().get(currentKeyFrame).time();
        }

        return getKeyFrames().get(currentKeyFrame).transform();
    }
    private int getTotalTime() {
        return keyFrames.get(keyFrames.size()-1).time();
    }
    public ArrayList<KeyFrame> getKeyFrames() {
        return keyFrames;
    }
    public void setKeyFrames(ArrayList<KeyFrame> keyFrames) {
        this.keyFrames = keyFrames;
        totalTime = getTotalTime();
    }
}
