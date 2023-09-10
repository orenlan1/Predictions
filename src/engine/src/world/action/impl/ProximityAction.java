package world.action.impl;

import world.action.api.Action;
import world.action.api.ActionType;
import world.context.Context;
import world.context.ContextImpl;
import world.entity.api.EntityDefinition;
import world.entity.api.EntityInstance;
import world.expressions.api.Expression;
import world.expressions.impl.HelperFunctionExpression;
import world.grid.Grid;
import world.grid.GridCoordinate;

import java.util.ArrayList;
import java.util.List;

public class ProximityAction implements Action {

    private ActionType actionType = ActionType.PROXIMITY;
    private Expression of;
    private final EntityDefinition sourceEntity;
    private final EntityDefinition targetEntity;
    private final List<Action> actionList;
    private final Context entitiesContext;

    public ProximityAction(EntityDefinition sourceEntity, EntityDefinition targetEntity, Expression expression, Context entitiesContext) {
        this.sourceEntity = sourceEntity;
        this.targetEntity = targetEntity;
        this.of = expression;
        actionList = new ArrayList<>();
        this.entitiesContext = entitiesContext;
    }

    @Override
    public void activate(EntityInstance sourceInstance, int currTick) throws Exception {
        List<EntityInstance> targetEntities = targetEntity.getEntityInstances();

        int depthLevel;
        if (of instanceof HelperFunctionExpression) {
            HelperFunctionExpression helperOf = (HelperFunctionExpression) of;
            depthLevel = (int) helperOf.evaluate(sourceInstance, currTick);
        } else
            depthLevel = (int) of.evaluate(sourceInstance);

        GridCoordinate sourceCoordinate = sourceInstance.getCoordinate();
        for ( EntityInstance targetInstance : targetEntities) {
            GridCoordinate targetCoordinate = targetInstance.getCoordinate();
            if (sourceCoordinate.isCoordinateInProximity(targetCoordinate,depthLevel)) {
                for ( Action action : actionList) {
                    action.activate(sourceInstance, currTick);
                }
            }
        }
    }

    public void addAction(Action action) {
        actionList.add(action);
    }

    @Override
    public ActionType getActionType() {
        return actionType;
    }

    @Override
    public EntityDefinition getEntityDefinition() {
        return sourceEntity;
    }

    @Override
    public EntityDefinition getSecondaryEntityDefinition() {
        return targetEntity;
    }

    @Override
    public List<String> getArguments() {
        List<String> args = new ArrayList<>();
        args.add(of.toString());
        args.add(Integer.toString(actionList.size()));
        return args;
    }
}
