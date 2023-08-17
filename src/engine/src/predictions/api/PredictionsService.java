package predictions.api;

import dto.FileReaderDTO;
import dto.SimulationInfoDTO;
import world.World;

public interface PredictionsService {
    FileReaderDTO readFileAndLoad(String fileName);
    SimulationInfoDTO getSimulationInformation();
}
