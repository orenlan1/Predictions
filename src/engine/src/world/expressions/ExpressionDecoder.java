package world.expressions;

import world.World;
import world.action.api.ActionType;
import world.entity.api.EntityDefinition;
import world.exceptions.EntityNotExistException;
import world.expressions.api.Expression;
import world.expressions.impl.HelperFunctionExpression;
import world.property.api.PropertyDefinition;

import java.util.Optional;
import java.util.Set;

public class ExpressionDecoder {


    public static Expression decode(String expressionName, ActionType actionType, String propertyName, String entityName) throws EntityNotExistException {
        Optional<EntityDefinition> maybeEntityDefinition = World.getEntityDefinitionByName(entityName);
        EntityDefinition entityDefinition = maybeEntityDefinition.orElseThrow(() -> new EntityNotExistException(entityName));







        return null;
    }

    public boolean isHelperFunction(String expressionName)
    {
        try {
            if (expressionName.contains("random"))
            {
                String arg = expressionName.split("\\(")[1].split("\\)")[0];
            }
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }
}
