package world.expressions.impl;

import world.entity.api.EntityInstance;
import world.expressions.api.Expression;
import world.property.api.AbstractPropertyDefinition;
import world.property.api.PropertyDefinition;

import java.util.List;
import java.util.Optional;

public class ExpressionImpl implements Expression {
    private final String expression;
    private final PropertyDefinition propertyDefinition;


    public ExpressionImpl(String expression, PropertyDefinition propertyDefinition) {
        this.expression = expression;
        this.propertyDefinition = propertyDefinition;
    }

    @Override
    public Object evaluate(EntityInstance entityInstance) throws NumberFormatException {
        if (expression.contains("random")) {
            //implement
            return null;
        } else if (expression.contains("environment")) {
            //implement
            return null;
        } else if (entityInstance.getPropertyByName(expression) != null) {
            return expression;
        } else { //WILDCARD
            AbstractPropertyDefinition.PropertyType type = propertyDefinition.getType();
            if (type.equals(AbstractPropertyDefinition.PropertyType.DECIMAL)) {
                return Integer.parseInt(expression);
            } else if (type.equals(AbstractPropertyDefinition.PropertyType.FLOAT)) {
                return Float.parseFloat(expression);
            } else if (type.equals(AbstractPropertyDefinition.PropertyType.BOOLEAN)) {
                return Boolean.parseBoolean(expression);
            } else if (type.equals(AbstractPropertyDefinition.PropertyType.STRING)) {
                return expression;
            }
        }
        return null;
    }
}
