package com.gabriel.draw.command;

import com.gabriel.drawfx.command.Command;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.service.AppService;

import java.awt.Point;

public class ScaleShapeCommand implements Command {
    private final AppService appService;
    private final Shape shape;
    private final Point anchor;
    private final Point dragPoint;

    private Point originalLocation;
    private Point originalEnd;

    public ScaleShapeCommand(AppService appService, Shape shape, Point anchor, Point dragPoint) {
        this.appService = appService;
        this.shape = shape;
        this.anchor = anchor;
        this.dragPoint = dragPoint;
    }

    @Override
    public void execute() {
        originalLocation = shape.getLocation();
        originalEnd = shape.getEnd();

        int x1 = Math.min(anchor.x, dragPoint.x);
        int y1 = Math.min(anchor.y, dragPoint.y);
        int x2 = Math.max(anchor.x, dragPoint.x);
        int y2 = Math.max(anchor.y, dragPoint.y);

        shape.setLocation(new Point(x1, y1));
        shape.setEnd(new Point(x2, y2));

        appService.repaint();
    }

    @Override
    public void undo() {
        shape.setLocation(originalLocation);
        shape.setEnd(originalEnd);
        appService.repaint();
    }

    @Override
    public void redo() {
        execute();
    }
}
