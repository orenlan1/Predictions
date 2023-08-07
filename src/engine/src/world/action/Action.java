package world.action;

import world.entity.impl.Entity;

public interface Action {
    public void activate(Entity entity);
    public String show();
}
