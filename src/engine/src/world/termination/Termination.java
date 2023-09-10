package world.termination;

public class Termination {
    private final Integer ticksCount;
    private final Integer secondCount;
    private Boolean byUser;

    public Termination(Integer ticksCount, Integer secondCount, Boolean byUser) {
        this.ticksCount = ticksCount;
        this.secondCount = secondCount;
        this.byUser = byUser;
    }

    public Integer getTicksCount() {
        return ticksCount;
    }

    public Integer getSecondCount() {
        return secondCount;
    }
}