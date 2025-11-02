package com.anastacio.property.property;

import com.anastacio.property.validator.Validator;
import com.anastacio.property.validator.doubleNumber.DoubleValidator;

public class DoubleProperty extends AbstractProperty<Double> {

    public DoubleProperty(String name, Double value) {
        super(name, value, new DoubleValidator());
    }

    public DoubleProperty(String name, Double value, Validator validator) {
        super(name, value, validator);
    }
}
