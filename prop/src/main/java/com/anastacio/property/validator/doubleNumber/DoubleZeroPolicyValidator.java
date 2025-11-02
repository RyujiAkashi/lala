package com.anastacio.property.validator.doubleNumber;

import com.anastacio.property.validator.Validator;

public class DoubleZeroPolicyValidator implements Validator {

    protected boolean allowZero;

    public DoubleZeroPolicyValidator(boolean allowZero) {
        this.allowZero = allowZero;
    }

    public DoubleZeroPolicyValidator() {
        this(true);
    }

    @Override
    public boolean validate(Object object) {
        double value = Double.parseDouble((String) object);

        return value != 0 || allowZero;
    }
}
