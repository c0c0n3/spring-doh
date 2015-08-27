package util;

import static java.util.Objects.*;
import static java.util.stream.Collectors.*;
import static java.util.stream.Stream.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.function.BiFunction;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

/**
 * Utility methods to use with {@link Stream}'s.
 * Similar in concept to the Haskell list processing functions but with all
 * the (ridiculous?) limitations of Java. 
 * <em>Do not use for heavy duty or parallel computations</em>. You have been
 * warned...
 */
public class Streams {
    
    // NOTE: these methods all have naive implementations, you're welcome to 
    // replace them with something better; just beware of the ridiculous amount
    // of work involved---e.g. performance, mutation, parallel vs sequential, 
    // ordered vs unordered, etc. Love Java.

    /**
     * Repeats a <em>finite</em> stream the specified number of {@code times}.
     * This is a terminal operation on the input stream.
     * @param times how many times to repeat the input stream.
     * @param ts the input stream.
     * @return the stream {@code ts + ts + ... + ts}, the given number of 
     * {@code times}.
     * @throws NullPointerException if any argument is {@code null}. 
     */
    public static <T> Stream<T> cycle(int times, Stream<T> ts) {
        requireNonNull(ts, "ts");
        
        List<T> xs = ts.collect(toList());
        return iterate(xs.stream(), i -> xs.stream())
               .limit(times < 0 ? 0 : times)
               .reduce(empty(), (x, y) -> concat(x, y));
    }
    
    /**
     * Builds the initial segments of the given <em>finite</em> input stream
     * {@code ts}.
     * For example, if {@code ts = [1, 2, 3]} then {@code init(ts) = [[], [1], 
     * [1,2], [1,2,3]]}.
     * This is a terminal operation. 
     * @param ts the input stream.
     * @return the initial segments of {@code ts}.
     * @throws NullPointerException if any argument is {@code null}.
     */
    public static <T> Stream<Stream<T>> inits(Stream<T> ts) {
        requireNonNull(ts, "ts");
        
        List<T> xs = ts.collect(toList());
        Pair<Stream<T>, Long> seed = new Pair<>(empty(), 0L);
        
        UnaryOperator<Pair<Stream<T>, Long>> nextSegment = k -> {
            long size = k.snd() + 1;
            Stream<T> segment = xs.stream().limit(size);
            return new Pair<>(segment, size);
        };
        return iterate(seed, nextSegment)
               .map(Pair::fst)
               .limit(1 + xs.size());
    }
    
    /**
     * Maps the function {@code f} over the list of pairs (x<sub>0</sub>, 
     * y<sub>0</sub>), (x<sub>1</sub>, y<sub>1</sub>), ... , (x<sub>m</sub>, 
     * y<sub>m</sub>) with x<sub>k</sub> in {@code xs} and y<sub>k</sub> in
     * {@code ys} and where {@code m} is the length of the shortest between
     * {@code xs} and {@code ys}.
     * For example (pseudo code): {@code zipWith((x,y) -> x + y, [a,b], [1,2,3])
     * = [a1, b2]}.
     * This is a terminal operation on the input streams.
     * @param f the function to map.
     * @param xs the list providing the left values.
     * @param ys the list providing the right values.
     * @return the "zipped" list.
     * @throws NullPointerException if any argument is {@code null}.
     */
    public static <X, Y, Z>
    Stream<Z> zipWith(BiFunction<X, Y, Z> f, Stream<X> xs, Stream<Y> ys) {
        requireNonNull(f, "f");
        requireNonNull(xs, "xs");
        requireNonNull(ys, "ys");
        
        Iterator<X> ix = xs.iterator();
        Iterator<Y> iy = ys.iterator();
        List<Z> zs = new ArrayList<Z>();
        
        while (ix.hasNext() && iy.hasNext()) {
            zs.add(f.apply(ix.next(), iy.next()));
        }
        
        return zs.stream();
    }
    
    /**
     * Pairs up the elements of two lists up to the length of the shortest of
     * the two.
     * For example (pseudo code): {@code zip([a,b], [1,2,3]) = [(a, 1), (b, 2)]}.
     * This is a terminal operation on the input streams.
     * @param xs the list providing the left values.
     * @param ys the list providing the right values.
     * @return the "zipped" list.
     * @throws NullPointerException if any argument is {@code null}.
     */
    public static <X, Y> Stream<Pair<X, Y>> zip(Stream<X> xs, Stream<Y> ys) {
        return zipWith(Pair<X,Y>::new, xs, ys);
    }
    
    /**
     * Applies the function {@code f} to each element of the stream.
     * The function {@code f} is called with the index (in {@code ys}) of the
     * element to map (first argument) and with the element itself (second
     * argument). That is: {@code map(f,[v,w,...]) = [f(0,v),f(1,w),...]}.
     * For example (pseudo code) if {@code f(i,x) = i+x} then 
     * {@code map(f,[1,2,3]) = [1,3,5]}.
     * @param f turns an index and a {@code Y} into a {@code Z}.
     * @param ys the list to map.
     * @return a new list with the mapped elements.
     * @throws NullPointerException if any argument is {@code null}.
     */
    public static <Y, Z>
    Stream<Z> map(BiFunction<Integer, Y, Z> f, Stream<Y> ys) {
        requireNonNull(f, "f");
        requireNonNull(ys, "ys");
        
        Iterator<Y> iy = ys.iterator();
        List<Z> zs = new ArrayList<Z>();
        
        int index = 0;
        while (iy.hasNext()) {
            zs.add(f.apply(index++, iy.next()));
        }
        
        return zs.stream();
    }
    // ya, could be done using zipWith (e.g. map f = zipWith f [0..]) but the
    // implementation is more complicated and has worse performance than the 
    // code above!
    
    /**
     * Breaks up the given text into lines.
     * @param text the input text.
     * @return the sequence of lines making up text.
     * @throws NullPointerException if {@code null} arguments.
     */
    public static Stream<String> lines(String text) {
        requireNonNull(text);
        
        Scanner reader = new Scanner(text);
        List<String> lines = new ArrayList<>();
        while (reader.hasNextLine()) {
            lines.add(reader.nextLine());
        }
        reader.close();
        
        return lines.stream();
    }
    
}