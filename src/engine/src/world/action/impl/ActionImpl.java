package world.action.impl;

import world.action.api.Action;
import world.action.api.ActionType;
import world.context.Context;
import world.entity.api.EntityDefinition;
import world.entity.api.EntityInstance;
import world.expressions.api.Expression;
import world.property.api.PropertyDefinition;

public abstract class ActionImpl implements Action {
    protected final ActionType actionType;
    protected EntityDefinition entityDefinition;
    protected SecondaryEntity secondaryEntity;
    protected PropertyDefinition propertyDefinition;
    protected Context entitiesContext;

    protected ActionImpl(ActionType actionType, EntityDefinition entityDefinition,PropertyDefinition propertyDefinition, SecondaryEntity secondaryEntity, Context entitiesContext) {
        this.actionType = actionType;
        this.entityDefinition = entityDefinition;
        this.propertyDefinition = propertyDefinition;
        this.secondaryEntity = secondaryEntity;
        this.entitiesContext = entitiesContext;
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
    public EntityDefinition getSecondaryEntityDefinition() {
        if (secondaryEntity != null)
            return secondaryEntity.getSecondaryEntityDefinition();
        else
            return null;
    }

    @Override
    public String toString() {
        return actionType + " action on: " + entityDefinition.getName() + "'s " + propertyDefinition.getName();
    }
}
