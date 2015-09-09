package app.core.cyclic;

import java.util.stream.Stream;

import util.Pair;

/**
 * A {@link Cycle} with the ability to remember the sequence of elements that
 * have been requested.
 */
public interface TrackableCycle<T> extends Cycle<T> {

    /**
     * Lists the sequence of elements drawn from the cycle up to this point.
     * @return the elements the {@link Cycle} has produced so far; the order 
     * of the elements in the stream reflects the order in which {@link Cycle#
     * next() next} and {@link Cycle#advance() advance} have been called.
     */
    Stream<Pair<T, Cycle.Position>> iteratedSoFar();
    
}
