package com.anastacio.draw.view;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

public class DrawingStatusPanel extends JPanel {
    private final JLabel statusLabel = new JLabel("Status:");
    private final JTextField xText = new JTextField(6);
    private final JTextField yText = new JTextField(6);

    public DrawingStatusPanel() {
        setBorder(new BevelBorder(BevelBorder.LOWERED));
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 2));

        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        xText.setEditable(false);
        yText.setEditable(false);
        xText.setHorizontalAlignment(JTextField.CENTER);
        yText.setHorizontalAlignment(JTextField.CENTER);

        xText.setPreferredSize(new Dimension(60, 24));
        yText.setPreferredSize(new Dimension(60, 24));

        add(statusLabel);
        add(new JLabel("X:"));
        add(xText);
        add(new JLabel("Y:"));
        add(yText);

        setPreferredSize(new Dimension(1440, 30));
        setBackground(new Color(230, 230, 230));
    }

    public void setPoint(Point p) {
        xText.setText(String.valueOf(p.x));
        yText.setText(String.valueOf(p.y));
    }
}
