package world.action.impl;

import world.action.api.Action;
import world.action.api.ActionType;
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


    public ConditionAction(EntityDefinition entityDefinition, PropertyDefinition propertyDefinition, List<Action> thenActions, List<Action> elseActions) {
        super(ActionType.CONDITION, entityDefinition, propertyDefinition);
        this.thenActions = thenActions;
        this.elseActions = elseActions;
    }

    public abstract boolean evaluate(EntityInstance entityInstance) throws InvalidConditionOperatorException, InvalidVariableTypeException;

    @Override
    public void activate(EntityInstance entityInstance) throws Exception {
        boolean conditionRes = evaluate(entityInstance);

        if (conditionRes) {
            for (Action action : thenActions) {
                action.activate(entityInstance);
            }
        } else {
            for (Action action : elseActions) {
                action.activate(entityInstance);
            }
        }
    }

    public Integer getNumThen() { return thenActions.size(); }

    public Integer getNumElse() {return elseActions.size(); }
}
