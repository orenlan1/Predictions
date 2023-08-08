package world.rule;

import world.action.Action;
import world.entity.impl.EntityInstanceImpl;

import java.util.ArrayList;
import java.util.List;

public class Rule {
    private String ruleName;
    private List<Action> actions;
    private double probability;
    private int everyXTicks;


    public Rule(String name, double prob, int numTicks) {
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
}


