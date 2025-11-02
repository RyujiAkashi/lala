package com.anastacio.draw.component;

import com.anastacio.property.property.BooleanProperty;
import com.anastacio.property.property.ColorProperty;
import com.anastacio.property.property.IntegerProperty;
import com.anastacio.property.property.StringProperty;
import com.anastacio.draw.controller.PropertyEventListener;
import com.anastacio.drawfx.ShapeMode;
import com.anastacio.drawfx.model.Drawing;
import com.anastacio.property.PropertyOptions;
import com.anastacio.property.PropertyPanel;
import com.anastacio.property.cell.SelectionCellComponent;
import com.anastacio.property.property.selection.Item;
import com.anastacio.property.property.selection.SelectionProperty;
import com.anastacio.drawfx.model.Shape;
import com.anastacio.drawfx.service.AppService;

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
        
        IntegerProperty foreAlphaProp = new IntegerProperty("Fore Alpha", shape != null ? shape.getAlpha() : 255);
        propertyTable.addProperty(foreAlphaProp);

        ColorProperty fillColorProp = new ColorProperty("Fill color",  appService.getFill());
        propertyTable.addProperty(fillColorProp);
        
        IntegerProperty fillAlphaProp = new IntegerProperty("Fill Alpha", shape != null ? shape.getFillAlpha() : 255);
        propertyTable.addProperty(fillAlphaProp);

        BooleanProperty isGradientProp = new BooleanProperty("IsGradient",  appService.isGradient());
        propertyTable.addProperty(isGradientProp);

        if (appService.isGradient()) {
            ColorProperty startColorProp = new ColorProperty("Start color",  appService.getStartColor());
            propertyTable.addProperty(startColorProp);
            
            IntegerProperty startAlphaProp = new IntegerProperty("Start Alpha", shape != null ? shape.getStartAlpha() : 255);
            propertyTable.addProperty(startAlphaProp);

            ColorProperty endColorProp = new ColorProperty("End color",  appService.getEndColor());
            propertyTable.addProperty(endColorProp);
            
            IntegerProperty endAlphaProp = new IntegerProperty("End Alpha", shape != null ? shape.getEndAlpha() : 255);
            propertyTable.addProperty(endAlphaProp);
            
            IntegerProperty startXProp = new IntegerProperty("Start x", appService.getStartX());
            propertyTable.addProperty(startXProp);

            IntegerProperty startYProp = new IntegerProperty("Start y", appService.getStarty());
            propertyTable.addProperty(startYProp);

            IntegerProperty endXProp = new IntegerProperty("End x", appService.getEndx());
            propertyTable.addProperty(endXProp);

            IntegerProperty endYProp = new IntegerProperty("End y", appService.getEndy());
            propertyTable.addProperty(endYProp);
        }
        
        BooleanProperty isVisibleProp = new BooleanProperty("IsVisible", appService.isVisible());
        propertyTable.addProperty(isVisibleProp);

        IntegerProperty lineThicknessProp = new IntegerProperty("Line Thickness", appService.getThickness());
        propertyTable.addProperty(lineThicknessProp);

        if (shape != null) {
            IntegerProperty xLocationProp = new IntegerProperty("X Location", appService.getXLocation());
            propertyTable.addProperty(xLocationProp);

            IntegerProperty yLocationProp = new IntegerProperty("Y Location", appService.getYLocation());
            propertyTable.addProperty(yLocationProp);

            IntegerProperty widthProp = new IntegerProperty("Width", appService.getWidth());
            propertyTable.addProperty(widthProp);

            IntegerProperty heightProp = new IntegerProperty("Height", appService.getHeight());
            propertyTable.addProperty(heightProp);
        }

        if (shape != null && shape.getShapeMode() == ShapeMode.Text) {
            StringProperty textProp = new StringProperty("Text", appService.getText() != null ? appService.getText() : "");
            propertyTable.addProperty(textProp);

            Font font = appService.getFont();
            if(font != null) {
                StringProperty fontFamilyProp = new StringProperty("Font family", font.getFamily());
                propertyTable.addProperty(fontFamilyProp);

                IntegerProperty fontSizeProp = new IntegerProperty("Font size", font.getSize());
                propertyTable.addProperty(fontSizeProp);
                
                String fontStyle;
                int style = font.getStyle();
                if (style == Font.BOLD) {
                    fontStyle = "Bold";
                } else if (style == Font.ITALIC) {
                    fontStyle = "Italic";
                } else if (style == (Font.BOLD | Font.ITALIC)) {
                    fontStyle = "Bold Italic";
                } else {
                    fontStyle = "Plain";
                }
                
                ArrayList<Item<Integer>> fontStyles = new ArrayList<>(Arrays.asList(
                    new Item<Integer>(Font.PLAIN, "Plain"),
                    new Item<Integer>(Font.BOLD, "Bold"),
                    new Item<Integer>(Font.ITALIC, "Italic"),
                    new Item<Integer>(Font.BOLD | Font.ITALIC, "Bold Italic")
                ));
                SelectionProperty<Integer> fontStyleProp = new SelectionProperty<Integer>("Font style", fontStyles);
                propertyTable.addProperty(fontStyleProp);
                
                SelectionCellComponent styleComponent = propertyTable.getSelectionCellComponent();
                for (Item item : fontStyles) {
                    if (item.getValue().equals(style)) {
                        styleComponent.setCellEditorValue(item);
                        break;
                    }
                }
            }
        }
        
        if (shape == null) {
            String text = appService.getText();
            if(text != null && !text.isEmpty()) {
                StringProperty textProp = new StringProperty("Default Text", text);
                propertyTable.addProperty(textProp);
            }
        }

        if (shape != null && shape.getShapeMode() == ShapeMode.Image) {
            String imagePath = appService.getImageFilename();
            if(imagePath == null) {
                imagePath = "";
            }
            StringProperty imageProp = new StringProperty("Image Path", imagePath);
            propertyTable.addProperty(imageProp);
        }
    }
}
