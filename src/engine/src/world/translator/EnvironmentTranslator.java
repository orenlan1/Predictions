package world.translator;

import generated.PRDEnvProperty;
import generated.PRDEvironment;
import world.environment.api.EnvironmentVariablesManager;
import world.environment.impl.EnvironmentVariablesManagerImpl;
import world.property.api.PropertyDefinition;
import world.property.impl.BooleanPropertyDefinition;
import world.property.impl.FloatPropertyDefinition;
import world.property.impl.IntegerPropertyDefinition;
import world.property.impl.StringPropertyDefinition;
import world.value.generator.api.ValueGeneratorFactory;

import java.util.LinkedList;
import java.util.List;

public class EnvironmentTranslator {
    public static EnvironmentVariablesManager translateEnvironment(PRDEvironment prdEvironment) throws Exception {
        EnvironmentVariablesManager environmentVariablesManager = new EnvironmentVariablesManagerImpl();
        List<PropertyDefinition> envProperties = EnvironmentTranslator.translateEnvironmentProperties(prdEvironment);

        return environmentVariablesManager;
    }

    public static PropertyDefinition TranslateEnvironmentPropertyDefinition(PRDEnvProperty prdEnvProperty) throws Exception {
        String name = prdEnvProperty.getPRDName();
        String propertyType = prdEnvProperty.getType();
        double from = prdEnvProperty.getPRDRange().getFrom(), to = prdEnvProperty.getPRDRange().getTo();
        PropertyDefinition propertyDefinition = null;

        switch (propertyType) {
            case "decimal":
                propertyDefinition = new IntegerPropertyDefinition(name, ValueGeneratorFactory.createRandomInteger((int) from, (int) to), (int) from, (int) to);
                break;
            case "float":
                propertyDefinition = new FloatPropertyDefinition(name, ValueGeneratorFactory.createRandomFloat((float) from, (float) to), (float) from, (float) to);
                break;
            case "boolean":
                propertyDefinition = new BooleanPropertyDefinition(name, ValueGeneratorFactory.createRandomBoolean());
                break;
            case "string":
                propertyDefinition = new StringPropertyDefinition(name, ValueGeneratorFactory.createRandomString());
                break;
            default:
                throw new Exception("failed translation");
        }
        return propertyDefinition;
    }

    public static List<PropertyDefinition> translateEnvironmentProperties(PRDEvironment prdEvironment) throws Exception {
        List<PropertyDefinition> propertyDefinitions = new LinkedList<>();
        for (PRDEnvProperty prdEnvProperty : prdEvironment.getPRDEnvProperty()) {
            propertyDefinitions.add(EnvironmentTranslator.TranslateEnvironmentPropertyDefinition(prdEnvProperty));
        }
        return propertyDefinitions;
    }
}
