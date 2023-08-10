package world;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import world.entity.api.EntityInstance;
import world.entity.impl.EntityInstanceImpl;
import world.environment.api.ActiveEnvironment;

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

    public void addEntityInstanceList(String name, List<EntityInstance> lst) {
        nameToEntityList.put(name, lst);
        population += lst.size();
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
}
