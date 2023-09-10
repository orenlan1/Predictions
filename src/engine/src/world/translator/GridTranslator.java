package world.translator;

import jaxb.generated.PRDWorld.PRDGrid;
import world.grid.Grid;

public class GridTranslator {
    public static Grid translateGrid(PRDGrid prdGrid) {
        return new Grid(prdGrid.getColumns(), prdGrid.getRows());
    }
}
