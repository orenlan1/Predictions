package world.action.impl;

import world.action.api.Action;
import world.action.api.ActionType;
import world.context.Context;
import world.entity.api.EntityDefinition;
import world.entity.api.EntityInstance;
import world.exceptions.InvalidConditionOperatorException;
import world.exceptions.InvalidVariableTypeException;
import world.expressions.api.Expression;
import world.property.api.PropertyDefinition;

import java.util.ArrayList;
import java.util.List;

public abstract class ConditionAction extends ActionImpl {
    private final List<Action> thenActions;
    private final List<Action> elseActions;


    public ConditionAction(EntityDefinition entityDefinition, List<Action> thenActions, List<Action> elseActions, SecondaryEntity secondaryEntity, Context entitiesContext) {
        super(ActionType.CONDITION, entityDefinition, null, secondaryEntity, entitiesContext);
        this.thenActions = thenActions;
        this.elseActions = elseActions;
    }

    public abstract boolean evaluate(EntityInstance entityInstance, Integer currTick) throws Exception;

    @Override
    public void activate(EntityInstance entityInstance, int currTick) throws Exception {
        boolean conditionRes = evaluate(entityInstance, currTick);

        if (conditionRes) {
            for (Action action : thenActions) {
                action.activate(entityInstance, currTick);
            }
        } else {
            for (Action action : elseActions) {
                action.activate(entityInstance, currTick);
            }
        }
    }

    public Integer getNumThen() { return thenActions.size(); }

    public Integer getNumElse() {return elseActions.size(); }
}
