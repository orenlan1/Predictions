package world.expressions.impl;

import world.action.api.ActionType;
import world.entity.api.EntityDefinition;
import world.environment.api.ActiveEnvironment;
import world.expressions.api.Expression;
import world.expressions.impl.ExpressionImpl;
import world.helper.function.api.HelperFunction;
import world.property.api.PropertyDefinition;

public class HelperFunctionExpression extends ExpressionImpl {
    private final HelperFunction helperFunction;

    public HelperFunctionExpression(String expression, PropertyDefinition propertyDefinition, ActiveEnvironment activeEnvironment, EntityDefinition entityDefinition, ActionType actionType, HelperFunction helperFunction) {
        super(expression, propertyDefinition, activeEnvironment, entityDefinition, actionType);
        this.helperFunction = helperFunction;
    }


    @Override
    public Object evaluate() {
        return null;
    }
}
