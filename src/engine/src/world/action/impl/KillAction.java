package world.action.impl;

import world.action.api.Action;
import world.action.api.ActionType;
import world.entity.api.EntityDefinition;
import world.entity.api.EntityInstance;
import world.property.api.PropertyDefinition;

public class KillAction implements Action {
    ActionType actionType = ActionType.KILL;
    private final EntityDefinition entityDefinition;
    public KillAction(EntityDefinition entityDefinition) {
        this.entityDefinition = entityDefinition;

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
    public void activate(EntityInstance entityInstance) throws Exception {
        entityDefinition.removeEntity(entityInstance);
    }

}
