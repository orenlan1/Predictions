package world.action.impl;

import world.entity.api.EntityDefinition;
import world.entity.api.EntityInstance;

public class IncreaseAction extends ActionImpl {


    public IncreaseAction(EntityDefinition entityDefinition) {
        super(ActionType.INCREASE, entityDefinition);
    }


    @Override
    public void activate(EntityInstance entityInstance) {

    }

}
