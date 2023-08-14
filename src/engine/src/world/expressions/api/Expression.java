package world.expressions.api;

import world.entity.api.EntityInstance;

public interface Expression {
    Object evaluate() throws NumberFormatException;
    String getType();
}
