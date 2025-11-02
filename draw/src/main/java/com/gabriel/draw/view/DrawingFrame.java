package com.gabriel.draw.view;

import com.gabriel.draw.component.PropertySheet;
import com.gabriel.draw.controller.ActionController;
import com.gabriel.draw.controller.DrawingController;
import com.gabriel.draw.controller.DrawingWindowController;
import com.gabriel.draw.service.DrawingAppService;
import com.gabriel.draw.service.DrawingCommandAppService;
import com.gabriel.drawfx.ShapeMode;
import com.gabriel.drawfx.model.Drawing;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.service.AppService;
import com.gabriel.property.PropertyOptions;
import com.gabriel.property.event.PropertyEventAdapter;
import com.gabriel.property.property.Property;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DrawingFrame extends JFrame {

    Drawing drawing;
    DrawingAppService drawingAppService;
    AppService appService;
    DrawingFrame drawingFrame;
    Container pane;
    private PropertySheet propertySheet;
    ActionController actionListener;
    DrawingMenuBar drawingMenuBar;
    DrawingToolBar drawingToolBar;
    DrawingView drawingView;
    DrawingController drawingController;
    JScrollPane jScrollPane;
    DrawingStatusPanel drawingStatusPanel;
    DrawingWindowController drawingWindowController;
    
    public DrawingFrame() {

        drawing = new Drawing();
        drawingAppService = new DrawingAppService();
        appService = DrawingCommandAppService.getInstance(drawingAppService);

        pane = getContentPane();
        setLayout(new BorderLayout());

        actionListener = new ActionController(appService);
        actionListener.setFrame(this);
        drawingMenuBar = new DrawingMenuBar( actionListener);

        setJMenuBar(drawingMenuBar);

        drawingMenuBar.setVisible(true);


        drawingToolBar = new DrawingToolBar(actionListener);
        drawingToolBar.setVisible(true);

        drawingView = new DrawingView(appService);
        actionListener.setComponent(drawingView);


        drawingController = new DrawingController(appService, drawingView);
        drawingController.setDrawingView(drawingView);

        drawingView.addMouseMotionListener(drawingController);
        drawingView.addMouseListener(drawingController);
        drawingView.setPreferredSize(new Dimension(4095, 8192));

        jScrollPane = new JScrollPane(drawingView);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        drawingStatusPanel = new DrawingStatusPanel();
        drawingController.setDrawingStatusPanel(drawingStatusPanel);

        pane.add(drawingToolBar, BorderLayout.PAGE_START);
        pane.add(jScrollPane, BorderLayout.CENTER );
        pane.add(drawingStatusPanel, BorderLayout.PAGE_END);

        drawingAppService.setDrawingView(drawingView);

        setVisible(true);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                handleExit();
            }
        });
        
        setSize(500,500);

        drawingWindowController = new DrawingWindowController(appService);
        this.addWindowListener(drawingWindowController);
        this.addWindowFocusListener(drawingWindowController);
        this.addWindowStateListener(drawingWindowController);
        buildGUI(pane);
        drawingController.setPropertySheet(propertySheet);
    }
    
    private void handleExit() {
        if(!drawing.getShapes().isEmpty()) {
            int result = JOptionPane.showConfirmDialog(
                this,
                "Do you want to save your changes before exiting?",
                "Save Changes?",
                JOptionPane.YES_NO_CANCEL_OPTION
            );
            
            if (result == JOptionPane.YES_OPTION) {
                String filename = drawing.getFilename();
                if (filename == null || filename.isEmpty()) {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setDialogTitle("Save Drawing");
                    int saveResult = fileChooser.showSaveDialog(this);
                    if (saveResult == JFileChooser.APPROVE_OPTION) {
                        filename = fileChooser.getSelectedFile().getAbsolutePath();
                        if(!filename.endsWith(".xml")) {
                            filename += ".xml";
                        }
                        drawing.setFilename(filename);
                        appService.save();
                        System.exit(0);
                    }
                } else {
                    appService.save();
                    System.exit(0);
                }
            } else if (result == JOptionPane.NO_OPTION) {
                System.exit(0);
            }
        } else {
            System.exit(0);
        }
    }

    public void buildGUI(Container pane){
        buildPropertyTable(pane);
        JScrollPane scrollPane = new JScrollPane(propertySheet);
        pane.add(scrollPane, BorderLayout.LINE_END);
        pack();
    }


    void buildPropertyTable(Container pane) {
        String[] headers = new String[]{"Property", "value"};
        Color backgroundColor = new Color(255, 255, 255);
        Color invalidColor = new Color(255, 179, 176);
        int rowHeight = 30;
        PropertyOptions options = new PropertyOptions(headers, backgroundColor, invalidColor, rowHeight);

        propertySheet = new PropertySheet(new PropertyOptions.Builder().build());
        propertySheet.addEventListener(new EventListener());
        propertySheet.populateTable(appService);

        repaint();
    }

    class EventListener extends PropertyEventAdapter {
        @Override
        public void onPropertyUpdated(Property property) {
            Shape shape  = appService.getSelectedShape();
            
            if(property.getName().equals("Current Tool")){
                appService.setShapeMode((ShapeMode) property.getValue());
            }
            else if(property.getName().equals("Fore color")){
                if(shape ==null) {
                    appService.setColor((Color) property.getValue());
                } else {
                    shape.setColor((Color) property.getValue());
                }
            }
            else if(property.getName().equals("Fill color")){
                if(shape ==null) {
                    appService.setFill((Color)property.getValue());
                } else {
                    shape.setFill((Color) property.getValue());
                }
            }
            else if(property.getName().equals("IsGradient")){
                appService.setIsGradient((Boolean)property.getValue());
                propertySheet.populateTable(appService);
            }
            else if(property.getName().equals("Start color")){
                appService.setStartColor((Color)property.getValue());
            }
            else if(property.getName().equals("End color")){
                appService.setEndColor((Color)property.getValue());
            }
            else if(property.getName().equals("Start x")){
                appService.setStartX((Integer)property.getValue());
            }
            else if(property.getName().equals("Start y")){
                appService.setStarty((Integer)property.getValue());
            }
            else if(property.getName().equals("End x")){
                appService.setEndx((Integer)property.getValue());
            }
            else if(property.getName().equals("End y")){
                appService.setEndy((Integer)property.getValue());
            }
            else if(property.getName().equals("IsVisible")){
                appService.setIsVisible((Boolean)property.getValue());
            }
            else if(property.getName().equals("Line Thickness")){
                appService.setThickness((Integer)property.getValue());
            }
            else if(property.getName().equals("X Location")){
                appService.setXLocation((Integer)property.getValue());
            }
            else if(property.getName().equals("Y Location")){
                appService.setYLocation((Integer)property.getValue());
            }
            else if(property.getName().equals("Width")){
                appService.setWidth((Integer)property.getValue());
            }
            else if(property.getName().equals("Height")){
                appService.setHeight((Integer)property.getValue());
            }
            else if(property.getName().equals("Text") || property.getName().equals("Default Text")){
                appService.setText((String)property.getValue());
            }
            else if(property.getName().equals("Font family")){
                Font currentFont = appService.getFont();
                Font newFont = new Font((String)property.getValue(), currentFont.getStyle(), currentFont.getSize());
                appService.setFont(newFont);
            }
            else if(property.getName().equals("Font size")){
                appService.setFontSize((Integer)property.getValue());
            }
            else if(property.getName().equals("Image Path")){
                appService.setImageFilename((String)property.getValue());
            }

            drawingView.repaint();
        }
    }
}

