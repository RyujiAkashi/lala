package com.gabriel.draw.command;

import com.gabriel.drawfx.command.Command;
import com.gabriel.drawfx.model.Drawing;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.service.AppService;

public class AddShapeCommand implements Command {
    private final AppService appService;
    private final Shape shape;

    public AddShapeCommand(AppService appService, Shape shape) {
        this.appService = appService;
        this.shape = shape;
    }

    @Override
    public void execute() {
        Drawing drawing = (Drawing) appService.getModel();
        drawing.getShapes().add(shape);
        appService.repaint();
    }

    @Override
    public void undo() {
        Drawing drawing = (Drawing) appService.getModel();
        drawing.getShapes().remove(shape);
        appService.repaint();
    }

    @Override
    public void redo() {
        execute();
    }
}
