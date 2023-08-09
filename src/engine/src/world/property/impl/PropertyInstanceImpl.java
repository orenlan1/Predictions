package world.property.impl;

import world.property.api.PropertyDefinition;
import world.property.api.PropertyInstance;

public class PropertyInstanceImpl implements PropertyInstance {
    private final PropertyDefinition propertyDefinition;
    private Object value;

    public PropertyInstanceImpl(PropertyDefinition propertyDefinition) { // Ctor for entity property
        this.propertyDefinition = propertyDefinition;
        this.value = getPropertyDefinition().generateValue();
    }

    public PropertyInstanceImpl(PropertyDefinition propertyDefinition, Object value) { // Ctor for environment property
        this.propertyDefinition = propertyDefinition;
        this.value = value;
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
    public void updateValue(Object value) {
        this.value = value;
    }
}
