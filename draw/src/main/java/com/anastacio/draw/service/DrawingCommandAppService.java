package com.anastacio.draw.service;

import com.anastacio.draw.command.AddShapeCommand;
import com.anastacio.draw.command.DeleteShapeCommand;
import com.anastacio.draw.command.MoveShapeCommand;
import com.anastacio.draw.command.ScaleShapeCommand;
import com.anastacio.draw.command.SetDrawModeCommand;
import com.anastacio.drawfx.DrawMode;
import com.anastacio.drawfx.ShapeMode;
import com.anastacio.drawfx.command.Command;
import com.anastacio.drawfx.command.CommandService;
import com.anastacio.drawfx.model.Drawing;
import com.anastacio.drawfx.model.Shape;
import com.anastacio.drawfx.service.AppService;

import java.awt.*;
import java.util.List;

public class DrawingCommandAppService implements AppService {
    public AppService appService;
    protected static AppService drawingCommandAppService = null;

    protected DrawingCommandAppService(AppService appService){
        this.appService = appService;
    }

    public static AppService getInstance(){
        return drawingCommandAppService;
    }

    public static AppService getInstance(AppService appService){
        if(drawingCommandAppService == null){
            drawingCommandAppService = new DrawingCommandAppService(appService);
        };
        return drawingCommandAppService;
    }
    
    public DrawingAppService getAppService() {
        return (DrawingAppService) appService;
    }

    @Override
    public void undo() {
        CommandService.undo();;
    }

    @Override
    public void redo() {
        CommandService.redo();
    }

    @Override
    public ShapeMode getShapeMode() {
        return appService.getShapeMode();
    }

    @Override
    public void setShapeMode(ShapeMode shapeMode) {
        appService.setShapeMode(shapeMode);
    }

    @Override
    public DrawMode getDrawMode() {
        return appService.getDrawMode();
    }

    @Override
    public void setDrawMode(DrawMode drawMode) {
        Command command = new SetDrawModeCommand(appService, drawMode);
        CommandService.ExecuteCommand(command);
    }

    @Override
    public Color getColor() {
        return appService.getColor();
    }

    @Override
    public void setColor(Color color) {
        List<Shape> selectedShapes = appService.getSelectedShapes();
        if (selectedShapes != null && !selectedShapes.isEmpty()) {
            for (Shape shape : selectedShapes) {
                Command command = new com.anastacio.draw.command.SetPropertyCommand(
                    shape, "color", "setColor", "getColor", color);
                CommandService.ExecuteCommand(command);
            }
        } else {
            appService.setColor(color);
        }
    }

    @Override
    public Color getFill() {
        return appService.getFill();
    }

    @Override
    public void setFill(Color color) {
        List<Shape> selectedShapes = appService.getSelectedShapes();
        if (selectedShapes != null && !selectedShapes.isEmpty()) {
            for (Shape shape : selectedShapes) {
                Command command = new com.anastacio.draw.command.SetPropertyCommand(
                    shape, "fill", "setFill", "getFill", color);
                CommandService.ExecuteCommand(command);
            }
        } else {
            appService.setFill(color);
        }
    }

    @Override
    public void move(Shape shape, Point start, Point end) {
        Command command = new MoveShapeCommand(appService, shape, start, end);
        CommandService.ExecuteCommand(command);
    }

    @Override
    public void move(Point start, Point end) {
        appService.move(start, end);
    }

    @Override
    public void scale(Point start, Point end) {

    }

    @Override
    public void scale(Shape shape, Point start, Point end) {
        Command command = new ScaleShapeCommand(appService, shape, start, end);
        CommandService.ExecuteCommand(command);
    }

    @Override
    public void scale(Shape shape, Point end) {
        appService.scale(shape,end);
    }

    @Override
    public void create(Shape shape) {
        Command command = new AddShapeCommand(appService, shape);
        CommandService.ExecuteCommand(command);
    }

    @Override
    public void delete(Shape shape) {
        Command command = new DeleteShapeCommand(appService, shape);
        CommandService.ExecuteCommand(command);
    }

    @Override
    public void close() {
        appService.close();
    }

    @Override
    public Drawing getDrawing() {
        return appService.getDrawing();
    }

    @Override
    public void setDrawing(Drawing drawing) {
        appService.setDrawing(drawing);
    }

    @Override
    public int getSearchRadius() {
        return appService.getSearchRadius();
    }

    @Override
    public void setSearchRadius(int radius) {
        appService.setSearchRadius(radius);
    }

    @Override
    public void search(Point p) {
        appService.search(p);
    }

    @Override
    public void search(Point p, boolean single) {
        appService.search(p, single);
    }

    @Override
    public void open(String filename) {
        appService.open(filename);
    }


    @Override
    public void save() {
        appService.save();;
    }

    @Override
    public String getFileName() {
        return appService.getFileName();
    }

    @Override
    public void select(Shape selectedShape) {
        appService.select(selectedShape);
    }

    @Override
    public void unSelect(Shape selectedShape) {
        appService.unSelect(selectedShape);
    }

    @Override
    public Shape getSelectedShape() {
        return appService.getSelectedShape();
    }

    @Override
    public List<Shape> getSelectedShapes() {
        return appService.getSelectedShapes();
    }

    @Override
    public void clearSelections(){
        appService.clearSelections();;
    }

    @Override
    public void setThickness(int thickness) {
        List<Shape> selectedShapes = appService.getSelectedShapes();
        if (selectedShapes != null && !selectedShapes.isEmpty()) {
            for (Shape shape : selectedShapes) {
                Command command = new com.anastacio.draw.command.SetPropertyCommand(
                    shape, "thickness", "setThickness", "getThickness", thickness);
                CommandService.ExecuteCommand(command);
            }
        }
        appService.setThickness(thickness);
    }

    @Override
    public int getThickness() {
        return appService.getThickness();
    }

    @Override
    public void setLineStyle(String lineStyle) {
        List<Shape> selectedShapes = appService.getSelectedShapes();
        if (selectedShapes != null && !selectedShapes.isEmpty()) {
            for (Shape shape : selectedShapes) {
                Command command = new com.anastacio.draw.command.SetPropertyCommand(
                    shape, "lineStyle", "setLineStyle", "getLineStyle", lineStyle);
                CommandService.ExecuteCommand(command);
            }
        }
        appService.setLineStyle(lineStyle);
    }

    @Override
    public String getLineStyle() {
        return appService.getLineStyle();
    }

    @Override
    public void setXLocation(int xLocation) {
        List<Shape> selectedShapes = appService.getSelectedShapes();
        if (selectedShapes != null && !selectedShapes.isEmpty()) {
            for (Shape shape : selectedShapes) {
                Point newLocation = new Point(xLocation, shape.getLocation().y);
                Command command = new com.anastacio.draw.command.SetPropertyCommand(
                    shape, "location", "setLocation", "getLocation", newLocation);
                CommandService.ExecuteCommand(command);
            }
        } else {
            appService.setXLocation(xLocation);
        }
    }

    @Override
    public int getXLocation() {
        return appService.getXLocation();
    }

    @Override
    public void setYLocation(int yLocation) {
        List<Shape> selectedShapes = appService.getSelectedShapes();
        if (selectedShapes != null && !selectedShapes.isEmpty()) {
            for (Shape shape : selectedShapes) {
                Point newLocation = new Point(shape.getLocation().x, yLocation);
                Command command = new com.anastacio.draw.command.SetPropertyCommand(
                    shape, "location", "setLocation", "getLocation", newLocation);
                CommandService.ExecuteCommand(command);
            }
        } else {
            appService.setYLocation(yLocation);
        }
    }

    @Override
    public int getYLocation() {
        return appService.getYLocation();
    }

    @Override
    public void setWidth(int width) {
        List<Shape> selectedShapes = appService.getSelectedShapes();
        if (selectedShapes != null && !selectedShapes.isEmpty()) {
            for (Shape shape : selectedShapes) {
                Command command = new com.anastacio.draw.command.SetPropertyCommand(
                    shape, "width", "setWidth", "getWidth", width);
                CommandService.ExecuteCommand(command);
            }
        } else {
            appService.setWidth(width);
        }
    }

    @Override
    public int getWidth() {
        return appService.getWidth();
    }

    @Override
    public void setHeight(int height) {
        List<Shape> selectedShapes = appService.getSelectedShapes();
        if (selectedShapes != null && !selectedShapes.isEmpty()) {
            for (Shape shape : selectedShapes) {
                Command command = new com.anastacio.draw.command.SetPropertyCommand(
                    shape, "height", "setHeight", "getHeight", height);
                CommandService.ExecuteCommand(command);
            }
        } else {
            appService.setHeight(height);
        }
    }

    @Override
    public int getHeight() {
        return appService.getHeight();
    }

    @Override
    public void setImageFilename(String imageFilename) {
        Shape selectedShape = appService.getSelectedShape();
        if (selectedShape != null) {
            Command command = new com.anastacio.draw.command.SetPropertyCommand(
                selectedShape, "imageFilename", "setImageFilename", "getImageFilename", imageFilename);
            CommandService.ExecuteCommand(command);
        } else {
            appService.setImageFilename(imageFilename);
        }
    }

    @Override
    public String getImageFilename() {
        return appService.getImageFilename();
    }

    @Override
    public void setText(String text) {
        Shape selectedShape = appService.getSelectedShape();
        if (selectedShape != null) {
            Command command = new com.anastacio.draw.command.SetPropertyCommand(
                selectedShape, "text", "setText", "getText", text);
            CommandService.ExecuteCommand(command);
        } else {
            appService.setText(text);
        }
    }

    @Override
    public void setFontSize(int fontSize) {
        Shape selectedShape = appService.getSelectedShape();
        if (selectedShape != null) {
            Font oldFont = selectedShape.getFont();
            if (oldFont != null) {
                Font newFont = new Font(oldFont.getFamily(), oldFont.getStyle(), fontSize);
                Command command = new com.anastacio.draw.command.SetPropertyCommand(
                    selectedShape, "font", "setFont", "getFont", newFont);
                CommandService.ExecuteCommand(command);
            }
        } else {
            appService.setFontSize(fontSize);
        }
    }

    @Override
    public Color getStartColor() {
        return appService.getStartColor();
    }

    @Override
    public void setStartColor(Color color) {
        List<Shape> selectedShapes = appService.getSelectedShapes();
        if (selectedShapes != null && !selectedShapes.isEmpty()) {
            for (Shape shape : selectedShapes) {
                Command command = new com.anastacio.draw.command.SetPropertyCommand(
                    shape, "startColor", "setStartColor", "getStartColor", color);
                CommandService.ExecuteCommand(command);
            }
        } else {
            appService.setStartColor(color);
        }
    }

    @Override
    public Color getEndColor() {
        return appService.getEndColor();
    }

    @Override
    public void setEndColor(Color color) {
        List<Shape> selectedShapes = appService.getSelectedShapes();
        if (selectedShapes != null && !selectedShapes.isEmpty()) {
            for (Shape shape : selectedShapes) {
                Command command = new com.anastacio.draw.command.SetPropertyCommand(
                    shape, "endColor", "setEndColor", "getEndColor", color);
                CommandService.ExecuteCommand(command);
            }
        } else {
            appService.setEndColor(color);
        }
    }

    @Override
    public boolean isGradient() {
        return appService.isGradient();
    }

    @Override
    public void setIsGradient(boolean yes) {
        List<Shape> selectedShapes = appService.getSelectedShapes();
        if (selectedShapes != null && !selectedShapes.isEmpty()) {
            for (Shape shape : selectedShapes) {
                Command command = new com.anastacio.draw.command.SetPropertyCommand(
                    shape, "gradient", "setGradient", "isGradient", yes);
                CommandService.ExecuteCommand(command);
            }
        } else {
            appService.setIsGradient(yes);
        }
    }

    @Override
    public boolean isVisible() {
        return appService.isVisible();
    }

    @Override
    public void setIsVisible(boolean yes) {
        List<Shape> selectedShapes = appService.getSelectedShapes();
        if (selectedShapes != null && !selectedShapes.isEmpty()) {
            for (Shape shape : selectedShapes) {
                Command command = new com.anastacio.draw.command.SetPropertyCommand(
                    shape, "visible", "setVisible", "isVisible", yes);
                CommandService.ExecuteCommand(command);
            }
        } else {
            appService.setIsVisible(yes);
        }
    }

    @Override
    public void delete() {
        appService.delete();
    }

    @Override
    public void setStartX(int startx) {
        List<Shape> selectedShapes = appService.getSelectedShapes();
        if (selectedShapes != null && !selectedShapes.isEmpty()) {
            for (Shape shape : selectedShapes) {
                Point newStart = new Point(startx, shape.getStart().y);
                Command command = new com.anastacio.draw.command.SetPropertyCommand(
                    shape, "start", "setStart", "getStart", newStart);
                CommandService.ExecuteCommand(command);
            }
        } else {
            appService.setStartX(startx);
        }
    }

    @Override
    public int getStartX() {
        return appService.getStartX();
    }

    @Override
    public void setStarty(int starty) {
        List<Shape> selectedShapes = appService.getSelectedShapes();
        if (selectedShapes != null && !selectedShapes.isEmpty()) {
            for (Shape shape : selectedShapes) {
                Point newStart = new Point(shape.getStart().x, starty);
                Command command = new com.anastacio.draw.command.SetPropertyCommand(
                    shape, "start", "setStart", "getStart", newStart);
                CommandService.ExecuteCommand(command);
            }
        } else {
            appService.setStarty(starty);
        }
    }

    @Override
    public int getStarty() {
        return appService.getStarty();
    }

    @Override
    public void setEndx(int endx) {
        List<Shape> selectedShapes = appService.getSelectedShapes();
        if (selectedShapes != null && !selectedShapes.isEmpty()) {
            for (Shape shape : selectedShapes) {
                Point newEnd = new Point(endx, shape.getEnd().y);
                Command command = new com.anastacio.draw.command.SetPropertyCommand(
                    shape, "end", "setEnd", "getEnd", newEnd);
                CommandService.ExecuteCommand(command);
            }
        } else {
            appService.setEndx(endx);
        }
    }

    @Override
    public int getEndx() {
        return appService.getEndx();
    }

    @Override
    public void setEndy(int endy) {
        List<Shape> selectedShapes = appService.getSelectedShapes();
        if (selectedShapes != null && !selectedShapes.isEmpty()) {
            for (Shape shape : selectedShapes) {
                Point newEnd = new Point(shape.getEnd().x, endy);
                Command command = new com.anastacio.draw.command.SetPropertyCommand(
                    shape, "end", "setEnd", "getEnd", newEnd);
                CommandService.ExecuteCommand(command);
            }
        } else {
            appService.setEndy(endy);
        }
    }

    @Override
    public int getEndy() {
        return appService.getEndy();
    }

    @Override
    public String getText() {
        return appService.getText();
    }

    @Override
    public Font getFont() {
        return appService.getFont();
    }

    @Override
    public void setFont(Font font) {
        Shape selectedShape = appService.getSelectedShape();
        if (selectedShape != null) {
            Command command = new com.anastacio.draw.command.SetPropertyCommand(
                selectedShape, "font", "setFont", "getFont", font);
            CommandService.ExecuteCommand(command);
        } else {
            appService.setFont(font);
        }
    }

    @Override
    public boolean isPinned() {
        return appService.isPinned();
    }
    
    @Override
    public void setIsPinned(boolean yes) {
        List<Shape> selectedShapes = appService.getSelectedShapes();
        if (selectedShapes != null && !selectedShapes.isEmpty()) {
            for (Shape shape : selectedShapes) {
                Command command = new com.anastacio.draw.command.SetPropertyCommand(
                    shape, "pinned", "setPinned", "isPinned", yes);
                CommandService.ExecuteCommand(command);
            }
        }
    }
}
