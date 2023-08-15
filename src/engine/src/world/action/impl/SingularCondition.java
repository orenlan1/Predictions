package world.action.impl;

import world.action.api.Action;
import world.entity.api.EntityDefinition;
import world.entity.api.EntityInstance;
import world.exceptions.InvalidConditionOperatorException;
import world.exceptions.InvalidVariableTypeException;
import world.expressions.api.Expression;
import world.property.api.PropertyDefinition;
import world.property.api.PropertyInstance;

import java.util.List;

public class SingularCondition extends ConditionAction {
    private final Expression value;
    private final String operator;

    public SingularCondition(EntityDefinition entityDefinition, PropertyDefinition propertyDefinition, Expression value, String operator, List<Action> thenActions, List<Action> elseActions) {
        super(entityDefinition, propertyDefinition, thenActions, elseActions);
        this.value = value;
        this.operator = operator;
    }

    @Override
    public boolean evaluate(EntityInstance entityInstance) throws InvalidConditionOperatorException, InvalidVariableTypeException {
        PropertyInstance property = entityInstance.getPropertyByName(propertyDefinition.getName());
        Object propertyValue = property.getValue();
        Object expValue = value.evaluate();

        if ((propertyValue instanceof Integer || propertyValue instanceof Float) && (expValue instanceof Integer || expValue instanceof Float)) {
            if (operator.equals("="))
                return ((Float) propertyValue).equals((Float) expValue);
            else if (operator.equals("!="))
                return (!((Float) propertyValue).equals((Float) expValue));
            else if (operator.equals("bt"))
                return (Float) propertyValue > (Float) expValue;
            else if (operator.equals("lt"))
                return (Float) propertyValue < (Float) expValue;
            else
                throw new InvalidConditionOperatorException(operator);
        } else {
            throw new InvalidVariableTypeException("evaluating condition", "Integer of Float", propertyValue.getClass().getTypeName() + ", " + expValue.getClass().getTypeName());
        }
    }

}
