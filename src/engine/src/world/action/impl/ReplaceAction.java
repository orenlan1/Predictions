package world.action.impl;

import world.action.api.Action;
import world.action.api.ActionType;
import world.entity.api.EntityDefinition;
import world.entity.api.EntityInstance;


public class ReplaceAction implements Action {
    private ActionType actionType = ActionType.REPLACE;
    private final String mode;
    private EntityDefinition removedEntityDefinition;
    private EntityDefinition createdEntityDefinition;

    public ReplaceAction(String mode, EntityDefinition removedEntity, EntityDefinition createdEntity) {
        this.mode = mode;
        this.removedEntityDefinition = removedEntity;
        this.createdEntityDefinition = createdEntity;
    }

    @Override
    public void activate(EntityInstance entityInstance) throws Exception {
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



}
