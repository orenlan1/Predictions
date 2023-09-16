package dto;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class PastSimulationDTO {
    private final int id;
    private final List<PastEntityDTO> entitiesDTO;
    private final Date date;
    private final Map<String , Map<Integer, Integer>> entityToPopulation;

    public PastSimulationDTO(int simulationID, List<PastEntityDTO> entitiesDTO, Date date, Map<String , Map<Integer, Integer>> entityToPopulation) {
        this.id = simulationID;
        this.entitiesDTO = entitiesDTO;
        this.date = date;
        this.entityToPopulation = entityToPopulation;
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
}
