package com.anastacio.draw.command;

import com.anastacio.drawfx.command.Command;
import com.anastacio.drawfx.model.Shape;

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
                try {
                    Method setter = shape.getClass().getMethod(setterMethodName, boolean.class);
                    setter.invoke(shape, value);
                } catch (Exception e3) {
                    try {
                        Method setter = shape.getClass().getMethod(setterMethodName, Integer.class);
                        setter.invoke(shape, value);
                    } catch (Exception e4) {
                        throw new RuntimeException("Failed to set property: " + propertyName + ". Tried value.getClass(), int.class, boolean.class, and Integer.class", e4);
                    }
                }
            }
        }
    }
}
