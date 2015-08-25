package app.beans;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import util.Pair;

/**
 * Decorates a {@link Cycle} to collect the elements the cycle has produced.
 */
public class CycleTracker<T> implements Cycle<T> {

    private Cycle<T> target;
    private List<Pair<T, Cycle.Position>> iterated;

    /**
     * Creates a new instance to track the given {@code target}.
     * @param target the underlying cycle to track.
     */
    public CycleTracker(Cycle<T> target) {
        requireNonNull(target, "target");

        this.target = target;
        this.iterated = new ArrayList<>();
    }

    @Override
    public T next() {
        return advance().fst(); // (*)
    }
    // (*) possible NPE if advance default implementation was overridden;
    // if it happens, let it bubble up...

    @Override
    public Pair<T, Cycle.Position> advance() {
        Pair<T, Cycle.Position> next = target.advance();
        iterated.add(next); // (*)
        return next;
    }
    // (*) possible NPE if advance default implementation was overridden;
    // if it happens, let it bubble up...

    @Override
    public Cycle.Position where() {
        return target.where();
    }

    /**
     * @return the elements the underlying {@link Cycle} has produced so far;
     * the order of the elements in the stream reflects the order in which 
     * {@link Cycle#next() next} and {@link Cycle#advance() advance} have been
     * called.
     */
    public Stream<Pair<T, Cycle.Position>> iteratedSoFar() {
        return iterated.stream(); // (*)
    }
    // so our private collection can't be modified by others...

}
