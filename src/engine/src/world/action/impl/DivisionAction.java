package world.action.impl;

import world.action.api.ActionType;
import world.entity.api.EntityDefinition;
import world.entity.api.EntityInstance;
import world.exceptions.InvalidVariableTypeException;
import world.exceptions.MismatchTypesException;
import world.expressions.api.Expression;
import world.property.api.AbstractPropertyDefinition;
import world.property.api.PropertyDefinition;
import world.property.api.PropertyInstance;

public class DivisionAction extends CalculationAction {

    public DivisionAction(EntityDefinition entityDefinition, PropertyDefinition propertyDefinition, Expression arg1, Expression arg2, SecondaryEntity secondaryEntity) {
        super(ActionType.CALCULATION, entityDefinition, propertyDefinition, arg1, arg2, secondaryEntity);
    }

    @Override
    public void activate(EntityInstance entityInstance) throws Exception {
        PropertyInstance property = entityInstance.getPropertyByName(propertyDefinition.getName());
        AbstractPropertyDefinition.PropertyType type = propertyDefinition.getType();
        Object arg1Value = arg1.evaluate();
        Object arg2Value = arg2.evaluate();


        if (type.equals(AbstractPropertyDefinition.PropertyType.FLOAT)) {
            if ((arg1Value instanceof Float) || (arg1Value instanceof Integer) && ((arg2Value instanceof Float) || (arg2Value instanceof Integer))) {
                if ((float) arg2Value != 0) {
                    property.updateValue((float) arg1Value / (float) arg2Value);
                } else {
                    throw new ArithmeticException("division by zero");
                }
            } else {
                throw new MismatchTypesException("One or more of the arguments in division action", "Integer or Float", arg1Value.getClass().getTypeName() + ", " + arg2Value.getClass().getTypeName());
            }
        }

        if (type.equals(AbstractPropertyDefinition.PropertyType.DECIMAL)) {
            if ((arg1Value instanceof Float) || (arg1Value instanceof Integer) && ((arg2Value instanceof Float) || (arg2Value instanceof Integer))) {
                if ((float) arg2Value != 0) {
                    float newValue = (float) arg1Value / (float) arg2Value;
                    if (newValue - (int) newValue > 0) {
                        throw new InvalidVariableTypeException("performing division", "Integer", "a fraction");
                    } else
                        property.updateValue((int) newValue);
                } else {
                    throw new ArithmeticException("division by zero");
                }
            }
        }
    }
}
