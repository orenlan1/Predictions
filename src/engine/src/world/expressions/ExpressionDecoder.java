package world.expressions;

import world.action.api.ActionType;
import world.entity.api.EntityDefinition;
import world.expressions.api.Expression;
import world.expressions.impl.HelperFunctionExpression;
import world.property.api.PropertyDefinition;

import java.util.Set;

public class ExpressionDecoder {


    public static Expression decode(String expressionName, ActionType actionType, PropertyDefinition propertyDefinition, EntityDefinition entityDefinition) {




        return null;
    }

    public boolean isHelperFunction(String expressionName)
    {
        try {
            if (expressionName.contains("random"))
            {
                String arg = expressionName.split("\\(")[1].split("\\)")[0];
                Integer.parseInt(arg);

            }
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }
}
