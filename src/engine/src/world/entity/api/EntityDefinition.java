package world.entity.api;

import world.exceptions.EntityPropertyNameExistException;
import world.exceptions.EntityPropertyNotExistException;
import world.property.api.PropertyDefinition;

import java.util.List;

public interface EntityDefinition {
    String getName();
    int getPopulation();
    List<PropertyDefinition> getPropertiesList();
    void addEntityInstance(EntityInstance entityInstance);
    void addPropertyDefinition(PropertyDefinition propertyDefinition) throws EntityPropertyNameExistException;
    PropertyDefinition getPropertyByName(String propertyName) throws EntityPropertyNotExistException;
    void createEntityInstancesPopulation();
    public List<EntityInstance> getEntityInstances();
    void removeEntity(EntityInstance entityInstance);

}
