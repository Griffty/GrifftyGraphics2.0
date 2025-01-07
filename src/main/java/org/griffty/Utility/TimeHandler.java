package org.griffty.Utility;

public class TimeHandler {
    public static double getTimeSinceStartUp() {
        return System.currentTimeMillis() / (double) 1000;
    }
    public static long getTimeInMillisSinceStartUp() {
        return System.currentTimeMillis();
    }
    private long timerTime = -1;
    public void startTimer(){
        if (timerTime == -1){
            timerTime = getTimeInMillisSinceStartUp();
        }else{
            throw new RuntimeException("Timer already started");
        }
    }
    public int getTimeInMillis(){
        if (timerTime != -1){
            return (int) (getTimeInMillisSinceStartUp() - timerTime);
        }else{
            throw new RuntimeException("Timer isn't running");
        }
    }
    public float getTime(){
        if (timerTime != -1){
            return (getTimeInMillisSinceStartUp() - timerTime) / (float) 1000;
        }else{
            throw new RuntimeException("Timer isn't running");
        }
    }
    public void addStartTime(int timeToAdd){
        timerTime +=timeToAdd;
    }
    public void subtractStartTime(int timeToSubtract){
        timerTime -=timeToSubtract;
    }
    public float stopTimer(){
        if (timerTime != -1){
            timerTime = -1;
            return (getTimeInMillisSinceStartUp() - timerTime) / (float) 1000;
        }else{
            throw new RuntimeException("Timer isn't running");
        }
    }
}
