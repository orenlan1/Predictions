package world;

import world.property.api.PropertyDefinition;
import world.property.impl.IntegerProperty;
import world.value.generator.api.ValueGeneratorFactory;

public class Main {
    public static void main(String[] args) {
        Integer a = ValueGeneratorFactory.createRandomInteger(10,20).generateValue();
        PropertyDefinition age = new IntegerProperty("age",ValueGeneratorFactory.createRandomInteger(20,50));

    }
}
