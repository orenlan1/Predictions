package world.action.impl;

import world.action.api.Action;
import world.action.api.ActionType;
import world.entity.api.EntityDefinition;
import world.entity.api.EntityInstance;
import world.expressions.api.Expression;
import world.property.api.PropertyDefinition;

public abstract class ActionImpl implements Action {
    protected final ActionType actionType;
    protected EntityDefinition entityDefinition;
    protected SecondaryEntity secondaryEntity;
    protected PropertyDefinition propertyDefinition;

    protected ActionImpl(ActionType actionType, EntityDefinition entityDefinition,PropertyDefinition propertyDefinition, SecondaryEntity secondaryEntity) {
        this.actionType = actionType;
        this.entityDefinition = entityDefinition;
        this.propertyDefinition = propertyDefinition;
        this.secondaryEntity = secondaryEntity;
    }

    @Override
    public ActionType getActionType() {
        return actionType;
    }

    @Override
    public EntityDefinition getEntityDefinition() {
        return entityDefinition;
    }

    public void addSecondaryEntity(SecondaryEntity secondaryEntity) {
        this.secondaryEntity = secondaryEntity;
    }

    @Override
    public String toString() {
        return actionType + " action on: " + entityDefinition.getName() + "'s " + propertyDefinition.getName();
    }
}
