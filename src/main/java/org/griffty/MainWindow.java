package org.griffty;

import org.griffty.Utility.ResolutionType;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private final MainPanel mainPanel = new MainPanel();
    MainWindow(){
        initGUI();
        setTitle("Ultimate Game Hub");
        setResizable(false);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void initGUI() {
        switch (MainHandler.getResolution().getCurrentResolutionType()){
            case r1600x900 ->  setSize(1600, 900);
            case r1920x1080 -> setSize(1920, 1080);
        }
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu resolutionMenu = new JMenu("res");
        menuBar.add(resolutionMenu);
        JMenuItem r1920x1080 = new JMenuItem("1920x1080");
        r1920x1080.addActionListener(e -> setRes(ResolutionType.r1920x1080));
        JMenuItem r1600x900 = new JMenuItem("1600x900");
        r1600x900.addActionListener(e -> setRes(ResolutionType.r1600x900));
        resolutionMenu.add(r1920x1080);
        resolutionMenu.add(r1600x900);
        add(mainPanel);
    }

    public void showFrame(int[] frame){
        if (!isRightSize(frame)){
            System.out.println("Wrong frame size");
        }
        mainPanel.showFrame(frame);
    }

    private boolean isRightSize(int[] frame) {
        Dimension size = MainHandler.getResolution().getCurrentResolutionSize();
        return frame.length == size.height * size.width;
    }

    private void setRes(ResolutionType res){
        MainHandler.getResolution().setCurrentResolutionType(res);
        switch (res){
            case r1920x1080 -> {
                setExtendedState(Frame.MAXIMIZED_BOTH);
                setSize(MainHandler.getResolution().getCurrentResolutionSize());
            }
            case r1600x900 -> {
                setExtendedState(Frame.NORMAL);
                setSize(MainHandler.getResolution().getCurrentResolutionSize());
            }
        }
    }
}
