package world.property.impl;

import world.property.api.AbstractPropertyDefinition;
import world.value.generator.api.ValueGenerator;

public class FloatPropertyDefinition extends AbstractPropertyDefinition<Float> {
    private final Float from;
    private final Float to;
    public FloatPropertyDefinition(String name, ValueGenerator<Float> valueGenerator, Float from, Float to) {
        super(name, PropertyType.FLOAT, valueGenerator);
        this.to = to;
        this.from = from;
    }

    public Float getTo() {
        return to;
    }

    public Float getFrom() {
        return from;
    }
}
