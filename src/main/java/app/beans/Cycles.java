package app.beans;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.stream.Stream;

import util.Pair;

/**
 * Convenience adapters to convert a {@link Cycle} into a {@link Stream} by
 * using the cycle as a stream supplier as well as methods for collecting
 * items produced by a cycle.
 */
public class Cycles {

    /**
     * Uses {@code source} as a supplier for a stream of {@code T} elements
     * produced by invocations of {@link Cycle#next() source.next()}.
     * @param source the elements supply.
     * @return a stream of {@code T}'s as produced by {@code source}.
     */
    public static <T> Stream<T> stream(Cycle<T> source) {
        requireNonNull(source, "source");
        return Stream.generate(() -> source.next());
    }

    /**
     * Uses {@code source} as a supplier for a stream of pairs of {@code T} 
     * elements and their position in the cycle produced by invocations of 
     * {@link Cycle#advance() source.advance()}.
     * @param source the elements supply.
     * @return a stream of pairs of {@code T}'s and {@link Cycle.Position}'s
     * as produced by {@code source}.
     */
    public static <T> 
    Stream<Pair<T, Cycle.Position>> streamWithPosition(Cycle<T> source) {
        requireNonNull(source, "source");
        return Stream.generate(() -> source.advance());
    }

    /**
     * Collects the given number of elements from the {@code source} cycle into
     * an array. 
     * @param howMany elements to collect.
     * @param source the elements supply.
     * @return the collected elements.
     */
    public static <T> List<T> collect(int howMany, Cycle<T> source) {
        return stream(source).limit(howMany).collect(toList());
    }
    
}
