package dto;

import java.util.Date;
import java.util.List;

public class PastSimulationDTO {
    private final int id;
    private final List<PastEntityDTO> entitiesDTO;
    private final Date date;

    public PastSimulationDTO(int simulationID, List<PastEntityDTO> entitiesDTO, Date date) {
        this.id = simulationID;
        this.entitiesDTO = entitiesDTO;
        this.date = date;
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
}
