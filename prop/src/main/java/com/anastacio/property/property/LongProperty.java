package com.anastacio.property.property;

import com.anastacio.property.validator.Validator;
import com.anastacio.property.validator.longNumber.LongValidator;

public class LongProperty extends AbstractProperty<Long> {

    public LongProperty(String name, Long value) {
        super(name, value, new LongValidator());
    }

    public LongProperty(String name, Long value, Validator validator) {
        super(name, value, validator);
    }
}
