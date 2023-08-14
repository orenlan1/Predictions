
package world.expressions;

import world.entity.api.EntityDefinition;
import world.environment.api.ActiveEnvironment;
import world.exceptions.EntityPropertyNameExistException;
import world.exceptions.MismatchTypesException;
import world.expressions.api.Expression;
import world.expressions.impl.HelperFunctionExpression;
import world.expressions.impl.PropertyNameExpression;
import world.expressions.impl.ValueExpression;
import world.helper.function.impl.EnvironmentFunction;
import world.helper.function.impl.RandomFunction;
import world.property.api.AbstractPropertyDefinition;
import world.property.api.PropertyDefinition;
import world.property.api.PropertyInstance;

public class ExpressionDecoder {
    public static Expression decode(String expressionName, ActiveEnvironment activeEnvironment, EntityDefinition entityDefinition, AbstractPropertyDefinition.PropertyType type, String actionName) throws Exception {
        Expression expression = null;
        expression = ExpressionDecoder.isHelperFunction(expressionName,activeEnvironment);
        if (expression != null)
            return expression;

        expression = ExpressionDecoder.isPropertyName(expressionName, entityDefinition);
        if (expression != null)
            return expression;




        return null;
    }

    public static Expression isHelperFunction(String expressionName, ActiveEnvironment activeEnvironment) throws Exception
    {
        Expression expression = null;
        try {
            if (expressionName.contains("environment")) {
                String arg = expressionName.split("\\(")[1].split("\\)")[0];
                PropertyInstance propertyInstance = activeEnvironment.getProperty(arg).orElseThrow(Exception::new);/// some exception
                expression = new HelperFunctionExpression(expressionName, new EnvironmentFunction(propertyInstance));// maybe the environment function should get the property instance
            }
            if (expressionName.contains("random")) {
                String arg = expressionName.split("\\(")[1].split("\\)")[0];
                try {
                    Integer.parseInt(arg);
                } catch (NumberFormatException e) {
                    throw new MismatchTypesException("Random helper function", "Integer", arg);
                }
                expression = new HelperFunctionExpression(expressionName, new RandomFunction(arg));
            }

        } catch (NumberFormatException  e) {
            throw new RuntimeException(e);
        }
        return expression;
    }

    public static Expression isPropertyName(String expressionName, EntityDefinition entityDefinition) throws EntityPropertyNameExistException {
        for (PropertyDefinition property : entityDefinition.getPropertiesList())
        {
            if (property.getName().equals(expressionName))
                return new PropertyNameExpression(expressionName);
        }
        return null;
    }

    public static Expression freeValue(String expressionName, String actionName, AbstractPropertyDefinition.PropertyType type) throws Exception {
        if (type.equals(AbstractPropertyDefinition.PropertyType.DECIMAL)) {
            try {
                Integer.parseInt(expressionName);
                return new ValueExpression(expressionName, "decimal");
            } catch (NumberFormatException e) {
                throw new MismatchTypesException(actionName, "Integer", expressionName);
            }
        } else if (type.equals(AbstractPropertyDefinition.PropertyType.FLOAT)) {
            try {
                Float.parseFloat(expressionName);
                return new ValueExpression(expressionName, "float");
            } catch (NumberFormatException e) {
                throw new MismatchTypesException(actionName, "Float", expressionName);
            }
        } else if (type.equals(AbstractPropertyDefinition.PropertyType.BOOLEAN)) {
            if (expressionName.equals("true") || expressionName.equals("false"))
                return new ValueExpression(expressionName, "boolean");
            else
                throw new MismatchTypesException(actionName, "Boolean", expressionName);
        } else
            return new ValueExpression(expressionName, "string");
    }
}

