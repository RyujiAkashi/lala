package com.gabriel.draw.controller;

import com.gabriel.draw.command.AddShapeCommand;
import com.gabriel.draw.command.MoveShapeCommand;
import com.gabriel.draw.command.ScaleShapeCommand;
import com.gabriel.draw.model.Ellipse;
import com.gabriel.draw.model.Line;
import com.gabriel.draw.model.Rectangle;
import com.gabriel.draw.view.DrawingView;
import com.gabriel.drawfx.DrawMode;
import com.gabriel.drawfx.ShapeMode;
import com.gabriel.drawfx.command.CommandService;
import com.gabriel.drawfx.model.Drawing;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.service.AppService;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class DrawingController implements MouseListener, MouseMotionListener {
    private final DrawingView drawingView;
    private final AppService appService;
    private final ActionController actionController;

    private boolean dragging = false;
    private Point dragPoint;

    private Shape currentShape;
    private Shape selectedShape;
    private Point selectedShapeOffset;
    private Point scaleAnchor;
    private int moveWidth;
    private int moveHeight;

    public DrawingController(AppService appService, DrawingView drawingView, ActionController actionController){
        this.appService = appService;
        this.drawingView = drawingView;
        this.actionController = actionController;
        drawingView.addMouseListener(this);
        drawingView.addMouseMotionListener(this);
        this.actionController.refreshEnablement();
    }

    public Shape getCurrentShape() {
        return currentShape;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        dragPoint = e.getPoint();
        DrawMode mode = appService.getDrawMode();

        if (mode == DrawMode.Move || mode == DrawMode.Scale) {
            selectedShape = findShapeAt(dragPoint);
            if (selectedShape != null) {
                if (mode == DrawMode.Move) {
                    Point topLeft = getTopLeft(selectedShape);
                    selectedShapeOffset = new Point(dragPoint.x - topLeft.x, dragPoint.y - topLeft.y);
                    moveWidth = Math.abs(selectedShape.getEnd().x - selectedShape.getLocation().x);
                    moveHeight = Math.abs(selectedShape.getEnd().y - selectedShape.getLocation().y);
                } else {
                    scaleAnchor = getClosestCorner(selectedShape, dragPoint);
                }
                dragging = true;
            }
            return;
        }

        if (mode == DrawMode.Idle) {
            switch (appService.getShapeMode()){
                case Line:
                    currentShape = new Line(dragPoint, dragPoint);
                    break;
                case Rectangle:
                    currentShape = new Rectangle(dragPoint, dragPoint);
                    break;
                case Ellipse:
                    currentShape = new Ellipse(dragPoint, dragPoint);
                    break;
            }
            currentShape.setColor(appService.getColor());
            currentShape.setFill(appService.getFill());
            dragging = true;
            drawingView.repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (!dragging) return;

        Point end = e.getPoint();
        DrawMode mode = appService.getDrawMode();

        if (mode == DrawMode.Move && selectedShape != null) {
            Point newTopLeft = new Point(end.x - selectedShapeOffset.x, end.y - selectedShapeOffset.y);
            CommandService.ExecuteCommand(new MoveShapeCommand(appService, selectedShape, newTopLeft));
        } else if (mode == DrawMode.Scale && selectedShape != null) {
            CommandService.ExecuteCommand(new ScaleShapeCommand(appService, selectedShape, scaleAnchor, end));
        } else if (mode == DrawMode.Idle && currentShape != null) {
            currentShape.setEnd(end);
            CommandService.ExecuteCommand(new AddShapeCommand(appService, currentShape));
            currentShape = null;
        }

        dragging = false;
        selectedShape = null;
        selectedShapeOffset = null;
        scaleAnchor = null;

        appService.repaint();
        actionController.markNewCommand();
        actionController.afterShapeMutatingCommand();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (!dragging) return;

        Point p = e.getPoint();
        DrawMode mode = appService.getDrawMode();

        if (mode == DrawMode.Move && selectedShape != null) {
            Point newTopLeft = new Point(p.x - selectedShapeOffset.x, p.y - selectedShapeOffset.y);
            selectedShape.setLocation(newTopLeft);
            selectedShape.setEnd(new Point(newTopLeft.x + moveWidth, newTopLeft.y + moveHeight));
            appService.repaint();
        } else if (mode == DrawMode.Scale && selectedShape != null) {
            Point anchor = scaleAnchor;
            int x1 = Math.min(anchor.x, p.x);
            int y1 = Math.min(anchor.y, p.y);
            int x2 = Math.max(anchor.x, p.x);
            int y2 = Math.max(anchor.y, p.y);
            selectedShape.setLocation(new Point(x1, y1));
            selectedShape.setEnd(new Point(x2, y2));
            appService.repaint();
        } else if (mode == DrawMode.Idle && currentShape != null) {
            currentShape.setEnd(p);
            drawingView.repaint();
        }
    }

    private Shape findShapeAt(Point p) {
        Drawing drawing = (Drawing) appService.getModel();
        java.util.List<Shape> shapes = drawing.getShapes();
        for (int i = shapes.size() - 1; i >= 0; i--) {
            Shape s = shapes.get(i);
            if (getBounds(s).contains(p)) {
                return s;
            }
        }
        return null;
    }

    private java.awt.Rectangle getBounds(Shape s) {
        int x = Math.min(s.getLocation().x, s.getEnd().x);
        int y = Math.min(s.getLocation().y, s.getEnd().y);
        int w = Math.abs(s.getEnd().x - s.getLocation().x);
        int h = Math.abs(s.getEnd().y - s.getLocation().y);
        return new java.awt.Rectangle(x, y, w, h);
    }

    private Point getTopLeft(Shape s) {
        return new Point(
                Math.min(s.getLocation().x, s.getEnd().x),
                Math.min(s.getLocation().y, s.getEnd().y)
        );
    }

    private Point getClosestCorner(Shape shape, Point p) {
        Point loc = shape.getLocation();
        Point end = shape.getEnd();

        Point topLeft = new Point(Math.min(loc.x, end.x), Math.min(loc.y, end.y));
        Point topRight = new Point(Math.max(loc.x, end.x), Math.min(loc.y, end.y));
        Point bottomLeft = new Point(Math.min(loc.x, end.x), Math.max(loc.y, end.y));
        Point bottomRight = new Point(Math.max(loc.x, end.x), Math.max(loc.y, end.y));

        Point[] corners = { topLeft, topRight, bottomLeft, bottomRight };
        Point closest = topLeft;
        double minDist = p.distance(topLeft);

        for (Point corner : corners) {
            double dist = p.distance(corner);
            if (dist < minDist) {
                minDist = dist;
                closest = corner;
            }
        }
        return closest;
    }

    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
    @Override public void mouseMoved(MouseEvent e) {}
}
