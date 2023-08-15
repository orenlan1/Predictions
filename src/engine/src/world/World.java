package world;

import java.util.*;

import generated.PRDProperties;
import generated.PRDProperty;
import world.entity.api.EntityDefinition;
import world.entity.api.EntityInstance;
import world.entity.impl.EntityInstanceImpl;
import world.environment.api.ActiveEnvironment;
import world.environment.api.EnvironmentVariablesManager;
import world.exceptions.RuleNameExistException;
import world.property.api.PropertyDefinition;
import world.rule.api.Rule;
import world.translator.PropertyTranslator;

public class World {
    private final Map<String, EntityDefinition> nameToEntityDefinition;
    private EnvironmentVariablesManager environmentVariablesManager;
    private ActiveEnvironment activeEnvironment;
    private final List<Rule> rules;
    public static int ticks = 0;
    private int population;

    public World() {
        population = 0;
        nameToEntityDefinition = new HashMap<>();
        rules = new ArrayList<>();
    }
    public int getTotalPopulation() { return population; }

    public void addEntityDefinition(String name, EntityDefinition entityDefinition) {
        nameToEntityDefinition.put(name, entityDefinition);
        population += entityDefinition.getPopulation();
    }

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

    public ActiveEnvironment getActiveEnvironment() {
        return activeEnvironment;
    }

    public void tick() { ticks++; }

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
}
