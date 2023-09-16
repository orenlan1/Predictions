package world.simulation;

import dto.SimulationRunnerDTO;
import world.World;
import world.action.api.Action;
import world.entity.api.EntityDefinition;
import world.entity.api.EntityInstance;
import world.rule.api.Rule;

import java.util.*;

public class SimulationExecutor {


    public SimulationRunnerDTO runSimulation(World world) {
        Integer seconds = world.getTermination().getSecondCount();
        Integer ticks = world.getTermination().getTicksCount();
        world.resetTicks();
        try {
            simulationRulesPerform(world, seconds, ticks);
        } catch (Exception e) {
            return new SimulationRunnerDTO(Boolean.FALSE, e.getMessage(), world.getSimulationID(), Boolean.FALSE);
        }
        if (ticks != null && seconds != null) {
            if (world.getTicks() >= ticks)
                return new SimulationRunnerDTO(Boolean.TRUE, null, world.getSimulationID(), Boolean.TRUE);
            else
                return new SimulationRunnerDTO(Boolean.TRUE, null, world.getSimulationID(), Boolean.FALSE);
        }
        else if (ticks != null)
            return new SimulationRunnerDTO(Boolean.TRUE, null, world.getSimulationID(), Boolean.TRUE);
        else
            return new SimulationRunnerDTO(Boolean.TRUE, null, world.getSimulationID(), Boolean.FALSE);
    }


    public void simulationRulesPerform(World world, Integer seconds, Integer ticks) throws Exception {
        Map<String, Map<Integer, Integer>> entityToPopulation = new HashMap<>();
        for (EntityDefinition entityDefinition : world.getEntityDefinitions()) {
            entityDefinition.createEntityInstancesPopulation(world.getGrid());
            entityToPopulation.put(entityDefinition.getName(), new LinkedHashMap<>());
        }

        List<Rule> rules = world.getRules();
        Boolean valid = true;
        long start = System.currentTimeMillis();
        while (valid) {
            for (EntityDefinition entityDefinition : world.getEntityDefinitions()) {
                for (EntityInstance entityInstance : entityDefinition.getEntityInstances()) {
                    if (entityInstance.isAlive()) {
                        for (Rule rule : rules) {
                            if (rule.getActivation().isActive(world.getTicks()))
                                rule.performActions(entityInstance, world.getTicks());
                        }
                    }
                }
            }
            world.tick();

            int sumPopulation = 0;
            for (EntityDefinition entityDefinition : world.getEntityDefinitions()) {
                List<EntityInstance> entityInstances = entityDefinition.getEntityInstances();
                entityInstances.removeIf(entityInstance -> !entityInstance.isAlive());
                sumPopulation += entityInstances.size();
                if (world.getTicks() % 1000 == 0) {
                    Map<Integer, Integer> populationOverTime = entityToPopulation.get(entityDefinition.getName());
                    populationOverTime.put(world.getTicks(), entityInstances.size());
                }
            }
            world.updatePopulation(sumPopulation);

            if (ticks != null && seconds != null)
                valid = world.getTicks() < ticks && System.currentTimeMillis() - start < (seconds * 1000L);
            else if (ticks != null)
                valid = world.getTicks() < ticks;
            else
                valid = System.currentTimeMillis() - start < (seconds * 1000L);
        }
        world.updateSimulationID();
        Date date = new Date(start);



        List<EntityDefinition> newEntityDefinitions = new ArrayList<>();
        for (EntityDefinition entityDefinition : (world.getEntityDefinitions())) {
            newEntityDefinitions.add(entityDefinition.cloneEntityDefinition());
        }

        world.addPastSimulation(new PastSimulation(newEntityDefinitions, world.getSimulationID(), date, entityToPopulation));
    }
}
