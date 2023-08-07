package world.property.impl;

import world.property.api.AbstractPropertyDefinition;
import world.value.generator.api.ValueGenerator;

public class IntegerProperty extends AbstractPropertyDefinition<Integer> {

    Object value;

    public IntegerProperty(String name, ValueGenerator<Integer> valueGenerator) {
        super(name,PropertyType.DECIMAL, valueGenerator);
        this.value = valueGenerator.generateValue();
    }


}
