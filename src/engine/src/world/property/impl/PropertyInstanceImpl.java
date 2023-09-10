package world.property.impl;

import world.property.api.PropertyDefinition;
import world.property.api.PropertyInstance;

public class PropertyInstanceImpl implements PropertyInstance {
    private final PropertyDefinition propertyDefinition;
    protected Object value;
    protected int lastUpdateTick;

    public PropertyInstanceImpl(PropertyDefinition propertyDefinition) { // Ctor for entity property
        this.propertyDefinition = propertyDefinition;
        this.value = propertyDefinition.generateValue();
        lastUpdateTick = 0;
    }

    public PropertyInstanceImpl(PropertyDefinition propertyDefinition, Object value) { // Ctor for environment property
        this.propertyDefinition = propertyDefinition;
        this.value = value;
        lastUpdateTick = 0;
    }

    @Override
    public PropertyDefinition getPropertyDefinition() {
        return propertyDefinition;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public void updateValue(Object value, int currTick)
    {
        this.value = value;
        lastUpdateTick = currTick;
    }

    public int getLastUpdateTick() {
        return lastUpdateTick;
    }
}
