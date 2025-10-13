package com.gabriel.draw.service;

import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.service.RendererService;

import java.awt.*;

public class LineRendererService implements RendererService {
    @Override
    public void render(Graphics g, Shape shape, boolean preview) {
        Point start = shape.getLocation();
        Point end = shape.getEnd();

        if (preview) {
            g.setXORMode(Color.GRAY);
            g.drawLine(start.x, start.y, end.x, end.y);
            ((Graphics2D) g).setPaintMode();
        } else {
            g.setColor(shape.getColor());
            g.drawLine(start.x, start.y, end.x, end.y);
        }
    }
}
