package com.anastacio.draw.view;

import com.anastacio.draw.controller.DrawingController;
import com.anastacio.draw.controller.DrawingWindowController;
import com.anastacio.drawfx.model.Drawing;
import com.anastacio.drawfx.model.Shape;
import com.anastacio.drawfx.service.AppService;

import java.util.List;
import javax.swing.*;
import java.awt.*;

public class DrawingView extends JPanel {

    AppService appService;

    public DrawingView(AppService appService){
        this.appService  = appService;
        JTextArea textArea = new JTextArea();
        add(textArea);
        textArea.setVisible(true);

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Drawing drawing = appService.getDrawing();
        List<Shape> shapes  = drawing.getShapes();
        for(Shape shape : shapes){
            shape.getRendererService().render(g, shape, false);
        }
    }


}
