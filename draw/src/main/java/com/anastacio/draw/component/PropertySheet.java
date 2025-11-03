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
import com.anastacio.drawfx.service.SelectionChangeListener;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class PropertySheet extends PropertyPanel implements SelectionChangeListener {
    PropertyPanel propertyTable;
    private SelectionProperty shapeProp;
    Item RectangleItem;
    Item EllipseItem;
    Item LineItem;
    Item TextItem;
    Item ImageItem;
    Item SelectItem;
    private AppService appService;
    private PropertyEventListener propertyEventListener;
    private boolean listenerRegistered = false;

    @Override
    public void onSelectionChanged(Shape selectedShape) {
        if (appService != null) {
            populateTable(appService);
        }
    }

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
        this.appService = appService;
        Drawing drawing = appService.getDrawing();
        propertyTable = this;
        
        if (!listenerRegistered) {
            com.anastacio.draw.view.DrawingView drawingView = null;
            if (appService instanceof com.anastacio.draw.service.DrawingAppService) {
                drawingView = ((com.anastacio.draw.service.DrawingAppService) appService).getDrawingView();
            } else if (appService instanceof com.anastacio.draw.service.DrawingCommandAppService) {
                drawingView = ((com.anastacio.draw.service.DrawingCommandAppService) appService).getAppService().getDrawingView();
            }
            
            propertyEventListener = new PropertyEventListener(appService, drawingView, this);
            propertyTable.addEventListener(propertyEventListener);
            listenerRegistered = true;
        }

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

        if (shape != null) {
            ColorProperty foreColorProp = new ColorProperty("Fore color", shape.getColor());
            propertyTable.addProperty(foreColorProp);
            
            IntegerProperty foreAlphaProp = new IntegerProperty("Fore Alpha", shape.getAlpha());
            propertyTable.addProperty(foreAlphaProp);

            ColorProperty fillColorProp = new ColorProperty("Fill color", shape.getFill());
            propertyTable.addProperty(fillColorProp);
            
            IntegerProperty fillAlphaProp = new IntegerProperty("Fill Alpha", shape.getFillAlpha());
            propertyTable.addProperty(fillAlphaProp);

            BooleanProperty isGradientProp = new BooleanProperty("IsGradient", shape.isGradient());
            propertyTable.addProperty(isGradientProp);
        } else {
            ColorProperty foreColorProp = new ColorProperty("Fore color", drawing.getColor());
            propertyTable.addProperty(foreColorProp);
            
            ColorProperty fillColorProp = new ColorProperty("Fill color", drawing.getFill());
            propertyTable.addProperty(fillColorProp);
        }

        if (shape != null && shape.isGradient()) {
            ColorProperty startColorProp = new ColorProperty("Start color", shape.getStartColor());
            propertyTable.addProperty(startColorProp);
            
            IntegerProperty startAlphaProp = new IntegerProperty("Start Alpha", shape.getStartAlpha());
            propertyTable.addProperty(startAlphaProp);

            ColorProperty endColorProp = new ColorProperty("End color", shape.getEndColor());
            propertyTable.addProperty(endColorProp);
            
            IntegerProperty endAlphaProp = new IntegerProperty("End Alpha", shape.getEndAlpha());
            propertyTable.addProperty(endAlphaProp);
            
            IntegerProperty startXProp = new IntegerProperty("Start x", shape.getStart().x);
            propertyTable.addProperty(startXProp);

            IntegerProperty startYProp = new IntegerProperty("Start y", shape.getStart().y);
            propertyTable.addProperty(startYProp);

            IntegerProperty endXProp = new IntegerProperty("End x", shape.getEnd().x);
            propertyTable.addProperty(endXProp);

            IntegerProperty endYProp = new IntegerProperty("End y", shape.getEnd().y);
            propertyTable.addProperty(endYProp);
        }
        
        if (shape != null) {
            BooleanProperty isVisibleProp = new BooleanProperty("IsVisible", shape.isVisible());
            propertyTable.addProperty(isVisibleProp);

            IntegerProperty lineThicknessProp = new IntegerProperty("Line Thickness", shape.getThickness());
            propertyTable.addProperty(lineThicknessProp);
        } else {
            IntegerProperty lineThicknessProp = new IntegerProperty("Line Thickness", drawing.getThickness());
            propertyTable.addProperty(lineThicknessProp);
        }

        if (shape != null) {
            String currentLineStyle = shape.getLineStyle();
            ArrayList<Item<String>> lineStyles = new ArrayList<>(Arrays.asList(
                new Item<String>("Solid", "Solid"),
                new Item<String>("Dashed", "Dashed"),
                new Item<String>("Dotted", "Dotted"),
                new Item<String>("Dash-Dot", "Dash-Dot")
            ));
            SelectionProperty<String> lineStyleProp = new SelectionProperty<String>("Line Style", lineStyles);
            propertyTable.addProperty(lineStyleProp);
            
            SelectionCellComponent lineStyleComponent = propertyTable.getSelectionCellComponent();
            for (Item item : lineStyles) {
                if (item.getValue().equals(currentLineStyle)) {
                    lineStyleComponent.setCellEditorValue(item);
                    break;
                }
            }
            
            BooleanProperty isPinnedProp = new BooleanProperty("Pinned", shape.isPinned());
            propertyTable.addProperty(isPinnedProp);
        }

        if (shape != null) {
            IntegerProperty xLocationProp = new IntegerProperty("X Location", shape.getLocation().x);
            propertyTable.addProperty(xLocationProp);

            IntegerProperty yLocationProp = new IntegerProperty("Y Location", shape.getLocation().y);
            propertyTable.addProperty(yLocationProp);

            IntegerProperty widthProp = new IntegerProperty("Width", shape.getWidth());
            propertyTable.addProperty(widthProp);

            IntegerProperty heightProp = new IntegerProperty("Height", shape.getHeight());
            propertyTable.addProperty(heightProp);
        }

        if (shape != null && shape.getShapeMode() == ShapeMode.Text) {
            StringProperty textProp = new StringProperty("Text", shape.getText() != null ? shape.getText() : "");
            propertyTable.addProperty(textProp);

            Font font = shape.getFont();
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
            String imagePath = shape.getImageFilename() != null ? shape.getImageFilename() : "";
            StringProperty imageProp = new StringProperty("Image Path", imagePath);
            propertyTable.addProperty(imageProp);
        }
    }
}
