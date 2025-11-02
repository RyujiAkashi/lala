package com.anastacio.property.property.selection;

import com.anastacio.property.property.AbstractProperty;
import com.anastacio.property.validator.NullValidator;

import java.util.ArrayList;

public class SelectionProperty<T> extends AbstractProperty<T> {

    protected ArrayList<Item<T>> items;

    public SelectionProperty(String name, ArrayList<Item<T>> items) {
        super(name, items.get(0).getValue(), new NullValidator());

        this.items = items;
    }

    public ArrayList<Item<T>> getItems() {
        return items;
    }
}
