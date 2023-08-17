package world;

public class Termination {
    private final Integer ticksCount;
    private final Integer secondCount;

    public Termination(Integer ticksCount, Integer secondCount) {
        this.ticksCount = ticksCount;
        this.secondCount = secondCount;
    }

    public Integer getTicksCount() {
        return ticksCount;
    }

    public Integer getSecondCount() {
        return secondCount;
    }
}