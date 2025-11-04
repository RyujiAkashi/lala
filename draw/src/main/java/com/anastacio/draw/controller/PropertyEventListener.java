package com.anastacio.draw.controller;

import com.anastacio.property.event.PropertyEventAdapter;
import com.anastacio.property.property.Property;
import com.anastacio.property.property.selection.Item;
import com.anastacio.drawfx.service.AppService;
import com.anastacio.drawfx.model.Shape;
import com.anastacio.draw.view.DrawingView;
import com.anastacio.draw.component.PropertySheet;

import java.awt.Color;
import java.awt.Font;

public class PropertyEventListener extends PropertyEventAdapter {
    private AppService appService;
    private DrawingView drawingView;
    private PropertySheet propertySheet;

    public PropertyEventListener(AppService appService) {
        this(appService, null, null);
    }

    public PropertyEventListener(AppService appService, DrawingView drawingView) {
        this(appService, drawingView, null);
    }

    public PropertyEventListener(AppService appService, DrawingView drawingView, PropertySheet propertySheet) {
        this.appService = appService;
        this.drawingView = drawingView;
        this.propertySheet = propertySheet;
    }

    @Override
    public void onPropertyUpdated(Property property) {
        if (property.getName().equals("Fill color")) {
            appService.setFill((Color) property.getValue());
        } else if (property.getName().equals("Fore color")) {
            appService.setColor((Color) property.getValue());
        } else if (property.getName().equals("X Location")) {
            appService.setXLocation((int) property.getValue());
        } else if (property.getName().equals("Y Location")) {
            appService.setYLocation((int) property.getValue());
        } else if (property.getName().equals("Width")) {
            appService.setWidth((int) property.getValue());
        } else if (property.getName().equals("Height")) {
            appService.setHeight((int) property.getValue());
        } else if (property.getName().equals("Line Thickness")) {
            appService.setThickness((int) property.getValue());
        } else if (property.getName().equals("Text")) {
            appService.setText((String) property.getValue());
        } else if (property.getName().equals("Start color")) {
            appService.setStartColor((Color) property.getValue());
        } else if (property.getName().equals("End color")) {
            appService.setEndColor((Color) property.getValue());
        } else if (property.getName().equals("IsGradient")) {
            appService.setIsGradient((Boolean) property.getValue());
            if (propertySheet != null) {
                propertySheet.populateTable(appService);
            }
        } else if (property.getName().equals("IsVisible")) {
            appService.setIsVisible((Boolean) property.getValue());
        } else if (property.getName().equals("Start x")) {
            appService.setStartX((int) property.getValue());
        } else if (property.getName().equals("Start y")) {
            appService.setStarty((int) property.getValue());
        } else if (property.getName().equals("End x")) {
            appService.setEndx((int) property.getValue());
        } else if (property.getName().equals("End y")) {
            appService.setEndy((int) property.getValue());
        } else if (property.getName().equals("Font family")) {
            Font font = appService.getFont();
            Font newFont = new Font((String) property.getValue(), font.getStyle(), font.getSize());
            appService.setFont(newFont);
        } else if (property.getName().equals("Font style")) {
            Font font = appService.getFont();
            Font newFont = new Font(font.getFamily(), (int) property.getValue(), font.getSize());
            appService.setFont(newFont);
        } else if (property.getName().equals("Font size")) {
            Font font = appService.getFont();
            Font newFont = new Font(font.getFamily(), font.getStyle(), (int) property.getValue());
            appService.setFont(newFont);
        } else if (property.getName().equals("Fore Alpha")) {
            Shape selectedShape = appService.getSelectedShape();
            if (selectedShape != null) {
                int alpha = (int) property.getValue();
                Color currentColor = selectedShape.getColor();
                if (currentColor != null) {
                    Color newColor = new Color(currentColor.getRed(), currentColor.getGreen(),
                        currentColor.getBlue(), alpha);
                    appService.setColor(newColor);
                }
            }
        } else if (property.getName().equals("Fill Alpha")) {
            Shape selectedShape = appService.getSelectedShape();
            if (selectedShape != null) {
                int alpha = (int) property.getValue();
                Color currentColor = selectedShape.getFill();
                if (currentColor != null) {
                    Color newColor = new Color(currentColor.getRed(), currentColor.getGreen(),
                        currentColor.getBlue(), alpha);
                    appService.setFill(newColor);
                }
            }
        } else if (property.getName().equals("Start Alpha")) {
            Shape selectedShape = appService.getSelectedShape();
            if (selectedShape != null) {
                int alpha = (int) property.getValue();
                Color currentColor = selectedShape.getStartColor();
                if (currentColor != null) {
                    Color newColor = new Color(currentColor.getRed(), currentColor.getGreen(),
                        currentColor.getBlue(), alpha);
                    appService.setStartColor(newColor);
                }
            }
        } else if (property.getName().equals("End Alpha")) {
            Shape selectedShape = appService.getSelectedShape();
            if (selectedShape != null) {
                int alpha = (int) property.getValue();
                Color currentColor = selectedShape.getEndColor();
                if (currentColor != null) {
                    Color newColor = new Color(currentColor.getRed(), currentColor.getGreen(),
                        currentColor.getBlue(), alpha);
                    appService.setEndColor(newColor);
                }
            }
        } else if (property.getName().equals("Pinned")) {
            appService.setIsPinned((Boolean) property.getValue());
        }

        if (drawingView != null) {
            drawingView.repaint();
        }
    }
}
