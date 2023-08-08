package world.action.api;

import world.entity.api.EntityDefinition;
import world.entity.api.EntityInstance;

public interface Action {
    void activate(EntityInstance entityInstance);
    ActionType getActionType();
    EntityDefinition getContextEntity();
}
