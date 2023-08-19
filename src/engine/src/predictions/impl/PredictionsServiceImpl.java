package predictions.impl;

import dto.*;
import predictions.api.PredictionsService;
import world.*;
import world.entity.api.EntityDefinition;
import world.entity.api.EntityInstance;
import world.environment.EnvVariablesUpdater;
import world.environment.api.ActiveEnvironment;
import world.factory.DTOFactory;
import world.file.reader.EngineFileReader;
import world.property.api.AbstractPropertyDefinition;
import world.property.api.PropertyDefinition;
import world.property.api.PropertyInstance;
import world.simulation.PastSimulation;
import world.simulation.SimulationExecutor;
import world.simulation.SimulationInfoBuilder;

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
            EnvVariablesUpdater envVariablesUpdater = new EnvVariablesUpdater();
            envVariablesUpdater.updateVariable(propertyInstance, type, dto);
        } catch (NumberFormatException e) {
            return new EnvVariableSetValidationDTO(Boolean.FALSE, "Failed to assign the value to the environment variable due to incompatible types");
        }
        catch (Exception e) {
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

    @Override
    public SimulationRunnerDTO runSimulation() {
        SimulationExecutor simulationExecutor = new SimulationExecutor();
        return simulationExecutor.runSimulation(world);
    }

    @Override
    public AllSimulationsDTO getSimulationsDTO() {
        DTOFactory dtoFactory = new DTOFactory();
        List<PastSimulationDTO> pastSimulationDTOList = new ArrayList<>();
        for (PastSimulation pastSimulation : world.getPastSimulations()) {
            Collection<EntityDefinition> entityDefinitionCollection = pastSimulation.getEntities();
            int id = pastSimulation.getSimulationId();
            Date date = pastSimulation.getSimulationDate();
            List<PastEntityDTO> pastEntityDTOList = new ArrayList<>();
            for (EntityDefinition entityDefinition : entityDefinitionCollection) {
                List<PropertyDTO> propertyDTOList = new ArrayList<>();
                for (PropertyDefinition propertyDefinition : entityDefinition.getPropertiesList()) {
                    PropertyDTO propertyDTO = dtoFactory.createPropertyDTO(propertyDefinition);
                    propertyDTOList.add(propertyDTO);
                }
                pastEntityDTOList.add(new PastEntityDTO(entityDefinition.getName(), entityDefinition.getPopulation(), entityDefinition.getEntityInstances().size(), propertyDTOList));
            }
            pastSimulationDTOList.add(new PastSimulationDTO(id, pastEntityDTOList, date));
        }
        return new AllSimulationsDTO(pastSimulationDTOList);
    }



    public HistogramDTO getHistogram(String propertyName) {
        Collection<EntityDefinition> entityDefinitions = world.getEntityDefinitions();
        Map<Object, Integer> valueToAmount = new HashMap<>();

        for (EntityDefinition entityDefinition : entityDefinitions) {
            List<EntityInstance> entityInstances = entityDefinition.getEntityInstances();
            for (EntityInstance entityInstance : entityInstances) {
                Object key = (entityInstance.getPropertyByName(propertyName).getValue());

                if (key instanceof Integer || key instanceof Float) {
                    Number numKey = (Number) key;
                    if (valueToAmount.containsKey(numKey))
                        valueToAmount.put(numKey, valueToAmount.get(numKey) + 1);
                    else
                        valueToAmount.put(numKey, 1);
                } else if (key instanceof Boolean) {
                    Boolean boolKey = (Boolean) key;
                    if (valueToAmount.containsKey(boolKey))
                        valueToAmount.put(boolKey, valueToAmount.get(boolKey) + 1);
                    else
                        valueToAmount.put(boolKey, 1);
                } else if (key instanceof String) {
                    String strKey = (String) key;
                    if (valueToAmount.containsKey(strKey))
                        valueToAmount.put(strKey, valueToAmount.get(strKey) + 1);
                    else
                        valueToAmount.put(strKey, 1);
                }
            }
        }
        return new HistogramDTO(valueToAmount);
    }
}
