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
    private final Expression property;
    private final Expression value;
    private final String operator;

    public SingularCondition(EntityDefinition entityDefinition, Expression property, Expression value, String operator, List<Action> thenActions, List<Action> elseActions, SecondaryEntity secondaryEntity) {
        super(entityDefinition, thenActions, elseActions, secondaryEntity);
        this.value = value;
        this.property = property;
        this.operator = operator;
    }

    @Override
    public boolean evaluate(EntityInstance entityInstance) throws InvalidConditionOperatorException, InvalidVariableTypeException {
        PropertyInstance property = entityInstance.getPropertyByName(propertyDefinition.getName());
        Object propertyValue = this.property.evaluate();
        String propType = this.property.getType();
        //Object propertyValue = property.getValue();
        //String propType = property.getPropertyDefinition().getType().name().toLowerCase();
        Object expValue = value.evaluate();
        String expType = value.getType();

        switch (operator) {
            case "=":
                if (expType.equals("string"))
                    return ((String) propertyValue).equals((String) expValue);
                else
                    return propertyValue == expValue;
            case "!=":
                if (expType.equals("string"))
                    return !((String) propertyValue).equals((String) expValue);
                else
                    return propertyValue != expValue;
            case "bt":
                if (expType.equals("string") || expType.equals("boolean"))
                    throw new InvalidVariableTypeException("evaluating a condition", "decimal or float", expType);
                else if (expType.equals("decimal") && propType.equals("decimal"))
                    return ((int) propertyValue) > ((int) expValue);
                else if (expType.equals("float") && propType.equals("decimal"))
                    return ((int) propertyValue) > ((float) expValue);
                else if (expType.equals("decimal") && propType.equals("float"))
                    return ((float) propertyValue) > ((int) expValue);
                else if (expType.equals("float") && propType.equals("float"))
                    return ((float) propertyValue) > ((float) expValue);
            case "lt":
                if (expType.equals("string") || expType.equals("boolean"))
                    throw new InvalidVariableTypeException("evaluating a condition", "decimal or float", expType);
                else if (expType.equals("decimal") && propType.equals("decimal"))
                    return ((int) propertyValue) < ((int) expValue);
                else if (expType.equals("float") && propType.equals("decimal"))
                    return ((int) propertyValue) < ((float) expValue);
                else if (expType.equals("decimal") && propType.equals("float"))
                    return ((float) propertyValue) < ((int) expValue);
                else if (expType.equals("float") && propType.equals("float"))
                    return ((float) propertyValue) < ((float) expValue);
            default:
                throw new InvalidConditionOperatorException(operator);
        }
    }
}


