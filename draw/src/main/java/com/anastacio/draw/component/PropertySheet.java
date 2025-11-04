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
    private boolean isPopulating = false;

    // Called when the selected shape changes
    @Override
    public void onSelectionChanged(Shape selectedShape) {
        if (appService != null) {
            populateTable(appService);
        }
    }

    // Updates the current tool selection in the UI
    public void setShapeProp(ShapeMode shapeMode) {
        SelectionCellComponent selectionComponent = propertyTable.getSelectionCellComponent();
        switch (shapeMode) {
            case Rectangle: selectionComponent.setCellEditorValue(RectangleItem); break;
            case Ellipse: selectionComponent.setCellEditorValue(EllipseItem); break;
            case Line: selectionComponent.setCellEditorValue(LineItem); break;
            case Text: selectionComponent.setCellEditorValue(TextItem); break;
            case Image: selectionComponent.setCellEditorValue(ImageItem); break;
            case Select: selectionComponent.setCellEditorValue(SelectItem); break;
        }
    }

    // Constructor initializes tool selection property
    public PropertySheet(PropertyOptions options) {
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

    // Populates the property table based on selected shape or drawing
    public void populateTable(AppService appService) {
        if (isPopulating) return;
        isPopulating = true;

        try {
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
            Shape shape = appService.getSelectedShape();

            String objectType = (shape == null) ? "Drawing" : "Shape";
            propertyTable.addProperty(new StringProperty("Object Type", objectType));

            if (shape != null) {
                String shapeType;
                switch (shape.getShapeMode()) {
                    case Rectangle: shapeType = "Rectangle"; break;
                    case Ellipse: shapeType = "Ellipse"; break;
                    case Line: shapeType = "Line"; break;
                    case Text: shapeType = "Text"; break;
                    case Image: shapeType = "Image"; break;
                    default: shapeType = "None";
                }
                propertyTable.addProperty(new StringProperty("Shape Type", shapeType));
            }

            RectangleItem = new Item<>(ShapeMode.Rectangle, "Rectangle");
            EllipseItem = new Item<>(ShapeMode.Ellipse, "Ellipse");
            LineItem = new Item<>(ShapeMode.Line, "Line");
            TextItem = new Item<>(ShapeMode.Text, "Text");
            ImageItem = new Item<>(ShapeMode.Image, "Image");
            SelectItem = new Item<>(ShapeMode.Select, "Select");

            shapeProp = new SelectionProperty<>(
                    "Current Tool",
                    new ArrayList<>(Arrays.asList(RectangleItem, EllipseItem, LineItem, TextItem, ImageItem, SelectItem))
            );
            propertyTable.addProperty(shapeProp);

            SelectionCellComponent selectionComponent = propertyTable.getSelectionCellComponent();
            switch (appService.getShapeMode()) {
                case Rectangle: selectionComponent.setCellEditorValue(RectangleItem); break;
                case Ellipse: selectionComponent.setCellEditorValue(EllipseItem); break;
                case Line: selectionComponent.setCellEditorValue(LineItem); break;
                case Text: selectionComponent.setCellEditorValue(TextItem); break;
                case Image: selectionComponent.setCellEditorValue(ImageItem); break;
                case Select: selectionComponent.setCellEditorValue(SelectItem); break;
            }

            propertyTable.addProperty(new ColorProperty("Fore color", shape != null ? shape.getColor() : drawing.getColor()));
            propertyTable.addProperty(new IntegerProperty("Fore Alpha", shape != null ? shape.getAlpha() : 255));

            propertyTable.addProperty(new ColorProperty("Fill color", shape != null ? shape.getFill() : drawing.getFill()));
            propertyTable.addProperty(new IntegerProperty("Fill Alpha", shape != null ? shape.getFillAlpha() : 255));

            propertyTable.addProperty(new BooleanProperty("IsGradient", shape != null && shape.isGradient()));

            if (shape != null && shape.isGradient()) {
                propertyTable.addProperty(new ColorProperty("Start color", shape.getStartColor()));
                propertyTable.addProperty(new IntegerProperty("Start Alpha", shape.getStartAlpha()));
                propertyTable.addProperty(new ColorProperty("End color", shape.getEndColor()));
                propertyTable.addProperty(new IntegerProperty("End Alpha", shape.getEndAlpha()));
                propertyTable.addProperty(new IntegerProperty("Start x", shape.getStart().x));
                propertyTable.addProperty(new IntegerProperty("Start y", shape.getStart().y));
                propertyTable.addProperty(new IntegerProperty("End x", shape.getEnd().x));
                propertyTable.addProperty(new IntegerProperty("End y", shape.getEnd().y));
            }

            propertyTable.addProperty(new IntegerProperty("Line Thickness", shape != null ? shape.getThickness() : drawing.getThickness()));
            propertyTable.addProperty(new BooleanProperty("IsVisible", shape != null ? shape.isVisible() : true));
            propertyTable.addProperty(new BooleanProperty("Pinned", shape != null && shape.isPinned()));
            propertyTable.addProperty(new IntegerProperty("X Location", shape != null ? shape.getLocation().x : 0));
            propertyTable.addProperty(new IntegerProperty("Y Location", shape != null ? shape.getLocation().y : 0));
            propertyTable.addProperty(new IntegerProperty("Width", shape != null ? shape.getWidth() : 0));
            propertyTable.addProperty(new IntegerProperty("Height", shape != null ? shape.getHeight() : 0));

            if (shape != null && shape.getShapeMode() == ShapeMode.Text) {
                propertyTable.addProperty(new StringProperty("Text", shape.getText() != null ? shape.getText() : ""));
                Font font = shape.getFont();
                if (font != null) {
                    propertyTable.addProperty(new StringProperty("Font family", font.getFamily()));
                    propertyTable.addProperty(new IntegerProperty("Font size", font.getSize()));

                    int style = font.getStyle();
                    ArrayList<Item<Integer>> fontStyles = new ArrayList<>(Arrays.asList(
                            new Item<>(Font.PLAIN, "Plain"),
                            new Item<>(Font.BOLD, "Bold"),
                            new Item<>(Font.ITALIC, "Italic"),
                            new Item<>(Font.BOLD | Font.ITALIC, "Bold Italic")
                    ));
                    SelectionProperty<Integer> fontStyleProp = new SelectionProperty<>("Font style", fontStyles);
                    propertyTable.addProperty(fontStyleProp);

                    for (Item item : fontStyles) {
                        if (item.getValue().equals(style)) {
                            propertyTable.getSelectionCellComponent().setCellEditorValue(item);
                            break;
                        }
                    }
                }
            }

            if (shape == null) {
                String text = appService.getText();
                if (text != null && !text.isEmpty()) {
                    propertyTable.addProperty(new StringProperty("Default Text", text));
                }
            }

            if (shape != null && shape.getShapeMode() == ShapeMode.Image) {
                propertyTable.addProperty(new StringProperty("Image Path", shape.getImageFilename() != null ? shape.getImageFilename() : ""));
            }

        } finally {
            isPopulating = false;
        }
    }



}
