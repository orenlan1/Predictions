package world.action;

import world.entity.impl.EntityInstanceImpl;

public interface Action {
    public void activate(EntityInstanceImpl entity);
    public String show();
}
