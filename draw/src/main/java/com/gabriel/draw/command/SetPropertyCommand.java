package com.gabriel.draw.command;

import com.gabriel.drawfx.command.Command;
import com.gabriel.drawfx.model.Shape;

import java.lang.reflect.Method;

public class SetPropertyCommand implements Command {
    private final Shape shape;
    private final String propertyName;
    private final Object newValue;
    private final Object oldValue;
    private final String setterMethodName;
    private final String getterMethodName;

    public SetPropertyCommand(Shape shape, String propertyName, String setterMethodName, String getterMethodName, Object newValue) {
        this.shape = shape;
        this.propertyName = propertyName;
        this.setterMethodName = setterMethodName;
        this.getterMethodName = getterMethodName;
        this.newValue = newValue;
        
        try {
            Method getter = shape.getClass().getMethod(getterMethodName);
            this.oldValue = getter.invoke(shape);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get old value for property: " + propertyName, e);
        }
    }

    @Override
    public void execute() {
        setProperty(newValue);
    }

    @Override
    public void undo() {
        setProperty(oldValue);
    }

    @Override
    public void redo() {
        execute();
    }

    private void setProperty(Object value) {
        try {
            Method setter = shape.getClass().getMethod(setterMethodName, value.getClass());
            setter.invoke(shape, value);
        } catch (Exception e) {
            try {
                Method setter = shape.getClass().getMethod(setterMethodName, int.class);
                setter.invoke(shape, value);
            } catch (Exception e2) {
                throw new RuntimeException("Failed to set property: " + propertyName, e2);
            }
        }
    }
}
