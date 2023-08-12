package world.translator;

import generated.PRDProperty;
import world.property.api.PropertyDefinition;
import world.property.impl.BooleanPropertyDefinition;
import world.property.impl.FloatPropertyDefinition;
import world.property.impl.IntegerPropertyDefinition;
import world.property.impl.StringPropertyDefinition;
import world.value.generator.api.ValueGeneratorFactory;

public class PropertyTranslator {
    public static PropertyDefinition TranslatePropertyDefinition(PRDProperty prdProperty) throws Exception {
        String name = prdProperty.getPRDName();
        String propertyType = prdProperty.getType();
        boolean randomInit = prdProperty.getPRDValue().isRandomInitialize();
        double from = prdProperty.getPRDRange().getFrom(), to = prdProperty.getPRDRange().getTo();
        String initValue = prdProperty.getPRDValue().getInit();
        PropertyDefinition propertyDefinition = null;

        switch (propertyType) {
            case "decimal":
                if (randomInit) {
                    propertyDefinition = new IntegerPropertyDefinition(name, ValueGeneratorFactory.createRandomInteger((int) from, (int) to));
                } else {
                    propertyDefinition = new IntegerPropertyDefinition(name, ValueGeneratorFactory.createFixed(Integer.parseInt(initValue)));
                }
                break;
            case "float":
                if (randomInit) {
                    propertyDefinition = new FloatPropertyDefinition(name, ValueGeneratorFactory.createRandomFloat((float) from, (float) to));
                } else {
                    propertyDefinition = new FloatPropertyDefinition(name, ValueGeneratorFactory.createFixed(Float.parseFloat(initValue)));
                }
                break;
            case "boolean":
                if (randomInit) {
                    propertyDefinition = new BooleanPropertyDefinition(name, ValueGeneratorFactory.createRandomBoolean());
                } else {
                    propertyDefinition = new BooleanPropertyDefinition(name, ValueGeneratorFactory.createFixed(Boolean.parseBoolean(initValue)));
                }
                break;
            case "string":
                if (randomInit) {
                    propertyDefinition = new StringPropertyDefinition(name, ValueGeneratorFactory.createRandomString());
                } else {
                    propertyDefinition = new StringPropertyDefinition(name, ValueGeneratorFactory.createFixed(initValue));
                }
                break;
            default:
                throw new Exception("failed translation");
        }
        return propertyDefinition;
    }
}
