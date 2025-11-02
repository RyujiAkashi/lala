package com.anastacio.property.property;

import com.anastacio.property.action.Action;
import com.anastacio.property.validator.NullValidator;


public class ActionProperty extends AbstractProperty<Action> {

    private String actionName;

    public ActionProperty(String label, String actionName, Action action) {
        super(label, action, new NullValidator());

        this.actionName = actionName;
    }

    public String getActionName() {
        return actionName;
    }
}
