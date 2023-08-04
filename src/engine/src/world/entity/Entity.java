package world.entity;

import world.properties.Property;
import generated.PRDEntity;
import java.util.HashMap;
import java.util.Map;

public class Entity {
    private String name;
    private Map<String, Property> string2property;




    public Entity(PRDEntity prdEntity) {
        this.name = prdEntity.getName();
        string2property = new HashMap<>();

    }
}
