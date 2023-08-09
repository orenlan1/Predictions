package world.property.impl;

import world.property.api.AbstractPropertyDefinition;
import world.value.generator.api.ValueGenerator;

public class IntegerPropertyDefinition extends AbstractPropertyDefinition<Integer> {
    public IntegerPropertyDefinition(String name, ValueGenerator<Integer> valueGenerator) {
        super(name, PropertyType.DECIMAL, valueGenerator);
    }
}
