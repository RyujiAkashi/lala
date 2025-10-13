package com.gabriel.draw.service;

import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.service.RendererService;

import java.awt.*;

public class RectangleRendererService implements RendererService {
    @Override
    public void render(Graphics g, Shape shape, boolean preview) {
        int x = Math.min(shape.getLocation().x, shape.getEnd().x);
        int y = Math.min(shape.getLocation().y, shape.getEnd().y);
        int w = Math.abs(shape.getEnd().x - shape.getLocation().x);
        int h = Math.abs(shape.getEnd().y - shape.getLocation().y);

        if (preview) {
            g.setXORMode(Color.GRAY);
            g.drawRect(x, y, w, h);
            ((Graphics2D) g).setPaintMode();
        } else {
            if (shape.getFill() != null) {
                g.setColor(shape.getFill());
                g.fillRect(x, y, w, h);
            }
            g.setColor(shape.getColor());
            g.drawRect(x, y, w, h);
        }
    }
}
