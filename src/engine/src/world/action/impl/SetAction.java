package world.action.impl;

import world.action.api.ActionType;
import world.context.Context;
import world.entity.api.EntityDefinition;
import world.entity.api.EntityInstance;
import world.exceptions.InvalidVariableTypeException;
import world.expressions.api.Expression;
import world.property.api.AbstractPropertyDefinition;
import world.property.api.PropertyDefinition;
import world.property.api.PropertyInstance;
import world.property.impl.FloatPropertyDefinition;
import world.property.impl.IntegerPropertyDefinition;

public class SetAction extends ActionImpl{

    private Expression expression;

    public SetAction(EntityDefinition entityDefinition, Expression expression, PropertyDefinition propertyDefinition, SecondaryEntity secondaryEntity, Context entitiesContext) {
        super(ActionType.SET, entityDefinition, propertyDefinition, secondaryEntity, entitiesContext);
        this.expression = expression;
    }

    @Override
    public void activate(EntityInstance entityInstance, int currTick) throws Exception {
        PropertyInstance property = entityInstance.getPropertyByName(propertyDefinition.getName());
        AbstractPropertyDefinition.PropertyType type = property.getPropertyDefinition().getType();

        try {
            Object value = expression.evaluate(entityInstance);
            if (type.equals(AbstractPropertyDefinition.PropertyType.DECIMAL)) {
                if ((value instanceof Integer)) {
                    IntegerPropertyDefinition intProperty = (IntegerPropertyDefinition) property.getPropertyDefinition();
                    Integer intValue = (Integer) value;
                    int from = intProperty.getFrom();
                    int to = intProperty.getTo();
                    if (intValue >= from && intValue <= to)
                        property.updateValue(intValue, currTick);
                }
            } else if (type.equals(AbstractPropertyDefinition.PropertyType.FLOAT)) {
                if ((value instanceof Float)) {
                    FloatPropertyDefinition floatProperty = (FloatPropertyDefinition) property.getPropertyDefinition();
                    Float floatValue = (float) value;
                    float from = floatProperty.getFrom();
                    float to = floatProperty.getTo();
                    if (floatValue >= from && floatValue <= to)
                        property.updateValue(floatValue, currTick);
                }
            } else if (type.equals(AbstractPropertyDefinition.PropertyType.BOOLEAN)) {
                if ((value instanceof Boolean))
                    property.updateValue(value, currTick);
            } else if (type.equals(AbstractPropertyDefinition.PropertyType.STRING)) {
                if ((value instanceof String))
                    property.updateValue(value, currTick);
            } else {
                throw new InvalidVariableTypeException("performing Set action", type.toString(), value.getClass().getTypeName());
            }
        }
        catch (NumberFormatException e) {
            throw new Exception("Invalid expression, expected " + propertyDefinition.getType().toString());
        }
    }
}
