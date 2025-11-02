package com.anastacio.drawfx.service;

import com.anastacio.drawfx.SelectionMode;
import com.anastacio.drawfx.model.Drawing;
import com.anastacio.drawfx.model.Shape;

import java.awt.*;
import java.util.List;
public final class SearchService {

    public Shape getSelectedShape(Drawing drawing){
        return drawing.getSelectedShape();
    }

    public void search(AppService appService, Point p) {
        search(appService, p,true);
    }

    public void search(AppService appService, Point p, boolean single) {
        Drawing drawing = appService.getDrawing();
        List<Shape> shapes = drawing.getShapes();
        int r = appService.getSearchRadius();
        boolean foundShape = false;
        Shape clickedShape = null;

        for (Shape shape : shapes) {
            Point loc = shape.getLocation();
            int width = shape.getWidth();
            int height = shape.getHeight();
            if (p.x > loc.x - r && p.x < loc.x + width + r && p.y > loc.y - r && p.y < loc.y + height + r) {
                if (found(shape, p, loc.x, loc.y, r)) {
                    shape.setSelectionMode(SelectionMode.UpperLeft);
                } else if (found(shape, p, loc.x, loc.y + height / 2, r)) {
                    shape.setSelectionMode(SelectionMode.MiddleLeft);
                } else if (found(shape, p, loc.x, loc.y + height, r)) {
                    shape.setSelectionMode(SelectionMode.LowerLeft);
                } else if (found(shape, p, loc.x + width / 2, loc.y, r)) {
                    shape.setSelectionMode(SelectionMode.MiddleTop);
                } else if (found(shape, p, loc.x + width, loc.y, r)) {
                    shape.setSelectionMode(SelectionMode.UpperRight);
                } else if (found(shape, p, loc.x + width, loc.y + height / 2, r)) {
                    shape.setSelectionMode(SelectionMode.MiddleRight);
                } else if (found(shape, p, loc.x + width, loc.y + height, r)) {
                    shape.setSelectionMode(SelectionMode.LowerRight);
                } else if (found(shape, p, loc.x + width / 2, loc.y + height, r)) {
                    shape.setSelectionMode(SelectionMode.MiddleBottom);
                } else {
                    shape.setSelectionMode(SelectionMode.None);
                }
                
                if (!single) {
                    shape.setSelected(!shape.isSelected());
                } else {
                    shape.setSelected(true);
                }
                clickedShape = shape;
                foundShape = true;
                break;
            }
        }
        
        if (single && foundShape) {
            for (Shape shape : shapes) {
                if (shape != clickedShape && shape.isSelected()) {
                    shape.setSelected(false);
                }
            }
        }
        
        if (foundShape) {
            if (clickedShape.isSelected()) {
                drawing.setSelectedShape(clickedShape);
            } else {
                Shape anySelected = null;
                for (Shape shape : shapes) {
                    if (shape.isSelected()) {
                        anySelected = shape;
                        break;
                    }
                }
                drawing.setSelectedShape(anySelected);
            }
        } else if (single) {
            drawing.setSelectedShape(null);
            for (Shape shape : shapes) {
                if(shape.isSelected()) {
                    shape.setSelected(false);
                }
            }
        }
    }
    boolean found(Shape shape, Point p, int x, int y, int r){
        return (p.x>x-r && p.x< x+r && p.y>y-r && p.y<y+r);
    }
}