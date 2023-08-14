package world.action.impl;

import world.action.api.ActionType;
import world.entity.api.EntityDefinition;
import world.entity.api.EntityInstance;
import world.expressions.api.Expression;
import world.property.api.AbstractPropertyDefinition;
import world.property.api.PropertyDefinition;
import world.property.api.PropertyInstance;

public class DecreaseAction extends ActionImpl{

    private Expression by;

    public DecreaseAction(EntityDefinition entityDefinition, Expression expression, PropertyDefinition propertyDefinition) {
        super(ActionType.DECREASE, entityDefinition, propertyDefinition);
        this.by = expression;
    }
    @Override
    public void activate(EntityInstance entityInstance) throws Exception {
        PropertyInstance property = entityInstance.getPropertyByName(propertyDefinition.getName());
        try {
            Object value = by.evaluate(entityInstance);
            Object newValue = null;

            if (propertyDefinition.getType().equals(AbstractPropertyDefinition.PropertyType.DECIMAL)) {
                if (value instanceof Integer) {
                    newValue = (Integer) property.getValue() - (Integer) value;
                }
            }
            else if (propertyDefinition.getType().equals(AbstractPropertyDefinition.PropertyType.FLOAT)) {
                if (value instanceof Float) {
                    newValue = (Float) property.getValue() - (Float) value;
                }
            }
            property.updateValue(newValue);
        }
        catch (NumberFormatException e) {
            throw new Exception("Invalid expression, expected " + propertyDefinition.getType().toString());
        }

    }
}
