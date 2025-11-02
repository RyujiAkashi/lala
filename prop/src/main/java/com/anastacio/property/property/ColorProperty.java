package com.anastacio.property.property;

import com.anastacio.property.validator.NullValidator;

import java.awt.*;

public class ColorProperty extends AbstractProperty<Color> {

    public ColorProperty(String name, Color value) {
        super(name, value, new NullValidator());
    }
}
