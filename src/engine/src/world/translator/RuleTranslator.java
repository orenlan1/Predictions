package world.translator;

import generated.*;
import world.action.api.Action;
import world.action.impl.*;
import world.entity.api.EntityDefinition;
import world.environment.api.ActiveEnvironment;
import world.exceptions.*;
import world.expressions.ExpressionDecoder;
import world.property.api.AbstractPropertyDefinition;
import world.property.api.PropertyDefinition;
import world.rule.activation.Activation;
import world.rule.activation.ActivationImpl;
import world.rule.api.Rule;
import world.rule.impl.RuleImpl;
import world.expressions.api.Expression;

import java.util.ArrayList;
import java.util.List;

public class RuleTranslator {

    public static Rule translateRule(PRDRule prdRule, List<EntityDefinition> entitiesList, ActiveEnvironment activeEnvironment) throws Exception {
        String ruleName = prdRule.getName();
        Double ruleProbability = prdRule.getPRDActivation().getProbability();
        Integer ruleTicks = prdRule.getPRDActivation().getTicks();
        Activation ruleActivation = new ActivationImpl(ruleProbability,ruleTicks);
        Rule rule = new RuleImpl(ruleName, ruleActivation);
        for (PRDAction prdAction : prdRule.getPRDActions().getPRDAction())
        {
            rule.addAction(translateAction(prdAction, entitiesList, activeEnvironment));
        }
        return rule;
    }


    public static Action translateAction(PRDAction prdAction, List<EntityDefinition> entitiesList, ActiveEnvironment activeEnvironment) throws Exception {
        String actionName = prdAction.getType();
        String entityName = prdAction.getEntity();
        EntityDefinition entityDefinition = getEntityDefinition(entityName,entitiesList);
        String propertyName = prdAction.getProperty();
        String resultProp = prdAction.getResultProp();

        switch(actionName) {
            case "increase":
                return translateIncreaseAction(prdAction, entityDefinition, propertyName, activeEnvironment);
            case "decrease":
                return translateDecreaseAction(prdAction, entityDefinition, propertyName, activeEnvironment);
            case "calculation":
                return translateCalculationAction(prdAction, entityDefinition, resultProp, activeEnvironment);
            case "condition":
                return translateConditionAction(prdAction.getPRDCondition(), prdAction.getPRDThen(), prdAction.getPRDElse(), entityDefinition, activeEnvironment, entitiesList);
            case "set":
                return translateSetAction(prdAction, entityDefinition, propertyName, activeEnvironment);
            case "kill":
                return translateKillAction(entityDefinition);
            default:
                return null;
        }
    }


    public static EntityDefinition getEntityDefinition(String entityName, List<EntityDefinition> entitiesList) throws Exception {
        for (EntityDefinition entityDefinition : entitiesList) {
            if ( entityDefinition.getName().equals(entityName)) {
                return entityDefinition;
            }
        }
        throw new EntityNotExistException(entityName);
    }

    public static IncreaseAction translateIncreaseAction(PRDAction prdAction, EntityDefinition entityDefinition, String propertyName, ActiveEnvironment activeEnvironment) throws Exception {
        Expression expression;
        PropertyDefinition propertyDefinition = entityDefinition.getPropertyByName(propertyName);
        AbstractPropertyDefinition.PropertyType type = propertyDefinition.getType();
        if (type.equals(AbstractPropertyDefinition.PropertyType.DECIMAL) || type.equals(AbstractPropertyDefinition.PropertyType.FLOAT) ) {
            expression = ExpressionDecoder.decode(prdAction.getBy(), activeEnvironment, entityDefinition, type,"increase");
        } else {
            throw new PropertyNotMatchActionException(prdAction.getType(),propertyDefinition.getName(),entityDefinition.getName(),type.toString());
        }
        return new IncreaseAction(entityDefinition, expression, propertyDefinition);
    }

    public static DecreaseAction translateDecreaseAction(PRDAction prdAction, EntityDefinition entityDefinition, String propertyName, ActiveEnvironment activeEnvironment) throws Exception {
        Expression expression;
        PropertyDefinition propertyDefinition = entityDefinition.getPropertyByName(propertyName);
        AbstractPropertyDefinition.PropertyType type = propertyDefinition.getType();
        if (type.equals(AbstractPropertyDefinition.PropertyType.DECIMAL) || type.equals(AbstractPropertyDefinition.PropertyType.FLOAT) ) {
            expression = ExpressionDecoder.decode(prdAction.getBy(), activeEnvironment, entityDefinition, type,"decrease");
        } else {
            throw new PropertyNotMatchActionException(prdAction.getType(),propertyDefinition.getName(),entityDefinition.getName(),type.toString());
        }
        return new DecreaseAction(entityDefinition, expression,propertyDefinition);
    }

    public static CalculationAction translateCalculationAction(PRDAction prdAction, EntityDefinition entityDefinition, String resultProp, ActiveEnvironment activeEnvironment) throws Exception {
        PRDMultiply prdMultiply = prdAction.getPRDMultiply();
        PRDDivide prdDivide = prdAction.getPRDDivide();
        PropertyDefinition propertyDefinition = entityDefinition.getPropertyByName(resultProp);
        AbstractPropertyDefinition.PropertyType type = propertyDefinition.getType();
        if (type.equals(AbstractPropertyDefinition.PropertyType.DECIMAL) || type.equals(AbstractPropertyDefinition.PropertyType.FLOAT)) {
            if ( prdMultiply == null) {
                Expression expressionArg1 = ExpressionDecoder.decode(prdDivide.getArg1(),activeEnvironment,entityDefinition,type,"calculation");
                Expression expressionArg2 = ExpressionDecoder.decode(prdDivide.getArg2(),activeEnvironment,entityDefinition,type,"calculation");
                return new DivisionAction(entityDefinition,propertyDefinition,expressionArg1,expressionArg2);
            }
            else {
                Expression expressionArg1 = ExpressionDecoder.decode(prdMultiply.getArg1(),activeEnvironment,entityDefinition,type,"calculation");
                Expression expressionArg2 = ExpressionDecoder.decode(prdMultiply.getArg2(),activeEnvironment,entityDefinition,type,"calculation");
                return new MultiplicationAction(entityDefinition,propertyDefinition,expressionArg1,expressionArg2);
            }
        }
        throw new PropertyNotMatchActionException(prdAction.getType(),propertyDefinition.getName(),entityDefinition.getName(),type.toString());
    }

    public static ConditionAction translateConditionAction(PRDCondition prdCondition, PRDThen prdThen, PRDElse prdElse, EntityDefinition entityDefinition, ActiveEnvironment activeEnvironment, List<EntityDefinition> entityDefinitions) throws  Exception {
        String singularity = prdCondition.getSingularity();
        String logic = prdCondition.getLogical();
        String operator = prdCondition.getOperator();
        String propertyName = prdCondition.getProperty();
        PropertyDefinition propertyDefinition = entityDefinition.getPropertyByName(propertyName);
        String valueStr = prdCondition.getValue();
        Expression value = ExpressionDecoder.decode(valueStr, activeEnvironment, entityDefinition, propertyDefinition.getType(), "condition");

        List<Action> thenActions = new ArrayList<>();
        List<Action> elseActions = new ArrayList<>();
        List<ConditionAction> conditions = new ArrayList<>();

        for (PRDAction prdAction1 : prdThen.getPRDAction()) {
            thenActions.add(translateAction(prdAction1, entityDefinitions, activeEnvironment));
        }
        for (PRDAction prdAction2 : prdElse.getPRDAction()) {
            elseActions.add(translateAction(prdAction2, entityDefinitions, activeEnvironment));
        }
        for (PRDCondition prdCondition1 : prdCondition.getPRDCondition()) {
            conditions.add(translateConditionActionSecondary(prdCondition1, entityDefinition, activeEnvironment));
        }

        boolean boolSingularity = !singularity.equals("multiple");

        if (boolSingularity)
            return new SingularCondition(entityDefinition, propertyDefinition, value, operator, thenActions, elseActions);
        else
            return new MultipleCondition(conditions, logic, entityDefinition, propertyDefinition, thenActions, elseActions);
    }

    public static ConditionAction translateConditionActionSecondary(PRDCondition prdCondition, EntityDefinition entityDefinition, ActiveEnvironment activeEnvironment) throws Exception {
        String singularity = prdCondition.getSingularity();
        String logic = prdCondition.getLogical();
        String operator = prdCondition.getOperator();
        String propertyName = prdCondition.getProperty();
        PropertyDefinition propertyDefinition = entityDefinition.getPropertyByName(propertyName);
        String valueStr = prdCondition.getValue();
        Expression value = ExpressionDecoder.decode(valueStr, activeEnvironment, entityDefinition, propertyDefinition.getType(), "condition");

        List<Action> thenActions = new ArrayList<>();
        List<Action> elseActions = new ArrayList<>();
        List<ConditionAction> conditions = new ArrayList<>();

        for (PRDCondition prdCondition1 : prdCondition.getPRDCondition()) {
            conditions.add(translateConditionActionSecondary(prdCondition1, entityDefinition, activeEnvironment));
        }
        boolean boolSingularity = !singularity.equals("multiple");

        if (boolSingularity)
            return new SingularCondition(entityDefinition, propertyDefinition, value, operator, thenActions, elseActions);
        else
            return new MultipleCondition(conditions, logic, entityDefinition, propertyDefinition, thenActions, elseActions);
    }


    public static SetAction translateSetAction(PRDAction prdAction, EntityDefinition entityDefinition, String propertyName, ActiveEnvironment activeEnvironment) throws Exception {
        PropertyDefinition propertyDefinition = entityDefinition.getPropertyByName(propertyName);
        AbstractPropertyDefinition.PropertyType type = propertyDefinition.getType();
        Expression expression = ExpressionDecoder.decode(prdAction.getValue(), activeEnvironment,entityDefinition,type,"set" );
        return new SetAction(entityDefinition,expression,propertyDefinition);
    }

    public static KillAction translateKillAction(EntityDefinition entityDefinition) {
        return new KillAction(entityDefinition);
    }
}
