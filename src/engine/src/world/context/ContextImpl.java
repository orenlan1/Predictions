package world.context;

import world.action.impl.SecondaryEntity;
import world.entity.api.EntityDefinition;

public class ContextImpl implements Context {

    EntityDefinition primaryEntityDefinition;
    EntityDefinition secondaryEntityDefinition; // can be null

    public ContextImpl(EntityDefinition primeEntityDefinition, EntityDefinition secondaryEntityDefinition) {
        this.primaryEntityDefinition = primeEntityDefinition;
        this.secondaryEntityDefinition = secondaryEntityDefinition;
    }

    @Override
    public EntityDefinition getPrimaryEntity() {
        return primaryEntityDefinition;
    }

    @Override
    public EntityDefinition getSecondaryEntity() {
        return secondaryEntityDefinition;
    }


}
