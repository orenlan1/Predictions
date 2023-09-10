package world.action.impl;

import world.action.api.ActionType;
import world.context.Context;
import world.entity.api.EntityDefinition;
import world.entity.api.EntityInstance;
import world.expressions.api.Expression;
import world.expressions.impl.HelperFunctionExpression;
import world.helper.function.api.HelperFunction;
import world.property.api.AbstractPropertyDefinition;
import world.property.api.PropertyDefinition;
import world.property.api.PropertyInstance;
import world.property.impl.FloatPropertyDefinition;
import world.property.impl.IntegerPropertyDefinition;

import java.util.ArrayList;
import java.util.List;

public class DecreaseAction extends ActionImpl{

    private final Expression by;

    public DecreaseAction(EntityDefinition entityDefinition, Expression expression, PropertyDefinition propertyDefinition, SecondaryEntity secondaryEntity, Context entitiesContext) {
        super(ActionType.DECREASE, entityDefinition, propertyDefinition, secondaryEntity, entitiesContext);
        this.by = expression;
    }
    @Override
    public void activate(EntityInstance entityInstance, int currTick) throws Exception {
        PropertyInstance property = entityInstance.getPropertyByName(propertyDefinition.getName());
        try {
            Object value = by.evaluate(entityInstance);
            Object newValue = null;

            if (propertyDefinition.getType().equals(AbstractPropertyDefinition.PropertyType.DECIMAL)) {
                if (value instanceof Integer) {
                    IntegerPropertyDefinition intPropertyDef = (IntegerPropertyDefinition) propertyDefinition;
                    int from = intPropertyDef.getFrom();
                    newValue = (Integer) property.getValue() + (Integer) value;
                    if ((Integer) newValue < from) {
                        return;
                    }


                }
            }
            else if (propertyDefinition.getType().equals(AbstractPropertyDefinition.PropertyType.FLOAT)) {
                if (value instanceof Float) {
                    FloatPropertyDefinition floatPropertyDef = (FloatPropertyDefinition) propertyDefinition;
                    float from = floatPropertyDef.getFrom();
                    newValue = (float) property.getValue() + (float) value;
                    if ((float) newValue < from) {
                        return;
                    }
                }
            }
            if (newValue != null) {
                property.updateValue(newValue, currTick);
            }
        }
        catch (NumberFormatException e) {
            throw new Exception("Invalid expression, expected " + propertyDefinition.getType().toString());
        }
    }

    @Override
    public List<String> getArguments() {
        List<String> args =  new ArrayList<>();
        args.add(by.toString());
        return args;
    }
}
