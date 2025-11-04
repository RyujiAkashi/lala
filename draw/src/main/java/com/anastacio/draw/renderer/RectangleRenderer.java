package com.anastacio.draw.renderer;

import com.anastacio.drawfx.model.Shape;
import com.anastacio.drawfx.renderer.ShapeRenderer;

import java.awt.*;

public class RectangleRenderer extends ShapeRenderer {

    @Override
    public void render(Graphics g, Shape shape, boolean xor) {
        if(!shape.isVisible()){
            return;
        }

        int x = shape.getLocation().x;
        int y = shape.getLocation().y;
        int width = shape.getWidth() ;
        int height = shape.getHeight();

        Graphics2D g2 = (Graphics2D) g;
        Stroke originalStroke = g2.getStroke();
        g2.setStroke(getStroke(shape));

        if (xor) {
            if(shape.getColor() != null) {
                g2.setXORMode(shape.getColor());
            }
        } else {
            if(shape.getColor() != null) {
                g2.setColor(shape.getColor());
            }
            if(shape.getFill() != null){
                if(shape.isGradient()) {
                    GradientPaint gp = new GradientPaint(shape.getLocation().x+ shape.getStart().x, shape.getLocation().y + shape.getStart().y, shape.getStartColor(), shape.getLocation().x+ width+ shape.getEnd().x, shape.getLocation().y+ shape.getEnd().y + shape.getHeight(), shape.getEndColor());
                    g2.setPaint(gp);
                } else {
                    g2.setColor(shape.getFill());
                }
                g2.fillRect(x,y,width, height);
                if(shape.getColor() != null) {
                    g2.setColor(shape.getColor());
                }
            }
        }
        g2.drawRect(x, y, width, height);
        g2.setStroke(originalStroke);
        super.render(g, shape, xor);

    }

    private Stroke getStroke(Shape shape) {
        float thickness = shape.getThickness();
        String lineStyle = shape.getLineStyle();
        
        if ("Dashed".equals(lineStyle)) {
            float[] dash = {10.0f, 10.0f};
            return new BasicStroke(thickness, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);
        } else if ("Dotted".equals(lineStyle)) {
            float[] dash = {2.0f, 6.0f};
            return new BasicStroke(thickness, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);
        } else if ("Dash-Dot".equals(lineStyle)) {
            float[] dash = {10.0f, 5.0f, 2.0f, 5.0f};
            return new BasicStroke(thickness, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);
        } else {
            return new BasicStroke(thickness);
        }
    }
}
