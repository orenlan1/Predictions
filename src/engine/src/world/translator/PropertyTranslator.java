package world.translator;

import generated.PRDEnvProperty;
import generated.PRDEvironment;
import generated.PRDProperties;
import generated.PRDProperty;
import world.exceptions.InvalidVariableTypeException;
import world.property.api.PropertyDefinition;
import world.property.impl.BooleanPropertyDefinition;
import world.property.impl.FloatPropertyDefinition;
import world.property.impl.IntegerPropertyDefinition;
import world.property.impl.StringPropertyDefinition;
import world.value.generator.api.ValueGeneratorFactory;

import java.util.LinkedList;
import java.util.List;

public class PropertyTranslator {
    public static PropertyDefinition TranslatePropertyDefinition(PRDProperty prdProperty) throws InvalidVariableTypeException {
        String name = prdProperty.getPRDName();
        String propertyType = prdProperty.getType();
        boolean randomInit = prdProperty.getPRDValue().isRandomInitialize();
        double from = -Double.MAX_VALUE;
        double to = Double.MAX_VALUE;
        if ( prdProperty.getPRDRange() != null) {
            from = prdProperty.getPRDRange().getFrom();
            to = prdProperty.getPRDRange().getTo();
        }
        String initValue = prdProperty.getPRDValue().getInit();
        PropertyDefinition propertyDefinition = null;

        switch (propertyType) {
            case "decimal":
                if (randomInit) {
                    propertyDefinition = new IntegerPropertyDefinition(name, ValueGeneratorFactory.createRandomInteger((int) from, (int) to), (int) from, (int) to);
                } else {
                    propertyDefinition = new IntegerPropertyDefinition(name, ValueGeneratorFactory.createFixed(Integer.parseInt(initValue)), (int) from, (int) to);
                }
                break;
            case "float":
                if (randomInit) {
                    propertyDefinition = new FloatPropertyDefinition(name, ValueGeneratorFactory.createRandomFloat((float) from, (float) to), (float) from, (float) to);
                } else {
                    propertyDefinition = new FloatPropertyDefinition(name, ValueGeneratorFactory.createFixed(Float.parseFloat(initValue)), (float) from, (float) to);
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
                throw new InvalidVariableTypeException("translating properties", "decimal, float, boolean or string", propertyType);
        }
        return propertyDefinition;
    }


    public static List<PropertyDefinition> translateProperties(PRDProperties prdPropertiesList) throws InvalidVariableTypeException {
        List<PropertyDefinition> propertyDefinitions = new LinkedList<>();
        for (PRDProperty prdProperty : prdPropertiesList.getPRDProperty()) {
            propertyDefinitions.add(PropertyTranslator.TranslatePropertyDefinition(prdProperty));
        }
        return propertyDefinitions;
    }
}
