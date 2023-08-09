package world.property.api;

import world.property.api.PropertyDefinition;
import world.value.generator.api.ValueGenerator;

public abstract class AbstractPropertyDefinition<T> implements PropertyDefinition {
    public enum PropertyType {
        DECIMAL, FLOAT, BOOLEAN, STRING
    }
    private final String name;
    private final PropertyType type;
    private final ValueGenerator<T> valueGenerator;

    public AbstractPropertyDefinition(String name, PropertyType propertyType, ValueGenerator<T> valueGenerator) {
        this.name = name;
        this.type = propertyType;
        this.valueGenerator = valueGenerator;
    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public PropertyType getType() {
        return type;
    }

    @Override
    public T generateValue() {
        return valueGenerator.generateValue();
    }



}
