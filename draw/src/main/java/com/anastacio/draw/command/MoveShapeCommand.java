package com.anastacio.draw.command;

import com.anastacio.drawfx.command.Command;
import com.anastacio.drawfx.model.Shape;
import com.anastacio.drawfx.service.AppService;

import java.awt.Point;

public class MoveShapeCommand implements Command {
    private final AppService appService;
    private final Shape shape;
    private final Point start;
    private final Point end;
    private final int originalX;
    private final int originalY;

    public MoveShapeCommand(AppService appService, Shape shape, Point start, Point end) {
        this.appService = appService;
        this.shape = shape;
        this.start = new Point(start);
        this.end = new Point(end);
        this.originalX = shape.getLocation().x;
        this.originalY = shape.getLocation().y;
    }

    @Override
    public void execute() {
        int dx = end.x - start.x;
        int dy = end.y - start.y;
        shape.getLocation().x += dx;
        shape.getLocation().y += dy;
    }

    @Override
    public void undo() {
        shape.getLocation().x = originalX;
        shape.getLocation().y = originalY;
    }

    @Override
    public void redo() {
        execute();
    }
}
