package org.griffty.Utility;

import org.griffty.MainHandler;

import java.awt.*;

public class Resolution{
    private ResolutionType currentResolutionType;

    public Resolution(ResolutionType resolutionType){
        this.currentResolutionType = resolutionType;
    }

    public ResolutionType getCurrentResolutionType() {
        return currentResolutionType;
    }
    public void setCurrentResolutionType(ResolutionType resolutionType) {
        this.currentResolutionType = resolutionType;
        MainHandler.getRender().setUpKernel();
    }
    public Dimension getCurrentResolutionSize(){
        return getResolutionSize(currentResolutionType);
    }
    private final Dimension R1600X900D = new Dimension(1600, 900);
    private final Dimension R1920x1080 = new Dimension(1920, 1080);

    public Dimension getResolutionSize(ResolutionType resType){
        switch (resType){
            case r1920x1080 -> {
                return R1920x1080;
            }
            case r1600x900 -> {
                return R1600X900D;
            }
        }
        throw new RuntimeException("Cannot get resolution size");
    }
}
