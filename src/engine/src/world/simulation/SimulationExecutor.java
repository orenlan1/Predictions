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
        for (EntityDefinition entityDefinition : world.getEntityDefinitions()) {
            entityDefinition.createEntityInstancesPopulation(world.getGrid());
        }
        Map<String, Map<Integer, Integer>> entityToPopulation = new HashMap<>();

        List<Rule> rules = world.getRules();
        Boolean valid = true;
        long start = System.currentTimeMillis();
        while (valid) {
            world.moveAllEntitiesCoordinates(world.getGrid());
            List<Rule> activeRules = world.computeActiveRules(world.getTicks());
            if (!activeRules.isEmpty()) {
                List<Action> actionsList = world.getActiveRulesActions(activeRules);
                for (EntityDefinition entityDefinition : world.getEntityDefinitions()) {
                    for (EntityInstance entityInstance : entityDefinition.getEntityInstances()) {
                        for (Action action : actionsList) {
                            if (action.getMainEntityDefinition() == entityDefinition) {
                                if (entityInstance.isAlive()) {
                                    if (action.getSecondaryEntityComponent() != null) {
                                        List<EntityInstance> secondaryEntityInstanceList = action.getSecondaryEntityComponent().computeSecondaryEntitiesForAction(world.getTicks());
                                        for (EntityInstance secondaryEntityInstance : secondaryEntityInstanceList) {
                                            action.activate(world.getTicks(), entityInstance, secondaryEntityInstance);
                                        }
                                    } else {
                                        action.activate(world.getTicks(), entityInstance);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            world.tick();

            if (ticks != null && seconds != null)
                valid = world.getTicks() < ticks && System.currentTimeMillis() - start < (seconds * 1000L);
            else if (ticks != null)
                valid = world.getTicks() < ticks;
            else if (seconds != null)
                valid = System.currentTimeMillis() - start < (seconds * 1000L);
            //TODO - add user terminated


            for (EntityDefinition entDefinition : world.getEntityDefinitions()) {
                List<EntityInstance> entitiesToReplace = new ArrayList<>();
                for (EntityInstance instance : entDefinition.getEntityInstances()) {
                    if (instance.isToReplace()) {
                        entitiesToReplace.add(instance);
                    }
                }
                for (EntityInstance instance : entitiesToReplace) {
                    Action action = instance.getReplaceAction();
                    action.activate(world.getTicks(), instance, null);
                }
            }


            int sumPopulation = 0;
            for (EntityDefinition entityDefinition : world.getEntityDefinitions()) {
                List<EntityInstance> entityInstances = entityDefinition.getEntityInstances();
                entityInstances.removeIf(entityInstance -> !entityInstance.isAlive());
                sumPopulation += entityInstances.size();
            }
            world.updatePopulation(sumPopulation);
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
