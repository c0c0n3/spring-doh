package util;

import static java.util.Objects.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntFunction;

/**
 * Convenience methods, mainly useful for testing {@link Streams} methods.
 * @param <A> the type of the array.
 */
public class Arrayz<A> {  // avoids conflicts with JDK Arrays class.

    /**
     * Convenience conversion from varargs to array.
     * @param ts the arguments.
     * @return the arguments array.
     */
    @SafeVarargs
    public static <T> T[] array(T...ts) {
        return ts;
    }
    
    /**
     * Creates a new array of generic {@link Pair}'s.
     * @param size the size of the array.
     * @return the new array.
     */
    @SuppressWarnings("unchecked")
    public static <X, Y> Pair<X, Y>[] newPairs(int size) {
        return (Pair<X, Y>[]) new Pair[size];
    }
    
    /**
     * Same as {@link Streams#zip(java.util.stream.Stream, java.util.stream.Stream)
     * Streams.zip()} but operating on arrays.
     * @throws NullPointerException if either or both arguments are {@code null}.
     */
    public static <X, Y> Pair<X, Y>[] zip(X[] xs, Y[] ys) {
        requireNonNull(xs, "xs");
        requireNonNull(ys, "ys");
        
        int size = Math.min(xs.length, ys.length);
        Pair<X, Y>[] zs = newPairs(size);
        
        for (int k = 0; k < size; ++k) {
            zs[k] = new Pair<X, Y>(xs[k], ys[k]);
        }
        return zs;
    }
    
    /**
     * Access to instance operations that require a way to instantiate arrays.
     * Example: {@code op(Long[]::new).cycle(2, arrayOfLongs)}.
     * @param generator function to create a new array of {@code T}'s given the
     * array size.
     * @return an {@code Aray} instance to call the desired method.
     * @throws NullPointerException if {@code generator} is {@code null}.
     */
    public static <T> Arrayz<T> op(IntFunction<T[]> generator) {
        return new Arrayz<T>(generator);
    }
    
    private IntFunction<A[]> generator;
    
    private Arrayz(IntFunction<A[]> generator) {
        requireNonNull(generator, "generator");
        this.generator = generator;
    }
    
    /**
     * Same as {@link Streams#cycle(int, java.util.stream.Stream) Streams.cycle()}
     * but operating on arrays.
     * @throws NullPointerException if {@code list} is {@code null}.
     */
    public A[] cycle(int times, A[] list) {
        requireNonNull(list, "list");
        
        int size = list.length * times;
        A[] xs = generator.apply(size);
        
        for (int k = 0; k < size; ++k) {
            xs[k] = list[k % list.length];
        }
        return xs;
    }
    
    /**
     * Same as {@link Streams#inits(java.util.stream.Stream) Streams.inits()}
     * but operating on arrays.
     * @throws NullPointerException if {@code list} is {@code null}.
     */
    public List<A[]> inits(A[] list) {
        requireNonNull(list, "list");
        
        int numberOfSegments = 1 + list.length;
        List<A[]> segments = new ArrayList<>(numberOfSegments);
        for(int k = 0; k < numberOfSegments; ++k) {
            A[] segment = generator.apply(k);
            System.arraycopy(list, 0, segment, 0, k);
            segments.add(segment);
        }
        return segments;
    }
    
}
