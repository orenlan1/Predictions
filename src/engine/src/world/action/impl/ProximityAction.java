package world.action.impl;

import world.action.api.Action;
import world.action.api.ActionType;
import world.entity.api.EntityDefinition;
import world.entity.api.EntityInstance;
import world.expressions.api.Expression;
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


    public ProximityAction(EntityDefinition sourceEntity, EntityDefinition targetEntity, Expression expression) {
        this.sourceEntity = sourceEntity;
        this.targetEntity = targetEntity;
        this.of = expression;
        actionList = new ArrayList<>();
    }

    @Override
    public void activate(EntityInstance sourceInstance) throws Exception {
        List<EntityInstance> targetEntities = targetEntity.getEntityInstances();
        int depthLevel = (int) of.evaluate();
        GridCoordinate sourceCoordinate = sourceInstance.getCoordinate();
        for ( EntityInstance targetInstance : targetEntities) {
            GridCoordinate targetCoordinate = targetInstance.getCoordinate();
            if (sourceCoordinate.isCoordinateInProximity(targetCoordinate,depthLevel)) {
                for ( Action action : actionList) {
                    action.activate(sourceInstance);
                }
            }
        }
    }

    public void addAction(Action action) {
        actionList.add(action);
    }

    @Override
    public ActionType getActionType() {
        return null;
    }

    @Override
    public EntityDefinition getEntityDefinition() {
        return null;
    }
}
