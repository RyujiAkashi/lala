package com.gabriel.draw;

import com.gabriel.draw.controller.ActionController;
import com.gabriel.draw.controller.DrawingController;
import com.gabriel.draw.service.DeawingCommandAppService;
import com.gabriel.draw.service.DrawingAppService;
import com.gabriel.draw.view.DrawingMenuBar;
import com.gabriel.draw.view.DrawingToolBar;
import com.gabriel.draw.view.DrawingView;
import com.gabriel.draw.view.DrawingFrame;
import com.gabriel.drawfx.service.AppService;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            AppService drawingAppService = new DrawingAppService();
            AppService appService = new DeawingCommandAppService(drawingAppService);


            DrawingFrame drawingFrame = new DrawingFrame(appService);
            drawingFrame.setTitle("Drawing App");
            drawingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


            ActionController actionController = new ActionController(appService, drawingFrame);
            DrawingMenuBar drawingMenuBar = new DrawingMenuBar(actionController);
            DrawingToolBar drawingToolBar = new DrawingToolBar(actionController);
            DrawingView drawingView = new DrawingView(appService);


            appService.setView(drawingView);


            DrawingController drawingController = new DrawingController(appService, drawingView, actionController);
            drawingView.setController(drawingController);


            JPanel root = new JPanel(new BorderLayout());
            root.add(drawingToolBar, BorderLayout.NORTH);
            root.add(drawingView, BorderLayout.CENTER);
            drawingFrame.setContentPane(root);
            drawingFrame.setJMenuBar(drawingMenuBar);
            drawingFrame.setSize(900, 600);
            drawingFrame.setLocationRelativeTo(null);
            drawingFrame.setVisible(true);
        });
    }
}
