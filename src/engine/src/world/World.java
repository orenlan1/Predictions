package world;

import java.util.*;

import world.entity.api.EntityDefinition;
import world.environment.api.ActiveEnvironment;
import world.environment.api.EnvironmentVariablesManager;
import world.exceptions.RuleNameExistException;
import world.grid.Grid;
import world.rule.api.Rule;
import world.simulation.PastSimulation;
import world.termination.Termination;

public class World {
    private final Map<String, EntityDefinition> nameToEntityDefinition;
    private EnvironmentVariablesManager environmentVariablesManager;
    private ActiveEnvironment activeEnvironment;
    private final List<Rule> rules;
    public static int ticks = 0;
    private int population;
    private int simulationID = 0;
    private Termination termination;
    private final List<PastSimulation> pastSimulations;
    private Grid grid;
    private int threadCount;

    public World() {
        population = 0;
        nameToEntityDefinition = new HashMap<>();
        rules = new ArrayList<>();
        pastSimulations = new ArrayList<>();
    }
    public int getTotalPopulation() { return population; }

    public void addEntityDefinition(String name, EntityDefinition entityDefinition) {
        nameToEntityDefinition.put(name, entityDefinition);
        population += entityDefinition.getPopulation();
    }

    public void updatePopulation(int n) { population = n; }

    public Optional<EntityDefinition> getEntityDefinitionByName(String name) {
        return Optional.ofNullable(nameToEntityDefinition.get(name));
    }

    public EnvironmentVariablesManager getEnvironmentVariablesManager() {
        return environmentVariablesManager;
    }

    public void setEnvironmentVariablesManager(EnvironmentVariablesManager environmentVariablesManager) {
        this.environmentVariablesManager = environmentVariablesManager;
    }

    public void setActiveEnvironment(ActiveEnvironment activeEnvironment) {
        this.activeEnvironment = activeEnvironment;
    }

    public Collection<EntityDefinition> getEntityDefinitions() {
        return nameToEntityDefinition.values();
    }

    public ActiveEnvironment getActiveEnvironment() {
        return activeEnvironment;
    }

    public void tick() { ticks++; }

    public void resetTicks() { ticks = 0; }

    public List<Rule> getRules() {
        return rules;
    }

    public void addRule(Rule rule) throws RuleNameExistException {
        for (Rule existingRule : rules) {
            if (existingRule.getName().equals(rule.getName())) {
                throw new RuleNameExistException(rule.getName());
            }
        }
        rules.add(rule);
    }

    public Map<String, EntityDefinition> getNameToEntityDefinition() {
        return nameToEntityDefinition;
    }

    public Termination getTermination() {
        return termination;
    }

    public void setTermination(Termination termination) {
        this.termination = termination;
    }

    public int getSimulationID() { return simulationID; }

    public void updateSimulationID() { simulationID++; }

    public void addPastSimulation(PastSimulation pastSimulation) { pastSimulations.add(pastSimulation); }

    public List<PastSimulation> getPastSimulations() { return pastSimulations;}

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public void setThreadCount(int threadCount) {
        this.threadCount = threadCount;
    }
}
