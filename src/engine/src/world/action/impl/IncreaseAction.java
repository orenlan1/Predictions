package world.action.impl;

import world.entity.api.EntityDefinition;
import world.entity.api.EntityInstance;
import world.expressions.api.Expression;

public class IncreaseAction extends ActionImpl {
    Expression expression;

    public IncreaseAction(EntityDefinition entityDefinition) {
        super(ActionType.INCREASE, entityDefinition);
        expression = new ExpressionImpl();
    }


    @Override
    public void activate(EntityInstance entityInstance) {

    }

}
