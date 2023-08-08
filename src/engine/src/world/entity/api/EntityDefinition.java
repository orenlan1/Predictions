package world.entity.api;

import world.property.api.PropertyDefinition;

import java.util.List;

public interface EntityDefinition {
    String getName();
    int getPopulation();
    List<PropertyDefinition> getPropertiesList();
    void addEntityInstance(EntityInstance entityInstance);
    void addPropertyDefinition(PropertyDefinition propertyDefinition);
    void createEntityInstancesPopulation();

}
