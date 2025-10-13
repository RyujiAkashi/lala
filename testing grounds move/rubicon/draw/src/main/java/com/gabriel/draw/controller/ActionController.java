package com.gabriel.draw.controller;

import com.gabriel.draw.command.DeleteShapeCommand;
import com.gabriel.draw.command.SetColorCommand;
import com.gabriel.draw.command.SetFillCommand;
import com.gabriel.draw.command.SetShapeModeCommand;
import com.gabriel.draw.command.SetDrawModeCommand;
import com.gabriel.drawfx.ActionCommand;
import com.gabriel.drawfx.DrawMode;
import com.gabriel.drawfx.ShapeMode;
import com.gabriel.drawfx.command.CommandService;
import com.gabriel.drawfx.model.Drawing;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.service.AppService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ActionController implements ActionListener {
    private final AppService appService;
    private final Component parentForDialogs;

    private final List<AbstractButton> undoControls = new ArrayList<>();
    private final List<AbstractButton> redoControls = new ArrayList<>();

    public ActionController(AppService appService, Component parentForDialogs) {
        this.appService = appService;
        this.parentForDialogs = parentForDialogs;
    }

    public void wireUndoRedo(AbstractButton undo, AbstractButton redo) {
        if (undo != null) undoControls.add(undo);
        if (redo != null) redoControls.add(redo);
        refreshEnablement();
    }

    public void markNewCommand() {
        refreshEnablement();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        switch (cmd) {
            case ActionCommand.UNDO:
                CommandService.undo();
                refreshEnablement();
                appService.repaint();
                return;

            case ActionCommand.REDO:
                CommandService.redo();
                refreshEnablement();
                appService.repaint();
                return;

            case ActionCommand.LINE:
                CommandService.ExecuteCommand(new SetShapeModeCommand(appService, ShapeMode.Line));
                CommandService.ExecuteCommand(new SetDrawModeCommand(appService, DrawMode.Idle));
                refreshEnablement();
                return;

            case ActionCommand.RECT:
                CommandService.ExecuteCommand(new SetShapeModeCommand(appService, ShapeMode.Rectangle));
                CommandService.ExecuteCommand(new SetDrawModeCommand(appService, DrawMode.Idle));
                refreshEnablement();
                return;

            case ActionCommand.ELLIPSE:
                CommandService.ExecuteCommand(new SetShapeModeCommand(appService, ShapeMode.Ellipse));
                CommandService.ExecuteCommand(new SetDrawModeCommand(appService, DrawMode.Idle));
                refreshEnablement();
                return;

            case ActionCommand.STROKE: {
                Color selected = JColorChooser.showDialog(parentForDialogs, "Choose stroke color", appService.getColor());
                if (selected != null) {
                    CommandService.ExecuteCommand(new SetColorCommand(appService, selected));
                    refreshEnablement();
                }
                appService.repaint();
                return;
            }

            case ActionCommand.FILL: {
                Color selected = JColorChooser.showDialog(parentForDialogs, "Choose fill color", appService.getFill());
                if (selected != null) {
                    CommandService.ExecuteCommand(new SetFillCommand(appService, selected));
                    refreshEnablement();
                }
                appService.repaint();
                return;
            }

            case ActionCommand.MOVE:
                CommandService.ExecuteCommand(new SetDrawModeCommand(appService, DrawMode.Move));
                refreshEnablement();
                return;

            case ActionCommand.SCALE:
                CommandService.ExecuteCommand(new SetDrawModeCommand(appService, DrawMode.Scale));
                refreshEnablement();
                return;

            case ActionCommand.DELETE:
                deleteTopShape();
                refreshEnablement();
                return;
        }
    }

    private void deleteTopShape() {
        Object model = appService.getModel();
        if (model instanceof Drawing) {
            Drawing drawing = (Drawing) model;
            if (!drawing.getShapes().isEmpty()) {
                Shape top = drawing.getShapes().get(drawing.getShapes().size() - 1);


                appService.delete(top);
                appService.repaint();

                clearUndoStack();

                refreshEnablement();
            }
        }
    }

    private void clearUndoStack() {
        try {
            java.lang.reflect.Field undoField = CommandService.class.getDeclaredField("undoStack");
            undoField.setAccessible(true);
            ((java.util.Stack<?>) undoField.get(null)).clear();

            java.lang.reflect.Field redoField = CommandService.class.getDeclaredField("redoStack");
            redoField.setAccessible(true);
            ((java.util.Stack<?>) redoField.get(null)).clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void afterShapeMutatingCommand() {
        refreshEnablement();
    }

    public void refreshEnablement() {
        boolean canUndo = CommandService.canUndo();
        boolean canRedo = CommandService.canRedo();

        for (AbstractButton undo : undoControls) {
            undo.setEnabled(canUndo);
        }
        for (AbstractButton redo : redoControls) {
            redo.setEnabled(canRedo);
        }
    }
}
