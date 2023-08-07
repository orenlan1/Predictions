package world.entity.impl;

import world.property.impl.IntegerProperty;
import generated.PRDEntity;
import java.util.HashMap;
import java.util.Map;

public class Entity {
    private String name;
    private Map<String, IntegerProperty> string2property;




    public Entity(PRDEntity prdEntity) {
        this.name = prdEntity.getName();
        string2property = new HashMap<>();

    }



}
