package com.anastacio.draw.renderer;

import com.anastacio.draw.model.Text;
import com.anastacio.drawfx.model.Shape;
import com.anastacio.drawfx.renderer.ShapeRenderer;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class TextRenderer extends ShapeRenderer {

    @Override
    public void render(Graphics g, Shape shape, boolean xor) {
        if(!shape.isVisible()){
            return;
        }

        Text text = (Text) shape;

        int x = shape.getLocation().x;
        int y = shape.getLocation().y;
        int width = shape.getWidth();
        int height = shape.getHeight();

        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(shape.getThickness()));

        if (xor) {
            if(shape.getColor() != null) {
                g2.setXORMode(shape.getColor());
            } else {
                g2.setXORMode(Color.WHITE);
            }
            g2.drawRect(x, y, width, height);
        } else {
            if (shape.isGradient() && shape.getStartColor() != null && shape.getEndColor() != null && shape.getStart() != null && shape.getEnd() != null) {
                int startX = shape.getStart().x;
                int startY = shape.getStart().y;
                int endX = shape.getEnd().x;
                int endY = shape.getEnd().y;
                
                GradientPaint gradient = new GradientPaint(
                    startX, startY, shape.getStartColor(),
                    endX, endY, shape.getEndColor()
                );
                g2.setPaint(gradient);
            } else if (shape.getColor() != null) {
                g2.setColor(shape.getColor());
            }
            
            Font font = shape.getFont();
            if (font != null) {
                g2.setFont(font);
            }
            
            String textStr = shape.getText();
            if (textStr != null && !textStr.isEmpty()) {
                FontMetrics fm = g2.getFontMetrics();
                Rectangle2D rect = fm.getStringBounds(textStr, g2);
                
                int textX = x + (width - (int)rect.getWidth()) / 2;
                int textY = y + ((height - (int)rect.getHeight()) / 2) + fm.getAscent();
                
                g2.drawString(textStr, textX, textY);
            }
        }
        super.render(g, shape, xor);
    }
}
