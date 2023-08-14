package world.expressions.impl;

import world.entity.api.EntityInstance;
import world.expressions.api.Expression;

public class PropertyNameExpression extends ExpressionImpl {

    public PropertyNameExpression(String expression) {
        super(expression);
    }

    @Override
    public Object evaluate(EntityInstance entityInstance) {
        return null;
    }
}
