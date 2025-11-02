package com.anastacio.drawfx.renderer;

import com.anastacio.drawfx.ShapeMode;
import com.anastacio.drawfx.model.Shape;

import java.awt.*;

public class ShapeRenderer implements Renderer {

    @Override
    public void render(Graphics g,  Shape shape, boolean xor) {
        if(shape.isSelected()){
            Point loc = shape.getLocation();
            int width = shape.getWidth();
            int height = shape.getHeight();
            int r = 5;

            if(xor){
                 Color color = shape.getColor();
                 if (color != null) {
                     g.setXORMode(color);
                 } else {
                     g.setXORMode(Color.WHITE);
                 }
            }
            else {
                g.setColor(Color.BLUE);
            }

            if (shape.getShapeMode() == ShapeMode.Line) {
                g.fillRect(loc.x-r,loc.y-r, 2*r,2*r);
                g.fillRect(loc.x + width -r,loc.y+height-r, 2*r, 2*r);
            } else {
                g.fillRect(loc.x-r,loc.y-r, 2*r,2*r);
                g.fillRect(loc.x-r,loc.y+height-r, 2*r, 2*r);
                g.fillRect(loc.x + width -r,loc.y -r, 2*r, 2*r);
                g.fillRect(loc.x + width -r,loc.y+height-r, 2*r, 2*r);

                g.fillRect(loc.x + width/2 -r,loc.y-r, 2*r, 2*r);
                g.fillRect(loc.x -r,loc.y+height/2-r, 2*r, 2*r);
                g.fillRect(loc.x + width -r,loc.y+height/2-r, 2*r, 2*r);
                g.fillRect(loc.x + width/2 -r,loc.y+height-r, 2*r, 2*r);
            }
        }
    }
}
