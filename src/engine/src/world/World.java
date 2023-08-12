package world;

import java.util.*;

import generated.PRDProperties;
import generated.PRDProperty;
import world.entity.api.EntityDefinition;
import world.entity.api.EntityInstance;
import world.entity.impl.EntityInstanceImpl;
import world.environment.api.ActiveEnvironment;
import world.property.api.PropertyDefinition;
import world.translator.PropertyTranslator;

public class World {
    private Map<String, List<EntityInstance>> nameToEntityList;
    private int population;
    private ActiveEnvironment activeEnvironment;
    public static int ticks = 0;

    public World() {
        population = 0;
        nameToEntityList = new HashMap<>();
    }
    public int getPopulation() { return population; }

    public void addEntityInstanceList(String name, EntityDefinition entityDefinition) {
        nameToEntityDefinition.put(name, entityDefinition);
        population += entityDefinition.getPopulation();
    }

    public Optional<List<EntityInstance>> getListOfEntityInstance(String name) {
        return Optional.ofNullable(nameToEntityList.get(name));
    }

    public void setActiveEnvironment(ActiveEnvironment activeEnvironment) {
        this.activeEnvironment = activeEnvironment;
    }

    public ActiveEnvironment getActiveEnvironment() {
        return activeEnvironment;
    }

    public void tick() { ticks++; }

    public List<PropertyDefinition> translateProperties(PRDProperties prdPropertiesList) throws Exception {
        List<PropertyDefinition> propertyDefinitions = new LinkedList<>();
        for (PRDProperty prdProperty : prdPropertiesList.getPRDProperty()) {
            propertyDefinitions.add(PropertyTranslator.TranslatePropertyDefinition(prdProperty));
        }
        return propertyDefinitions;
    }




}
