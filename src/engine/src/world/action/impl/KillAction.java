package world.action.impl;

import world.action.api.ActionType;
import world.entity.api.EntityDefinition;
import world.entity.api.EntityInstance;
import world.property.api.PropertyDefinition;

public class KillAction extends ActionImpl{
    public KillAction(EntityDefinition entityDefinition, PropertyDefinition propertyDefinition) {
        super(ActionType.KILL, entityDefinition, propertyDefinition);
    }

    @Override
    public void activate(EntityInstance entityInstance) throws Exception {
        entityDefinition.removeEntity(entityInstance);
    }
}
