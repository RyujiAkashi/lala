package com.gabriel.draw.component;

import com.gabriel.draw.controller.PropertyEventListener;
import com.gabriel.drawfx.ShapeMode;
import com.gabriel.drawfx.model.Drawing;
import com.gabriel.property.PropertyOptions;
import com.gabriel.property.PropertyPanel;
import com.gabriel.property.cell.SelectionCellComponent;
import com.gabriel.property.property.*;
import com.gabriel.property.property.selection.Item;
import com.gabriel.property.property.selection.SelectionProperty;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.service.AppService;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class PropertySheet extends PropertyPanel {
    PropertyPanel propertyTable;
    private SelectionProperty shapeProp;
    Item RectangleItem;
    Item EllipseItem;
    Item LineItem;
    Item TextItem;
    Item ImageItem;
    Item SelectItem;


    public void setShapeProp(ShapeMode shapeMode ){
        SelectionCellComponent  selectionComponent =  propertyTable.getSelectionCellComponent();
        if (shapeMode ==ShapeMode.Rectangle) {
            selectionComponent.setCellEditorValue(RectangleItem);
        } else if (shapeMode == ShapeMode.Ellipse) {
            selectionComponent.setCellEditorValue(EllipseItem);
        } else if (shapeMode == ShapeMode.Line) {
            selectionComponent.setCellEditorValue(LineItem);
        } else if (shapeMode == ShapeMode.Text) {
            selectionComponent.setCellEditorValue(TextItem);
        } else if (shapeMode == ShapeMode.Image) {
            selectionComponent.setCellEditorValue(ImageItem);
        } else if (shapeMode == ShapeMode.Select) {
            selectionComponent.setCellEditorValue(SelectItem);
        }
    }

    public PropertySheet(PropertyOptions options){
        super(options);
        shapeProp = new SelectionProperty<>(
                "Current Tool",
                new ArrayList<>(Arrays.asList(
                        new Item<>(ShapeMode.Rectangle, "Rectangle"),
                        new Item<>(ShapeMode.Ellipse, "Ellipse"),
                        new Item<>(ShapeMode.Line, "Line"),
                        new Item<>(ShapeMode.Text, "Text"),
                        new Item<>(ShapeMode.Image, "Image"),
                        new Item<>(ShapeMode.Select, "Select")
                ))
        );
    }

    public void populateTable(AppService appService) {
        Drawing drawing = appService.getDrawing();
        propertyTable = this;
        propertyTable.addEventListener(new PropertyEventListener(appService));

        propertyTable.clear();
        Shape shape  = appService.getSelectedShape();
        String objectType;
        String shapeType = "None";
        
        if ( shape == null) {
            objectType = "Drawing";
        } else {
            objectType = "Shape";
            ShapeMode mode = shape.getShapeMode();
            if (mode == ShapeMode.Rectangle) {
                shapeType = "Rectangle";
            } else if (mode == ShapeMode.Ellipse) {
                shapeType = "Ellipse";
            } else if (mode == ShapeMode.Line) {
                shapeType = "Line";
            } else if (mode == ShapeMode.Text) {
                shapeType = "Text";
            } else if (mode == ShapeMode.Image) {
                shapeType = "Image";
            }
        }

        StringProperty typeProp = new StringProperty("Object Type", objectType);
        propertyTable.addProperty(typeProp);

        if (shape != null) {
            StringProperty shapeProp = new StringProperty("Shape Type", shapeType);
            propertyTable.addProperty(shapeProp);
        }

        RectangleItem = new Item<ShapeMode>(ShapeMode.Rectangle, "Rectangle");
        EllipseItem = new Item<ShapeMode>(ShapeMode.Ellipse, "Ellipse");
        LineItem =    new Item<ShapeMode>(ShapeMode.Line, "Line");
        TextItem =    new Item<ShapeMode>(ShapeMode.Text, "Text");
        ImageItem =   new Item<ShapeMode>(ShapeMode.Image, "Image");
        SelectItem =  new Item<ShapeMode>(ShapeMode.Select, "Select");
        
        shapeProp = new SelectionProperty<>(
                "Current Tool",
                new ArrayList<>(Arrays.asList(
                        RectangleItem,
                        EllipseItem,
                        LineItem,
                        TextItem,
                        ImageItem,
                        SelectItem
                ))
        );

        propertyTable.addProperty(shapeProp);

        SelectionCellComponent  selectionComponent =  propertyTable.getSelectionCellComponent();
        ShapeMode shapeMode = appService.getShapeMode();
        if(shapeMode == ShapeMode.Rectangle) {
            selectionComponent.setCellEditorValue(RectangleItem);
        } else if(shapeMode == ShapeMode.Ellipse) {
            selectionComponent.setCellEditorValue(EllipseItem);
        } else if(shapeMode == ShapeMode.Line) {
            selectionComponent.setCellEditorValue(LineItem);
        } else if(shapeMode == ShapeMode.Text) {
            selectionComponent.setCellEditorValue(TextItem);
        } else if(shapeMode == ShapeMode.Image) {
            selectionComponent.setCellEditorValue(ImageItem);
        } else if(shapeMode == ShapeMode.Select) {
            selectionComponent.setCellEditorValue(SelectItem);
        }

        ColorProperty foreColorProp = new ColorProperty("Fore color", appService.getColor());
        propertyTable.addProperty(foreColorProp);

        ColorProperty fillColorProp = new ColorProperty("Fill color",  appService.getFill());
        propertyTable.addProperty(fillColorProp);

        BooleanProperty isGradientProp = new BooleanProperty("IsGradient",  appService.isGradient());
        propertyTable.addProperty(isGradientProp);

        if (appService.isGradient()) {
            ColorProperty startColorProp = new ColorProperty("Start color",  appService.getStartColor());
            propertyTable.addProperty(startColorProp);

            ColorProperty endColorProp = new ColorProperty("End color",  appService.getEndColor());
            propertyTable.addProperty(endColorProp);
        }

        IntegerProperty lineThicknessProp = new IntegerProperty("Line Thickness", appService.getThickness());
        propertyTable.addProperty(lineThicknessProp);

        if (shape != null) {
            IntegerProperty startXProp = new IntegerProperty("Start x", (int)shape.getStart().getX());
            propertyTable.addProperty(startXProp);

            IntegerProperty startYProp = new IntegerProperty("Start y", (int)shape.getStart().getY());
            propertyTable.addProperty(startYProp);

            IntegerProperty endXProp = new IntegerProperty("End x", (int)shape.getEnd().getX());
            propertyTable.addProperty(endXProp);

            IntegerProperty endYProp = new IntegerProperty("End y", (int)shape.getEnd().getY());
            propertyTable.addProperty(endYProp);

            int width = (int)(shape.getEnd().getX() - shape.getStart().getX());
            int height = (int)(shape.getEnd().getY() - shape.getStart().getY());

            IntegerProperty widthProp = new IntegerProperty("Width", Math.abs(width));
            propertyTable.addProperty(widthProp);

            IntegerProperty heightProp = new IntegerProperty("Height", Math.abs(height));
            propertyTable.addProperty(heightProp);
        }

        if (shape != null && shape.getShapeMode() == ShapeMode.Text) {
            StringProperty textProp = new StringProperty("Text", shape.getText() != null ? shape.getText() : "");
            propertyTable.addProperty(textProp);

            Font font = shape.getFont() != null ? shape.getFont() : appService.getFont();
            
            StringProperty fontFamilyProp = new StringProperty("Font family", font.getFamily());
            propertyTable.addProperty(fontFamilyProp);

            IntegerProperty fontSizeProp = new IntegerProperty("Font size", font.getSize());
            propertyTable.addProperty(fontSizeProp);
        }

        if (shape != null && shape.getShapeMode() == ShapeMode.Image) {
            String imagePath = shape.getImageFilename() != null ? shape.getImageFilename() : "";
            StringProperty imageProp = new StringProperty("Image Path", imagePath);
            propertyTable.addProperty(imageProp);
        }
    }
}
