package world.action.impl;

import world.entity.api.EntityDefinition;

public class SecondaryEntity {
    private final EntityDefinition secondaryEntityDefinition;
    private final String count;
    private final ConditionAction condition;

    public SecondaryEntity(EntityDefinition secondaryEntityDefinition, String count, ConditionAction condition) {
        this.secondaryEntityDefinition = secondaryEntityDefinition;
        this.count = count;
        this.condition = condition;
    }

    public EntityDefinition getSecondaryEntityDefinition() {
        return secondaryEntityDefinition;
    }

    public String getCount() {
        return count;
    }

    public ConditionAction getCondition() {
        return condition;
    }
}
