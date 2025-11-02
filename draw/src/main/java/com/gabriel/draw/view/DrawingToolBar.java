package com.gabriel.draw.view;

import com.gabriel.drawfx.ActionCommand;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.net.URL;

public class DrawingToolBar extends JToolBar {

    private final ActionListener actionListener;

    public DrawingToolBar(ActionListener actionListener) {
        setFloatable(false);
        setRollover(true);
        this.actionListener = actionListener;
        addButtons();

        setPreferredSize(new Dimension(800, 40));
        setBackground(new Color(240, 240, 240));
    }

    protected void addButtons() {
        JButton button;

        // Undo, Redo, Delete group
        button = makeNavigationButton("undo", ActionCommand.UNDO, "Undo last action", "Undo");
        add(button);

        button = makeNavigationButton("redo", ActionCommand.REDO, "Redo last action", "Redo");
        add(button);

        button = makeNavigationButton("delete", ActionCommand.DELETE, "Delete selected shapes", "Delete");
        add(button);

        button = makeNavigationButton("select", ActionCommand.SELECT, "Select and move shapes", "Select");
        add(button);

        addSeparator();
        addSeparator();

        // Shape tools group
        button = makeNavigationButton("rectangle", ActionCommand.RECT, "Draw a rectangle", "Rectangle");
        add(button);

        button = makeNavigationButton("ellipse", ActionCommand.ELLIPSE, "Draw an ellipse", "Ellipse");
        add(button);

        button = makeNavigationButton("line", ActionCommand.LINE, "Draw a line", "Line");
        add(button);

        addSeparator();
        addSeparator();

        // New buttons: Color and Fill
        button = makeNavigationButton("color", ActionCommand.COLOR, "Choose color", "Color");
        add(button);

        button = makeNavigationButton("fill", ActionCommand.FILL, "Toggle fill mode", "Fill");
        add(button);

        addSeparator();
        addSeparator();


        button = makeNavigationButton("image", ActionCommand.IMAGE, "Add an image", "Image");
        add(button);

        button = makeNavigationButton("text", ActionCommand.TEXT, "Add text", "Text");
        add(button);

        button = makeNavigationButton("font", ActionCommand.FONT, "Select font", "Font");
        add(button);

    }

    protected JButton makeNavigationButton(String imageName,
                                           String actionCommand,
                                           String toolTipText,
                                           String altText) {
        String imgLocation = "images/" + imageName + ".png";
        URL imageURL = DrawingToolBar.class.getResource(imgLocation);

        JButton button = new JButton();
        button.setActionCommand(actionCommand);
        button.setToolTipText(toolTipText);
        button.addActionListener(actionListener);

        if (imageURL != null) {
            ImageIcon originalIcon = new ImageIcon(imageURL, altText);
            Image scaledImage = originalIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            button.setIcon(new ImageIcon(scaledImage));
        } else {
            button.setText(altText);
            System.err.println("Resource not found: " + imgLocation);
        }

        button.setPreferredSize(new Dimension(32, 32)); // Consistent button size
        return button;
    }
}
