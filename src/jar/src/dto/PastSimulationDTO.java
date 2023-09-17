package dto;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class PastSimulationDTO {
    private final int id;
    private final List<PastEntityDTO> entitiesDTO;
    private final Date date;
    private final Map<String , Map<Integer, Integer>> entityToPopulation;
    private final List<EnvVariablesDTO> environmentVariables;

    public PastSimulationDTO(int simulationID, List<PastEntityDTO> entitiesDTO, Date date, Map<String , Map<Integer, Integer>> entityToPopulation, List<EnvVariablesDTO> environmentVariables) {
        this.id = simulationID;
        this.entitiesDTO = entitiesDTO;
        this.date = date;
        this.entityToPopulation = entityToPopulation;
        this.environmentVariables = environmentVariables;
    }

    public int getId() {
        return id;
    }

    public List<PastEntityDTO> getEntitiesDTO() {
        return entitiesDTO;
    }

    public Date getDate() {
        return date;
    }

    public Map<String, Map<Integer, Integer>> getEntityToPopulation() {
        return entityToPopulation;
    }

    public List<EnvVariablesDTO> getEnvironmentVariables() {
        return environmentVariables;
    }
}
