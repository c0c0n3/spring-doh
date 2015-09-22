package util;

import static java.util.Objects.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
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
     * Is the given array reference {@code null} or is the array length 0?
     * @param ts the array to test.
     * @return {@code true} for yes; {@code false} for no.
     */
    public static <T> boolean isNullOrZeroLength(T[] ts) {
        return ts == null || ts.length == 0;
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
    // works in most cases but see riddle at bottom of file...
    
    /**
     * Same as {@link Streams#zip(java.util.stream.Stream, java.util.stream.Stream)
     * Streams.zip} but operating on arrays.
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
     * Same as {@link Streams#zipIndex(java.util.stream.Stream) Streams.zipIndex} 
     * but operating on arrays.
     * @throws NullPointerException if the argument is {@code null}.
     */
    public static <X> Pair<Integer, X>[] zipIndex(X[] xs) {
        requireNonNull(xs, "xs");
        
        Pair<Integer, X>[] zs = newPairs(xs.length);
        
        for (int k = 0; k < xs.length; ++k) {
            zs[k] = new Pair<Integer, X>(k, xs[k]);
        }
        return zs;
    }
    // ya, could've used zip to avoid duplication, but this is more efficient.
    
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
     * Same as {@link Streams#cycle(int, java.util.stream.Stream) Streams.cycle}
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
     * Same as {@link Streams#inits(java.util.stream.Stream) Streams.inits}
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
    
    /**
     * Same as {@link Streams#map(BiFunction, java.util.stream.Stream) 
     * Streams.map} but operating on arrays.
     * @throws NullPointerException if any argument is {@code null}.
     */
    public <X> A[] map(BiFunction<Integer, X, A> f, X[] list) {
        requireNonNull(f, "f");
        requireNonNull(list, "list");
        
        A[] mapped = generator.apply(list.length);
        for (int k = 0; k < mapped.length; ++k) {
            mapped[k] = f.apply(k, list[k]);
        }
        return mapped;
    }
    
}
/* So here's a riddle:
 * 
    @SuppressWarnings("unchecked")
    public static <X, Y>
    Pair<X, Y>[] why(Stream<Pair<X, Y>> ps) {
        Pair<X, Y>[] path = ps.toArray(Arrayz::newPairs);       // newPairs works here
                             
        return Arrayz //.op(Arrayz::newPairs)                   // but not here!
                        .op(sz -> (Pair<X, Y>[]) new Pair[sz])  // inlining works!
                        .map((i, p) -> p, path);
    }
 *
 * Arrayz::newPairs works in the first call but wouldn't work in the second. 
 * Moreover, the type of map inferred by the compiler seems to be (note the
 * nested Pair):
 * 
 * <Pair<X, Y>> Pair<Pair<X, Y>, Y>[] 
 * Arrayz.map(BiFunction<Integer, Pair<X, Y>, Pair<Pair<X, Y>, Y>> f, Pair<X, Y>[] list)
 * 
 * But then why the inferred type for map in this case:
 *
    @SuppressWarnings("unchecked")
    Pair<Long, Byte>[] why1(Stream<Pair<Long, Byte>> ps) {
        Pair<Long, Byte>[] path = ps.toArray(Arrayz::newPairs);
                             
        return Arrayz //.op(Arrayz::newPairs)
                        .op(sz -> (Pair<Long, Byte>[]) new Pair[sz])
                        .map((i, p) -> p, path);
    }
 *
 * seems to be (no nested Pair):
 * 
 * <Pair<Long, Byte>> Pair<Long, Byte>[] 
 * Arrayz.map(BiFunction<Integer, Pair<Long, Byte>, Pair<Long, Byte>> f, Pair<Long, Byte>[] list)
 * 
 * For example look at: StreamsMapTest.mapWithPairIsZip(). 
 * What the hell is going on?  
 * If somebody could explain it to me I'll be forever grateful. 
 */