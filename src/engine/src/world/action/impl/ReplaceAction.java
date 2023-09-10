package world.action.impl;

import world.action.api.Action;
import world.action.api.ActionType;
import world.context.Context;
import world.context.ContextImpl;
import world.entity.api.EntityDefinition;
import world.entity.api.EntityInstance;

import java.util.ArrayList;
import java.util.List;


public class ReplaceAction implements Action {
    private ActionType actionType = ActionType.REPLACE;
    private final String mode;
    private EntityDefinition removedEntityDefinition;
    private EntityDefinition createdEntityDefinition;
    private final Context entitiesContext;

    public ReplaceAction(String mode, EntityDefinition removedEntity, EntityDefinition createdEntity, Context entitiesContext) {
        this.mode = mode;
        this.removedEntityDefinition = removedEntity;
        this.createdEntityDefinition = createdEntity;
        this.entitiesContext = entitiesContext;
    }

    @Override
    public void activate(EntityInstance entityInstance, int currTick) throws Exception {
        if ( mode.equals("scratch")) {
            entityInstance.kill();
            EntityInstance newEntity = EntityInstance.createEntityInstance(createdEntityDefinition);
            createdEntityDefinition.addEntityInstance(newEntity);
        }
        else if ( mode.equals("derived")) {
            EntityInstance newEntity = entityInstance.createDerivedEntityInstance(createdEntityDefinition);
            createdEntityDefinition.addEntityInstance(newEntity);
            entityInstance.kill();
        }
    }

    @Override
    public ActionType getActionType() {
        return actionType;
    }

    @Override
    public EntityDefinition getEntityDefinition() {
        return removedEntityDefinition;
    }

    @Override
    public EntityDefinition getSecondaryEntityDefinition() {
        return createdEntityDefinition;
    }

    @Override
    public List<String> getArguments() {
        List<String> args = new ArrayList<>();
        args.add(mode);
        return args;
    }

}
