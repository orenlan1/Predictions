package world;

import java.util.List;
import java.util.Map;
import world.entity.Entity;

public class World {
    private static int ticks = 0;
    private Map<String, List<Entity>> string2entity;


    public void tick() { ticks++; }
}
