package world.expressions.api;

import world.entity.api.EntityInstance;

public interface Expression {
    Object evaluate(EntityInstance entityInstance) throws NumberFormatException;
    void checkExpressionValidation();
}
