package world.environment;

import dto.UserInputEnvironmentVariableDTO;
import world.property.api.AbstractPropertyDefinition;
import world.property.api.PropertyInstance;
import world.property.impl.FloatPropertyDefinition;
import world.property.impl.IntegerPropertyDefinition;

public class EnvVariablesUpdater {
    public void updateVariable(PropertyInstance propertyInstance, AbstractPropertyDefinition.PropertyType type, UserInputEnvironmentVariableDTO dto, int currTick) throws NumberFormatException, Exception {
        if (type.equals(AbstractPropertyDefinition.PropertyType.DECIMAL)) {
            Integer intValue = Integer.parseInt(dto.getValue());
            IntegerPropertyDefinition integerPropertyDefinition = (IntegerPropertyDefinition) propertyInstance.getPropertyDefinition();
            if (intValue >= integerPropertyDefinition.getFrom() && intValue <= integerPropertyDefinition.getTo())
                propertyInstance.updateValue(intValue, currTick);
            else throw new Exception("The value is out of range");
        }
        else if (type.equals(AbstractPropertyDefinition.PropertyType.FLOAT)) {
            Float floatValue = Float.parseFloat(dto.getValue());
            FloatPropertyDefinition floatPropertyDefinition = (FloatPropertyDefinition) propertyInstance.getPropertyDefinition();
            if (floatValue >= floatPropertyDefinition.getFrom() && floatValue <= floatPropertyDefinition.getTo())
                propertyInstance.updateValue(floatValue, currTick);
            else throw new Exception("The value is out of range");
        }
        else if (type.equals(AbstractPropertyDefinition.PropertyType.BOOLEAN)) {
            if (dto.getValue().equals("0"))
                propertyInstance.updateValue(false, currTick);
            else if (dto.getValue().equals("1"))
                propertyInstance.updateValue(true, currTick);
            else
                throw new Exception("Should receive the number 1 for \"true\" or the number 0 for \"false\"");
        }
        else if (type.equals(AbstractPropertyDefinition.PropertyType.STRING))
            propertyInstance.updateValue((dto.getValue()), currTick);
    }
}
