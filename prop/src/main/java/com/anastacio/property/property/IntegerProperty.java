package com.anastacio.property.property;

import com.anastacio.property.validator.Validator;
import com.anastacio.property.validator.integer.IntegerValidator;

public class IntegerProperty extends AbstractProperty<Integer> {

    public IntegerProperty(String name, Integer value) {
        super(name, value, new IntegerValidator());
    }

    public IntegerProperty(String name, Integer value, Validator validator) {
        super(name, value, validator);
    }
}
