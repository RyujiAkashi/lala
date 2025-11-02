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
        
        button = makeNavigationButton("select", ActionCommand.SELECT, "Select and move shapes", ActionCommand.SELECT);
        add(button);

        addSeparator();

        button = makeNavigationButton("rectangle", ActionCommand.RECT, "Draw a rectangle",ActionCommand.RECT);
        add(button);

        button = makeNavigationButton("ellipse", ActionCommand.ELLIPSE,"Draw an ellipse",ActionCommand.ELLIPSE);
        add(button);

        button = makeNavigationButton("line", ActionCommand.LINE, "Draw a line",ActionCommand.LINE);
        add(button);

        addSeparator();

        button = makeNavigationButton("text",ActionCommand.TEXT,"Add text",ActionCommand.TEXT);
        add(button);

        button = makeNavigationButton("image",ActionCommand.IMAGE,"Add an image",ActionCommand.IMAGE);
        add(button);

        addSeparator();

        button = makeNavigationButton("color", ActionCommand.COLOR, "Stroke color", "Color");
        add(button);

        button = makeNavigationButton("fill", ActionCommand.FILL, "Fill color", "Fill");
        add(button);

        addSeparator();

        button = makeNavigationButton("font",ActionCommand.FONT,"Select font",ActionCommand.FONT);
        add(button);

        addSeparator();

        button = new JButton("Delete");
        button.setActionCommand(ActionCommand.DELETE);
        button.setToolTipText("Delete selected shapes");
        button.addActionListener(actionListener);
        add(button);

        addSeparator();

        button = new JButton("Undo");
        button.setActionCommand(ActionCommand.UNDO);
        button.setToolTipText("Undo last action");
        button.addActionListener(actionListener);
        add(button);

        button = new JButton("Redo");
        button.setActionCommand(ActionCommand.REDO);
        button.setToolTipText("Redo last action");
        button.addActionListener(actionListener);
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
