package world.simulation;

import world.entity.api.EntityDefinition;

import java.text.DateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class PastSimulation {
    private final Collection<EntityDefinition> entities;
    private final int id;
    private final Date date;
    private final Map<String , Map<Integer, Integer>> entityToPopulation;

    public PastSimulation(Collection<EntityDefinition> entityDefinitions, int simulationID, Date date, Map<String , Map<Integer, Integer>> entityToPopulation) {
        entities = entityDefinitions;
        id = simulationID;
        this.date = date;
        this.entityToPopulation = entityToPopulation;
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
