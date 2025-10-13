package com.gabriel.draw.view;

import com.gabriel.draw.controller.ActionController;
import com.gabriel.drawfx.ActionCommand;

import javax.swing.*;
import java.awt.*;

public class DrawingToolBar extends JToolBar {


    private ImageIcon loadIcon(String name) {
        String path = "/com/anastacio/draw/view/" + name;
        java.net.URL url = getClass().getResource(path);
        if (url == null) {
            System.err.println(" Icon not found: " + path);
            return new ImageIcon(); // fallback to empty icon
        }
        ImageIcon original = new ImageIcon(url);
        Image scaled = original.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }


    public final JButton undoButton = new JButton(loadIcon("undo.png"));
    public final JButton redoButton = new JButton(loadIcon("redo.png"));

    public final JButton lineButton = new JButton(loadIcon("line.png"));
    public final JButton rectangleButton = new JButton(loadIcon("rectangle.png"));
    public final JButton ellipseButton = new JButton(loadIcon("ellipse.png"));

    public final JButton strokeButton = new JButton(loadIcon("color.png"));
    public final JButton fillButton = new JButton(loadIcon("fill.png"));

    public final JButton moveButton = new JButton(loadIcon("move.png"));
    public final JButton scaleButton = new JButton(loadIcon("scale.png"));

    public final JButton deleteButton = new JButton(loadIcon("delete.png"));

    public DrawingToolBar(ActionController actionController) {
        super("Tools");


        undoButton.setActionCommand(ActionCommand.UNDO);
        redoButton.setActionCommand(ActionCommand.REDO);

        lineButton.setActionCommand(ActionCommand.LINE);
        rectangleButton.setActionCommand(ActionCommand.RECT);
        ellipseButton.setActionCommand(ActionCommand.ELLIPSE);

        strokeButton.setActionCommand(ActionCommand.STROKE);
        fillButton.setActionCommand(ActionCommand.FILL);

        moveButton.setActionCommand(ActionCommand.MOVE);
        scaleButton.setActionCommand(ActionCommand.SCALE);

        deleteButton.setActionCommand(ActionCommand.DELETE);


        undoButton.addActionListener(actionController);
        redoButton.addActionListener(actionController);
        lineButton.addActionListener(actionController);
        rectangleButton.addActionListener(actionController);
        ellipseButton.addActionListener(actionController);
        strokeButton.addActionListener(actionController);
        fillButton.addActionListener(actionController);
        moveButton.addActionListener(actionController);
        scaleButton.addActionListener(actionController);
        deleteButton.addActionListener(actionController);


        add(undoButton);
        add(redoButton);
        addSeparator();
        add(lineButton);
        add(rectangleButton);
        add(ellipseButton);
        addSeparator();
        add(strokeButton);
        add(fillButton);
        addSeparator();
        add(moveButton);
        add(scaleButton);
        addSeparator();
        add(deleteButton);


        actionController.wireUndoRedo(undoButton, redoButton);
    }
}
