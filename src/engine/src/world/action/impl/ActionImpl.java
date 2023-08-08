package world.action.impl;

import world.action.api.Action;
import world.entity.api.EntityDefinition;
import world.entity.api.EntityInstance;

public abstract class ActionImpl implements Action {
    public enum ActionType {INCREASE, DECREASE, CALCULATION ,CONDITION, SET, KILL}
    private final ActionType actionType;
    EntityDefinition entityDefinition;

    protected ActionImpl(ActionType actionType, EntityDefinition entityDefinition) {
        this.actionType = actionType;
        this.entityDefinition = entityDefinition;
    }

    @Override
    public ActionType getActionType() {
        return actionType;
    }

    @Override
    public EntityDefinition getEntityDefinition() {
        return entityDefinition;
    }
}
