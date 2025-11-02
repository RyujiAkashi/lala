package com.anastacio.draw.model;

import com.anastacio.draw.renderer.EllipseRenderer;
import com.anastacio.draw.renderer.ImageRenderer;
import com.anastacio.drawfx.ShapeMode;
import com.anastacio.drawfx.model.Shape;
import lombok.Data;

import java.awt.*;

@Data
public class Image extends Shape {
    public Image(Point start){
        super(start);
        this.setShapeMode(ShapeMode.Image);
        setRendererService(new ImageRenderer());
    }

    public Image(Point start, Point end, String imageFilename){
        super(start, end );
        this.setShapeMode(ShapeMode.Image);
        setRendererService(new ImageRenderer());
    }
    public Image(Point start, int width, int height) {
        super(start, width, height);
        this.setShapeMode(ShapeMode.Image);
        this.setRendererService(new ImageRenderer());
    }
}
