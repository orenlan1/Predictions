package world.property.impl;

import world.property.api.AbstractPropertyDefinition;
import world.value.generator.api.ValueGenerator;

public class FloatPropertyDefinition extends AbstractPropertyDefinition<Float> {
    public FloatPropertyDefinition(String name, ValueGenerator<Float> valueGenerator) {
        super(name, PropertyType.FLOAT, valueGenerator);
    }
}
