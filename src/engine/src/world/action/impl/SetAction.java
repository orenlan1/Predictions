package world.action.impl;

import world.action.api.ActionType;
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

    public SetAction(EntityDefinition entityDefinition, Expression expression, PropertyDefinition propertyDefinition) {
        super(ActionType.SET, entityDefinition, propertyDefinition);
        this.expression = expression;
    }

    @Override
    public void activate(EntityInstance entityInstance) throws Exception {
        PropertyInstance property = entityInstance.getPropertyByName(propertyDefinition.getName());
        AbstractPropertyDefinition.PropertyType type = property.getPropertyDefinition().getType();

        try {
            Object value = expression.evaluate();
            if (type.equals(AbstractPropertyDefinition.PropertyType.DECIMAL)) {
                if ((value instanceof Integer)) {
                    IntegerPropertyDefinition intProperty = (IntegerPropertyDefinition) property.getPropertyDefinition();
                    Integer intValue = (Integer) value;
                    int from = intProperty.getFrom();
                    int to = intProperty.getTo();
                    if (intValue >= from && intValue <= to)
                        property.updateValue(intValue);
                }
            } else if (type.equals(AbstractPropertyDefinition.PropertyType.FLOAT)) {
                if ((value instanceof Float)) {
                    FloatPropertyDefinition floatProperty = (FloatPropertyDefinition) property.getPropertyDefinition();
                    Float floatValue = (Float) value;
                    float from = floatProperty.getFrom();
                    float to = floatProperty.getTo();
                    if (floatValue >= from && floatValue <= to)
                        property.updateValue(floatValue);
                }
            } else if (type.equals(AbstractPropertyDefinition.PropertyType.BOOLEAN)) {
                if ((value instanceof Boolean))
                    property.updateValue(value);
            } else if (type.equals(AbstractPropertyDefinition.PropertyType.STRING)) {
                if ((value instanceof String))
                    property.updateValue(value);
            } else {
                throw new InvalidVariableTypeException("performing Set action", type.toString(), value.getClass().getTypeName());
            }
        }
        catch (NumberFormatException e) {
            throw new Exception("Invalid expression, expected " + propertyDefinition.getType().toString());
        }
    }
}
