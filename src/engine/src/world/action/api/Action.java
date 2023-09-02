package world.action.api;

import world.action.impl.ActionImpl;
import world.entity.api.EntityDefinition;
import world.entity.api.EntityInstance;

import java.util.List;

public interface Action {
    void activate(EntityInstance entityInstance) throws Exception;
    ActionType getActionType();
    EntityDefinition getEntityDefinition();
    List<String> getArguments();
}
