package predictions.impl;

import dto.*;
import predictions.api.PredictionsService;
import world.EngineFileReader;
import world.SimulationInfoBuilder;
import world.World;
import world.environment.api.ActiveEnvironment;
import world.factory.DTOFactory;
import world.property.api.AbstractPropertyDefinition;
import world.property.api.PropertyDefinition;
import world.property.api.PropertyInstance;
import world.property.impl.FloatPropertyDefinition;
import world.property.impl.IntegerPropertyDefinition;

import java.util.ArrayList;
import java.util.List;


public class PredictionsServiceImpl implements PredictionsService {
    private  World world;


    @Override
    public FileReaderDTO readFileAndLoad(String fileName) {
       EngineFileReader fileReader = new EngineFileReader();
       try {
           this.world = fileReader.checkFileValidation(fileName);
       } catch (Exception e) {
           return new FileReaderDTO(Boolean.FALSE, e.getMessage());
       }
        return new FileReaderDTO(Boolean.TRUE, null);
    }

    @Override
    public SimulationInfoDTO getSimulationInformation() {
        SimulationInfoBuilder simulationInfoBuilder = new SimulationInfoBuilder();
        return simulationInfoBuilder.createSimulationInfo(world);
    }

    @Override
    public PropertiesDTO getEnvPropertiesDTO() {
        DTOFactory dtoFactory = new DTOFactory();
        List<PropertyDTO> DTOs = new ArrayList<>();
        for (PropertyDefinition envProperty : world.getEnvironmentVariablesManager().getEnvironmentVariables()) {
            DTOs.add(dtoFactory.createPropertyDTO(envProperty));
        }
        return new PropertiesDTO(DTOs);
    }

    @Override
    public EnvVariableSetValidationDTO setEnvironmentVariable(UserInputEnvironmentVariableDTO dto) {
        ActiveEnvironment activeEnvironment = world.getActiveEnvironment();
        PropertyInstance propertyInstance = activeEnvironment.getProperty(dto.getPropertyDTO().getPropertyName()).get();
        AbstractPropertyDefinition.PropertyType type = propertyInstance.getPropertyDefinition().getType();

        try {
            if (type.equals(AbstractPropertyDefinition.PropertyType.DECIMAL)) {
                Integer intValue = Integer.parseInt(dto.getValue());
                IntegerPropertyDefinition integerPropertyDefinition = (IntegerPropertyDefinition) propertyInstance.getPropertyDefinition();
                if (intValue >= integerPropertyDefinition.getFrom() && intValue <= integerPropertyDefinition.getTo())
                    propertyInstance.updateValue(intValue);
                else throw new Exception("The value is out of range");
            }
            else if (type.equals(AbstractPropertyDefinition.PropertyType.FLOAT)) {
                Float floatValue = Float.parseFloat(dto.getValue());
                FloatPropertyDefinition floatPropertyDefinition = (FloatPropertyDefinition) propertyInstance.getPropertyDefinition();
                if (floatValue >= floatPropertyDefinition.getFrom() && floatValue <= floatPropertyDefinition.getTo())
                    propertyInstance.updateValue(floatValue);
                else throw new Exception("The value is out of range");
            }
            else if (type.equals(AbstractPropertyDefinition.PropertyType.BOOLEAN)) {
                if (dto.getValue().equals("0"))
                    propertyInstance.updateValue(false);
                else if (dto.getValue().equals("1"))
                    propertyInstance.updateValue(true);
                else
                    throw new Exception("Should receive \"true\" (as 1) or \"false\" (as 0) ");
            }
            else if (type.equals(AbstractPropertyDefinition.PropertyType.STRING))
                propertyInstance.updateValue((dto.getValue()));
        } catch (Exception e) {
            return new EnvVariableSetValidationDTO(Boolean.FALSE, "Failed to assign the value to the environment variable. " + e.getMessage());
        }
        return new EnvVariableSetValidationDTO(Boolean.TRUE, null);
    }

    @Override
    public List<EnvVariablesDTO> getEnvVariablesDTOList() {
        ActiveEnvironment activeEnvironment = world.getActiveEnvironment();
        List<EnvVariablesDTO> lst = new ArrayList<>();
        for (PropertyInstance property : activeEnvironment.getEnvironmentVariables()) {
            lst.add(new EnvVariablesDTO(property.getPropertyDefinition().getName(), property.getValue().toString()));
        }
        return lst;
    }
}
