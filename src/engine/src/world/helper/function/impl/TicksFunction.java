package world.helper.function.impl;

import world.context.Context;
import world.entity.api.EntityInstance;
import world.exceptions.SimulationFunctionsException;
import world.property.api.PropertyDefinition;
import world.property.api.PropertyInstance;

public class TicksFunction extends HelperFunctionImpl{

    private final String propertyName;
    private final String entityName;
    private final Context functionContext;

    public TicksFunction(String entityName, String propertyName, Context context) {
        super("ticks", 1, "String");
        this.entityName = entityName;
        this.propertyName = propertyName;
        this.functionContext = context;
    }

    @Override
    public Object invoke(EntityInstance entityInstance) throws Exception {
        return null;
    }

    public Object invoke(EntityInstance entityInstance, int currTick) throws Exception{
        if ( functionContext.getPrimaryEntity().getName().equals(entityName)) {
            PropertyDefinition propertyDefinition = functionContext.getPrimaryEntity().getPropertyByName(propertyName);
            PropertyInstance propertyInstance = entityInstance.getPropertyByName(propertyName);
            return currTick - propertyInstance.getLastUpdateTick();

        }
        if ( functionContext.getSecondaryEntity() != null) {
            if ( functionContext.getSecondaryEntity().getName().equals(entityName)) {
                PropertyDefinition propertyDefinition = functionContext.getSecondaryEntity().getPropertyByName(propertyName);
                PropertyInstance propertyInstance = entityInstance.getPropertyByName(propertyName);
                return currTick - propertyInstance.getLastUpdateTick();
            }
        }
        throw new SimulationFunctionsException(entityName, "evaluate");
    }
}
