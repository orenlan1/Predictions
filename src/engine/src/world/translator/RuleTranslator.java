package world.translator;


import jaxb.generated.PRDRule;
import jaxb.generated.PRDAction;
import jaxb.generated.PRDActivation;
import jaxb.generated.PRDThen;
import jaxb.generated.PRDElse;
import jaxb.generated.PRDCondition;
import jaxb.generated.PRDDivide;
import jaxb.generated.PRDMultiply;
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
        Double ruleProbability = 1.0;
        Integer ruleTicks = 1;
        PRDActivation prdActivation = prdRule.getPRDActivation();
        if (prdActivation != null) {
            if (prdRule.getPRDActivation().getProbability() != null)
                ruleProbability = prdRule.getPRDActivation().getProbability();
            if (prdRule.getPRDActivation().getTicks() != null)
                ruleTicks = prdRule.getPRDActivation().getTicks();
        }
        if (ruleTicks == null)
            ruleTicks = 1;
        if (ruleProbability == null)
            ruleProbability = 1.0;

        Activation ruleActivation = new ActivationImpl(ruleProbability, ruleTicks);
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
        String mode = prdAction.getMode();
        String createdEntityName = prdAction.getCreate();
        String mainEntityName = prdAction.getKill();
        String sourceEntity = prdAction.getPRDBetween().getSourceEntity();
        String targetEntity = prdAction.getPRDBetween().getTargetEntity();
        String of = prdAction.getPRDEnvDepth().getOf();
        SecondaryEntity secondaryEntity = translateSecondaryEntity(prdAction,entitiesList);



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
            case "replace":
                EntityDefinition mainEntity = getEntityDefinition(mainEntityName,entitiesList);
                EntityDefinition createdEntity = getEntityDefinition(createdEntityName,entitiesList);
                return translateReplaceAction(mainEntity, createdEntity, mode);
            case "proximity":
                EntityDefinition sourceEntityDefinition = getEntityDefinition(sourceEntity, entitiesList);
                EntityDefinition targetEntityDefinition = getEntityDefinition(targetEntity,entitiesList);
                List<PRDAction> prdActions = prdAction.getPRDActions().getPRDAction();
                return translateProximityAction(sourceEntityDefinition,targetEntityDefinition,prdActions, of, activeEnvironment, entitiesList);
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
            String expType = expression.getType();
            String lowerType = type.name().toLowerCase();
            if (!(lowerType.equals(expType) || (lowerType.equals("float") && expType.equals("decimal")))) {
                throw new MismatchTypesException("Expression in increase action", lowerType, expType);
            }
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
            String expType = expression.getType();
            String lowerType = type.name().toLowerCase();
            if (!(lowerType.equals(expType) || (lowerType.equals("float") && expType.equals("decimal")))) {
                throw new MismatchTypesException("Expression in increase action", lowerType, expType);
            }
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
            if (prdMultiply == null) {
                Expression expressionArg1 = ExpressionDecoder.decode(prdDivide.getArg1(),activeEnvironment,entityDefinition,type,"calculation");
                Expression expressionArg2 = ExpressionDecoder.decode(prdDivide.getArg2(),activeEnvironment,entityDefinition,type,"calculation");
                String exp1Type = expressionArg1.getType(), exp2Type = expressionArg2.getType();
                if ((exp1Type.equals("decimal") || exp1Type.equals("float")) && (exp2Type.equals("decimal") || exp2Type.equals("float")))
                    if ((float) expressionArg2.evaluate() != 0) {
                        return new DivisionAction(entityDefinition, propertyDefinition, expressionArg1, expressionArg2);
                    } else throw new Exception("Division by zero! Invalid xml file");
                else
                    throw new MismatchTypesException("Expression in division action", "Decimal or Float", exp1Type + ", " + exp2Type);
            }
            else {
                Expression expressionArg1 = ExpressionDecoder.decode(prdMultiply.getArg1(),activeEnvironment,entityDefinition,type,"calculation");
                Expression expressionArg2 = ExpressionDecoder.decode(prdMultiply.getArg2(),activeEnvironment,entityDefinition,type,"calculation");
                String exp1Type = expressionArg1.getType(), exp2Type = expressionArg2.getType();
                if ((exp1Type.equals("decimal") || exp1Type.equals("float")) && (exp2Type.equals("decimal") || exp2Type.equals("float")))
                    return new MultiplicationAction(entityDefinition,propertyDefinition,expressionArg1,expressionArg2);
                else
                    throw new MismatchTypesException("Expression in multiplication action", "Decimal or Float", exp1Type + ", " + exp2Type);
            }
        }
        throw new PropertyNotMatchActionException(prdAction.getType(),propertyDefinition.getName(),entityDefinition.getName(),type.toString());
    }

    public static ConditionAction translateConditionAction(PRDCondition prdCondition, PRDThen prdThen, PRDElse prdElse, EntityDefinition entityDefinition, ActiveEnvironment activeEnvironment, List<EntityDefinition> entityDefinitions) throws  Exception {
        String singularity = prdCondition.getSingularity();
        boolean boolSingularity = !singularity.equals("multiple");
        String logic = prdCondition.getLogical();

        List<Action> thenActions = new ArrayList<>();
        List<Action> elseActions = new ArrayList<>();
        List<ConditionAction> conditions = new ArrayList<>();

        for (PRDAction prdAction1 : prdThen.getPRDAction()) {
            thenActions.add(translateAction(prdAction1, entityDefinitions, activeEnvironment));
        }
        if (prdElse != null) {
            for (PRDAction prdAction2 : prdElse.getPRDAction()) {
                elseActions.add(translateAction(prdAction2, entityDefinitions, activeEnvironment));
            }
        }
        if (prdCondition.getPRDCondition() != null) {
            for (PRDCondition prdCondition1 : prdCondition.getPRDCondition()) {
                conditions.add(translateConditionActionSecondary(prdCondition1, entityDefinition, activeEnvironment));
            }
        }

        if (boolSingularity) {
            String operator = prdCondition.getOperator();
            String propertyName = prdCondition.getProperty();
            PropertyDefinition propertyDefinition = entityDefinition.getPropertyByName(propertyName);
            String valueStr = prdCondition.getValue();
            Expression expression = ExpressionDecoder.decode(valueStr, activeEnvironment, entityDefinition, propertyDefinition.getType(), "condition");
            String propType = propertyDefinition.getType().name().toLowerCase();
            String expType = expression.getType();
            if (expType.equals(propType) || (expType.equals("decimal")) && propType.equals("float"))
                return new SingularCondition(entityDefinition, propertyDefinition, expression, operator, thenActions, elseActions);
            else
                throw new MismatchTypesException("Expression in condition action", propType, expType);
        }
        else
            return new MultipleCondition(conditions, logic, entityDefinition, null, thenActions, elseActions);
    }

    public static ConditionAction translateConditionActionSecondary(PRDCondition prdCondition, EntityDefinition entityDefinition, ActiveEnvironment activeEnvironment) throws Exception {
        String singularity = prdCondition.getSingularity();
        boolean boolSingularity = !singularity.equals("multiple");

        String logic = prdCondition.getLogical();

        List<Action> thenActions = new ArrayList<>();
        List<Action> elseActions = new ArrayList<>();
        List<ConditionAction> conditions = new ArrayList<>();

        if (prdCondition.getPRDCondition() != null) {
            for (PRDCondition prdCondition1 : prdCondition.getPRDCondition()) {
                conditions.add(translateConditionActionSecondary(prdCondition1, entityDefinition, activeEnvironment));
            }
        }

        if (boolSingularity) {
            String operator = prdCondition.getOperator();
            String propertyName = prdCondition.getProperty();
            PropertyDefinition propertyDefinition = entityDefinition.getPropertyByName(propertyName);
            String valueStr = prdCondition.getValue();
            Expression expression = ExpressionDecoder.decode(valueStr, activeEnvironment, entityDefinition, propertyDefinition.getType(), "condition");
            String propType = propertyDefinition.getType().name().toLowerCase();
            String expType = expression.getType();
            if (expType.equals(propType) || (expType.equals("decimal")) && propType.equals("float"))
                return new SingularCondition(entityDefinition, propertyDefinition, expression, operator, thenActions, elseActions);
            else
                throw new MismatchTypesException("Expression in condition action", propType, expType);
        }
        else
            return new MultipleCondition(conditions, logic, entityDefinition, null, thenActions, elseActions);
    }


    public static SetAction translateSetAction(PRDAction prdAction, EntityDefinition entityDefinition, String propertyName, ActiveEnvironment activeEnvironment) throws Exception {
        PropertyDefinition propertyDefinition = entityDefinition.getPropertyByName(propertyName);
        AbstractPropertyDefinition.PropertyType type = propertyDefinition.getType();
        Expression expression = ExpressionDecoder.decode(prdAction.getValue(), activeEnvironment,entityDefinition,type,"set");
        String expType = expression.getType();
        String propertyType = type.name().toLowerCase();
        if ((propertyType.equals(expType)) || (propertyType.equals("float") && expType.equals("decimal")))
            return new SetAction(entityDefinition,expression,propertyDefinition);
        else
            throw new MismatchTypesException("Expression in set action", propertyType, expType);
    }

    public static KillAction translateKillAction(EntityDefinition entityDefinition) {
        return new KillAction(entityDefinition);
    }


    public static ReplaceAction translateReplaceAction(EntityDefinition mainEntityName, EntityDefinition createdEntityName,String mode) {
        return new ReplaceAction(mode, mainEntityName, createdEntityName);
    }

    public static ProximityAction translateProximityAction(EntityDefinition sourceEntity, EntityDefinition targetEntity, List<PRDAction> prdActions, String ofExpression, ActiveEnvironment activeEnvironment, List<EntityDefinition> entityDefinitions) throws  Exception {
        Expression of = ExpressionDecoder.decode(ofExpression,activeEnvironment, sourceEntity, AbstractPropertyDefinition.PropertyType.DECIMAL,"proximity");
        ProximityAction proximityAction = new ProximityAction(sourceEntity,targetEntity,of);
        for (PRDAction prdAction : prdActions) {
            proximityAction.addAction(translateAction(prdAction, entityDefinitions, activeEnvironment));
        }
        return proximityAction;
    }


    public static SecondaryEntity translateSecondaryEntity(PRDAction prdAction, List<EntityDefinition> entitiesList) throws Exception {

        if ( prdAction.getPRDSecondaryEntity() != null) {
            EntityDefinition secondaryEntity = getEntityDefinition(prdAction.getPRDSecondaryEntity().getEntity(),entitiesList);
            PRDAction.PRDSecondaryEntity.PRDSelection selection = prdAction.getPRDSecondaryEntity().getPRDSelection();
            String count = selection.getCount();
            PRDCondition condition = selection.getPRDCondition();
            condition.
            return new SecondaryEntity(secondaryEntity,count,);
        }
    }
}
