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
}
