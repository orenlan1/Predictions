package world.expressions.impl;

import world.entity.api.EntityInstance;
import world.environment.api.ActiveEnvironment;
import world.expressions.api.Expression;
import world.helper.function.impl.EnvironmentFunction;
import world.helper.function.impl.RandomFunction;
import world.property.api.AbstractPropertyDefinition;
import world.property.api.PropertyDefinition;

import java.util.List;
import java.util.Optional;

public class ExpressionImpl implements Expression {
    private final String expression;
    private final PropertyDefinition propertyDefinition;
    private final ActiveEnvironment activeEnvironment;

    public ExpressionImpl(String expression, PropertyDefinition propertyDefinition, ActiveEnvironment activeEnvironment) {
        this.expression = expression;
        this.propertyDefinition = propertyDefinition;
        this.activeEnvironment = activeEnvironment;
    }

    @Override
    public Object evaluate(EntityInstance entityInstance) throws NumberFormatException {
        if (expression.contains("random")) {
            String arg = expression.split("\\(")[1].split("\\)")[0];
            return new RandomFunction().invoke(arg, activeEnvironment);
        } else if (expression.contains("environment")) {
            String arg = expression.split("\\(")[1].split("\\)")[0];
            return new EnvironmentFunction().invoke(arg, activeEnvironment);
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
