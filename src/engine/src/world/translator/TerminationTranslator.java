package world.translator;

import generated.PRDBySecond;
import generated.PRDByTicks;
import generated.PRDTermination;
import world.termination.Termination;

import java.util.List;

public class TerminationTranslator {
    public static Termination translateTermination(PRDTermination prdTermination) {
        Integer ticks = null;
        Integer seconds = null;
        List<Object> ticksOrSecond = prdTermination.getPRDByTicksOrPRDBySecond();
        Object possibleTicks = ticksOrSecond.get(0), possibleSeconds = null;
        if (ticksOrSecond.size() > 1)
            possibleSeconds = ticksOrSecond.get(1);

        if (possibleTicks instanceof PRDByTicks) {
            ticks = ((PRDByTicks) possibleTicks).getCount();
        }
        else if (possibleTicks instanceof PRDBySecond) {
            seconds = ((PRDBySecond) possibleTicks).getCount();
        }
        if (possibleSeconds instanceof PRDBySecond) {
            seconds = ((PRDBySecond) possibleSeconds).getCount();
        }
        return new Termination(ticks, seconds);
    }
}
