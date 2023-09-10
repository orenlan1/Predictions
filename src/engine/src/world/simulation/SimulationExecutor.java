package world.simulation;

import dto.SimulationRunnerDTO;
import world.World;
import world.entity.api.EntityDefinition;
import world.entity.api.EntityInstance;
import world.rule.api.Rule;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SimulationExecutor {


    public SimulationRunnerDTO runSimulation(World world) {
        Integer seconds = world.getTermination().getSecondCount();
        Integer ticks = world.getTermination().getTicksCount();
        boolean valid = true;
        world.resetTicks();
        try {
            simulationRulesPerform(valid, world, seconds, ticks);
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


    public void simulationRulesPerform(Boolean valid, World world, Integer seconds, Integer ticks) throws Exception {
        for (EntityDefinition entityDefinition : world.getEntityDefinitions()) {
            entityDefinition.createEntityInstancesPopulation();
        }
        List<Rule> rules = world.getRules();
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

            if (ticks != null && seconds != null)
                valid = world.getTicks() < ticks && System.currentTimeMillis() - start < (seconds * 1000L);
            else if (ticks != null)
                valid = world.getTicks() < ticks;
            else
                valid = System.currentTimeMillis() - start < (seconds * 1000L);
        }
        world.updateSimulationID();
        Date date = new Date(start);

        int sumPopulation = 0;
        for (EntityDefinition entityDefinition : world.getEntityDefinitions()) {
            List<EntityInstance> entityInstances = entityDefinition.getEntityInstances();
            entityInstances.removeIf(entityInstance -> !entityInstance.isAlive());
            sumPopulation += entityInstances.size();
        }
        world.updatePopulation(sumPopulation);

        List<EntityDefinition> newEntityDefinitions = new ArrayList<>();
        for (EntityDefinition entityDefinition : (world.getEntityDefinitions())) {
            newEntityDefinitions.add(entityDefinition.cloneEntityDefinition());
        }

        world.addPastSimulation(new PastSimulation(newEntityDefinitions, world.getSimulationID(), date));
    }
}
