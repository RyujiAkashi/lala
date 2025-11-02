package com.anastacio.property.property;

import com.anastacio.property.validator.NullValidator;

public class BooleanProperty extends AbstractProperty<Boolean> {

    public BooleanProperty(String name, Boolean value) {
        super(name, value, new NullValidator());
    }
}
