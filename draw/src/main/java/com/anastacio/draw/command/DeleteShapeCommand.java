package com.anastacio.draw.command;

import com.anastacio.drawfx.command.Command;
import com.anastacio.drawfx.model.Shape;
import com.anastacio.drawfx.service.AppService;

public class DeleteShapeCommand implements Command {
    private final AppService appService;
    private final Shape shape;

    public DeleteShapeCommand(AppService appService, Shape shape) {
        this.appService = appService;
        this.shape = shape;
    }

    @Override
    public void execute() {
        appService.getDrawing().getShapes().remove(shape);
    }

    @Override
    public void undo() {
        appService.getDrawing().getShapes().add(shape);
    }

    @Override
    public void redo() {
        execute();
    }
}
