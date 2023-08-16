package predictions.impl;

import dto.FileReaderDTO;
import dto.SimulationInfoDTO;
import predictions.api.PredictionsService;
import world.EngineFileReader;
import world.World;

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
        
    }
}
