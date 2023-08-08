package world.rule.impl;

import world.World;
import world.action.api.Action;
import world.rule.api.Activation;
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
    public Activation getActivation() {
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


    /*
    private String ruleName;
    private List<Action> actions;
    private double probability;
    private int everyXTicks;


    public RuleImpl(String name, double prob, int numTicks) {
        this.ruleName = name;
        if (prob < 0) {
            probability = 1;
        } else {
            probability = prob;
        }

        if (numTicks < 0) {
            everyXTicks = 1;
        } else {
            everyXTicks = numTicks;
        }
        actions = new ArrayList<>();
    }

    public void performRule(EntityInstanceImpl entity, int ticks){
        double prob = Math.random();
        if ((ticks % everyXTicks == 0) && prob < probability) {
            for (Action action : actions)
                action.activate(entity);
        }
    }


    public void addAction(Action action) {
        actions.add(action);
    }

*/
}
