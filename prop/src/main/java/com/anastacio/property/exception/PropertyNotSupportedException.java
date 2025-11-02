package com.anastacio.property.exception;

import com.anastacio.property.property.Property;

public class PropertyNotSupportedException extends RuntimeException {

    public PropertyNotSupportedException(Property property) {
        super("Could not find default editor/renderer for property '" + property.getName() + "'");
    }
}
