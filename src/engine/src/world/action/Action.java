package world.action;

import world.entity.Entity;

public interface Action {
    public void activate(Entity entity);
    public String show();
}
