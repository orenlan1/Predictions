package world.expressions.impl;

import world.action.api.ActionType;
import world.entity.api.EntityDefinition;
import world.entity.api.EntityInstance;
import world.environment.api.ActiveEnvironment;
import world.expressions.api.Expression;
import world.helper.function.impl.EnvironmentFunction;
import world.helper.function.impl.RandomFunction;
import world.property.api.AbstractPropertyDefinition;
import world.property.api.PropertyDefinition;

import java.util.List;
import java.util.Optional;

public abstract class ExpressionImpl implements Expression {
    protected final String expression;
    //protected final PropertyDefinition propertyDefinition;
    //protected final ActiveEnvironment activeEnvironment;
    //private final EntityDefinition entityDefinition;
   // private final ActionType actionType;
    public ExpressionImpl(String expression)
    {
        this.expression = expression;
    }


//    public ExpressionImpl(String expression, PropertyDefinition propertyDefinition, ActiveEnvironment activeEnvironment, EntityDefinition entityDefinition,ActionType actionType) {
//        this.expression = expression;
//        this.propertyDefinition = propertyDefinition;
//        this.activeEnvironment = activeEnvironment;
//        this.entityDefinition = entityDefinition;
//        this.actionType = actionType;
//    }

//    @Override
//    public void checkExpressionValidation() {
//        AbstractPropertyDefinition.PropertyType propertyType = propertyDefinition.getType();
//        if (actionType.equals(actionType.INCREASE) || actionType.equals(actionType.DECREASE)) {
//            if (propertyType.equals(AbstractPropertyDefinition.PropertyType.DECIMAL) || propertyType.equals(AbstractPropertyDefinition.PropertyType.FLOAT))
//        }
//
//
//
//
//
//
//
//    }


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

    //@Override
    //public Object evaluate(EntityInstance entityInstance) throws NumberFormatException {
    //    if (expression.contains("random")) {
    //        String arg = expression.split("\\(")[1].split("\\)")[0];
    //        return new RandomFunction().invoke(arg, activeEnvironment);
    //    } else if (expression.contains("environment")) {
    //        String arg = expression.split("\\(")[1].split("\\)")[0];
    //        return new EnvironmentFunction().invoke(arg, activeEnvironment);
    //    } else if (entityInstance.getPropertyByName(expression) != null) {
    //        return expression;
    //    } else { //WILDCARD
    //        AbstractPropertyDefinition.PropertyType type = propertyDefinition.getType();
    //        if (type.equals(AbstractPropertyDefinition.PropertyType.DECIMAL)) {
    //            return Integer.parseInt(expression);
    //        } else if (type.equals(AbstractPropertyDefinition.PropertyType.FLOAT)) {
    //            return Float.parseFloat(expression);
    //        } else if (type.equals(AbstractPropertyDefinition.PropertyType.BOOLEAN)) {
    //            return Boolean.parseBoolean(expression);
    //        } else if (type.equals(AbstractPropertyDefinition.PropertyType.STRING)) {
    //            return expression;
    //        }
    //    }
    //    return null;
    //}
}
