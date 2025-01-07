package org.griffty;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MainPanel extends JPanel {
    private BufferedImage currentFrame;
    public void showFrame(int[] frame) {
        Dimension size = MainHandler.getResolution().getCurrentResolutionSize();
        BufferedImage preFrame = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < size.height; y++){
            for (int x = 0; x < size.width; x++){
                preFrame.setRGB(x, y, frame[x + y * size.width]);
            }
        }
        currentFrame = preFrame;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(currentFrame, 0,0, null);
    }
}
