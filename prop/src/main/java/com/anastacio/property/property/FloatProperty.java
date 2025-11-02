package com.anastacio.property.property;

import com.anastacio.property.validator.Validator;
import com.anastacio.property.validator.floatNumber.FloatValidator;

public class FloatProperty extends AbstractProperty<Float> {

    public FloatProperty(String name, Float value) {
        super(name, value, new FloatValidator());
    }

    public FloatProperty(String name, Float value, Validator validator) {
        super(name, value, validator);
    }
}
