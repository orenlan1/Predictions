package world.rule.impl;

import world.World;
import world.action.api.Action;
import world.entity.api.EntityInstance;
import world.rule.activation.Activation;
import world.rule.api.Rule;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class RuleImpl implements Rule, Activation {
    private final String name;
    private final List<Action> actions;
    private final float probability;

    public RuleImpl(String name, float probability) {
        this.name = name;
        this.probability = probability;
        actions = new ArrayList<>();
    }

    @Override
    public String getName() {return name; }

    @Override
    public Activation getActivation() {                 //ADD IMPLEMENTATION!!!!!
        return null;
    }

    @Override
    public List<Action> getaActionsToPerform() { return actions; }

    @Override
    public void addAction(Action action) {
        actions.add(action);
    }

    @Override
    public boolean isActive(int tickNumber) {
        Random random = new Random();
        return ((World.ticks % tickNumber == 0) && (random.nextFloat() < probability));
    }

    @Override
    public void performAction(EntityInstance entityInstance) throws Exception {
        for (Action action : actions) {
            action.activate(entityInstance);
        }
    }
}
