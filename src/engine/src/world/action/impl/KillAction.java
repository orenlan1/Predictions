package world.action.impl;

import world.action.api.Action;
import world.action.api.ActionType;
import world.context.Context;
import world.entity.api.EntityDefinition;
import world.entity.api.EntityInstance;
import world.property.api.PropertyDefinition;

import java.util.ArrayList;
import java.util.List;

public class KillAction implements Action {
    ActionType actionType = ActionType.KILL;
    private final EntityDefinition entityDefinition;
    private final SecondaryEntity secondaryEntity;
    private final Context entitiesContext;
    public KillAction(EntityDefinition entityDefinition, SecondaryEntity secondaryEntity, Context entitiesContext) {
        this.entityDefinition = entityDefinition;
        this.secondaryEntity = secondaryEntity;
        this.entitiesContext = entitiesContext;
    }

    @Override
    public ActionType getActionType() {
        return actionType;
    }

    @Override
    public EntityDefinition getEntityDefinition() {
        return entityDefinition;
    }


    @Override
    public void activate(EntityInstance entityInstance, int currTick) throws Exception {
        entityInstance.kill();
    }

    @Override
    public EntityDefinition getSecondaryEntityDefinition() {
        return null;
    }

    @Override
    public List<String> getArguments() {
        return null;
    }
}
