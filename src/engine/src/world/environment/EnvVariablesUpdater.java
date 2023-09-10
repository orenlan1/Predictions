package world.environment;

import dto.UserInputEnvironmentVariableDTO;
import world.property.api.AbstractPropertyDefinition;
import world.property.api.PropertyInstance;
import world.property.impl.FloatPropertyDefinition;
import world.property.impl.IntegerPropertyDefinition;

public class EnvVariablesUpdater {
    public void updateVariable(PropertyInstance propertyInstance, AbstractPropertyDefinition.PropertyType type, UserInputEnvironmentVariableDTO dto) throws NumberFormatException, Exception {
        if (dto.getValue() != null) {
            if (type.equals(AbstractPropertyDefinition.PropertyType.DECIMAL)) {
                if (dto.getValue().contains(".")) {
                    double doubleValue = Double.parseDouble(dto.getValue());
                    if (doubleValue % 1 == 0) {
                        int intValue = (int) doubleValue;
                        IntegerPropertyDefinition integerPropertyDefinition = (IntegerPropertyDefinition) propertyInstance.getPropertyDefinition();
                        if (intValue >= integerPropertyDefinition.getFrom() && intValue <= integerPropertyDefinition.getTo()) {
                            propertyInstance.updateValue(intValue);
                        } else {
                            throw new Exception("The value is out of range");
                        }
                    } else {
                        throw new Exception("The value is not a valid integer.");
                    }
                } else {
                    Integer intValue = Integer.parseInt(dto.getValue());
                    IntegerPropertyDefinition integerPropertyDefinition = (IntegerPropertyDefinition) propertyInstance.getPropertyDefinition();
                    if (intValue >= integerPropertyDefinition.getFrom() && intValue <= integerPropertyDefinition.getTo())
                        propertyInstance.updateValue(intValue);
                    else throw new Exception("The value is out of range");
                }
            } else if (type.equals(AbstractPropertyDefinition.PropertyType.FLOAT)) {
                Float floatValue = Float.parseFloat(dto.getValue());
                FloatPropertyDefinition floatPropertyDefinition = (FloatPropertyDefinition) propertyInstance.getPropertyDefinition();
                if (floatValue >= floatPropertyDefinition.getFrom() && floatValue <= floatPropertyDefinition.getTo())
                    propertyInstance.updateValue(floatValue);
                else throw new Exception("The value is out of range");
            } else if (type.equals(AbstractPropertyDefinition.PropertyType.BOOLEAN)) {
                if (dto.getValue().equals("False"))
                    propertyInstance.updateValue(false);
                else if (dto.getValue().equals("True"))
                    propertyInstance.updateValue(true);
            } else if (type.equals(AbstractPropertyDefinition.PropertyType.STRING))
                propertyInstance.updateValue((dto.getValue()));
        }
    }
}
