package integration.spring.wiring;

import java.util.function.IntFunction;

import org.springframework.stereotype.Component;

import util.Pair;
import app.core.cyclic.ArrayCycle;
import app.core.cyclic.Cycle;
import app.core.cyclic.CycleTracker;

@Component
public class TrackerBean<T> {

    private CycleTracker<T> tracker;
    
    public void track(T[] cycle) {
        Cycle<T> target = new ArrayCycle<>(cycle);
        tracker = new CycleTracker<>(target);
    }
    
    public void iterate(int howMany) {
        for (int k = 0; k < howMany; ++k) {
            tracker.next();
        }
    }
    
    public T[] collectTracked(IntFunction<T[]> newTs) {
        return tracker.iteratedSoFar()
                      .map(Pair::fst)
                      .toArray(newTs);
    }
    
}
