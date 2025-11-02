package com.anastacio.draw.model;

import com.anastacio.draw.renderer.RectangleRenderer;
import com.anastacio.drawfx.ShapeMode;
import com.anastacio.drawfx.model.Shape;

import java.awt.*;

public class Rectangle extends Shape {

    public Rectangle(Point start) {
        super(start);
        this.setShapeMode(ShapeMode.Rectangle);
        this.setRendererService(new RectangleRenderer());
    }

    public Rectangle(Point start, Point end){
        super(start, end);
        this.setShapeMode(ShapeMode.Rectangle);
        this.setRendererService(new RectangleRenderer());
    }
    public Rectangle(Point start, int width, int height){
        super(start, width, height);
        this.setShapeMode(ShapeMode.Rectangle);
        this.setRendererService(new RectangleRenderer());
    }
}
