package world.action.impl;

import world.action.api.ActionType;
import world.context.Context;
import world.entity.api.EntityDefinition;
import world.entity.api.EntityInstance;
import world.exceptions.InvalidVariableTypeException;
import world.exceptions.MismatchTypesException;
import world.expressions.api.Expression;
import world.expressions.impl.HelperFunctionExpression;
import world.property.api.AbstractPropertyDefinition;
import world.property.api.PropertyDefinition;
import world.property.api.PropertyInstance;

public class MultiplicationAction extends CalculationAction {
    public MultiplicationAction(EntityDefinition entityDefinition, PropertyDefinition propertyDefinition, Expression arg1, Expression arg2, SecondaryEntity secondaryEntity, Context entitiesContext) {
        super(ActionType.MULTIPLICATION, entityDefinition, propertyDefinition, arg1, arg2, secondaryEntity, entitiesContext);
    }

    @Override
    public void activate(EntityInstance entityInstance, int currTick) throws Exception, MismatchTypesException, InvalidVariableTypeException {
        PropertyInstance property = entityInstance.getPropertyByName(propertyDefinition.getName());
        AbstractPropertyDefinition.PropertyType type = propertyDefinition.getType();

        Object arg1Value = null;
        Object arg2Value = null;

        if (arg1 instanceof HelperFunctionExpression) {
            HelperFunctionExpression helperArg1 = (HelperFunctionExpression) arg1;
            arg1Value = helperArg1.evaluate(entityInstance, currTick);
        } else
            arg1Value = arg1.evaluate(entityInstance);

        if (arg2 instanceof HelperFunctionExpression) {
            HelperFunctionExpression helperArg2 = (HelperFunctionExpression) arg2;
            arg2Value = helperArg2.evaluate(entityInstance, currTick);
        } else
            arg2Value = arg2.evaluate(entityInstance);

        if (type.equals(AbstractPropertyDefinition.PropertyType.FLOAT)) {
            if ((arg1Value instanceof Float) || (arg1Value instanceof Integer) && ((arg2Value instanceof Float) || (arg2Value instanceof Integer))) {
                property.updateValue((float) arg1Value * (float) arg2Value, currTick);
            }
            else {
                throw new MismatchTypesException("One or more of the arguments in multiplication action", "Integer or Float", arg1Value.getClass().getTypeName() + ", " + arg2Value.getClass().getTypeName());
            }
        }

        if (type.equals(AbstractPropertyDefinition.PropertyType.DECIMAL)) {
            if ((arg1Value instanceof Float) || (arg1Value instanceof Integer) && ((arg2Value instanceof Float) || (arg2Value instanceof Integer))) {
                float newValue = (float) arg1Value * (float) arg2Value;
                if (newValue - (int) newValue > 0) {
                    throw new InvalidVariableTypeException("performing multiplication", "Integer", "a fraction");
                }
                else
                    property.updateValue((int) newValue, currTick);
            }
        }
    }
}
