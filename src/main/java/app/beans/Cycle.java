package app.beans;

import util.Pair;

/**
 * Iterates a finite sequence of <code>T</code>'s, starting over from the first
 * element after reaching the last one. Picture it as a cycle in a directed
 * graph: start from a node, go to the next, next again, and so on until
 * eventually coming back to the starting point.
 */
public interface Cycle<T> {

    /**
     * {@link Cycle#where() Where} are we in the cycle? We can either be at the
     * {@link #Start beginning} or {@link #OnWay on the way} to get back there.
     */
    enum Position { Start, OnWay }

    /**
     * @return the next element in the cycle.
     */
    T next();

    /**
     * Tells where the element returned by the last call to {@link #next() next}
     * is in the cycle.
     * @return the {@link Position} in the cycle of the latest element produced
     * by {@link #next() next} or {@link Position#Start Start} if {@link #next()
     * next} has never been called.
     */
    Position where();

    /**
     * Convenience method to combine a call to {@link #next() next} and
     * {@link Cycle#where() where}.
     * @return the next element in the cycle along with its {@link Position}.
     */
    default Pair<T, Position> advance() {
        return new Pair<>(next(), where());
    }

}
/*
 * NOTE. Admittedly a bit contrived but want to show Spring 4 meshes well with
 * Java 8...
 */
