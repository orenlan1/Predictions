package world.helper.function.impl;

import world.entity.api.EntityInstance;
import world.exceptions.MismatchTypesException;
import world.expressions.api.Expression;

public class PercentFunction extends HelperFunctionImpl {

    private final Expression value;
    private final Expression percent;

    public PercentFunction(Expression value, Expression percent) {
        super("percent",2, "expression,expression");
        this.value = value;
        this.percent = percent;
    }

    @Override
    public Object invoke(EntityInstance entityInstance)  throws Exception{
        Object val = value.evaluate(entityInstance);
        Object percentOfVal = percent.evaluate(entityInstance);
        if (!(val instanceof Float)) {
            if ( val instanceof String) throw new MismatchTypesException("Error while running simulation.\n" +
                    "Percent function","number", "string");
            else if ( val instanceof Boolean) throw new MismatchTypesException("Error while running simulation.\n" +
                    "Percent function","number", "boolean");
        }
        else if (!(percentOfVal instanceof Float)) {
            if ( percentOfVal instanceof String) throw new MismatchTypesException("Error while running simulation.\n" +
                    "Percent function","number", "string");
            else if ( percentOfVal instanceof Boolean) throw new MismatchTypesException("Error while running simulation.\n" +
                    "Percent function","number", "boolean");
        }
        return ( (float) percentOfVal / 100) *  (float) val;

    }
}
