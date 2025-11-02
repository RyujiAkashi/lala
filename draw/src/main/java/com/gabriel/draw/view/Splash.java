package com.gabriel.draw.view;

import com.gabriel.draw.util.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Splash extends JPanel implements MouseListener {
    private BufferedImage image;
    private GPanel gPanel;
    private ImageLoader imageLoader;

    public Splash() {
        try {
            imageLoader = new ImageLoader();
            image = imageLoader.loadImage("/love.jpg");
        } catch (IOException ex) {
            System.err.println("Failed to load splash image: " + ex.getMessage());
        }

        setLayout(null);

        gPanel = new GPanel("GoDraw");
        gPanel.setSize(250, 150);
        gPanel.addMouseListener(this);
        this.add(gPanel);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        }

        // Position GoDraw near bottom-right
        int marginX = 30;
        int marginY = 30;
        int panelWidth = gPanel.getWidth();
        int panelHeight = gPanel.getHeight();
        gPanel.setLocation(getWidth() - panelWidth - marginX, getHeight() - panelHeight - marginY);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getSource() == gPanel) {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);

            DrawingFrame mf = new DrawingFrame();
            mf.setExtendedState(JFrame.MAXIMIZED_BOTH);
            mf.setVisible(true);

            topFrame.setVisible(false);
            this.dispatchEvent(new WindowEvent(topFrame, WindowEvent.WINDOW_CLOSING));
        }
    }

    // Unused MouseListener methods
    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}
