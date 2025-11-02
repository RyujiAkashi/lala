package com.gabriel.draw.view;

import com.gabriel.draw.controller.ActionController;
import com.gabriel.drawfx.ActionCommand;
import com.gabriel.drawfx.service.AppService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.net.URL;

public class DrawingToolBar extends JToolBar {

    ActionListener actionListener;

    public DrawingToolBar( ActionListener actionListener){
        setFloatable(false);
        setRollover(true);
        this.actionListener = actionListener;
        addButtons();

        setPreferredSize(new Dimension(800, 40));
        setBackground(new Color(240, 240, 240));
    }

    protected void addButtons() {
        JButton button = null;
        
        // Undo, Redo, Delete group
        button = makeNavigationButton("undo", ActionCommand.UNDO, "Undo last action", "Undo");
        add(button);

        button = makeNavigationButton("redo", ActionCommand.REDO, "Redo last action", "Redo");
        add(button);

        button = makeNavigationButton("delete", ActionCommand.DELETE, "Delete selected shapes", "Delete");
        add(button);

        addSeparator();

        // Shape tools group
        button = makeNavigationButton("rectangle", ActionCommand.RECT, "Draw a rectangle", "Rectangle");
        add(button);

        button = makeNavigationButton("ellipse", ActionCommand.ELLIPSE, "Draw an ellipse", "Ellipse");
        add(button);

        button = makeNavigationButton("line", ActionCommand.LINE, "Draw a line", "Line");
        add(button);

        button = makeNavigationButton("select", ActionCommand.SELECT, "Select and move shapes", "Select");
        add(button);

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
        String imgLocation = "images/"
                + imageName
                + ".png";
        URL imageURL = DrawingToolBar.class.getResource(imgLocation);

        JButton button = new JButton();
        button.setActionCommand(actionCommand);
        button.setToolTipText(toolTipText);
        button.addActionListener(actionListener);

        if (imageURL != null) {
            button.setIcon(new ImageIcon(imageURL, altText));
        } else {
            button.setText(altText);
            System.err.println("Resource not found: "
                    + imgLocation);
        }
        return button;
    }

}
