package world.simulation;

import world.entity.api.EntityDefinition;
import world.environment.api.ActiveEnvironment;

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
    private final ActiveEnvironment activeEnvironment;

    public PastSimulation(Collection<EntityDefinition> entityDefinitions, int simulationID, Date date, Map<String , Map<Integer, Integer>> entityToPopulation, ActiveEnvironment activeEnvironment) {
        entities = entityDefinitions;
        id = simulationID;
        this.date = date;
        this.entityToPopulation = entityToPopulation;
        this.activeEnvironment = activeEnvironment;
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

    public Map<String, Map<Integer, Integer>> getEntityToPopulation() {
        return entityToPopulation;
    }

    public ActiveEnvironment getActiveEnvironment() {
        return activeEnvironment;
    }
}
