package world.rule.activation;

import world.World;

import java.util.Random;

public class ActivationImpl implements Activation{
    private final Double probability;
    private final Integer ticks;

    public ActivationImpl(Double probability, Integer ticks) {
        this.probability = probability;
        this.ticks = ticks;
    }

    @Override
    public boolean isActive(int tickNumber) {
        Random random = new Random();
        return ((World.ticks % tickNumber == 0) && (random.nextFloat() < probability));
    }
}
