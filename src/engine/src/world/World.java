package world;

import java.util.List;
import java.util.Map;
import world.entity.impl.EntityInstanceImpl;

public class World {
    private static int ticks = 0;
    private Map<String, List<EntityInstanceImpl>> string2entity;


    public void tick() { ticks++; }
}
