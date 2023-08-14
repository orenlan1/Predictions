package world.action.impl;

import world.action.api.Action;
import world.action.api.ActionType;
import world.entity.api.EntityDefinition;
import world.entity.api.EntityInstance;
import world.property.api.PropertyDefinition;

public abstract class ActionImpl implements Action {
    //public enum ActionType {INCREASE, DECREASE, CALCULATION ,CONDITION, SET, KILL}
    //protected final ActionType actionType;

    protected final ActionType actionType;
    protected EntityDefinition entityDefinition;
    protected PropertyDefinition propertyDefinition;

    protected ActionImpl(ActionType actionType, EntityDefinition entityDefinition,PropertyDefinition propertyDefinition) {
        this.actionType = actionType;
        this.entityDefinition = entityDefinition;
        this.propertyDefinition = propertyDefinition;
    }

    @Override
    public ActionType getActionType() {
        return actionType;
    }

    @Override
    public EntityDefinition getEntityDefinition() {
        return entityDefinition;
    }

    @Override
    public String toString() {
        return actionType + " action on: " + entityDefinition.getName() + "'s " + propertyDefinition.getName();
    }
}
