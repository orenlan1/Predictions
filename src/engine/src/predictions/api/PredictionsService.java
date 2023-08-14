package predictions.api;

import dto.FileReaderDTO;
import world.World;

public interface PredictionsService {
    FileReaderDTO readFileAndLoad(String fileName);
    //SimulationInfoDTO getSimulationInformation()ף
}
