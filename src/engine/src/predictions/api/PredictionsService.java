package predictions.api;

import dto.*;
import world.World;

import java.util.List;

public interface PredictionsService {
    FileReaderDTO readFileAndLoad(String fileName);
    SimulationInfoDTO getSimulationInformation();
    void randomizeEnvProperties();
    PropertiesDTO getEnvPropertiesDTO();
    EnvVariableSetValidationDTO setEnvironmentVariables(List<UserInputEnvironmentVariableDTO> DTOs);
    void setEntitiesPopulation(List<EntityInitializationDTO> DTOs);
    List<EnvVariablesDTO> getEnvVariablesDTOList();
    SimulationRunnerDTO runSimulation();
    AllSimulationsDTO getSimulationsDTO();
    HistogramDTO getHistogram(String propertyName);
}
