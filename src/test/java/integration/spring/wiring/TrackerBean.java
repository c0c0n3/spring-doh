package integration.spring.wiring;

import java.util.function.IntFunction;

import org.springframework.stereotype.Component;

import util.Pair;
import app.core.cyclic.ArrayCycle;
import app.core.cyclic.Cycle;
import app.core.cyclic.CycleTracker;

/**
 * Bean to hold a {@link CycleTracker}.
 * We can't make {@link CycleTracker} a bean because it needs to be instantiated
 * with a {@link Cycle} but it's not possible to know, at configuration time, 
 * which cycle a given tracker will use.
 */
@Component
public class TrackerBean<T> {

    private CycleTracker<T> tracker;
    
    /**
     * Creates a new {@link Cycle} from the given array and a new {@link 
     * CycleTracker} to track it. 
     * @param targetCycleElements the cycle elements.
     * @throws NullPointerException if the argument is {@code null}.
     */
    public void track(T[] targetCycleElements) {
        Cycle<T> target = new ArrayCycle<>(targetCycleElements);
        track(target);
    }
    
    /**
     * Creates a new {@link CycleTracker} to track the given cycle.
     * @param target the cycle to track.
     * @throws NullPointerException if the argument is {@code null}.
     */
    public void track(Cycle<T> target) {
        tracker = new CycleTracker<>(target);
    }
    
    /**
     * @return the current tracker or {@code null} if none of the {@code track}
     * methods has ever been called.
     */
    public CycleTracker<T> getTracker() {
        return tracker;
    }
    
    /**
     * Iterates the underlying cycle.
     * @param howMany how many elements to iterate.
     */
    public void iterate(int howMany) {
        for (int k = 0; k < howMany; ++k) {
            tracker.next();
        }
    }
    
    /**
     * Collects the cycle elements iterated so far into an array.
     * @param newTs array generator.
     * @return the iterated elements from the cycle.
     */
    public T[] collectTracked(IntFunction<T[]> newTs) {
        return tracker.iteratedSoFar()
                      .map(Pair::fst)
                      .toArray(newTs);
    }
    
}
