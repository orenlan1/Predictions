package world.grid;

import world.entity.api.EntityInstance;

import java.util.HashMap;
import java.util.Map;

public class Grid {
    private final int cols;
    private final int rows;
    private Map<GridCoordinate, EntityInstance> entityMap;

    public Grid(int cols, int rows) {
        this.cols = cols;
        this.rows = rows;
        entityMap = new HashMap<>();
    }

    public int getCols() {
        return cols;
    }

    public int getRows() {
        return rows;
    }

    public boolean isCoordinateTaken(GridCoordinate coordinate) {
        return entityMap.containsKey(coordinate);
    }

    public void removeCoordinateFromMap(GridCoordinate coordinate) {
        entityMap.remove(coordinate);
    }

    public boolean addCoordinateToMap(EntityInstance entityInstance) {
        if ( entityMap.containsKey(entityInstance.getCoordinate())){
            return false;
        }
        else {
            entityMap.put(entityInstance.getCoordinate(),entityInstance);
            return true;
        }
    }




}

