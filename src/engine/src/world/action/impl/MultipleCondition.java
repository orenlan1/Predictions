package world.action.impl;

import world.action.api.Action;
import world.context.Context;
import world.entity.api.EntityDefinition;
import world.entity.api.EntityInstance;
import world.exceptions.InvalidConditionOperatorException;
import world.exceptions.InvalidVariableTypeException;
import world.property.api.PropertyDefinition;

import java.util.List;

public class MultipleCondition extends ConditionAction {
    private final List<ConditionAction> conditions;
    private final String logic;

    public MultipleCondition(List<ConditionAction> conditions, String logic, EntityDefinition entityDefinition, List<Action> thenActions, List<Action> elseActions, SecondaryEntity secondaryEntity, Context entitiesContext) {
        super(entityDefinition, thenActions, elseActions, secondaryEntity, entitiesContext);
        this.conditions = conditions;
        this.logic = logic;
    }

    @Override
    public boolean evaluate(EntityInstance entityInstance) throws Exception, InvalidConditionOperatorException, InvalidVariableTypeException {
        boolean conditionRes = false, tempRes;

        if (logic.equals("and"))
            conditionRes = true;

        for (ConditionAction condition : conditions) {
            tempRes = condition.evaluate(entityInstance);

            if (logic.equals("or"))
                conditionRes = conditionRes || tempRes;
            else
                conditionRes = conditionRes && tempRes;
        }
        return conditionRes;
    }

    @Override
    public String toString() { return "MULTIPLE CONDITION action"; }
}
