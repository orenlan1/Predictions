package predictions.api;

import components.queue.management.ThreadPoolDelegate;
import dto.*;
import world.World;
import world.exceptions.EntityPropertyNotExistException;

import java.util.List;

public interface PredictionsService {
    FileReaderDTO readFileAndLoad(String fileName);
    SimulationInfoDTO getSimulationInformation();
    void randomizeEnvProperties();
    PropertiesDTO getEnvPropertiesDTO();
    EnvVariableSetValidationDTO setEnvironmentVariables(List<UserInputEnvironmentVariableDTO> DTOs);
    void setEntitiesPopulation(List<EntityInitializationDTO> DTOs);
    List<EnvVariablesDTO> getEnvVariablesDTOList(Integer id);
    Integer runSimulation(ThreadPoolDelegate threadPoolDelegate);
    PastSimulationDTO getSimulationsDTO(Integer id);
    HistogramDTO getHistogram(Integer id, String entityName, String propertyName);
    Double getConsistency(Integer id, String entityName, String propertyName);
    MeanPropertyDTO getMeanOfProperty(Integer id, String entityName, String propertyName) throws EntityPropertyNotExistException;
}


