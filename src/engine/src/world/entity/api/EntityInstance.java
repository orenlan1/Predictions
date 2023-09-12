package world.entity.api;

import world.entity.impl.EntityInstanceImpl;
import world.grid.Grid;
import world.grid.GridCoordinate;
import world.property.api.PropertyDefinition;
import world.property.api.PropertyInstance;
import world.property.impl.PropertyInstanceImpl;


public interface EntityInstance {
    EntityDefinition getEntityDefinition();
    PropertyInstance getPropertyByName(String name);
    void addPropertyInstance(PropertyInstance propertyInstance);
    boolean isAlive();
    void kill();
    EntityInstance createDerivedEntityInstance(EntityDefinition createdEntityDefinition);
    void setCoordinate(Grid grid);
    void setCoordinate(GridCoordinate coordinate);
    GridCoordinate getCoordinate();
    void moveEntityCoordinate(Grid grid);




}
