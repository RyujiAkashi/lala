package com.gabriel.draw.command;

import com.gabriel.drawfx.command.Command;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.service.AppService;

import java.awt.Point;

public class MoveShapeCommand implements Command {
    private final AppService appService;
    private final Shape shape;
    private final Point targetTopLeft;

    private Point originalLocation;
    private Point originalEnd;

    public MoveShapeCommand(AppService appService, Shape shape, Point targetTopLeft) {
        this.appService = appService;
        this.shape = shape;
        this.targetTopLeft = targetTopLeft;
    }

    @Override
    public void execute() {
        originalLocation = shape.getLocation();
        originalEnd = shape.getEnd();

        int width = Math.abs(originalEnd.x - originalLocation.x);
        int height = Math.abs(originalEnd.y - originalLocation.y);

        shape.setLocation(targetTopLeft);
        shape.setEnd(new Point(targetTopLeft.x + width, targetTopLeft.y + height));

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
