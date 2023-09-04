package world.grid;

import world.entity.api.EntityInstance;

import java.util.Random;

public class GridCoordinate {
    private int x;
    private int y;

    private final int maxCols;
    private final int maxRows;

    public GridCoordinate(int maxRows, int maxCols) {
        this.maxRows = maxRows;
        this.maxCols = maxCols;
    }

    public void setRandomlyCoordinate(Grid grid, EntityInstance entityInstance) {
        Random random = new Random();
        do {
            this.x = random.nextInt(maxCols) + 1;
            this.y = random.nextInt(maxRows) + 1;
        } while (!grid.addCoordinateToMap(entityInstance));
    }

    public boolean move(Direction direction, Grid grid) {
        this.x = (x + direction.getDeltaX() > maxCols)? (x + direction.getDeltaX()) % maxCols : x + direction.getDeltaX();
        this.y = (y + direction.getDeltaY() > maxRows)? (x + direction.getDeltaY()) % maxRows : x + direction.getDeltaY();
        return grid.isCoordinateTaken(this);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isCoordinateInProximity(GridCoordinate targetCoordinate,int proximityLevel) {
        for (int i = -proximityLevel; i <= proximityLevel; i++) {
            for (int j = -proximityLevel; j <= proximityLevel; j++) {
                if (Math.abs(i) == proximityLevel || Math.abs(j) == proximityLevel) {
                    int x = this.x + i;
                    int y = this.y + j;
                    if ( x > maxCols )
                        x = x % maxCols;
                    if ( y > maxRows )
                        y = y % maxRows;
                    if ( x == targetCoordinate.getX() && y == targetCoordinate.getY()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GridCoordinate coordinate = (GridCoordinate) o;

        return x == coordinate.x && y == coordinate.y;
    }

    @Override
    public int hashCode() {
        return x + y;
    }
}
