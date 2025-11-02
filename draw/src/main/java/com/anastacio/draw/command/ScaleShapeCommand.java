package com.anastacio.draw.command;

import com.anastacio.drawfx.command.Command;
import com.anastacio.drawfx.model.Shape;
import com.anastacio.drawfx.service.AppService;

import java.awt.Point;

public class ScaleShapeCommand implements Command {
    private final AppService appService;
    private final Shape shape;
    private final Point start;
    private final Point end;
    private final int originalX;
    private final int originalY;
    private final int originalWidth;
    private final int originalHeight;

    public ScaleShapeCommand(AppService appService, Shape shape, Point start, Point end) {
        this.appService = appService;
        this.shape = shape;
        this.start = new Point(start);
        this.end = new Point(end);
        this.originalX = shape.getLocation().x;
        this.originalY = shape.getLocation().y;
        this.originalWidth = shape.getWidth();
        this.originalHeight = shape.getHeight();
    }

    @Override
    public void execute() {
        appService.scale(shape, start, end);
    }

    @Override
    public void undo() {
        shape.getLocation().x = originalX;
        shape.getLocation().y = originalY;
        shape.setWidth(originalWidth);
        shape.setHeight(originalHeight);
    }

    @Override
    public void redo() {
        execute();
    }
}
