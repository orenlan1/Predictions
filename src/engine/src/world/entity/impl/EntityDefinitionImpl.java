package world.entity.impl;

import world.entity.api.EntityDefinition;
import world.entity.api.EntityInstance;
import world.property.api.PropertyDefinition;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class EntityDefinitionImpl implements EntityDefinition {
    private final String name;
    private final int population;
    private List<EntityInstance> entityInstances;
    private final List<PropertyDefinition> propertiesList;


    public EntityDefinitionImpl(String name, int population) {
        this.name = name;
        this.population = population;
        this.entityInstances = new LinkedList<>();
        this.propertiesList = new ArrayList<>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getPopulation() {
        return population;
    }

    @Override
    public List<PropertyDefinition> getPropertiesList() {
        return propertiesList;
    }

    @Override
    public void addEntityInstance(EntityInstance entityInstance) {
        this.entityInstances.add(entityInstance);
    }

    public void addPropertyDefinition(PropertyDefinition propertyDefinition) {
        this.propertiesList.add(propertyDefinition);
    }

    @Override
    public void createEntityInstancesPopulation() {
        for ( int i = 0; i < population; i++) {
            entityInstances.add(EntityInstance.createEntityInstance(this));
        }
    }
}
