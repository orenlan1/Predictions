package world.translator;

import generated.PRDBySecond;
import generated.PRDByTicks;
import generated.PRDTermination;
import world.Termination;

import java.util.List;

public class TerminationTranslator {
    public static Termination translateTermination(PRDTermination prdTermination) {
        Integer ticks = null;
        Integer seconds = null;
        List<Object> ticksOrSecond = prdTermination.getPRDByTicksOrPRDBySecond();
        if (ticksOrSecond.get(0) != null && ticksOrSecond.get(0) instanceof PRDByTicks) {
            ticks = ((PRDByTicks) ticksOrSecond.get(0)).getCount();
        }
        if (ticksOrSecond.get(1) != null && ticksOrSecond.get(1) instanceof PRDBySecond) {
            seconds = ((PRDBySecond) ticksOrSecond.get(1)).getCount();
        }
        return new Termination(ticks,seconds);
    }
}
