package com.anastacio.draw.model;

import java.awt.*;

import com.anastacio.draw.renderer.RectangleRenderer;
import com.anastacio.draw.renderer.TextRenderer;
import com.anastacio.drawfx.ShapeMode;
import com.anastacio.drawfx.model.Shape;

public class Text extends  Shape {

    public Text(Point location) {
        super(location);
        this.setShapeMode(ShapeMode.Text);
        this.setRendererService(new TextRenderer());
    }

    public Text(Point start, int width, int height) {
        super(start, width, height);
        this.setShapeMode(ShapeMode.Text);
        this.setRendererService(new TextRenderer());
    }
}
