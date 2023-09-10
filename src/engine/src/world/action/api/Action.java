package world.action.api;

import world.action.impl.ActionImpl;
import world.action.impl.SecondaryEntity;
import world.entity.api.EntityDefinition;
import world.entity.api.EntityInstance;

import java.util.List;

public interface Action {
    void activate(EntityInstance entityInstance, int currTick) throws Exception;
    ActionType getActionType();
    EntityDefinition getEntityDefinition();
    EntityDefinition getSecondaryEntityDefinition();
    List<String> getArguments();
}
