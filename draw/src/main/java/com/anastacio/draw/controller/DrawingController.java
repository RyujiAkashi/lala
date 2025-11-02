package com.anastacio.draw.controller;

import com.anastacio.draw.component.PropertySheet;
import com.anastacio.draw.model.Image;
import com.anastacio.draw.model.Rectangle;
import com.anastacio.draw.view.DrawingStatusPanel;
import com.anastacio.drawfx.DrawMode;
import com.anastacio.drawfx.model.Drawing;
import com.anastacio.drawfx.util.Normalizer;
import com.anastacio.drawfx.SelectionMode;
import com.anastacio.drawfx.ShapeMode;
import com.anastacio.draw.view.DrawingView;
import com.anastacio.drawfx.service.AppService;
import com.anastacio.drawfx.model.Shape;
import com.anastacio.draw.model.Line;
import com.anastacio.draw.model.Text;
import com.anastacio.draw.model.Rectangle;
import com.anastacio.draw.model.Ellipse;
import lombok.Setter;

import java.util.List;
import java.awt.*;
import java.awt.event.*;

public class DrawingController  implements MouseListener, MouseMotionListener, KeyListener {
    Point start;
    Point originalStart;
    private Point end;
    private java.util.Map<Shape, Point> originalPositions = new java.util.HashMap<>();
    private Point originalScaleLocation;
    private int originalScaleWidth;
    private int originalScaleHeight;

    private final AppService appService;
    private final Drawing drawing;

    @Setter
    private DrawingView drawingView;

    @Setter
    private DrawingStatusPanel drawingStatusPanel;

    @Setter
    private PropertySheet propertySheet;

    private Shape currentShape = null;

     public DrawingController(AppService appService, DrawingView drawingView){
       this.appService = appService;
       this.drawing = appService.getDrawing();
       this.drawingView = drawingView;
       drawingView.addMouseListener(this);
       drawingView.addMouseMotionListener(this);
     }
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(appService.getDrawMode() == DrawMode.Idle) {
            start = e.getPoint();
            originalStart = new Point(start);
            originalPositions.clear();
            ShapeMode currentShapeMode = appService.getShapeMode();
            if(currentShapeMode == ShapeMode.Select) {
                appService.search(start, !e.isControlDown());
                Shape selectedShape = drawing.getSelectedShape();
                if(selectedShape != null && !selectedShape.isPinned()){
                    if(selectedShape.getSelectionMode() == SelectionMode.None){
                        List<Shape> shapes = drawing.getShapes();
                        for(Shape shape : shapes) {
                            if (shape.isSelected() && !shape.isPinned()) {
                                originalPositions.put(shape, new Point(shape.getLocation()));
                            }
                        }
                    } else {
                        originalScaleLocation = new Point(selectedShape.getLocation());
                        originalScaleWidth = selectedShape.getWidth();
                        originalScaleHeight = selectedShape.getHeight();
                    }
                }

            }
            else {
                if(currentShape!=null){
                    currentShape.setSelected(false);
                }
                switch (currentShapeMode) {
                    case Line:
                        currentShape = new Line(start);
                        currentShape.setColor(appService.getColor());
                        currentShape.setThickness(appService.getThickness());
                        currentShape.setLineStyle(drawing.getLineStyle());
                        currentShape.getRendererService().render(drawingView.getGraphics(), currentShape, false);
                        break;
                    case Rectangle:
                        currentShape = new Rectangle(start);
                        currentShape.setColor(appService.getColor());
                        currentShape.setThickness(appService.getThickness());
                        currentShape.setLineStyle(drawing.getLineStyle());
                        currentShape.getRendererService().render(drawingView.getGraphics(), currentShape, false);
                        break;
                    case Text:
                        currentShape = new Text(start);
                        currentShape.setColor(appService.getColor());
                        currentShape.setThickness(appService.getThickness());
                        currentShape.setLineStyle(drawing.getLineStyle());
                        currentShape.setText(drawing.getText());
                        currentShape.getRendererService().render(drawingView.getGraphics(), currentShape, true);
                        appService.setDrawMode(DrawMode.MousePressed);
                        break;
                    case Ellipse:
                        currentShape = new Ellipse(start);
                        currentShape.setColor(appService.getColor());
                        currentShape.setThickness(appService.getThickness());
                        currentShape.setLineStyle(drawing.getLineStyle());
                        currentShape.getRendererService().render(drawingView.getGraphics(), currentShape, false);
                        break;
                    case Image:
                        currentShape = new Image(start);
                        currentShape.setImageFilename(drawing.getImageFilename());
                        currentShape.setColor(appService.getColor());
                        currentShape.setThickness(appService.getThickness());
                        currentShape.setLineStyle(drawing.getLineStyle());
                }

/*                if(currentShape!=null) {
                    currentShape.getRendererService().render(drawingView.getGraphics(), currentShape, false);
                }
  */          }
            appService.setDrawMode(DrawMode.MousePressed);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        end = e.getPoint();
        if(appService.getDrawMode() == DrawMode.MousePressed) {
            if (appService.getShapeMode() == ShapeMode.Select) {
                Shape selectedShape = drawing.getSelectedShape();
                if (selectedShape != null) {
                    if (selectedShape.getSelectionMode() == SelectionMode.None) {
                        if (!originalStart.equals(end)) {
                            for (Shape shape : originalPositions.keySet()) {
                                Point originalPos = originalPositions.get(shape);
                                shape.getLocation().x = originalPos.x;
                                shape.getLocation().y = originalPos.y;
                            }
                            for (Shape shape : originalPositions.keySet()) {
                                appService.move(shape, originalStart, end);
                            }
                        }
                        drawingView.repaint();
                    } else {
                        if (!originalStart.equals(end)) {
                            selectedShape.getLocation().x = originalScaleLocation.x;
                            selectedShape.getLocation().y = originalScaleLocation.y;
                            selectedShape.setWidth(originalScaleWidth);
                            selectedShape.setHeight(originalScaleHeight);
                            appService.scale(selectedShape, originalStart, end);
                        }
                        Normalizer.normalize(selectedShape);
                        drawingView.repaint();
                    }
                }
            }
            else {
                currentShape.getRendererService().render(drawingView.getGraphics(), currentShape, true);
                appService.scale(currentShape, end);
                currentShape.setText(drawing.getText());
                currentShape.setFont(drawing.getFont());
                currentShape.setGradient(drawing.isGradient());
                currentShape.setFill(drawing.getFill());
                currentShape.setStartColor(drawing.getStartColor());
                currentShape.setEndColor(drawing.getEndColor());
                Normalizer.normalize(currentShape);
                appService.create(currentShape);
                currentShape.setSelected(true);
//              currentShape.getRendererService().render(drawingView.getGraphics(), currentShape, false);
                drawing.setSelectedShape(currentShape);
                drawing.setShapeMode(ShapeMode.Select);
                if (appService instanceof com.anastacio.draw.service.DrawingAppService) {
                    ((com.anastacio.draw.service.DrawingAppService) appService).notifySelectionChanged();
                } else if (appService instanceof com.anastacio.draw.service.DrawingCommandAppService) {
                    ((com.anastacio.draw.service.DrawingCommandAppService) appService).getAppService().notifySelectionChanged();
                }
                drawingView.repaint();
            }
            appService.setDrawMode(DrawMode.Idle);
        }
        drawingView.repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(appService.getDrawMode() == DrawMode.MousePressed) {
            end = e.getPoint();
            if(drawing.getShapeMode() == ShapeMode.Select){
                Shape selectedShape = drawing.getSelectedShape();
                if(selectedShape != null && !selectedShape.isPinned()){
                    if(selectedShape.getSelectionMode() == SelectionMode.None){
                        List<Shape> shapes =drawing.getShapes();
                        for(Shape shape : shapes) {
                            if (shape.isSelected() && !shape.isPinned()) {
                                shape.getRendererService().render(drawingView.getGraphics(), shape, true);
                                int dx = end.x - start.x;
                                int dy = end.y - start.y;
                                shape.getLocation().x += dx;
                                shape.getLocation().y += dy;
                                shape.getRendererService().render(drawingView.getGraphics(), shape, true);
                            }
                        }
                    }
                    else {
                        selectedShape.getRendererService().render(drawingView.getGraphics(), selectedShape, true);
                        int dx = end.x - start.x;
                        int dy = end.y - start.y;
                        int height = selectedShape.getHeight();
                        int width = selectedShape.getWidth();
                        if(selectedShape.getSelectionMode() == SelectionMode.UpperLeft) {
                            selectedShape.getLocation().x += dx;
                            selectedShape.getLocation().y += dy;
                            selectedShape.setWidth(width - dx);
                            selectedShape.setHeight(height - dy);
                        } if(selectedShape.getSelectionMode() == SelectionMode.LowerLeft) {
                            selectedShape.getLocation().x += dx;
                            selectedShape.setWidth(width -dx);
                            selectedShape.setHeight(height + dy);
                        } else if(selectedShape.getSelectionMode() == SelectionMode.UpperRight){
                            selectedShape.getLocation().y += dy;
                            selectedShape.setWidth(width + dx);
                            selectedShape.setHeight(height - dy);
                        } if(selectedShape.getSelectionMode() == SelectionMode.LowerRight){
                            selectedShape.setWidth(width + dx);
                            selectedShape.setHeight(height+ dy);
                        } else if(selectedShape.getSelectionMode() == SelectionMode.MiddleRight){
                            selectedShape.setWidth(width + dx);
                        } else if(selectedShape.getSelectionMode() == SelectionMode.MiddleLeft){
                            selectedShape.setWidth(width - dx);
                            selectedShape.getLocation().x += dx;
                        } else if(selectedShape.getSelectionMode() == SelectionMode.MiddleTop) {
                            selectedShape.setHeight(height - dy);
                            selectedShape.getLocation().y += dy;
                        } else if(selectedShape.getSelectionMode() == SelectionMode.MiddleBottom){
                            selectedShape.setHeight(height + dy);
                        }
                        selectedShape.getRendererService().render(drawingView.getGraphics(), selectedShape, true);
                    }
                }
                start = end;

            }
            else {
                currentShape.getRendererService().render(drawingView.getGraphics(), currentShape, true);
                appService.scale(currentShape, end);
                currentShape.getRendererService().render(drawingView.getGraphics(), currentShape, true);
            }
       }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        drawingStatusPanel.setPoint(e.getPoint());
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
//        if (e.getKeyCode() == KeyEvent.VK_DELETE) {
//          appService.delete();
//        }
    }


    @Override
    public void keyReleased(KeyEvent e) {

    }
}
