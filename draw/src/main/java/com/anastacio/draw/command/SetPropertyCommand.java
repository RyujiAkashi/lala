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
        if (value == null) {
            return;
        }
        
        Class<?>[] parameterTypes = {
            value.getClass(),
            java.awt.Color.class,
            java.awt.Point.class,
            java.awt.Font.class,
            int.class,
            Integer.class,
            boolean.class,
            Boolean.class,
            String.class
        };
        
        for (Class<?> paramType : parameterTypes) {
            try {
                Method setter = shape.getClass().getMethod(setterMethodName, paramType);
                setter.invoke(shape, value);
                return;
            } catch (NoSuchMethodException | IllegalAccessException | java.lang.reflect.InvocationTargetException e) {
            }
        }
        
        throw new RuntimeException("Failed to set property: " + propertyName + 
            ". No compatible setter method found for value type: " + value.getClass().getName());
    }
}
