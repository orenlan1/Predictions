package predictions.api;

import dto.*;
import world.World;

import java.util.List;

public interface PredictionsService {
    FileReaderDTO readFileAndLoad(String fileName);
    SimulationInfoDTO getSimulationInformation();
    PropertiesDTO getEnvPropertiesDTO();
    EnvVariableSetValidationDTO setEnvironmentVariable(UserInputEnvironmentVariableDTO dto);
    List<EnvVariablesDTO> getEnvVariablesDTOList();
}
