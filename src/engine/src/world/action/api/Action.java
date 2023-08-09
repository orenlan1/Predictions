package world.action.api;

import world.action.impl.ActionImpl;
import world.entity.api.EntityDefinition;
import world.entity.api.EntityInstance;

public interface Action {
    void activate(EntityInstance entityInstance) throws Exception;
    ActionImpl.ActionType getActionType();
    EntityDefinition getEntityDefinition();
}
