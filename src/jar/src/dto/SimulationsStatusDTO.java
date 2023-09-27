package dto;

import java.util.Map;

public class SimulationsStatusDTO {
    private final Map<Integer, Boolean> runningSimulations;
    private final Map<Integer, Boolean> validSimulations;

    public SimulationsStatusDTO(Map<Integer, Boolean> runningSimulations, Map<Integer, Boolean> validSimulations) {
        this.runningSimulations = runningSimulations;
        this.validSimulations = validSimulations;
    }

    public Map<Integer, Boolean> getRunningSimulations() {
        return runningSimulations;
    }

    public Map<Integer, Boolean> getValidSimulations() {
        return validSimulations;
    }
}
