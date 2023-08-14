package world.expressions.impl;

import world.entity.api.EntityInstance;
import world.expressions.api.Expression;

public class PropertyNameExpression extends ExpressionImpl {

    public PropertyNameExpression(String expression, String type) {
        super(expression, type);
    }

    @Override
    public Object evaluate() {
        return expression;
    }
}
