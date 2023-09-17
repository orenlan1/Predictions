package world.property.impl;

import world.property.api.AbstractPropertyDefinition;
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
        if (propertyDefinition.getType().equals(AbstractPropertyDefinition.PropertyType.DECIMAL)) {
            if (value instanceof Integer) {
                IntegerPropertyDefinition intPropertyDef = (IntegerPropertyDefinition) propertyDefinition;
                int to = intPropertyDef.getTo();
                int from = intPropertyDef.getFrom();
                if ((Integer) value > to || (Integer) value < from) {
                    return;
                }
            }
        }
        else if (propertyDefinition.getType().equals(AbstractPropertyDefinition.PropertyType.FLOAT)) {
            if (value instanceof Float) {
                FloatPropertyDefinition floatPropertyDef = (FloatPropertyDefinition) propertyDefinition;
                float to = floatPropertyDef.getTo();
                float from = floatPropertyDef.getFrom();
                if ((float) value > to || (float) value < from) {
                    return;
                }
            }
        }
        if (value instanceof String) {
            if (!((this.value).equals(value))) {
                this.value = value;
                lastUpdateTick = currTick;
            }
        } else if (this.value != value) {
            this.value = value;
            lastUpdateTick = currTick;
        }
    }

    public int getLastUpdateTick() {
        return lastUpdateTick;
    }
}
