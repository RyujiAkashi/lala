package com.gabriel.draw.view;

import com.gabriel.draw.controller.DrawingController;
import com.gabriel.drawfx.model.Drawing;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.service.AppService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DrawingView extends JPanel {

    private final AppService appService;
    private DrawingController controller;

    public DrawingView(AppService appService) {
        this.appService = appService;
        setBackground(Color.WHITE);
        setDoubleBuffered(true);
    }

    public void setController(DrawingController controller) {
        this.controller = controller;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Drawing drawing = (Drawing) appService.getModel();
        List<Shape> shapes = drawing.getShapes();

        for (Shape shape : shapes) {
            shape.getRendererService().render(g, shape, false);
        }

        if (controller != null) {
            Shape preview = controller.getCurrentShape();
            if (preview != null) {
                preview.getRendererService().render(g, preview, true);
            }
        }
    }
}
