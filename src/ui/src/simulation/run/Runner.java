package simulation.run;

import dto.SimulationRunnerDTO;
import predictions.api.PredictionsService;

public class Runner {
    public void run(PredictionsService predictionsService) {
        SimulationRunnerDTO simulationDTO = predictionsService.runSimulation();
        if (simulationDTO.isValid()) {
            System.out.println("Simulation ID:\t" + simulationDTO.getId());
            if (simulationDTO.getTicksEnded())
                System.out.println("The simulation reached the desired number of ticks");
            else
                System.out.println("The simulation reached the desired number of seconds");
        }
        else
            System.out.println(simulationDTO.getMessage());
    }
}
