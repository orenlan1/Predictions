package world.simulation;

import world.entity.api.EntityDefinition;

import java.text.DateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class PastSimulation {
    private final Collection<EntityDefinition> entities;
    private final int id;
    private final Date date;

    public PastSimulation(Collection<EntityDefinition> entityDefinitions, int simulationID, Date date) {
        entities = entityDefinitions;
        id = simulationID;
        this.date = date;
    }

    public Collection<EntityDefinition> getEntities() {
        return entities;
    }

    public int getSimulationId() {
        return id;
    }

    public Date getSimulationDate() {
        return date;
    }

}
