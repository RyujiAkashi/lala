package com.gabriel.draw.view;

import com.gabriel.draw.controller.ActionController;
import com.gabriel.drawfx.ActionCommand;

import javax.swing.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class DrawingMenuBar extends JMenuBar {

    private final JMenuItem undoMenuItem = new JMenuItem("Undo");
    private final JMenuItem redoMenuItem = new JMenuItem("Redo");
    private final JMenuItem deleteMenuItem = new JMenuItem("Delete");

    private final JMenuItem lineMenuItem = new JMenuItem("Line");
    private final JMenuItem rectangleMenuItem = new JMenuItem("Rectangle");
    private final JMenuItem ellipseMenuItem = new JMenuItem("Ellipse");

    private final JMenuItem strokeColorMenuItem = new JMenuItem("Stroke color...");
    private final JMenuItem fillColorMenuItem = new JMenuItem("Fill color...");

    private final JMenuItem moveMenuItem = new JMenuItem("Move mode");
    private final JMenuItem scaleMenuItem = new JMenuItem("Scale mode");

    public DrawingMenuBar(ActionController actionController){
        super();

        JMenu editMenu = new JMenu("Edit");
        editMenu.setMnemonic(KeyEvent.VK_E);
        add(editMenu);

        undoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK));
        undoMenuItem.setActionCommand(ActionCommand.UNDO);
        undoMenuItem.addActionListener(actionController);
        undoMenuItem.setEnabled(false);
        editMenu.add(undoMenuItem);

        redoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
        redoMenuItem.setActionCommand(ActionCommand.REDO);
        redoMenuItem.addActionListener(actionController);
        redoMenuItem.setEnabled(false);
        editMenu.add(redoMenuItem);

        deleteMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
        deleteMenuItem.setActionCommand(ActionCommand.DELETE);
        deleteMenuItem.addActionListener(actionController);
        editMenu.add(deleteMenuItem);

        JMenu drawMenu = new JMenu("Draw");
        drawMenu.setMnemonic(KeyEvent.VK_D);
        add(drawMenu);

        lineMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_DOWN_MASK));
        rectangleMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK));
        ellipseMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK));

        lineMenuItem.setActionCommand(ActionCommand.LINE);
        rectangleMenuItem.setActionCommand(ActionCommand.RECT);
        ellipseMenuItem.setActionCommand(ActionCommand.ELLIPSE);

        lineMenuItem.addActionListener(actionController);
        rectangleMenuItem.addActionListener(actionController);
        ellipseMenuItem.addActionListener(actionController);

        drawMenu.add(lineMenuItem);
        drawMenu.add(rectangleMenuItem);
        drawMenu.add(ellipseMenuItem);

        JMenu colorMenu = new JMenu("Color");
        colorMenu.setMnemonic(KeyEvent.VK_C);
        add(colorMenu);

        strokeColorMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, InputEvent.CTRL_DOWN_MASK));
        fillColorMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, InputEvent.CTRL_DOWN_MASK));

        strokeColorMenuItem.setActionCommand(ActionCommand.STROKE);
        fillColorMenuItem.setActionCommand(ActionCommand.FILL);

        strokeColorMenuItem.addActionListener(actionController);
        fillColorMenuItem.addActionListener(actionController);

        colorMenu.add(strokeColorMenuItem);
        colorMenu.add(fillColorMenuItem);

        JMenu toolsMenu = new JMenu("Tools");
        toolsMenu.setMnemonic(KeyEvent.VK_T);
        add(toolsMenu);

        moveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, InputEvent.CTRL_DOWN_MASK));
        scaleMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));

        moveMenuItem.setActionCommand(ActionCommand.MOVE);
        scaleMenuItem.setActionCommand(ActionCommand.SCALE);

        moveMenuItem.addActionListener(actionController);
        scaleMenuItem.addActionListener(actionController);

        toolsMenu.add(moveMenuItem);
        toolsMenu.add(scaleMenuItem);

        actionController.wireUndoRedo(undoMenuItem, redoMenuItem);
    }
}
