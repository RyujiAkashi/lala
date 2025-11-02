package com.anastacio.draw.model;

import com.anastacio.draw.renderer.EllipseRenderer;
import com.anastacio.draw.renderer.RectangleRenderer;
import com.anastacio.drawfx.ShapeMode;
import com.anastacio.drawfx.model.Shape;

import java.awt.*;

public class Ellipse extends Shape {

    public Ellipse(Point start, Point end) {
        super(start, end);
        this.setShapeMode(ShapeMode.Ellipse);
        this.setRendererService(new EllipseRenderer());
    }

    public Ellipse(Point start) {
        super(start);
        this.setShapeMode(ShapeMode.Ellipse);
        this.setRendererService(new EllipseRenderer());
    }

    public Ellipse(Point start, int width, int height) {
        super(start, width, height);
        this.setShapeMode(ShapeMode.Ellipse);
        this.setRendererService(new EllipseRenderer());
    }
}
